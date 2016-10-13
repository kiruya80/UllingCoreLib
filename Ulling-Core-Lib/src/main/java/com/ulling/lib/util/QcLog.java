/*
 * Copyright (c) 2016. iUlling Corp.
 * Created By Kil-Ho Choi
 */

package com.ulling.lib.util;

import android.os.Debug;
import android.util.Log;

/**
 * Log Class <p> 1. 디버그 모드 2. 파일저장 <p> option 3. 리스트, json
 *
 * @author KILHO
 */
public class QcLog {
  private static final String APP_NAME = "APP_NAME";
  //    private static final int STACK_NUMBUER = 2;
  public static boolean DEBUG_MODE = false;    // 최종 릴리즈시 false로
  public static boolean WRITE_TO_FILE = false;    // 로그를 파일로 쓰거나 쓰지 않거나..
  /**
   * log TRUE , FALSE
   */
//    public static final boolean LOG_FLAG = UllingDefine.DEBUG_FLAG;
//    private static Logger logger = null;
  private static StringBuilder msgBuilder = new StringBuilder();
  private static final int MAX_LOG_LEN = 4000;

  private enum logType {
    verbose,
    info,
    debug,
    warn,
    error
  }

  /**
   * Verbose Log <p> 개발용도
   *
   * @param message 보여줄 메세지
   */
  public static void v(String message) {
    log(logType.verbose, message);
  }

  /**
   * Information Log <p> 초록색 <p> 프리퍼런스 암호화
   *
   * @param message 보여줄 메세지
   */
  public static void i(String message) {
    log(logType.info, message);
  }

  /**
   * Debug Log <p> 검정색 로그 false 후 보여줄 메시지
   *
   * @param message 보여줄 메세지
   */
  public static void d(String message) {
    log(logType.debug, message);
  }

  /**
   * Warning Log <p> 주황색 <p> 수정부분 확인
   *
   * @param message 보여줄 메세지
   */
  public static void w(String message) {
    log(logType.warn, message);
  }

  /**
   * Error Log <p> 빨강색 <p> 일반적
   *
   * @param message 보여줄 메세지
   */
  public static void e(String message) {
    log(logType.error, message);
  }

  private static void log(logType type, String message) {
    if (DEBUG_MODE == false)
      return;
    msgBuilder = new StringBuilder();
    try {
      /**
       *
       */
//            String temp = new Throwable().getStackTrace()[STACK_NUMBUER].getClassName();
//            if (temp != null) {
//                int lastDotPos = temp.lastIndexOf(".");
//                className = temp.substring(lastDotPos + 1);
//            }
//            String methodName = new Throwable().getStackTrace()[STACK_NUMBUER].getMethodName();
//            int lineNumber = new Throwable().getStackTrace()[STACK_NUMBUER].getLineNumber();
//
//            logText = "[" + className + "] " + methodName + "()" + "[" + lineNumber + "]" + " >> " + message
//                    + "    (" + Thread.currentThread().getStackTrace()[4].getFileName() +
//                    ":" + Thread.currentThread().getStackTrace()[4] .getLineNumber()+ ")";
      /**
       *
       */
      msgBuilder
          // move class line
          .append(" (").append(Thread.currentThread().getStackTrace()[4].getFileName()).append(":")
          .append(Thread.currentThread().getStackTrace()[4].getLineNumber()).append(")")
          // methodName class name
          .append(" ")
          .append("[").append(Thread.currentThread().getStackTrace()[4].getMethodName()).append("()").append("]")
          .append(" ")
          .append(" == ").append(message);
    } catch (Exception e) {
      e.printStackTrace();
      msgBuilder.append(message);
    }
    print(type, msgBuilder.toString());
    if (WRITE_TO_FILE) {
      writeToFile(type.name(), msgBuilder.toString());
    }
  }

  private static void writeToFile(String name, String logText) {
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
}
