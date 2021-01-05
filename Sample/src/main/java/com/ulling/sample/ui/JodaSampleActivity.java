package com.ulling.sample.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ulling.lib.core.common.QcDefine;
import com.ulling.lib.core.utils.QcJodaTimeUtils;
import com.ulling.lib.core.utils.QcLog;
import com.ulling.sample.R;

import net.danlew.android.joda.DateUtils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 * Sample demonstrating the capabilities of joda-time-android.
 * <p>
 * This is by no means a comprehensive demonstration of all the capabilities
 * of joda-time-android, but it gives you a flavor of what is possible.
 * <p>
 * <p>
 * https://github.com/dlew/joda-time-android
 */
public class JodaSampleActivity extends AppCompatActivity {

    private ViewGroup mContent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_joda_sample);

        mContent = (ViewGroup) findViewById(R.id.content);
        Button btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 전체 타임존 변경
//        DateTimeZone.setDefault(DateTimeZone.UTC);
        DateTimeZone.setDefault(QcJodaTimeUtils.getTimezone(false));

        sampleDateTime();
        sampleDateTimeUtc();
        sampleDateTimeUtcFromUtcTime();
        sampleLocalDate();

        sampleFormatDateTime();
        sampleFormatDateTimeCustom();
        sampleDateRange();
        sampleFormatElapsedTime();
        sampleFormatDuration();
        sampleIsToday();
        sampleGetRelativeTimeSpanString();
        sampleGetRelativeTimeSpanStringWithPreposition();
        sampleGetRelativeDateTimeString();
    }

    //    DateTime
//    Now: 2019-10-26T20:52:31.155+09:00
//    Now + 30 minutes: 2019-10-26T21:22:31.155+09:00
//    Now + 5 hours: 2019-10-27T01:52:31.155+09:00
//    Now + 2 days: 2019-10-28T20:52:31.155+09:00
    private void sampleDateTime() {
        List<String> text = new ArrayList<String>();
        DateTime now = DateTime.now(QcJodaTimeUtils.getTimezone(false));
//        DateTime now = DateTime.now();
        text.add("Now: " + now);
        text.add("Now + 30 minutes: " + now.plusMinutes(30));
        text.add("Now + 5 hours: " + now.plusHours(5));
        text.add("Now + 2 days: " + now.plusDays(2));
        addSample("DateTime", text);
    }

    //    DateTime UTC
//    Now: 2019-10-26T11:52:31.174Z
//    Now + 30 minutes: 2019-10-26T12:22:31.174Z
//    Now + 5 hours: 2019-10-26T16:52:31.174Z
//    Now + 2 days: 2019-10-28T11:52:31.174Z
    private void sampleDateTimeUtc() {
        List<String> text = new ArrayList<String>();
        DateTime now = DateTime.now(QcJodaTimeUtils.getTimezone(true));
//        DateTime now = DateTime.now();
        text.add("Now: " + now);
        text.add("Now + 30 minutes: " + now.plusMinutes(30));
        text.add("Now + 5 hours: " + now.plusHours(5));
        text.add("Now + 2 days: " + now.plusDays(2));
        addSample("DateTime UTC", text);
    }

    /**
     * 받아온 시간이 UTC인경우 포맷할때 타임존 설정
     * 다음에 DateTime 을 타임존 변경해야한다
     */
    private void sampleDateTimeUtcFromUtcTime() {
        List<String> text = new ArrayList<String>();

        QcLog.e("DateTimeZone 타임존으로 받아온 시간인 경우 ==================================================  ");
        TimeZone tz = TimeZone.getDefault();
        QcLog.e("DateTimeZone 현재 TimeZone 구역 의 이름 ::: " + tz.getDisplayName());
        QcLog.e("DateTimeZone 현재 TimeZone 구역 의 해당 ID ::: " + tz.getID());

        String timeZoneStr = tz.getID();
        String dateUTCStr = "2019-10-26T09:36:52Z";

        // 받아온 시간이 UTC 타임인 경우
        DateTimeFormatter fmtTime = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").withZone(DateTimeZone.forID(QcDefine.TIMEZONE_UTC));
        DateTime dtTime = fmtTime.parseDateTime(dateUTCStr);
        QcLog.e("DateTimeZone 받아온 시간을 UTC타임존을 적용한 포맷팅 ========  " + dtTime);
        QcLog.e("DateTimeZone 로컬지역존에 맞춰 시간 변경   ========  " + dtTime.withZone(DateTimeZone.forID(timeZoneStr)));

        QcLog.e("DateTimeZone 타임존으로 받아온 시간이 아닌 경우 ==================================================  ");

        String timeZone = "America/New_York";
        String dateStr = "06/22/2014 10:43";
        QcLog.e("DateTimeZone ========  " + timeZone + " , " + dateStr);
        DateTimeFormatter fmt = DateTimeFormat.forPattern("M/d/yyyy HH:mm"); // uses local zone
        DateTime dt0 = fmt.parseDateTime(dateStr);
        DateTime dt1 = fmt.parseDateTime(dateStr).withZone(DateTimeZone.forID(timeZone));

        QcLog.e("DateTimeZone 변경완료 1 ========  " + dt1);
        QcLog.e("DateTimeZone 변경완료 2 ========  " + dt0.withZone(DateTimeZone.forID(timeZone)));


        addSample("LocalDate", text);
    }

    //    LocalDate
