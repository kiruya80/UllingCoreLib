/*
 * Copyright (c) 2016. iUlling Corp.
 * Created By Kil-Ho Choi
 */

package com.ulling.lib.core.common;

import android.Manifest;
import android.os.Environment;
import com.ulling.lib.core.base.QcBaseApplication;
import java.io.File;

public class QcDefine {

    /**
     * 상용/ 테스트용
     */
    public static final boolean DEBUG_FLAG = true;

    //    public final static boolean IS_REAL = true;// true 상용, false 개발
//    protected static final String URL = (IS_REAL ? REAL_CONN : DEV_CONN);
//    public static final String SP_APP_NAME = "";
    public static final String SP_COMPANNY = "ULLING";

    public static final String OS = "ANDROID";
    public static final String APP_VERSION = "APP_VERSION";
    public static final String USER_U_ID = "USER_U_ID";
    public static final String TOKEN = "TOKEN";
    public static final String USER_INFO = "USER_INFO";
    public static final String USER_GCM_ID = "USER_GCM_ID";
    public static final String DEVICE_U_ID = "p_";

    /**
     * UTC 서버 데이트 타입 yyyy-MM-dd HH:mm:ss
     */
    public static final String IS_UTC_TYPE = "z";

    public static final String TIMEZONE_SEOUL = "Asia/Seoul";
    public static final String TIMEZONE_UTC = "UTC";
    public static final String UTC_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static final String DATE_FORMAT_UI_YYMMDDHH = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_UI_MMDDHH = "MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_UI_HHMMSS = "HH:mm:ss";

    public static final String displayDateFormatFrom = "MM.dd aa hh:mm";
    public static final String saveDateFormatFrom = "MM.dd aa hh:mm";

    /**
     * 앱내에서 보여지는 데이트 타입 MMM. d, yyyy
     */
    public static final String LOCAL_LIST_DATE_FORMAT = "MMM. dd, HH:mm";

    public static final String ACCESS_NETWORK_MOBILE = "ACCESS_NETWORK_MOBILE";

    public static final String INTENT_IMAGE_UPLOAD_URL = "INTENT_IMAGE_UPLOAD_URL";
    public static final String INTENT_SELCTED_TYPE = "INTENT_SELCTED_TYPE";
    public static final String INTENT_RESIZE_TYPE = "INTENT_RESIZE_TYPE";
    public static final String INTENT_CROP_TYPE = "INTENT_CROP_TYPE";
    public static final String INTENT_RATIO_WIDTH = "INTENT_RATIO_WIDTH";
    public static final String INTENT_RATIO_HEIGHT = "INTENT_RATIO_HEIGHT";

    public static final int REQUEST_GOOGLE_PLAY_SERVICES = 10000;
    public static final int REQUEST_PICK_CAMERA = 900;
    public static final int REQUEST_PICK_GALLERY = 901;
    public static final int REQUEST_PICK_CROP = 902;

    public static final int REQUEST_PERMISSIONS = 910;

    public static final String[] PERMISSIONS_READ_STORAGE_CAMERA =
        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};

//    public static final String URL_UPLOAD_IMAGE = "http://dev2.aritaum.com/comm/comm_appimage_upload.do?uploadCd=USR_PHOTO&thumbnailWidth=98;128&thumbnailHeight=98;128&flagFixed=N";


    public static final String[] PERMISSIONS_CAMERA_STORAGE = {
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public static final String[] PERMISSIONS_STORAGE = {
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public static final String[] PERMISSIONS_LOCATION = {
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION};

    public final static String DIRECTORY_LOG_CAT_NAME = "logcat";

    // /storage/emulated/0/DCU
    public static String APP_ROOT =
        Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator
            + QcBaseApplication.getInstance().getAppName();

    // /storage/emulated/0/DCU/log
    public static String LOG_CAT_ROOT =
        APP_ROOT + File.separator
            + DIRECTORY_LOG_CAT_NAME + File.separator;

    public static String FILE_TXT = ".txt";
}
