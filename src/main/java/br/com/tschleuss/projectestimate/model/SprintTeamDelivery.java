package br.com.tschleuss.projectestimate.model;

/**
 * Team Delivery model with basic attributes.<br/>
 * We extend {@code Sprint} because we need their attributes to.
 *
 * @author tschleuss
 *
 */
public class SprintTeamDelivery extends Sprint {

    private int tasks;

    /**
     * @return the tasks
     */
    public final int getTasks() {
        return tasks;
    }

    /**
     * @param tasks the tasks to set
     */
    public final void setTasks(int tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("week: ");
        sb.append(getWeek());
        sb.append("\n");
        sb.append("firstDayWeek: ");
        sb.append(getFirstDayWeek());
        sb.append("\n");
        sb.append("lastDayWeek: ");
        sb.append(getLastDayWeek());
        sb.append("\n");
        sb.append("firstDayWeekTimestamp: ");
        sb.append(getFirstDayWeekTimestamp());
        sb.append("\n");
        sb.append("lastDayWeekTimestamp: ");
        sb.append(getLastDayWeekTimestamp());
        sb.append("\n");
        sb.append("taks: ");
        sb.append(getTasks());
        sb.append("\n");
        return sb.toString();
    }
}
