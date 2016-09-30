package com.ulling.lib;

import android.content.Context;
import android.widget.Toast;

/**
 * 
 * @ProjectName  : BBuzzArt_Phase_2.0
 * @FileName     : ToastMaster.java
 * @FilePath  : net.bbuzzart.android.util
 * @Date         : 2015. 1. 8. 
 * @author       : KILHO 
 * 
 * @프로그램
 * ㄴ 토스트 클래스 
 * 
 * @변경이력
 *
 */
public class ToastMaster {
	/**
	 * Log - class name
	 */
	private static String TAG = "ToastMaster";

	private static ToastMaster mToastMaster = null;
	private static Context mCtx;
	private static Toast mToast;

	public static void init(Context context) {
		mCtx = context;
		mToastMaster = getInstance(context);
		UtilLog.eLog(TAG, "ToastMaster init complete !");
	}

	private ToastMaster(Context context) {
		mCtx = context;
		mToast = new Toast(context);
	}

	public static synchronized ToastMaster getInstance(Context context) {
		if (mToastMaster == null)
			mToastMaster = new ToastMaster(context);
		return mToastMaster; 
	}

	public void showToast(String toastStr, boolean longDuration) {
		try {
			UtilLog.eLog(TAG, "showToast==" + toastStr);
			if (toastStr == null)
				return;
			if ("".equals(toastStr))
				return;
			
			if (mToast != null) {
				mToast.cancel();
				if (longDuration) {
					mToast = Toast.makeText(mCtx, toastStr, Toast.LENGTH_LONG);
				} else {	
					mToast = Toast.makeText(mCtx, toastStr, Toast.LENGTH_SHORT);
				}
				mToast.show();
			}

		} catch (Exception e) {
		}
	}

	public Toast getSuperToast() {
		return mToast;
	}
}