//    Now: 2019-10-26
//    Now + 2 days: 2019-10-28
//    Now + 3 months: 2020-01-26
    private void sampleLocalDate() {
        List<String> text = new ArrayList<String>();
        LocalDate now = LocalDate.now();
        text.add("Now: " + now);
        text.add("Now + 2 days: " + now.plusDays(2));
        text.add("Now + 3 months: " + now.plusMonths(3));
        addSample("LocalDate", text);
    }


    //    DateUtils.formatDateTime()
//    Show time: 오후 8:52
//    Show date: 10월 26일
//    Numeric date: 10. 26.
//    Show date (abbreviated): 10월 26일
//    Show date and time: 10월 26일 오후 8:52
//    Show date (force year): 2019년 10월 26일
    private void sampleFormatDateTime() {
        List<String> text = new ArrayList<String>();
        DateTime now = DateTime.now();
        text.add("Show time: " + DateUtils.formatDateTime(this, now, DateUtils.FORMAT_SHOW_TIME));
        text.add("Show date: " + DateUtils.formatDateTime(this, now, DateUtils.FORMAT_SHOW_DATE));
        text.add("Numeric date: " + DateUtils.formatDateTime(this, now, DateUtils.FORMAT_NUMERIC_DATE));
        text.add("Show date (abbreviated): " + DateUtils.formatDateTime(this, now, DateUtils.FORMAT_SHOW_DATE
                | DateUtils.FORMAT_ABBREV_ALL));
        text.add("Show date and time: " + DateUtils.formatDateTime(this, now, DateUtils.FORMAT_SHOW_DATE
                | DateUtils.FORMAT_SHOW_TIME));
        text.add("Show date (force year): " + DateUtils.formatDateTime(this, now, DateUtils.FORMAT_SHOW_DATE
                | DateUtils.FORMAT_SHOW_YEAR));
        addSample("DateUtils.formatDateTime()", text);
    }

    //    DateUtils.formatDateTime()
//    Show time: 오전 11:22
//    Show date: 10월 23일
//    Numeric date: 10. 23.
//    Show date (abbreviated): 10월 23일
//    Show date and time: 10월 23일 오전 11:22
//    Show date (force year): 2019년 10월 23일
    private void sampleFormatDateTimeCustom() {
        List<String> text = new ArrayList<String>();

        String formatInput = "yyyy-MM-dd HH:mm:ss";
        String formatOutput = "yyyy-MM-dd";
        String dateStr = "2019-10-23 11:22:33";

        DateTimeFormatter fmt = DateTimeFormat.forPattern(formatInput);
        DateTime nowFormat = DateTime.parse(dateStr, fmt);
        text.add("parse: " + nowFormat);

        DateTime jodatime = fmt.parseDateTime(dateStr);
        text.add("DateTimeFormatter inPut (" + formatInput + ") : " + nowFormat);

        DateTimeFormatter dtfOut = DateTimeFormat.forPattern(formatOutput);
        String outPutDate = dtfOut.print(jodatime);
        text.add("DateTimeFormatter outPut (" + formatInput + ") : " + outPutDate);

        addSample("DateUtils.formatDateTime()", text);
    }

    //    DateUtils.formatDateRange()
