/*
 * Copyright (c) 2016. iUlling Corp.
 * Created By Kil-Ho Choi
 */
package com.ulling.lib.core.utils;

import android.content.Context;
import com.ulling.lib.core.base.QcBaseApplication;

/**
 *
 */
public class QcBackPressClose {

    private static QcBackPressClose SINGLE_U;
    private Context qCon;
    private long backKeyPressedTime = 0;
    private static final int BACK_KEY_TIMEOUT = 2000;

    public static synchronized QcBackPressClose getInstance() {
        if (QcBaseApplication.getInstance() == null) {
            QcLog.i("QcBackPressClose init failed !");
            return null;
        }
        if (SINGLE_U == null) {
            SINGLE_U = new QcBackPressClose();
        }
        return SINGLE_U;
    }

    private QcBackPressClose() {
        if (qCon == null) {
            qCon = QcBaseApplication.getInstance().getApplicationContext();
        }
    }

    public boolean isBackPress(String backKeyMsg) {
        if (System.currentTimeMillis() > backKeyPressedTime + BACK_KEY_TIMEOUT) {
            backKeyPressedTime = System.currentTimeMillis();
            if (qCon != null) {
//                    QcToast.with(qCtx, backKeyMsg, false);
                QcToast.getInstance().show(backKeyMsg, false);
            }
            return false;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + BACK_KEY_TIMEOUT) {
            return true;
        }
        return false;
    }

    public boolean isBackPress(int backKeyMsgId) {
        if (System.currentTimeMillis() > backKeyPressedTime + BACK_KEY_TIMEOUT) {
            backKeyPressedTime = System.currentTimeMillis();
            if (qCon != null) {
//                    QcToast.with(qCtx, qCtx.getResources().getString(backKeyMsgId), false);
                QcToast.getInstance()
                    .show(QcBaseApplication.getInstance().getResources().getString(backKeyMsgId),
                        false);
            }
            return false;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + BACK_KEY_TIMEOUT) {
            return true;
        }
        return false;
    }

//    public static WQcBackPressClose with(Context qCtx) {
//        if (SINGLE_U == null) {
//            SINGLE_U = new QcBackPressClose();
//        }
//        return SINGLE_U.get(qCtx);
//    }

//    private WQcBackPressClose get(Context qCtx) {
//        return new WQcBackPressClose(qCtx);
//    }
//
//    public class WQcBackPressClose {
//        private final Context qCtx;
//
//        public WQcBackPressClose(Context qCtx) {
//            this.qCtx = qCtx;
//        }
//
//        public boolean isBackPress(String backKeyMsg) {
//            if (System.currentTimeMillis() > backKeyPressedTime + BACK_KEY_TIMEOUT) {
//                backKeyPressedTime = System.currentTimeMillis();
//                if (qCtx != null) {
////                    QcToast.with(qCtx, backKeyMsg, false);
//                    QcToast.getInstance().show(backKeyMsg, false);
//                }
//                return false;
//            }
//            if (System.currentTimeMillis() <= backKeyPressedTime + BACK_KEY_TIMEOUT) {
//                return true;
//            }
//            return false;
//        }
//
//        public boolean isBackPress(int backKeyMsgId) {
//            if (System.currentTimeMillis() > backKeyPressedTime + BACK_KEY_TIMEOUT) {
//                backKeyPressedTime = System.currentTimeMillis();
//                if (qCtx != null) {
////                    QcToast.with(qCtx, qCtx.getResources().getString(backKeyMsgId), false);
//                    QcToast.getInstance().show(QbaseApplication.getInstance().getResources().getString(backKeyMsgId), false);
//                }
//                return false;
//            }
//            if (System.currentTimeMillis() <= backKeyPressedTime + BACK_KEY_TIMEOUT) {
//                return true;
//            }
//            return false;
//        }
//    }
}