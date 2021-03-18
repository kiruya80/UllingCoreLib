/*
 * Copyright (c) 2016. iUlling Corp.
 * Created By Kil-Ho Choi
 */

package com.ulling.lib.core.utils;

import android.os.Debug;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import com.ulling.lib.core.BuildConfig;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Log Class <p> 1. 디버그 모드 2. 파일저장 <p> option 3. 리스트, json
 *
 * @author KILHO
 */
public class QcLog {

    private static String APP_NAME = "APP_NAME";
    //    private static final int STACK_NUMBUER = 2;
    public static boolean DEBUG_MODE = BuildConfig.DEBUG;    // 최종 릴리즈시 false로
    public static boolean SAVE_FILE_LOG_MODE = false;    // 로그를 파일로 쓰거나 쓰지 않거나..
    /**
     * log TRUE , FALSE
     */
//    private static Logger logger = null;
    private static StringBuilder msgBuilder = new StringBuilder();
    private static final int MAX_LOG_LEN = 4000;
    private static final int FILE_MAX_SIZE = 2 * 1024 * 1024;

    public static String LOG_PATH = "/.ulling/" + APP_NAME + "/Logs/";
    private static String fileLogDirPath;

    private static BufferedWriter bufferedWriter;

    private enum logType {
        verbose,
        info,
        debug,
        warn,
        error
    }

    public static void initialize(String appName, String logPath) {
        if (fileLogDirPath != null) {
            return;
        }

        APP_NAME = appName;
        LOG_PATH = "/.ulling/" + appName + "/Logs/";
        if (logPath != null && !"".equals(logPath)) {
            LOG_PATH = logPath;
        }
        setFileLogDirPath(Environment.getExternalStorageDirectory().toString() + LOG_PATH);
    }

    public static void setFileLogDirPath(String path) {
        fileLogDirPath = path;
    }

    /**
     * Verbose Log <p> 개발용도
     *
     * @param message 보여줄 메세지
     */
    public static void v(String message) {
        log(logType.verbose, message);
    }

    public static void v(final String format, final Object... args) {
        log(logType.verbose, toMessage(format, args));
    }

    /**
     * Information Log <p> 초록색 <p> 프리퍼런스 암호화
     *
     * @param message 보여줄 메세지
     */
    public static void i(String message) {
        log(logType.info, message);
    }

    public static void i(final String format, final Object... args) {
        log(logType.info, toMessage(format, args));
    }

    /**
     * Debug Log <p> 검정색 로그 false 후 보여줄 메시지
     *
     * @param message 보여줄 메세지
     */
    public static void d(String message) {
        log(logType.debug, message);
    }

    public static void d(final String format, final Object... args) {
        log(logType.debug, toMessage(format, args));
    }

    /**
     * Warning Log <p> 주황색 <p> 수정부분 확인
     *
     * @param message 보여줄 메세지
     */
    public static void w(String message) {
        log(logType.warn, message);
    }

    public static void w(final String format, final Object... args) {
        log(logType.warn, toMessage(format, args));
    }

    /**
     * Error Log <p> 빨강색 <p> 일반적
     *
     * @param message 보여줄 메세지
     */
    public static void e(String message) {
        log(logType.error, message);
    }

    public static void e(final String format, final Object... args) {
        log(logType.error, toMessage(format, args));
    }

    private static String toMessage(final String format, final Object... args) {
//        return messagePrefix + (args.length > 0 ? String.format(format, args) : format);
        return (args.length > 0 ? String.format(format, args) : format);
    }

    private static void log(logType type, String message) {
        msgBuilder = new StringBuilder();
        try {
            msgBuilder
                // move class line
                .append(" (").append(Thread.currentThread().getStackTrace()[4].getFileName())
                .append(":")
                .append(Thread.currentThread().getStackTrace()[4].getLineNumber()).append(")")
                // methodName class name
                .append(" ")
                .append("[").append(Thread.currentThread().getStackTrace()[4].getMethodName())
                .append("()").append("]")
                .append(" ")
                .append(" == ").append(message);
        } catch (Exception e) {
            e.printStackTrace();
            msgBuilder.append(message);
        }

        if (SAVE_FILE_LOG_MODE && fileLogDirPath != null && !"".equals(fileLogDirPath)) {
//            writeToFile(type.name(), msgBuilder.toString());
            writeToFile(msgBuilder.toString());
        }

        if (DEBUG_MODE) {
            print(type, msgBuilder.toString());
        }
    }

    public static String getLogStyleMsg(String message) {
        return getLogStyleMsg(null, message);
    }

