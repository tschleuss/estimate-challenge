package br.com.tschleuss.projectestimate.model;

import java.util.Date;

/**
 * Sprint model with basic attributes.
 *
 * @author tschleuss
 *
 */
public class Sprint {

    private long firstDayWeekTimestamp;
    private long lastDayWeekTimestamp;
    private Date firstDayWeek;
    private Date lastDayWeek;
    private Date week;

    /**
     * @return the firstDayWeekTimestamp
     */
    public final long getFirstDayWeekTimestamp() {
        return firstDayWeekTimestamp;
    }

    /**
     * @param firstDayWeekTimestamp the firstDayWeekTimestamp to set
     */
    public final void setFirstDayWeekTimestamp(long firstDayWeekTimestamp) {
        this.firstDayWeekTimestamp = firstDayWeekTimestamp;
    }

    /**
     * @return the lastDayWeekTimestamp
     */
    public final long getLastDayWeekTimestamp() {
        return lastDayWeekTimestamp;
    }

    /**
     * @param lastDayWeekTimestamp the lastDayWeekTimestamp to set
     */
    public final void setLastDayWeekTimestamp(long lastDayWeekTimestamp) {
        this.lastDayWeekTimestamp = lastDayWeekTimestamp;
    }

    /**
     * @return the firstDayWeek
     */
    public final Date getFirstDayWeek() {
        return firstDayWeek;
    }

    /**
     * @param firstDayWeek the firstDayWeek to set
     */
    public final void setFirstDayWeek(Date firstDayWeek) {
        this.firstDayWeek = firstDayWeek;
    }

    /**
     * @return the lastDayWeek
     */
    public final Date getLastDayWeek() {
        return lastDayWeek;
    }

    /**
     * @param lastDayWeek the lastDayWeek to set
     */
    public final void setLastDayWeek(Date lastDayWeek) {
        this.lastDayWeek = lastDayWeek;
    }

    /**
     * @return the week
     */
    public final Date getWeek() {
        return week;
    }

    /**
     * @param week the week to set
     */
    public final void setWeek(Date week) {
        this.week = week;
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
        return sb.toString();
    }
}
