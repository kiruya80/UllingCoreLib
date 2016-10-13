/*
 * Copyright (c) 2016. iUlling Corp.
 * Created By Kil-Ho Choi
 */

package com.ulling.lib.base;

import android.app.Application;

public class QcBaseApplication extends Application {
  private Thread.UncaughtExceptionHandler mUncaughtExceptionHandler;

  @Override
  public void onCreate() {
    super.onCreate();
    mUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
    Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandlerApplication());
  }

  public class UncaughtExceptionHandlerApplication implements Thread.UncaughtExceptionHandler {
    public void uncaughtException(Thread thread, Throwable throwable) {
//            FPrefer.with(getApplicationContext()).addError(UString.with(throwable).get());
      if (mUncaughtExceptionHandler != null)
        mUncaughtExceptionHandler.uncaughtException(thread, throwable);
    }
  }
}