    public static String getLogStyleMsg(String title, String message) {
        String codeLine =
            Thread.currentThread().getStackTrace()[4].getFileName() + ":" + Thread.currentThread()
                .getStackTrace()[4].getLineNumber();
        String logStyleMsg = codeLine;
        if (title != null && !"".equals(title)) {
            logStyleMsg = ""
                + "    ┌──── ■ " + codeLine + " ■ ────┐\n"
                + "    ┌──────────────────────────────────────────────────────────────────────────────────────────────────────\n"
                + "    │  " + title + "\n"
                + "    │  " + message + "\n"
                + "    └──────────────────────────────────────────────────────────────────────────────────────────────────────";

        } else {
            logStyleMsg = ""
                + "    ┌──── ■ " + codeLine + " ■ ────┐\n"
                + "    ┌──────────────────────────────────────────────────────────────────────────────────────────────────────\n"
                + "    │  " + message + "\n"
                + "    └──────────────────────────────────────────────────────────────────────────────────────────────────────";
        }

//        print(logType.error, logStyleMsg);

        return logStyleMsg;
    }

    private static void print(logType type, String logText_) {
        String logText = logText_;
        if (logText.length() > MAX_LOG_LEN) {
            logText = logText_.substring(0, MAX_LOG_LEN);
        }
        if (type == logType.verbose) {
            Log.v(APP_NAME, logText);
        } else if (type == logType.info) {
            Log.i(APP_NAME, logText);
        } else if (type == logType.warn) {
            Log.w(APP_NAME, logText);
        } else if (type == logType.error) {
            Log.e(APP_NAME, logText);
        } else {
            Log.d(APP_NAME, logText);
        }
    }

    private static void nativeHeap() {
        String heapSize = " NativeHeapSize = " + Debug.getNativeHeapSize()
            + " NativeHeapFreeSize = " + Debug.getNativeHeapFreeSize()
            + " NativeHeapAllocatedSize() = " + Debug.getNativeHeapAllocatedSize();
        log(logType.error, heapSize);
    }

    /**
     * 로그 저장 파일 삭제
     *
     * @param dirName
     */
    public static void deleteFile(String dirName) {
        String status = Environment.getExternalStorageState();
        if (!status.equals(Environment.MEDIA_MOUNTED)) {
            Log.e(APP_NAME, "SDCard Status:$status");
            return;
        }

        String path = Environment.getExternalStorageDirectory().toString() + dirName;
        File dir = new File(path);

        File[] childFileList = dir.listFiles();
        if (dir.exists()) {
            for (File childFile : childFileList) {
                if (childFile.isDirectory()) {
                    deleteFile(childFile.getAbsolutePath()); //하위 디렉토리
                } else {
                    childFile.delete();
                }
            }
            dir.delete();
        }
    }

    /**
     * 파일 저장하기
     */
//    private static void writeToFile(String name, String logText) {
//        String fileNameDetail = "";
//
//        if (fileNameDetail != null && !"".equals(fileNameDetail)) {
//            File fileDir = new File(QcDefine.LOG_CAT_ROOT);
//            if (!fileDir.exists()) {
//                fileDir.mkdir();
//            }
//
//            String fileName = QcDefine.DIRECTORY_LOG_CAT_NAME + "_"
////                    + QcDateUtils.localtimeToUTC()
//                    + QcDateUtils.getCurrentTime()
//                    + "_" + fileNameDetail + QcDefine.FILE_TXT;
//
//            try {
//                bufferedWriter = new BufferedWriter(
//                        new FileWriter(QcDefine.LOG_CAT_ROOT + fileName, true));
//                bufferedWriter.write(QcDateUtils.getCurrentTime() + " " + logText + "\n");
//                bufferedWriter.flush();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
    private static void writeToFile(String logText) {
        String status = Environment.getExternalStorageState();
        if (!status.equals(Environment.MEDIA_MOUNTED)) {
            Log.e(APP_NAME, "SDCard Status:$status");
            return;
        }

        if (TextUtils.isEmpty(fileLogDirPath)) {
            return;
        }

        File fileDir = new File(fileLogDirPath);
        if (!fileDir.exists()) {
            if (!fileDir.mkdirs()) {
                Log.e(APP_NAME, "[saveLogToFile] Make Directory Error...");
                return;
            }
        }

        String fileName = getCurrentTime("yyyy_MM_dd_HH") + "_" + APP_NAME + ".txt";
        String message = getCurrentTime("MM/dd HH:mm:ss:SSS") + "\t" + logText + "\n\r";

        try {
            bufferedWriter = new BufferedWriter(
                new FileWriter(fileDir + "/" + fileName, true));
            bufferedWriter.write(message);
            bufferedWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getCurrentTime(String format) {
        return getSimpleDateFormat(format, new Date());
    }

    public static String getSimpleDateFormat(String format, Date date) {
        return new SimpleDateFormat(format).format(date);
    }

}
