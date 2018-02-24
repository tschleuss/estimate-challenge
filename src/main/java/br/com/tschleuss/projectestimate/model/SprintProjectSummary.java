package br.com.tschleuss.projectestimate.model;

/**
 * Project Summary model with basic attributes.<br/>
 * We extend {@code Sprint} because we need their attributes to.
 *
 * @author tschleuss
 *
 */
public class SprintProjectSummary extends Sprint {

    private int todo;
    private int inProgress;
    private int done;
    private int total;

    /**
     * @return the todo
     */
    public final int getTodo() {
        return todo;
    }

    /**
     * @param todo the todo to set
     */
    public final void setTodo(int todo) {
        this.todo = todo;
    }

    /**
     * @return the inProgress
     */
    public final int getInProgress() {
        return inProgress;
    }

    /**
     * @param inProgress the inProgress to set
     */
    public final void setInProgress(int inProgress) {
        this.inProgress = inProgress;
    }

    /**
     * @return the done
     */
    public final int getDone() {
        return done;
    }

    /**
     * @param done the done to set
     */
    public final void setDone(int done) {
        this.done = done;
    }

    /**
     * @return the total
     */
    public final int getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public final void setTotal(int total) {
        this.total = total;
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
        sb.append("todo: ");
        sb.append(getTodo());
        sb.append("\n");
        sb.append("inProgress: ");
        sb.append(getInProgress());
        sb.append("\n");
        sb.append("done: ");
        sb.append(getDone());
        sb.append("\n");
        sb.append("total: ");
        sb.append(getTotal());
        sb.append("\n");
        return sb.toString();
    }
}
