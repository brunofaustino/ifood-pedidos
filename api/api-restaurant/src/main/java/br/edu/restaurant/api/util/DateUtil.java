package br.edu.restaurant.api.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    /**
     * Set custom milliseconds, seconds, minutes and hours from the provided date
     *
     * @param date Date
     * @param hour hour
     * @param minute Minute
     * @param second Second
     *
     * @return Date with custom time
     */
    public static Date setCustomTime(Date date, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.HOUR, hour);

        return calendar.getTime();
    }
}
