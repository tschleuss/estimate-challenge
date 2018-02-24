package br.com.tschleuss.projectestimate;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONException;

import br.com.tschleuss.projectestimate.forecasting.MonteCarloSimulation;
import br.com.tschleuss.projectestimate.importer.ImportSprintService;
import br.com.tschleuss.projectestimate.model.SprintProjectSummary;
import br.com.tschleuss.projectestimate.model.SprintTeamDelivery;

/**
 * <strong>Problem:</strong>
 * <br/>
 * <br/>
 * Consider a team that develops software using agile methodologies and has a history of deliveries according to the throughput.json file.
 * In the cumulative_flow_diagram.json file it is possible to follow the project's evolution over the same period.
 * Based on this information, develop an algorithm to answer the following question:
 * <br/>
 * <br/>
 * <strong>What is the project completion estimate and the reliability of this information?<strong>
 *
 * @author tschleuss
 */
public class Main {

    private static final String FILE_THROUGHPUT = "throughput.json";
    private static final String FILE_CUMULATIVE = "cumulative_flow_diagram.json";

    /**
     * Rune the main program.
     *
     * @param args
     * @throws URISyntaxException
     */
    public static void main(String[] args) {

        try {

            // Loading the file in resources folder.
    			final URL teamDeliveryFilePath = Main.class.getResource(FILE_THROUGHPUT);
    			final URL projectSummaryFilePath = Main.class.getResource(FILE_CUMULATIVE);
    		
            final File teamDeliveryJsonFile = new File(teamDeliveryFilePath.toURI());
            final File projectSummaryJsonFile = new File(projectSummaryFilePath.toURI());

            // Reading and converting the data to our bussiness model.
            final ImportSprintService sprintImporter = new ImportSprintService();
            final Map<Long, SprintTeamDelivery> teamDeliveries = sprintImporter.readSprintTeamDelivery(teamDeliveryJsonFile);
            final Map<Long, SprintProjectSummary> projectSummaries = sprintImporter.readSprintProjectSummary(projectSummaryJsonFile);

            // Will store all date that we need to run the simulation.
            // TODO We probably should only use last X samples of data of last sprints to have a more accurate data.
            final List<Integer> throughputs = new ArrayList<>(teamDeliveries.size());
            final List<Integer> backlogGrowthRates = new ArrayList<>(projectSummaries.size());

            // We order the keys (sprint's last day timestamp) of our maps in ascending order, to have a cronological sprint sequence.
            final Comparator<Long> sprintComparator = Comparator.comparingLong(v -> v);
            final List<Long> weekTimestamps = projectSummaries.keySet().stream().sorted(sprintComparator).collect(Collectors.toList());

            // Here we build or backlog growth rate calculating the diff between our current sprint and the last sprint.
            for (int i = 0; i < weekTimestamps.size(); i++) {
                final Long timestamp = weekTimestamps.get(i);
                final SprintProjectSummary lastProjectSummary = (i == 0) ? null : projectSummaries.get(weekTimestamps.get(i - 1));
                final SprintProjectSummary currProjectSummary = projectSummaries.get(timestamp);
                final SprintTeamDelivery teamDelivery = teamDeliveries.get(timestamp);
                final int backlogGrowthRate = (i == 0) ? 0 : (lastProjectSummary.getTodo() - currProjectSummary.getTodo()) * -1;
                if (teamDelivery != null) {
                    throughputs.add(teamDelivery.getTasks());
                }
                backlogGrowthRates.add(backlogGrowthRate);
            }

            final Long lastSprintKey = projectSummaries.keySet().stream().sorted(sprintComparator.reversed()).findFirst().get();
            final int todoTasks = projectSummaries.get(lastSprintKey).getTodo(); // get the value of todo tasks from last sprint.
            final int iterations = 5000; // We run 5000 simulations with monte carlo.

            int weeks = 1; // Our weeks counter.
            double chances = 0; // Our chances counter.

            do {
                chances = MonteCarloSimulation.run(iterations, weeks, todoTasks, throughputs, backlogGrowthRates);
                System.out.printf("Weeks: %s - %.2f%% \n", weeks, chances);
                weeks++;
            } while (chances < 100);

        } catch (IOException e) {
            System.err.println("Problem reading json file.");
            e.printStackTrace();
        } catch (ParseException | JSONException e) {
            System.err.println("Problem parsing json file.");
            e.printStackTrace();
        } catch (URISyntaxException e) {
            System.err.println("Problem finding json file.");
            e.printStackTrace();
		}
    }
}
