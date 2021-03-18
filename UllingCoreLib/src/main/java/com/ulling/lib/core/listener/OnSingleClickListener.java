/*
 * Copyright (c) 2016. iUlling Corp.
 * Created By Kil-Ho Choi
 */
package com.ulling.lib.core.listener;

import android.os.SystemClock;
import android.view.View;
import com.ulling.lib.core.base.QcBaseApplication;

/**
 * @author : KILHO
 * @ProjectName : HummingPlayer_1.0
 * @FileName : OnSingleClickListener.java
 * @FilePath : com.ullim.util
 * @Date : 2015. 8. 6.
 * @프로그램 ㄴ 이중 클릭 방지 클릭 리스너
 * @변경이력
 */
public abstract class OnSingleClickListener implements View.OnClickListener {

    //    private static long MIN_CLICK_INTERVAL = 300;
    private long clickLastRunTime = 0;
    private long interval;

    public abstract void onSingleClick(View v);

    //    @Override
//    public final void onClick(View v) {
//        long currentClickTime = SystemClock.uptimeMillis();
//        long elapsedTime = currentClickTime - lastClickRunTime;
//
//        if (QcBaseApplication.CLICK_INTERVAL > 0) {
//            MIN_CLICK_INTERVAL = QcBaseApplication.CLICK_INTERVAL;
//        }
//
//        if (elapsedTime > MIN_CLICK_INTERVAL) {
//            lastClickRunTime = currentClickTime;
//            QcBaseApplication.LAST_CLICK_RUN_TIME = currentClickTime;
//            onSingleClick(v);
//        }
//    }
    @Override
    public final void onClick(View v) {
        long currentClickTime = SystemClock.uptimeMillis();
        if (QcBaseApplication.IS_CLICK_ALL) {
            interval = currentClickTime - QcBaseApplication.CLICK_LAST_RUN_TIME;
        } else {
            interval = currentClickTime - clickLastRunTime;
        }

//        QcLog.e(" QcBaseApplication.IS_CLICK_ALL ===== " + QcBaseApplication.IS_CLICK_ALL + " , " + currentClickTime);
//        QcLog.e("elapsedTime === " + elapsedTime + "  , " + QcBaseApplication.CLICK_INTERVAL + " , " + clickLastRunTime);

        if (interval > QcBaseApplication.CLICK_INTERVAL) {
            if (QcBaseApplication.IS_CLICK_ALL) {
                QcBaseApplication.CLICK_LAST_RUN_TIME = currentClickTime;
            } else {
                clickLastRunTime = currentClickTime;
            }
            onSingleClick(v);
        }
    }

    /**
     * currentClickTime 1000
     * elapsedTime
     * mLastClickTime
     *
     * @param v
     */
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        v.getBackground().setHotspot(event.getX(), event.getY());
//
//        long currentClickTime = SystemClock.uptimeMillis();
//        long elapsedTime = currentClickTime - mLastClickTime;
//        mLastClickTime = currentClickTime;
//        if (elapsedTime <= MIN_CLICK_INTERVAL)
//            return (false);
//        onSingleClick(v);
//
//        return (false);
//    }
}
