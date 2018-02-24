package br.com.tschleuss.projectestimate.helper;

import java.text.SimpleDateFormat;

/**
 * Classic date helper that all projects need.
 *
 * @author tschleuss
 *
 */
public class DateHelper {

    /**
     * Default pattern used in the json file.
     * We use this to parse date string in {@code Date} object.
     */
    public final static SimpleDateFormat DEFAULT_PATTERN = new SimpleDateFormat("yyyy-MM-dd");
}
