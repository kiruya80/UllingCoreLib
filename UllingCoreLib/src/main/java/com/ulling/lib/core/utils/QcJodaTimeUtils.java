package com.ulling.lib.core.utils;

import com.ulling.lib.core.common.QcDefine;
import java.util.Calendar;
import java.util.TimeZone;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * @Class: QcJodaTimeUtils
 * @Version 1.0.0
 * @Date 2019-04-29
 * <p>
 * Description:
 */
public class QcJodaTimeUtils {

    private static QcJodaTimeUtils SINGLE_U;

    public static synchronized QcJodaTimeUtils getInstance() {
        if (SINGLE_U == null) {
            SINGLE_U = new QcJodaTimeUtils();
        }

        return SINGLE_U;
    }

    public QcJodaTimeUtils() {
        init();
    }

    // [getDiffHours()]  == getDiffHours ========== 2019-10-26T09:36:52Z
//  [getDiffHours()]  == jodatime === 10월 26일 오전 9:36
//   [getDiffHours()]  == jodatime withZone === 10월 26일 오전 9:36
//   [getDiffHours()]  == now === 10월 26일 오후 3:10
//    [getDiffHours()]  == now withZone === 10월 26일 오후 3:10
//     [getDiffHours()]  == getDiffDate === now :2019-10-26T15:10:30.193Z , dateStr : 2019-10-26T09:36:52.000Z
//  [getDiffHours()]  == diffHours === 5
    private void init() {
        LocalDate today = LocalDate.now();
        QcLog.e("Joda Time today : " + today);
    }

    public static DateTimeZone getTimezone(boolean isUtc) {
        if (isUtc) {
            return DateTimeZone.forID(QcDefine.TIMEZONE_UTC);
        } else {
//            TimeZone tz = Calendar.getInstance().getTimeZone();
            TimeZone tz = TimeZone.getDefault();
            return DateTimeZone.forID(tz.getID());
//            return DateTimeZone.forID(QcDefine.TIMEZONE_SEOUL);
        }
    }

    public static String getCurrentTime(boolean isUtc, String pattern) {
        DateTime dateTime = getCurrentDateTime(isUtc);
        return dateTime.toString(pattern);
    }

    // 2019-04-29T14:42:10.095+09:00
    public static DateTime getCurrentDateTime(boolean isUtc) {
        DateTime dateTime = new DateTime(getTimezone(isUtc));
        return dateTime;
    }

    public static Calendar setCalendar(DateTime dateTime) {
        Calendar calendar = Calendar.getInstance();
//        dateTime = dateTime.plusDays(45).plusMonths(1).dayOfWeek().withMaximumValue();
//        System.out.println(dateTime.toString("yyyy-MM-dd E HH:mm:ss.SSS");
        calendar.setTime(dateTime.toDate());

        return calendar;
    }

    public static String convert(String dateTime, String fromPattern, String toPattern) {
//        String dateTime = "2013-11-15 08:12:34";
//        DateTimeFormatter dtf = DateTimeFormat.forPattern(fromPattern);
//        DateTime jodatime = dtf.parseDateTime(dateTime);
        DateTime jodatime = getDateTimeFromPattern(dateTime, fromPattern);

        DateTimeFormatter dtfOut = DateTimeFormat.forPattern(toPattern);
        return dtfOut.print(jodatime);
    }

