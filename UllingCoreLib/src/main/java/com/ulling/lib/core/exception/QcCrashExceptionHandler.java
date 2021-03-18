package com.ulling.lib.core.exception;

import android.os.Environment;
import android.util.Log;
import com.ulling.lib.core.base.QcBaseApplication;
import com.ulling.lib.core.utils.QcLog;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class: QcCrashExceptionHandler Created by 19001662 on 2021-01-05 오후 5:56
 *
 * @version 1.0.0
 * <p>
 * Description:
 * <p>
 * 앱 강제종료 등 로컬 로그 남기기
 **/
public class QcCrashExceptionHandler implements Thread.UncaughtExceptionHandler {

    public final static String TAG = QcCrashExceptionHandler.class.getSimpleName();
    ;

    /**
     * URQAController.InitializeAndStartSession class com.urqa.library.UncaughtExceptionHandler
     */
    private Thread.UncaughtExceptionHandler defaultUEHandler;

    private String localPath = "/mnt/sdcard/";
    private static String CRASH_FILE_NAME_PATTERN = "yyyy_MM_dd";

    private static String CRASH_DATE_PATTERN = "yyyy/MM/dd HH:mm:ss";
    private String FILE_PATH = "/.ulling/log/Crash/";

    public QcCrashExceptionHandler() {
        Log.e(TAG, "CustomizedExceptionHandler =============");
        this.defaultUEHandler = Thread.getDefaultUncaughtExceptionHandler();
        QcLog.e("defaultUEHandler ==== " + defaultUEHandler.getClass().toString());

        Thread.setDefaultUncaughtExceptionHandler(this);
        initFileDir();
    }

    public QcCrashExceptionHandler(String filePath) {
        Log.e(TAG, "CustomizedExceptionHandler =============");
//        this.localPath = localPath;
        this.FILE_PATH = filePath;

        this.defaultUEHandler = Thread.getDefaultUncaughtExceptionHandler();
        QcLog.e("defaultUEHandler ==== " + defaultUEHandler.getClass().toString());

        Thread.setDefaultUncaughtExceptionHandler(this);
        initFileDir();
    }

    public File initFileDir() {
//        File dir = new File(Environment.getExternalStorageDirectory(), FILE_PATH);
        File dir = new File(Environment.getExternalStorageDirectory().toString() + FILE_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        } else {
        }
        return dir;
    }

    @Override
    public void uncaughtException(Thread t, Throwable ex) {
        Log.e(TAG, "uncaughtException =============");

        try {
            final Writer stringBuffSync = new StringWriter();
            final PrintWriter printWriter = new PrintWriter(stringBuffSync);
            ex.printStackTrace(printWriter);
            String stacktrace = stringBuffSync.toString();
            printWriter.close();

            if (localPath != null) {
                String fileLog =
                    "\n┌──── ■ " + TAG + " ■ ────┐\n"
                        + "┌─────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────\n"
                        + "│ ▶ " + "발생시간 : " + getCurrentTime(CRASH_DATE_PATTERN) + "\n"
                        + "│ ▶ " + "앱 버전  : " + QcBaseApplication.getInstance().getAppVer() + "\n"
                        + "│ ▶ " + ex.toString() + "\n"
                        + "├─────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────\n"
                        + "│  " + stacktrace + "\n"
                        + "└─────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────\n ";

                writeToFile(fileLog);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        defaultUEHandler.uncaughtException(t, ex);
    }

    private void writeToFile(String currentStacktrace) {
        Log.e(TAG, "writeToFile =============\n\n" + currentStacktrace);
        try {
            File dir = initFileDir();

            String fileName = getCurrentTime(CRASH_FILE_NAME_PATTERN) + ".txt";

            File reportFile = new File(dir, fileName);
            FileWriter fileWriter = new FileWriter(reportFile, true);
            fileWriter.append(currentStacktrace);
            fileWriter.flush();
            fileWriter.close();
            Log.e(TAG, "crash log flush ========================");
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    public static String getCurrentTime(String format) {
        return getSimpleDateFormat(format, new Date());
    }

    public static String getSimpleDateFormat(String format, Date date) {
        return new SimpleDateFormat(format).format(date);
    }
}