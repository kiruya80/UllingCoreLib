package com.ulling.lib.core.view.fontview;

import android.content.Context;
import android.graphics.Typeface;

/**
 * 
 * @ProjectName  : HummingPlayer_1.0
 * @FileName     : CustormFontHelper.java
 * @FilePath  : com.ullim.util
 * @Date         : 2015. 5. 12. 
 * @author       : KILHO 
 * 
 * @프로그램
 *  커스텀 폰트 헬퍼 
 *  
 * @변경이력
 *
 */
public class StyledFontHelper {

	/**
	 * Log - class name
	 */
	//	private static String TAG = "StyledFontHelper";

	private static Context mCtx = null;

	private static StyledFontHelper customFontInstances = null;

	private static Typeface bauhausBold = null;
	private static Typeface bauhausLightRegular = null;
	private static Typeface bauhausNormal = null;

	public StyledFontHelper(Context context) {
		mCtx = context;
		//		initFont();
	}

	public static StyledFontHelper getInstance(Context context) {
		if (customFontInstances == null) {
			customFontInstances = new StyledFontHelper(context);
		}
		return customFontInstances;
	}

	/**
	 * 
	 * @MethodName   : initFont
	 * @Date         : 2015. 5. 12. 
	 * @author       : KILHO 
	 * 
	 * @Method
	 *  ㄴ 폰트 초기화 
	 * @변경이력
	 */
	public static void initFont() {
		bauhausBold = Typeface.createFromAsset(mCtx.getAssets(), "bauhausBold.ttf");
		bauhausLightRegular = Typeface.createFromAsset(mCtx.getAssets(), "bauhausLightRegular.ttf");
		bauhausNormal = Typeface.createFromAsset(mCtx.getAssets(), "bauhausNormal.ttf");
	}

	/**
	 * 
	 * @MethodName   : getTyprFaceFont
	 * @Date         : 2015. 5. 12. 
	 * @author       : KILHO 
	 * @param fontName
	 * @return
	 * 
	 * @Method
	 *  ㄴ 폰트 가져오기 
	 * @변경이력
	 */
	public Typeface getTyprFaceFont(String fontName) {

		if ("bauhausBold.ttf".equals(fontName)) {
			if (bauhausBold == null) {
				return bauhausBold = Typeface.createFromAsset(mCtx.getAssets(), "bauhausBold.ttf");
			} else {
				return bauhausBold;
			}

		} else if ("bauhausLightRegular.ttf".equals(fontName)) {
			if (bauhausLightRegular == null) {
				return bauhausLightRegular = Typeface.createFromAsset(mCtx.getAssets(), "bauhausLightRegular.ttf");
			} else {
				return bauhausLightRegular;
			}

		} else if ("bauhausNormal.ttf".equals(fontName)) {
			if (bauhausNormal == null) {
				return bauhausNormal = Typeface.createFromAsset(mCtx.getAssets(), "bauhausNormal.ttf");
			} else {
				return bauhausNormal;
			}

		}else {
			if (bauhausNormal == null) {
				return bauhausNormal = Typeface.createFromAsset(mCtx.getAssets(), "bauhausNormal.ttf");
			} else {
				return bauhausNormal;
			}
		}
	}
}