//    Range: 10월 26일 ~ 12월 21일
//    Range (with year): 2019년 10월 26일 ~ 12월 21일
//    Range (abbreviated): 10월 26일 ~ 12월 21일
//    Range (with time): 10월 26일 오후 8:52 ~ 12월 21일 오후 11:22
    private void sampleDateRange() {
        List<String> text = new ArrayList<String>();
        DateTime start = DateTime.now();
        DateTime end = start.plusMinutes(30).plusHours(2).plusDays(56);
        text.add("Range: " + DateUtils.formatDateRange(this, start, end, 0));
        text.add("Range (with year): " + DateUtils.formatDateRange(this, start, end, DateUtils.FORMAT_SHOW_YEAR));
        text.add("Range (abbreviated): " + DateUtils.formatDateRange(this, start, end, DateUtils.FORMAT_ABBREV_ALL));
        text.add("Range (with time): " + DateUtils.formatDateRange(this, start, end, DateUtils.FORMAT_SHOW_TIME));
        addSample("DateUtils.formatDateRange()", text);
    }

    //    DateUtils.formatElapsedTime()
//            25 seconds: 00:25
//            3 minutes: 03:00
//            3 minutes, 25 seconds: 03:25
//            3 hours: 3:00:00
//            3 hours, 3 minutes: 3:03:00
    private void sampleFormatElapsedTime() {
        List<String> text = new ArrayList<String>();
        text.add("25 seconds: " + DateUtils.formatElapsedTime(Duration.standardSeconds(25)));
        text.add("3 minutes: " + DateUtils.formatElapsedTime(Duration.standardMinutes(3)));
        text.add("3 minutes, 25 seconds: " + DateUtils.formatElapsedTime(
                Duration.standardMinutes(3).plus(Duration.standardSeconds(25))));
        text.add("3 hours: " + DateUtils.formatElapsedTime(Duration.standardHours(3)));
        text.add("3 hours, 3 minutes: " + DateUtils.formatElapsedTime(
                Duration.standardHours(3).plus(Duration.standardMinutes(3))));
        addSample("DateUtils.formatElapsedTime()", text);
    }

    //    DateUtils.formatDuration()
//    Seconds: 25초
//    Minutes: 5분
//    Hours: 3시간
    private void sampleFormatDuration() {
        List<String> text = new ArrayList<String>();
        text.add("Seconds: " + DateUtils.formatDuration(this, Duration.standardSeconds(25)));
        text.add("Minutes: " + DateUtils.formatDuration(this, Duration.standardMinutes(5)));
        text.add("Hours: " + DateUtils.formatDuration(this, Duration.standardHours(3)));
        addSample("DateUtils.formatDuration()", text);
    }

    //    DateUtils.isToday()
//    Today: true
//    Tomorrow: false
//    Yesterday: false
    private void sampleIsToday() {
        List<String> text = new ArrayList<String>();
        LocalDate today = LocalDate.now();
        text.add("Today: " + DateUtils.isToday(today));
        text.add("Tomorrow: " + DateUtils.isToday(today.plusDays(1)));
        text.add("Yesterday: " + DateUtils.isToday(today.minusDays(1)));
        addSample("DateUtils.isToday()", text);
    }

    //    DateUtils.getRelativeTimeSpanString()
