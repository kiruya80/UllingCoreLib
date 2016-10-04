package com.ulling.lib.Listener;

import android.os.SystemClock;
import android.view.View;

/**
 * 
 * @ProjectName  : HummingPlayer_1.0
 * @FileName     : OnSingleClickListener.java
 * @FilePath  : com.ullim.util
 * @Date         : 2015. 8. 6. 
 * @author       : KILHO 
 * 
 * @프로그램
 *  ㄴ 이중 클릭 방지 클릭 리스너 
 * @변경이력
 *
 */
public abstract class OnSingleClickListener implements View.OnClickListener {

	private static final long MIN_CLICK_INTERVAL = 300;

	private long mLastClickTime;

	@Override
	public final void onClick(View v) {
		long currentClickTime = SystemClock.uptimeMillis();
		long elapsedTime = currentClickTime - mLastClickTime;
		mLastClickTime = currentClickTime;

		if(elapsedTime <= MIN_CLICK_INTERVAL)
			return;

		onSingleClick(v);        
	}

	public abstract void onSingleClick(View v);
}
