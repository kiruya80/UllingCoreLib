/*
 * Copyright (c) 2016. iUlling Corp.
 * Created By Kil-Ho Choi
 */

package com.ulling.lib.common;

import android.Manifest;

public class QcDefine {
  /**
   * 상용/ 테스트용
   */
  public static final boolean DEBUG_FLAG = false;

  public static final String SP_APP_NAME = "Weaggle";
  public static final String SP_COMPANNY = "ULLING";

  public static final String OS = "ANDROID";
  public static final String APP_VERSION = "app_version";
  public static final String USER_U_ID = "USER_U_ID";
  public static final String TOKEN = "TOKEN";
  public static final String USER_INFO = "USER_INFO";
  public static final String USER_GCM_ID = "USER_GCM_ID";
  public static final String SVR_APP_VERSION = "SVR_APP_VERSION";
  public static final String SVR_TIME = "SVR_TIME";

  public static final String DEVICE_U_ID = "p_";

  /**
   * TimeOut
   */
  public static final int INTRO_TIMEOUT = 1500;
  public static final int VR_CLICK_BTN_TIME = 500;

  public static final int VOLLEY_DEFAULT_TIMEOUT = 30000;
  public static final int VOLLEY_PROGRESS_TIMEOUT = 1500;
  public static final int VOLLEY_MAX_RETRIES = 0;
  public static final float VOLLEY_BACKOFF_MULT = 1f;


  /**
   * UTC 서버 데이트 타입 yyyy-MM-dd HH:mm:ss
   */
  public static final String UTC_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

  /**
   * 앱내에서 보여지는 데이트 타입 MMM. d, yyyy
   */
  public static final String LOCAL_LIST_DATE_FORMAT = "MMM. dd, HH:mm";

  public static final String ACCESS_NETWORK_MOBILE = "ACCESS_NETWORK_MOBILE";

  public static final String[] PERMISSIONS_CAMERA_STORAGE = {
      Manifest.permission.CAMERA,
      Manifest.permission.READ_EXTERNAL_STORAGE,
      Manifest.permission.WRITE_EXTERNAL_STORAGE};

  public static final String[] PERMISSIONS_STORAGE = {
      Manifest.permission.READ_EXTERNAL_STORAGE,
      Manifest.permission.WRITE_EXTERNAL_STORAGE};

}