//    Short future: 25분 후
//    Medium future: 5시간 후
//    Long future: 3일 후
//    Short past: 25분 전
//    Medium past: 5시간 전
//    Long past: 3일 전
    private void sampleGetRelativeTimeSpanString() {
        List<String> text = new ArrayList<String>();
        DateTime now = DateTime.now();
        text.add("Short future: " + DateUtils.getRelativeTimeSpanString(this, now.plusMinutes(25)));
        text.add("Medium future: " + DateUtils.getRelativeTimeSpanString(this, now.plusHours(5)));
        text.add("Long future: " + DateUtils.getRelativeTimeSpanString(this, now.plusDays(3)));
        text.add("Short past: " + DateUtils.getRelativeTimeSpanString(this, now.minusMinutes(25)));
        text.add("Medium past: " + DateUtils.getRelativeTimeSpanString(this, now.minusHours(5)));
        text.add("Long past: " + DateUtils.getRelativeTimeSpanString(this, now.minusDays(3)));
        addSample("DateUtils.getRelativeTimeSpanString()", text);
    }

    //    DateUtils.getRelativeTimeSpanString() (with preposition)
//    Short future: 오후 9:17
//    Medium future: 10월 27일
//    Long future: 10월 29일
//    Short past: 오후 8:27
//    Medium past: 오후 3:52
//    Long past: 10월 23일
    private void sampleGetRelativeTimeSpanStringWithPreposition() {
        List<String> text = new ArrayList<String>();
        DateTime now = DateTime.now();
        text.add("Short future: " + DateUtils.getRelativeTimeSpanString(this, now.plusMinutes(25), true));
        text.add("Medium future: " + DateUtils.getRelativeTimeSpanString(this, now.plusHours(5), true));
        text.add("Long future: " + DateUtils.getRelativeTimeSpanString(this, now.plusDays(3), true));
        text.add("Short past: " + DateUtils.getRelativeTimeSpanString(this, now.minusMinutes(25), true));
        text.add("Medium past: " + DateUtils.getRelativeTimeSpanString(this, now.minusHours(5), true));
        text.add("Long past: " + DateUtils.getRelativeTimeSpanString(this, now.minusDays(3), true));
        addSample("DateUtils.getRelativeTimeSpanString() (with preposition)", text);
    }

    //    DateUtils.getRelativeDateTimeString()
//    Short future: 25분 후, 오후 9:17
//    Medium future: 5시간 후, 오전 1:52
//    Long future: 10월 29일 오후 8:52
//    Short past: 25분 전, 오후 8:27
//    Medium past: 5시간 전, 오후 3:52
//    Long past: 10월 23일 오후 8:52
    private void sampleGetRelativeDateTimeString() {
        List<String> text = new ArrayList<String>();
        DateTime now = DateTime.now();
        text.add("Short future: " + DateUtils.getRelativeDateTimeString(this, now.plusMinutes(25), null, 0));
        text.add("Medium future: " + DateUtils.getRelativeDateTimeString(this, now.plusHours(5), null, 0));
        text.add("Long future: " + DateUtils.getRelativeDateTimeString(this, now.plusDays(3), null, 0));
        text.add("Short past: " + DateUtils.getRelativeDateTimeString(this, now.minusMinutes(25), null, 0));
        text.add("Medium past: " + DateUtils.getRelativeDateTimeString(this, now.minusHours(5), null, 0));
        text.add("Long past: " + DateUtils.getRelativeDateTimeString(this, now.minusDays(3), null, 0));
        addSample("DateUtils.getRelativeDateTimeString()", text);
    }

    private void addSample(CharSequence title, Iterable<String> text) {
        addSample(title, TextUtils.join("\n", text));
        QcLog.e(title + "\n" + TextUtils.join("\n", text));
    }

    private void addSample(CharSequence title, CharSequence text) {
        View view = LayoutInflater.from(this).inflate(R.layout.include_joda_sample, mContent, false);
        ((TextView) view.findViewById(R.id.title)).setText(title);
        ((TextView) view.findViewById(R.id.text)).setText(text);
        mContent.addView(view);
    }

}