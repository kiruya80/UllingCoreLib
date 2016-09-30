package com.ulling.lib;

import android.os.Binder;
import android.util.Log;

import com.ulling.lib.common.UllingDefine;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Log Class
 * @author KILHO
 */
public class UtilLog {

	/**
	 * log TRUE , FALSE
	 */
	public static final boolean LOG_FLAG = UllingDefine.DEBUG_FLAG;
	public static final boolean PREFER_LOG_FLAG = UllingDefine.DEBUG_FLAG;
	private static Logger logger = null;

	/**
	 * Debug Log
	 * 
	 * 검정색	
	 * 로그 false 후 보여줄 메시지
	 * 
	 * @param msg 보여줄 메세지
	 * @param TAG 클래스명
	 */
	public static void dLog(String TAG, String msg) {
		if (logger != null) {
			logger.log(Level.INFO, String.format("V/%s(%d): %s\n", 
					TAG, Binder.getCallingPid(), msg));
		}
		if(LOG_FLAG) {
			Log.d(UllingDefine.SP_APP_NAME, "[" + TAG + "] : " + msg);
		}
	}

	/**
	 * Verbose Log
	 * 
	 * 개발용도
	 * 
	 * @param msg 보여줄 메세지
	 * @param TAG 클래스명
	 */
	public static void vLog(String TAG, String msg) {
		if (logger != null) {
			logger.log(Level.INFO, String.format("V/%s(%d): %s\n", 
					TAG, Binder.getCallingPid(), msg));
		}
		if(LOG_FLAG) {
			Log.v(UllingDefine.SP_APP_NAME, "[" + TAG + "] : " + msg);
		}
	}	

	/**
	 * Information Log
	 * 
	 * 초록색
	 * 
	 * 프리퍼런스
	 * 암호화
	 * 
	 * @param msg 보여줄 메세지
	 * @param TAG 클래스명
	 */
	public static void iLog(String TAG, String msg) {	
		if (logger != null) {
			logger.log(Level.INFO, String.format("V/%s(%d): %s\n", 
					TAG, Binder.getCallingPid(), msg));
		}	
		if(LOG_FLAG) {
			Log.i(UllingDefine.SP_APP_NAME, "[" + TAG + "] : " + msg);
		}
	}

	/**
	 * Warning Log
	 * 
	 * 주황색
	 * 
	 * 수정부분 확인
	 * 
	 * @param msg 보여줄 메세지
	 * @param TAG 클래스명
	 */
	public static void wLog(String TAG, String msg) {
		if(LOG_FLAG) {
			Log.w(UllingDefine.SP_APP_NAME, "[" + TAG + "] : " + msg);
		}
	}

	/**
	 * Error Log
	 * 
	 * 빨강색
	 * 
	 * 일반적
	 * 
	 * @param msg 보여줄 메세지
	 * @param TAG 클래스명
	 */
	public static void eLog(String TAG, String msg) {
		if (logger != null) {
			logger.log(Level.INFO, String.format("V/%s(%d): %s\n", 
					TAG, Binder.getCallingPid(), msg));
		}
		if(LOG_FLAG) {
			Log.e(UllingDefine.SP_APP_NAME, "[" + TAG + "] : " + msg);
		}
	}
}
