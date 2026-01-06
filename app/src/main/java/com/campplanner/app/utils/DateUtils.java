//==================== DateUtils.java ====================
package com.campplanner.app.utils;

import com.campplanner.app.utils.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DateUtils {

    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.FRANCE);
        return sdf.format(new Date());
    }

    public static String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATETIME_FORMAT, Locale.FRANCE);
        return sdf.format(new Date());
    }

    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.TIME_FORMAT, Locale.FRANCE);
        return sdf.format(new Date());
    }

    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.FRANCE);
        return sdf.format(date);
    }

    public static Date parseDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.FRANCE);
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static long getDaysBetween(String startDate, String endDate) {
        Date start = parseDate(startDate);
        Date end = parseDate(endDate);

        if (start == null || end == null) {
            return 0;
        }

        long diffInMillis = end.getTime() - start.getTime();
        return TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
    }

    public static boolean isDateInFuture(String dateString) {
        Date date = parseDate(dateString);
        if (date == null) return false;
        return date.after(new Date());
    }

    public static boolean isDateInPast(String dateString) {
        Date date = parseDate(dateString);
        if (date == null) return false;
        return date.before(new Date());
    }

    public static boolean isDateToday(String dateString) {
        Date date = parseDate(dateString);
        if (date == null) return false;

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(new Date());

        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    public static String getRelativeDate(String dateString) {
        Date date = parseDate(dateString);
        if (date == null) return dateString;

        Date now = new Date();
        long diffInMillis = date.getTime() - now.getTime();
        long days = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);

        if (days == 0) {
            return "Aujourd'hui";
        } else if (days == 1) {
            return "Demain";
        } else if (days == -1) {
            return "Hier";
        } else if (days > 1 && days <= 7) {
            return "Dans " + days + " jours";
        } else if (days < -1 && days >= -7) {
            return "Il y a " + Math.abs(days) + " jours";
        } else {
            return dateString;
        }
    }

    public static String addDays(String dateString, int days) {
        Date date = parseDate(dateString);
        if (date == null) return dateString;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, days);

        return formatDate(calendar.getTime());
    }

    public static boolean isValidDate(String dateString) {
        return parseDate(dateString) != null;
    }

    public static int getDaysUntil(String dateString) {
        Date date = parseDate(dateString);
        if (date == null) return 0;

        Date now = new Date();
        long diffInMillis = date.getTime() - now.getTime();
        return (int) TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
    }

    public static String getMonthName(int month) {
        String[] months = {
                "Janvier", "Février", "Mars", "Avril", "Mai", "Juin",
                "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"
        };

        if (month >= 0 && month < 12) {
            return months[month];
        }
        return "";
    }

    public static String getDayOfWeek(String dateString) {
        Date date = parseDate(dateString);
        if (date == null) return "";

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        String[] days = {
                "Dimanche", "Lundi", "Mardi", "Mercredi",
                "Jeudi", "Vendredi", "Samedi"
        };

        return days[calendar.get(Calendar.DAY_OF_WEEK) - 1];
    }
}