    public static DateTime getDateTimeFromPattern(String dateTimeStr, String pattern) {
//        DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
//        DateTime dateTime = fmt.parseDateTime(dateTimeStr);
        DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
        DateTime dateTime = DateTime.parse(dateTimeStr, fmt);
        return dateTime;
    }

//    public static String getCurrentTime(boolean isUtc) {
//        return getCurrentTime(isUtc, QcDefine.DATE_FORMAT_UI_HHMMSS);
//    }

//    public static String getCurrentTime(boolean isUtc, String format) {
//        return getSimpleDateFormat(isUtc, format);
//    }
//
//    public static String getSimpleDateFormat(boolean isUtc, String format) {
//
////        DateTime dateTime = new DateTime(2014, 1, 1, 0, 0, 0, 0);
//////        dateTime.withSecondOfMinute(System.currentTimeMillis());
////        System.out.println(dateTime.plusDays(90).toString(format));
//
//        DateTime dateTime = new DateTime();
////        return dtfOut.print(jodatime);
//        return dateTime.toString(format);
//    }

//    public void shouldGetAfterOneDay() {
//        Chronology chrono = GregorianChronology.getInstance();
//        LocalDate theDay = new LocalDate(1582, 10, 4, chrono);
//        String pattern = "yyyy.MM.dd";
//        assertThat(theDay.toString(pattern)).isEqualTo("1582.10.04");
//
//        LocalDate nextDay = theDay.plusDays(1);
//        assertThat(nextDay.toString(pattern)).isEqualTo("1582.10.05");
//    }
//
//    public void shouldGetAfterOneDayWithGJChronology() {
//        Chronology chrono = GJChronology.getInstance();
//        LocalDate theDay = new LocalDate(1582, 10, 4, chrono);
//        String pattern = "yyyy.MM.dd";
//        assertThat(theDay.toString(pattern)).isEqualTo("1582.10.04");
//
//        LocalDate nextDay = theDay.plusDays(1);
//        assertThat(nextDay.toString(pattern)).isEqualTo("1582.10.15");
//    }
//
//    public void shouldGetAfterOneHour(String pattern ) {
////        String pattern = "yyyy.MM.dd HH:mm";
//
//        DateTimeZone seoul = DateTimeZone.forID("Asia/Seoul");
//        DateTime theTime = new DateTime(1988,5,7,23,0, seoul);
//
//        assertThat(theTime.toString(pattern)).isEqualTo("1988.05.07 23:00");
//        assertThat(seoul.isStandardOffset(theTime.getMillis())).isTrue();
//
//        DateTime after1Hour = theTime.plusHours(1);
//        assertThat(after1Hour.toString(pattern)).isEqualTo("1988.05.08 01:00");
//        assertThat(seoul.isStandardOffset(after1Hour.getMillis())).isFalse();
//    }
//
//    public void shouldGetAfterOneMinute() {
//        DateTimeZone seoul = DateTimeZone.forID("Asia/Seoul");
//        DateTime theTime = new DateTime(1961, 8, 9, 23, 59, seoul);
//        String pattern = "yyyy.MM.dd HH:mm";
//        assertThat(theTime.toString(pattern)).isEqualTo("1961.08.09 23:59");
//
//        DateTime after1Minute = theTime.plusMinutes(1);
//        assertThat(after1Minute.toString(pattern)).isEqualTo("1961.08.10 00:30");
//    }
//
//    public void shouldGetAfterTwoSecond() {
//        DateTimeZone utc = DateTimeZone.forID("UTC");
//        DateTime theTime = new DateTime(2012, 6, 30, 23, 59, 59, utc);
//        String pattern = "yyyy.MM.dd HH:mm:ss";
//        assertThat(theTime.toString(pattern)).isEqualTo("2012.06.30 23:59:59");
//
//        DateTime after2Seconds = theTime.plusSeconds(2);
//        assertThat(after2Seconds.toString(pattern)).isEqualTo("2012.07.01 00:00:01");
//    }
//
//    public void shouldGetDate() {
//        LocalDate theDay = new LocalDate(1999, 12, 31);
//
//        assertThat(theDay.getYear()).isEqualTo(1999);
//        assertThat(theDay.getMonthOfYear()).isEqualTo(12);
//        assertThat(theDay.getDayOfMonth()).isEqualTo(31);
//    }
//
//    public void shouldNotAcceptWrongMonth() {
//        new LocalDate(1999, 13, 31);
//    }
//
//    public void shouldGetDayOfWeek() {
//        LocalDate theDay = new LocalDate(2014, 1, 1);
//
//        int dayOfWeek = theDay.getDayOfWeek();
//        assertThat(dayOfWeek).isEqualTo(DateTimeConstants.WEDNESDAY);
//        assertThat(dayOfWeek).isEqualTo(3);
//    }
}
