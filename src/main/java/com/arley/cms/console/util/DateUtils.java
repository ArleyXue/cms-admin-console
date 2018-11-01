package com.arley.cms.console.util;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;

/**
 * @author XueXianlei
 * @Description: JAVA 1.8 时间工具类
 * @date 2018/8/20 22:24
 */
public class DateUtils {

    // 缺省的日期时间显示格式: yyyy-MM-dd HH:mm:ss
    public static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    // 预定义日期格式1: yyyyMMddHHmmss
    public static final String DATE_PATTERN1 = "yyyyMMddHHmmss";
    // 预定义日期格式2: yyyy-MM-dd
    public static final String DATE_PATTERN2 = "yyyy-MM-dd";
    // 预定义日期格式3: yyyyMMdd
    public static final String DATE_PATTERN3 = "yyyyMMdd";
    // 预定义日期格式4: HH:mm:ss
    public static final String DATE_PATTERN4 = "HH:mm:ss";
    // 预定义日期格式5: hhmmss
    public static final String DATE_PATTERN5 = "HHmmss";


    /**
     * 获取当前日期 年月日
     * @return LocalDate
     */
    public static LocalDate getLocalDate() {
        return LocalDate.now();
    }

    /**
     * 获取当前日期时间 年月日时分秒
     * @return LocalDateTime
     */
    public static LocalDateTime getLocalDateTime() {
        return LocalDateTime.now();
    }

    /**
     * 获取当前时间 时分秒
     * @return LocalTime
     */
    public static LocalTime getLocalTime() {
        return LocalTime.now();
    }

    /**
     * 解析日期时间字符串 缺省格式 yyyy-MM-dd HH:mm:ss
     * @param dateTimeStr 日期时间字符串
     * @return LocalDateTime
     */
    public static LocalDateTime parseLocalDateTime(String dateTimeStr) {
        return parseLocalDateTime(dateTimeStr, null);
    }

