package com.ulling.lib.core.utils;

import com.ulling.lib.core.common.QcDefine;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Class: QcDateUtils Created by KILHO on 03/04/2019 8:15 PM
 *
 * @version 1.0.0
 * <p>
 * Description: 날짜 유틸
 **/
public class QcDateUtils {

    public static String getCurrentTime() {
        return getCurrentTime(QcDefine.DATE_FORMAT_UI_HHMMSS);
    }

    public static String getCurrentTime(String format) {
        return getSimpleDateFormat(format, new Date());
    }

    public static String getSimpleDateFormat(String format, Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * local time to UTC time
     *
     * @Class: DateUtil
     * @Version 1.0.0
     * @Date 2019-04-03
     * <p>
     * Description:
     * <p>
     * ㄴ utc 시간 계산하기
     */
    public static String localtimeToUTC(String localTime, String pattern) throws Exception {
        String utcTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        try {
            Date parseDate = sdf.parse(localTime);
            utcTime = localtimeToUTC(parseDate, pattern);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return utcTime;
    }

    public static String localtimeToUTC(Date localTime, String pattern) throws Exception {
        String utcTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        TimeZone tz = TimeZone.getDefault();

        try {
            long milliseconds = localTime.getTime();
            int offset = tz.getOffset(milliseconds);
            utcTime = sdf.format(milliseconds - offset);
            utcTime = utcTime.replace("+0000", "");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }

        return utcTime;
    }

    /**
     * @Class: DateUtil
     * @Version 1.0.0
     * @Date 2019-04-03
     *
     * Description: utc 시간으로 변경 후 포맷 변경하기
     */
//    public static String localtimeToUTC(String localTime, String fromPattern, String toPattern)
//            throws Exception {
//        String newDate = "";
//        String utcTime = localtimeToUTC(localTime, fromPattern);
//
//        if (utcTime != null && !"".equals(utcTime)) {
//            newDate = utcTime;
//            SimpleDateFormat qrDateFormat = new SimpleDateFormat(fromPattern);
//            SimpleDateFormat blListFormat = new SimpleDateFormat(toPattern);
//
//            try {
//                Date qrDate = qrDateFormat.parse(utcTime);
//                newDate = blListFormat.format(qrDate);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return newDate;
//    }


    /**
     * UTC time   to local time
     *
     * @Class: DateUtil
     * @Version 1.0.0
     * @Date 2019-04-03
     * <p>
     * Description:
     */
    public static String utcToLocaltime(String utcTime, String pattern) throws Exception {
        String localTime = null;
        //TimeZone tz = TimeZone.getTimeZone("GMT+08:00"); 해당 국가 일시 확인 할 때, 한국은 +9
        TimeZone tz = TimeZone.getDefault();
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

//        try {
//            Date parseDate = sdf.parse(utcTime);
//            long milliseconds = parseDate.getTime();
//            int offset = tz.getOffset(milliseconds);
//            localTime = sdf.format(milliseconds + offset);
//            localTime = localTime.replace("+0000", "");
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new Exception(e);
//        }

        try {
            Date parseDate = sdf.parse(utcTime);
            localTime = utcToLocaltime(parseDate, pattern);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return localTime;
    }

    public static String utcToLocaltime(Date utcTime, String pattern) throws Exception {
        String localTime = null;
        TimeZone tz = TimeZone.getDefault();
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        try {
            long milliseconds = utcTime.getTime();
            int offset = tz.getOffset(milliseconds);
            localTime = sdf.format(milliseconds + offset);
            localTime = localTime.replace("+0000", "");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }

        return localTime;
    }


    /**
     * convertToDate
     */
    public static String convertToDate(String inputDate, String fromPattern, String toPattern)
        throws Exception {
        String dateTime = inputDate;
//        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.US);
        SimpleDateFormat format = new SimpleDateFormat(fromPattern);
        Date parseDate = null;
        String convertedDate = null;

        try {
            parseDate = format.parse(dateTime);
            DateFormat dateFormatNeeded = new SimpleDateFormat(toPattern);
            convertedDate = dateFormatNeeded.format(parseDate);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new Exception(e);
        }

        return convertedDate;
    }

}
