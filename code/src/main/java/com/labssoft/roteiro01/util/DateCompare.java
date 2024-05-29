package com.labssoft.roteiro01.util;

import java.util.Calendar;
import java.util.Date;

import com.labssoft.roteiro01.config.Generated;

public class DateCompare {

    @Generated
    private DateCompare() {
    }

    private static Calendar truncateTime(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public static boolean isAtLeastToday(Date date) {
        Calendar currentDate = truncateTime(Calendar.getInstance());
        Calendar dueDate = truncateTime(Calendar.getInstance());
        dueDate.setTime(date);

        return dueDate.compareTo(currentDate) >= 0;
    }

    public static Date addDays(Date date, Integer dueDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, dueDays);
        return calendar.getTime();
    }

    public static boolean isPastDueDate(Date dueDate) {
        Calendar currentDate = truncateTime(Calendar.getInstance());
        Calendar dueDateCalendar = truncateTime(Calendar.getInstance());
        dueDateCalendar.setTime(dueDate);

        return dueDateCalendar.compareTo(currentDate) < 0;
    }

    public static Integer daysPastDueDate(Date dueDate) {
        Calendar currentDate = truncateTime(Calendar.getInstance());
        Calendar dueDateCalendar = truncateTime(Calendar.getInstance());
        dueDateCalendar.setTime(dueDate);

        return (int) ((currentDate.getTimeInMillis() - dueDateCalendar.getTimeInMillis()) / (1000 * 60 * 60 * 24));
    }
}
