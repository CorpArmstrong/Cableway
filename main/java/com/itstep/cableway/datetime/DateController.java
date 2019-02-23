package com.itstep.cableway.datetime;

import java.util.Calendar;

public class DateController {

    public static int currentYear;
    public static int currentMonth;
    public static int currentDay;

    public static int timeHours;
    public static int timeMinutes;
    public static int timeSeconds;
    public static int timeMillis;

    public static String today;
    public static String time;
    public static String date;

    public final static String[] daysOfWeek = new String[] { "Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс" };

    public static String date() {
        StringBuffer dateBuffer = new StringBuffer();
        dateBuffer.append(currentDay).append(".").append(currentMonth).append(".").append(currentYear);

        return (date = dateBuffer.toString());
    }

    public static String now() {
        getSystemTime();

        StringBuffer nowBuffer = new StringBuffer();
        nowBuffer.append(currentDay).append(".").append(currentMonth).append(".").append(currentYear).append(" ").append(time());

        return (today = nowBuffer.toString());
    }

    public static String time() {
        StringBuffer timeBuffer = new StringBuffer();
        timeBuffer.append(timeHours).append(":").append(timeMinutes).append(":").append(timeSeconds);

        return (time = timeBuffer.toString());
    }

    public static void getSystemTime() {

        Calendar date = Calendar.getInstance();

        currentYear = date.get(Calendar.YEAR);
        currentMonth = date.get(Calendar.MONTH); // Note: zero based!
        currentDay = date.get(Calendar.DAY_OF_MONTH);

        timeHours = date.get(Calendar.HOUR_OF_DAY);
        timeMinutes = date.get(Calendar.MINUTE);
        timeSeconds = date.get(Calendar.SECOND);
        timeMillis = date.get(Calendar.MILLISECOND);
    }
}
