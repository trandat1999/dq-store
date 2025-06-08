package com.thd.base.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static Date atStartOfDay(Date date) {
        if (date == null) {
            return null;
        }
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return localDateTimeToDate(startOfDay);
    }

    public static Date atEndOfDay(Date date) {
        if (date == null) {
            return null;
        }
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return localDateTimeToDate(endOfDay);
    }

    public static Date atStartOfYear(Integer year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.DAY_OF_YEAR, 1);
        return atStartOfDay(cal.getTime());
    }

    public static Date atEndOfYear(Integer year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, 11);
        cal.set(Calendar.DAY_OF_MONTH, 31);
        return atEndOfDay(cal.getTime());
    }

    public static LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date minusDate(Date date, int year, int month, int day, int hour, int minute, int second) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        localDateTime = localDateTime.minusYears(year);
        localDateTime = localDateTime.minusMonths(month);
        localDateTime = localDateTime.minusDays(day);
        localDateTime = localDateTime.minusHours(hour);
        localDateTime = localDateTime.minusMinutes(minute);
        localDateTime = localDateTime.minusSeconds(second);
        return localDateTimeToDate(localDateTime);
    }

    public static Date plusDate(Date date, int year, int month, int day, int hour, int minute, int second) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        localDateTime = localDateTime.plusYears(year);
        localDateTime = localDateTime.plusMonths(month);
        localDateTime = localDateTime.plusDays(day);
        localDateTime = localDateTime.plusHours(hour);
        localDateTime = localDateTime.plusMinutes(minute);
        localDateTime = localDateTime.plusSeconds(second);
        return localDateTimeToDate(localDateTime);
    }

    public static Date plusMinute(Date date, int minute) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        localDateTime = localDateTime.plusMinutes(minute);
        return localDateTimeToDate(localDateTime);
    }
    public static Date plusHours(Date date, int value) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        localDateTime = localDateTime.plusHours(value);
        return localDateTimeToDate(localDateTime);
    }
    public static Date plusDays(Date date, int value) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        localDateTime = localDateTime.plusDays(value);
        return localDateTimeToDate(localDateTime);
    }
    public static Date plusMonths(Date date, int value) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        localDateTime = localDateTime.plusMonths(value);
        return localDateTimeToDate(localDateTime);
    }
    public static Date plusYears(Date date, int value) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        localDateTime = localDateTime.plusYears(value);
        return localDateTimeToDate(localDateTime);
    }

    public static String formatDate(Date date, String format) {
        if (date == null || format == null || format.isEmpty()) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return dateToLocalDateTime(date).format(formatter);
    }


    public static int maxDay(int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, month - 1);
        cal.set(Calendar.YEAR, year);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
}