    /**
     * 解析日期时间字符串
     * @param dateTimeStr 日期时间字符串
     * @param datePattern 日期时间格式 缺省格式 yyyy-MM-dd HH:mm:ss
     * @return LocalDateTime
     */
    public static LocalDateTime parseLocalDateTime(String dateTimeStr, String datePattern) {
        if (StringUtils.isBlank(datePattern)) {
            datePattern = DATE_PATTERN;
        }
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(datePattern));
    }

    /**
     * 解析日期字符串 缺省格式 yyyy-MM-dd
     * @param dateStr 日期字符串
     * @return LocalDate
     */
    public static LocalDate parseLocalDate(String dateStr) {
        return parseLocalDate(dateStr, null);
    }

    /**
     * 解析日期字符串
     * @param dateStr 日期字符串
     * @param datePattern 日期格式 缺省格式 yyyy-MM-dd
     * @return LocalDate
     */
    public static LocalDate parseLocalDate(String dateStr, String datePattern) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        if (StringUtils.isBlank(datePattern)) {
            datePattern = DATE_PATTERN2;
        }
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(datePattern));
    }

    /**
     * 解析时间字符串 缺省格式 HH:mm:ss
     * @param timeStr 时间字符串
     * @return LocalTime
     */
    public static LocalTime parseLocalTime(String timeStr) {
        return parseLocalTime(timeStr, null);
    }

    /**
     * 解析时间字符串
     * @param timeStr 时间字符串
     * @param datePattern 时间格式 缺省格式 HH:mm:ss
     * @return LocalTime
     */
    public static LocalTime parseLocalTime(String timeStr, String datePattern) {
        if (StringUtils.isBlank(datePattern)) {
            datePattern = DATE_PATTERN4;
        }
        return LocalTime.parse(timeStr, DateTimeFormatter.ofPattern(datePattern));
    }

    /**
     * 格式化日期时间 缺省格式:yyyy-MM-dd HH:mm:ss
     * @return String
     */
    public static String formatLocalDateTime() {
        return formatLocalDateTime(null, null);
    }

    /**
     * 格式化日期时间
     * @param datePattern 日期时间格式 缺省格式:yyyy-MM-dd HH:mm:ss
     * @return String
     */
    public static String formatLocalDateTime(String datePattern) {
        return formatLocalDateTime(null, datePattern);
    }

    /**
     * 格式化日期时间 缺省格式:yyyy-MM-dd HH:mm:ss
     * @param localDateTime 日期时间
     * @return String
     */
    public static String formatLocalDateTime(LocalDateTime localDateTime) {
        return formatLocalDateTime(localDateTime, null);
    }

    /**
     * 格式化日期时间
     * @param localDateTime 日期时间
     * @param datePattern 日期时间格式 缺省格式:yyyy-MM-dd HH:mm:ss
     * @return String
     */
    public static String formatLocalDateTime(LocalDateTime localDateTime, String datePattern) {
        if (StringUtils.isBlank(datePattern)) {
            datePattern = DATE_PATTERN;
        }
        if (null == localDateTime) {
            localDateTime = getLocalDateTime();
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern(datePattern);
        return df.format(localDateTime);
    }

    /**
     * 格式化日期 缺省格式:yyyy-MM-dd
     * @return String
     */
    public static String formatLocalDate() {
        return formatLocalDate(null, null);
    }

    /**
     * 格式化日期
     * @param datePattern 日期格式 缺省格式:yyyy-MM-dd
     * @return String
     */
    public static String formatLocalDate(String datePattern) {
        return formatLocalDate(null, datePattern);
    }

    /**
     * 格式化日期 缺省格式:yyyy-MM-dd
     * @param localDate 日期
     * @return String
     */
    public static String formatLocalDate(LocalDate localDate) {
        return formatLocalDate(localDate, null);
    }

    /**
     * 格式化日期
     * @param localDate 日期
     * @param datePattern 日期格式 缺省格式:yyyy-MM-dd
     * @return String
     */
    public static String formatLocalDate(LocalDate localDate, String datePattern) {
        if (StringUtils.isBlank(datePattern)) {
            datePattern = DATE_PATTERN2;
        }
        if (null == localDate) {
            localDate = getLocalDate();
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern(datePattern);
        return df.format(localDate);
    }

    /**
     * 格式化时间 缺省格式:HH:mm:ss
     * @return String
     */
    public static String formatLocalTime() {
        return formatLocalTime(null, null);
    }

    /**
     * 格式化时间
     * @param datePattern 时间格式 缺省格式:HH:mm:ss
     * @return String
     */
    public static String formatLocalTime(String datePattern) {
        return formatLocalTime(null, datePattern);
    }

    /**
     * 格式化时间 缺省格式:yyyy-MM-dd
     * @param localTime 时间
     * @return String
     */
    public static String formatLocalTime(LocalTime localTime) {
        return formatLocalTime(localTime, null);
    }

    /**
     * 格式化时间
     * @param localTime 时间
     * @param datePattern 时间格式 缺省格式:HH:mm:ss
     * @return String
     */
    public static String formatLocalTime(LocalTime localTime, String datePattern) {
        if (StringUtils.isBlank(datePattern)) {
            datePattern = DATE_PATTERN4;
        }
        if (null == localTime) {
            localTime = getLocalTime();
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern(datePattern);
        return df.format(localTime);
    }

    /**
     * 获取增加或减少指定小时的日期时间
     * @param hours 指定小时 正数为增加小时 负数为减少小时
     * @return LocalDateTime
     */
    public static LocalDateTime getLocalDateTimeByPlusOrMinusHours(int hours) {
        return getLocalDateTimeByPlusOrMinusHours(null, hours);
    }

    /**
     * 获取增加或减少指定小时的日期时间
     * @param localDateTime 指定日期时间
     * @param hours 指定小时 正数为增加小时 负数为减少小时
     * @return LocalDateTime
     */
    public static LocalDateTime getLocalDateTimeByPlusOrMinusHours(LocalDateTime localDateTime, int hours) {
        if (null == localDateTime) {
            localDateTime = getLocalDateTime();
        }
        if (hours >= 0) {
            return invoke(localDateTime, hours, LocalDateTime.class, "plusHours");
        }
        return invoke(localDateTime, -hours, LocalDateTime.class, "minusHours");
    }

    /**
     * 获取增加或减少指定天数的日期时间
     * @param days 指定天数 正数为增加天数 负数为减少天数
     * @return LocalDateTime
     */
    public static LocalDateTime getLocalDateTimeByPlusOrMinusDays(int days) {
        return getLocalDateTimeByPlusOrMinusDays(null, days);
    }

    /**
     * 获取增加或减少指定天数的日期时间
     * @param localDateTime 指定日期时间
     * @param days 指定天数 正数为增加天数 负数为减少天数
     * @return LocalDateTime
     */
    public static LocalDateTime getLocalDateTimeByPlusOrMinusDays(LocalDateTime localDateTime, int days) {
        if (null == localDateTime) {
            localDateTime = getLocalDateTime();
        }
        if (days >= 0) {
            return invoke(localDateTime, days, LocalDateTime.class, "plusDays");
        }
        return invoke(localDateTime, -days, LocalDateTime.class, "minusDays");
    }

    /**
     * 获取增加或减少指定月的日期时间
     * @param months 指定月 正数为增加月 负数为减少月
     * @return LocalDateTime
     */
    public static LocalDateTime getLocalDateTimeByPlusOrMinusMonths(int months) {
        return getLocalDateTimeByPlusOrMinusMonths(null, months);
    }

    /**
     * 获取增加或减少指定月的日期时间
     * @param localDateTime 指定日期时间
     * @param months 指定月 正数为增加月 负数为减少月
     * @return LocalDateTime
     */
    public static LocalDateTime getLocalDateTimeByPlusOrMinusMonths(LocalDateTime localDateTime, int months) {
        if (null == localDateTime) {
            localDateTime = getLocalDateTime();
        }
        if (months >= 0) {
            return invoke(localDateTime, months, LocalDateTime.class, "plusMonths");
        }
        return invoke(localDateTime, -months, LocalDateTime.class, "minusMonths");
    }

    /**
     * 获取增加或减少指定年的日期时间
     * @param years 指定年 正数为增加年 负数为减少年
     * @return LocalDateTime
     */
    public static LocalDateTime getLocalDateTimeByPlusOrMinusYears(int years) {
        return getLocalDateTimeByPlusOrMinusYears(null, years);
    }

    /**
     * 获取增加或减少指定年的日期时间
     * @param localDateTime 指定日期时间
     * @param years 指定年 正数为增加年 负数为减少年
     * @return LocalDateTime
     */
    public static LocalDateTime getLocalDateTimeByPlusOrMinusYears(LocalDateTime localDateTime, int years) {
        if (null == localDateTime) {
            localDateTime = getLocalDateTime();
        }
        if (years >= 0) {
            return invoke(localDateTime, years, LocalDateTime.class, "plusYears");
        }
        return invoke(localDateTime, -years, LocalDateTime.class, "minusYears");
    }

    /**
     * 获取增加或减少指定小时的时间
     * @param hours 指定小时 正数为增加小时 负数为减少小时
     * @return LocalTime
     */
    public static LocalTime getLocalTimeByPlusOrMinusHours(int hours) {
        return getLocalTimeByPlusOrMinusHours(null, hours);
    }

    /**
     * 获取增加或减少指定小时的时间
     * @param localTime 指定时间
     * @param hours 指定小时 正数为增加小时 负数为减少小时
     * @return LocalTime
     */
    public static LocalTime getLocalTimeByPlusOrMinusHours(LocalTime localTime, int hours) {
        if (null == localTime) {
            localTime = getLocalTime();
        }
        if (hours >= 0) {
            return invoke(localTime, hours, LocalTime.class, "plusHours");
        }
        return invoke(localTime, -hours, LocalTime.class, "minusHours");
    }

    /**
     * 获取增加或减少指定分钟的时间
     * @param minutes 指定分钟 正数为增加分钟 负数为减少分钟
     * @return LocalTime
     */
    public static LocalTime getLocalTimeByPlusOrMinusMinutes(int minutes) {
        return getLocalTimeByPlusOrMinusMinutes(null, minutes);
    }

    /**
     * 获取增加或减少指定分钟的时间
     * @param localTime 指定时间
     * @param minutes 指定分钟 正数为增加分钟 负数为减少分钟
     * @return LocalTime
     */
    public static LocalTime getLocalTimeByPlusOrMinusMinutes(LocalTime localTime, int minutes) {
        if (null == localTime) {
            localTime = getLocalTime();
        }
        if (minutes >= 0) {
            return invoke(localTime, minutes, LocalTime.class, "plusMinutes");
        }
        return invoke(localTime, -minutes, LocalTime.class, "minusMinutes");
    }

    /**
     * 获取增加或减少指定天数的日期
     * @param days 指定天数 正数为增加天数 负数为减少天数
     * @return LocalDate
     */
    public static LocalDate getLocalDateByPlusOrMinusDays(int days) {
        return getLocalDateByPlusOrMinusDays(null, days);
    }

    /**
     * 获取增加或减少指定天数的日期
     * @param localDate 指定日期
     * @param days 指定天数 正数为增加天数 负数为减少天数
     * @return LocalDate
     */
    public static LocalDate getLocalDateByPlusOrMinusDays(LocalDate localDate, int days) {
        if (null == localDate) {
            localDate = getLocalDate();
        }
        if (days >= 0) {
            return invoke(localDate, days, LocalDate.class, "plusDays");
        }
        return invoke(localDate, -days, LocalDate.class, "minusDays");
    }


    /**
     * 获取增加或减少指定月的日期
     * @param months 指定月 正数为增加月 负数为减少月
     * @return LocalDate
     */
    public static LocalDate getLocalDateByPlusOrMinusMonths(int months) {
        return getLocalDateByPlusOrMinusMonths(null, months);
    }

    /**
     * 获取增加或减少指定月的日期
     * @param localDate 指定日期
     * @param months 指定月 正数为增加月 负数为减少月
     * @return LocalDate
     */
    public static LocalDate getLocalDateByPlusOrMinusMonths(LocalDate localDate, int months) {
        if (null == localDate) {
            localDate = getLocalDate();
        }
        if (months >= 0) {
            return invoke(localDate, months, LocalDate.class, "plusMonths");
        }
        return invoke(localDate, -months, LocalDate.class, "minusMonths");
    }

    /**
     * 获取增加或减少指定年的日期
     * @param years 指定年 正数为增加年 负数为减少年
     * @return LocalDate
     */
    public static LocalDate getLocalDateByPlusOrMinusYears(int years) {
        return getLocalDateByPlusOrMinusYears(null, years);
    }

    /**
     * 获取增加或减少指定年的日期
     * @param localDate 指定日期
     * @param years 指定年 正数为增加年 负数为减少年
     * @return LocalDate
     */
    public static LocalDate getLocalDateByPlusOrMinusYears(LocalDate localDate, int years) {
        if (null == localDate) {
            localDate = getLocalDate();
        }
        if (years >= 0) {
            return invoke(localDate, years, LocalDate.class, "plusYears");
        }
        return invoke(localDate, -years, LocalDate.class, "minusYears");
    }

    /**
     * 获取增加或减少指定周的日期
     * @param weeks 指定周 正数为增加周 负数为减少周
     * @return LocalDate
     */
    public static LocalDate getLocalDateByPlusOrMinusWeeks(int weeks) {
        return getLocalDateByPlusOrMinusWeeks(null, weeks);
    }

    /**
     * 获取增加或减少指定周的日期
     * @param localDate 指定日期
     * @param weeks 指定周 正数为增加周 负数为减少周
     * @return LocalDate
     */
    public static LocalDate getLocalDateByPlusOrMinusWeeks(LocalDate localDate, int weeks) {
        if (null == localDate) {
            localDate = getLocalDate();
        }
        if (weeks >= 0) {
            return invoke(localDate, weeks, LocalDate.class, "plusWeeks");
        }
        return invoke(localDate, -weeks, LocalDate.class, "minusWeeks");
    }



    /**
     * 利用反射增加或减少指定时间的时间处理
     * @param temporalAccessor
     * @param params
     * @param clazz
     * @param methodName
     * @param <T>
     * @return
     */
    private static <T> T invoke(TemporalAccessor temporalAccessor, int params, Class<T> clazz, String methodName) {
        try {
            Method minusDays = clazz.getMethod(methodName, long.class);
            return  (T) minusDays.invoke(temporalAccessor, params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * LocalDate 转为 Date
     * @param localDate
     * @return
     */
    public static Date localDateToDate(LocalDate localDate) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(zoneId);
        return Date.from(zonedDateTime.toInstant());
    }


    public static void main(String[] args) {

        // 获取当前时间
        // System.out.println(getLocalDate());
        // System.out.println(getLocalDateTime());
        // System.out.println(getLocalTime());

        // 解析时间
        // System.out.println(parseLocalDate("2018-08-08"));
        // System.out.println(parseLocalDate("2018-08-08", DateUtil.DATE_PATTERN1));

        // 格式化时间
        // System.out.println(formatLocalDateTime());

        // 减少或增加时间
        // System.out.println(getLocalDateTimeByPlusOrMinusYears(5));
        // System.out.println(getLocalTimeByPlusOrMinusHours(5));
        // System.out.println(getLocalDateByPlusOrMinusWeeks(-1));

        // 转换
        System.out.println(localDateToDate(getLocalDate()));
        LocalDate localDate = getLocalDate();
        LocalDate localDate1 = parseLocalDate("2018-08-08");
        boolean before = localDate1.isBefore(localDate);
        System.out.println(before);

    }

    /**
     * 当天是本周第几天
     *
     * @param date
     * @return
     */
    public static int getWeekOfDate(Date date) {
        int[] weekDays = {7, 1, 2, 3, 4, 5, 6};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
}
