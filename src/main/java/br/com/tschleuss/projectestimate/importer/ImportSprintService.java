package br.com.tschleuss.projectestimate.importer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.tschleuss.projectestimate.helper.DateHelper;
import br.com.tschleuss.projectestimate.model.Sprint;
import br.com.tschleuss.projectestimate.model.SprintProjectSummary;
import br.com.tschleuss.projectestimate.model.SprintTeamDelivery;

/**
 * Class responsible for importing and parse json sample files and output that data in business models.
 *
 * @author tschleuss
 *
 */
public class ImportSprintService {

    /**
     * Read a json file and build {@code SprintProjectSummary} bussiness models.
     *
     * @param file File with sprint project summary data.
     * @return Map with all {@code SprintProjectSummary}. The key is sprint's last day timestamp.
     *
     * @throws FileNotFoundException File was not found.
     * @throws IOException Error reading the file.
     * @throws JSONException Error reading json data.
     * @throws ParseException Error parsing json data.
     */
    public final Map<Long, SprintProjectSummary> readSprintProjectSummary(final File file)
            throws FileNotFoundException, IOException, JSONException, ParseException {

        final JSONObject dataset = readJSON(file);
        final JSONArray weeks = dataset.getJSONArray("weeks");
        final Map<Long, SprintProjectSummary> summaries = new HashMap<>();

        for (int i = 0; i < weeks.length(); i++) {
            final JSONObject sprint = weeks.getJSONObject(i);
            final SprintProjectSummary summary = new SprintProjectSummary();
            createSprintProjectSummary(summary, sprint);
            summaries.put(summary.getLastDayWeekTimestamp(), summary);
        }

        return summaries;
    }

    /**
     * Read a json file and build {@code SprintTeamDelivery} bussiness models.
     *
     * @param file File with sprint project summary data.
     * @return Map with all {@code SprintTeamDelivery}. The key is sprint's last day timestamp.
     *
     * @throws FileNotFoundException File was not found.
     * @throws IOException Error reading the file.
     * @throws JSONException Error reading json data.
     * @throws ParseException Error parsing json data.
     */
    public final Map<Long, SprintTeamDelivery> readSprintTeamDelivery(final File file)
            throws FileNotFoundException, IOException, JSONException, ParseException {

        final JSONObject dataset = readJSON(file);
        final JSONArray weeks = dataset.getJSONArray("weeks");
        final Map<Long, SprintTeamDelivery> deliveries = new HashMap<>();

        for (int i = 0; i < weeks.length(); i++) {
            final JSONObject sprint = weeks.getJSONObject(i);
            final SprintTeamDelivery delivery = new SprintTeamDelivery();
            createSprintTeamDelivery(delivery, sprint);
            deliveries.put(delivery.getLastDayWeekTimestamp(), delivery);
        }

        return deliveries;
    }

    /**
     * Read a file and convert into {@code JSONObject}.
     *
     * @param file File with json data.
     * @return {@code JSONObject} object.
     * @throws FileNotFoundException File was not found.
     * @throws IOException Error reading the file.
     */
    private final JSONObject readJSON(final File file) throws FileNotFoundException, IOException {
        try (final BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            final StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return new JSONObject(sb.toString());
        }
    }

    /**
     * Create a {@code Sprint} model from a {@code JSONObject} object.
     *
     * @param sprint Empty {@code Sprint} model to be loaded.
     * @param object {@code JSONObject} object to be readed.
     * @throws JSONException Error reading json data.
     * @throws ParseException Error parsing json data.
     */
    private final void createSprint(final Sprint sprint, final JSONObject object) throws JSONException, ParseException {

        final String FIELD_WEEK = "week".intern();
        final String FIELD_FIRST_DAY_WEEK = "firstDayWeek".intern();
        final String FIELD_LAST_DAY_WEEK = "lastDayWeek".intern();
        final String FIELD_FIRST_DAY_WEEK_TIMESTAMP = "firstDayWeekTimestamp".intern();
        final String FIELD_LAST_DAY_WEEK_TIMESTAMP = "lastDayWeekTimestamp".intern();

        sprint.setWeek(DateHelper.DEFAULT_PATTERN.parse(object.getString(FIELD_WEEK)));
        sprint.setFirstDayWeek(DateHelper.DEFAULT_PATTERN.parse(object.getString(FIELD_FIRST_DAY_WEEK)));
        sprint.setLastDayWeek(DateHelper.DEFAULT_PATTERN.parse(object.getString(FIELD_LAST_DAY_WEEK)));
        sprint.setFirstDayWeekTimestamp(object.getLong(FIELD_FIRST_DAY_WEEK_TIMESTAMP));
        sprint.setLastDayWeekTimestamp(object.getLong(FIELD_LAST_DAY_WEEK_TIMESTAMP));
    }

    /**
     * Create a {@code SprintProjectSummary} model from a {@code JSONObject} object.
     *
     * @param sprint Empty {@code SprintProjectSummary} model to be loaded.
     * @param object {@code JSONObject} object to be readed.
     * @throws JSONException Error reading json data.
     * @throws ParseException Error parsing json data.
     */
    private final void createSprintProjectSummary(final SprintProjectSummary sprint, final JSONObject object) throws JSONException, ParseException {

        final String FIELD_TODO = "todo".intern();
        final String FIELD_TOTAL = "total".intern();
        final String FIELD_IN_PROGRESS = "inProgress".intern();
        final String FIELD_DONE = "done".intern();

        createSprint(sprint, object);

        sprint.setTodo(object.getInt(FIELD_TODO));
        sprint.setTotal(object.getInt(FIELD_TOTAL));
        sprint.setInProgress(object.getInt(FIELD_IN_PROGRESS));
        sprint.setDone(object.getInt(FIELD_DONE));
    }

    /**
     * Create a {@code SprintTeamDelivery} model from a {@code JSONObject} object.
     *
     * @param sprint Empty {@code SprintTeamDelivery} model to be loaded.
     * @param object {@code JSONObject} object to be readed.
     * @throws JSONException Error reading json data.
     * @throws ParseException Error parsing json data.
     */
    private final void createSprintTeamDelivery(final SprintTeamDelivery sprint, final JSONObject object) throws JSONException, ParseException {
        final String FIELD_TASKS = "tasks".intern();
        createSprint(sprint, object);
        sprint.setTasks(object.getInt(FIELD_TASKS));
    }
}
