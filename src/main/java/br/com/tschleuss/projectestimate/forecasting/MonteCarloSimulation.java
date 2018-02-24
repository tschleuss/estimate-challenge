package br.com.tschleuss.projectestimate.forecasting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class to do forecasting using the Monte Carlo simulations.
 *
 * @author tschleuss
 *
 */
public class MonteCarloSimulation {

    /**
     * That method tries to predict a percentage of success for the project to be conclude in a fixed number of sprints (weeks).
     * We run a parameterized number of iterations. For each iteration we run a parameterized number of weeks trying to simulate the chances.
     * Each sprint (week) we randomically pick a number of tasks that our team did and we pick a number of new tasks that can be introduced in
     * our current sprint (backlog growth rate).
     * <br/>
     * <br/>
     * With that values we have an output that are our current number of tasks to do plus a backlog possible change minus a total of tasks that our
     * team did. In the end we retrieve all values that successfully ended our tasks in that sprint to calculate the percentage.
     *
     * @param iterations Number of iterations that the algorithm will do.
     * @param weeks Number of weeks that the algorithm will simulate.
     * @param todoTasks Initial number of tasks to do in the sprint.
     * @param throughput List with quantity of tasks that the team did in last sprints.
     * @param backlogGrowthRate List with a grwth rate of the backlog in last sprints.
     * @return A percentage of success for the project to be conclude
     */
    public static double run(final int iterations, final int weeks, final int todoTasks, final List<Integer> throughput,
            final List<Integer> backlogGrowthRate) {

        final Map<Integer, Integer> results = new HashMap<>();
        for (int sample = 0; sample < iterations; sample++) {
            int throughputDoneSum = 0;
            int backlogIncreaseSum = 0;
            for (int week = 0; week < weeks; week++) {
                final int throughputIdx = ThreadLocalRandom.current().nextInt(throughput.size());
                final int throughputDone = throughput.get(throughputIdx);
                throughputDoneSum += throughputDone;
                final int backlogIdx = ThreadLocalRandom.current().nextInt(backlogGrowthRate.size());
                final int backlogIncrease = backlogGrowthRate.get(backlogIdx);
                backlogIncreaseSum += backlogIncrease;
            }
            final int activityResult = (todoTasks + backlogIncreaseSum) - throughputDoneSum;
            results.compute(activityResult, (k, v) -> v == null ? 1 : v + 1);
        }

        double sum = results.entrySet().stream().filter(entry -> entry.getKey() <= 0).map(entry -> entry.getValue()).mapToInt(v -> v).sum();
        double percent = (sum * 100)/iterations;
        return percent;
    }
}
