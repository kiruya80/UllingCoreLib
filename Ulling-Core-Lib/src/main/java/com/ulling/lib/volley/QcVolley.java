/*
 * Copyright (c) 2016. iUlling Corp.
 * Created By Kil-Ho Choi
 */

/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.ulling.lib.volley;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;

public class QcVolley {
  public static final String TAG = QcVolley.class.getSimpleName();
  private static Context mCtx;

  private static RequestQueue mRequestQueue;
//	private static SimpleImageLoader mSimpleImageLoader;

  private QcVolley() {
    // no instances
  }

  /**
   *
   * @MethodName   : init
   * @Date         : 2015. 3. 11.
   * @author       : KILHO
   * @param context
   *
   * @Method
   *  ㄴ 발리 초기화
   * @변경이력
   */
  public static void init(Context context, boolean debug) {
//		UtilLog.eLog(TAG, "MyVolley init complete !");

    mCtx = context;
//		VolleyLog.setDebugMode(Define.DEBUG_FLAG);
    VolleyLog.DEBUG = debug;
    reSetRequestQueue();
//		reSetLruImageCacheImageLoader();  
  }

  /**
   *
   * @MethodName   : reSetRequestQueue
   * @Date         : 2015. 3. 11.
   * @author       : KILHO
   * @return
   *
   * @Method
   * RequestQueue 리셋
   *
   * @변경이력
   */
  public static synchronized RequestQueue reSetRequestQueue() {
    return mRequestQueue = Volley.newRequestQueue(mCtx);
  }


  /**
   *
   * @MethodName   : getRequestQueue
   * @Date         : 2015. 3. 11.
   * @author       : KILHO
   * @return
   *
   * @Method
   *
   * @변경이력
   */
  public static synchronized RequestQueue getRequestQueue() {
    if (mRequestQueue != null) {
      return mRequestQueue;
    } else {
      throw new IllegalStateException("RequestQueue not initialized");
    }
  }


  /**
   *
   * @MethodName   : reSetLruImageCacheImageLoader
   * @Date         : 2015. 3. 11.
   * @author       : KILHO
   * @return
   *
   * @Method
   *  ㄴ ImageLoader 리셋
   * @변경이력
   */
//	public static synchronized SimpleImageLoader reSetLruImageCacheImageLoader() {	
//		mSimpleImageLoader = new SimpleImageLoader(mRequestQueue, new LruImageCache());
//
//		if (mSimpleImageLoader != null) {
//			mSimpleImageLoader.clearCache();
//			return mSimpleImageLoader;
//		} else {
//			throw new IllegalStateException("reSetMemoryImageLoader not initialized");
//		}
//	}


  /**
   *
   * @MethodName   : getImageLoader
   * @Date         : 2015. 3. 11.
   * @author       : KILHO
   * @return
   *
   * @Method
   *
   * @변경이력
   */
//	public static synchronized SimpleImageLoader getImageLoader() {
//		if (mSimpleImageLoader != null) {
//			return mSimpleImageLoader;
//		} else {
//			throw new IllegalStateException("ImageLoader not initialized");
//		}
//	}
}
