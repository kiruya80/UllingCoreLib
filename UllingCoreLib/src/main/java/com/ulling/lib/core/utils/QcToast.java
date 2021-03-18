/*
 * Copyright (c) 2016. iUlling Corp.
 * Created By Kil-Ho Choi
 */
package com.ulling.lib.core.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;
import com.ulling.lib.core.base.QcBaseApplication;

public class QcToast {

    private static QcToast SINGLE_U;
    private Toast toast;
    private Context qCon;

    public static synchronized QcToast getInstance() {
        if (QcBaseApplication.getInstance() == null) {
            QcLog.i("QcToast init failed !");
            return null;
        }
        if (SINGLE_U == null) {
            SINGLE_U = new QcToast();
        }
        return SINGLE_U;
    }

    private QcToast() {
        if (qCon == null) {
            qCon = QcBaseApplication.getInstance().getApplicationContext();
        }
        if (qCon != null) {
            toast = new Toast(qCon);
        }
        QcLog.i("QcToast init Success !!");
    }

    public void show(String toastStr) {
        show(toastStr, false);
    }

    public void show(String toastStr, boolean longDuration) {
        if (toastStr == null) {
            return;
        }
        if ("".equals(toastStr)) {
            return;
        }
        if (toast == null) {
            QcLog.e("toast is null !!");
            return;
        }
        try {
            toast.cancel();
            if (longDuration) {
                toast = Toast.makeText(qCon, toastStr, Toast.LENGTH_LONG);
            } else {
                toast = Toast.makeText(qCon, toastStr, Toast.LENGTH_SHORT);
            }
            toast.show();
            QcLog.w("Toast ==");
        } catch (Exception e) {
            QcLog.w("Exception ==" + e.toString());
        }
    }

    public void show(String toastStr, boolean longDuration, boolean isCenter) {
        if (toastStr == null) {
            return;
        }
        if ("".equals(toastStr)) {
            return;
        }
        if (toast == null) {
            QcLog.e("toast is null !!");
            return;
        }
        try {
            toast.cancel();
            if (longDuration) {
                toast = Toast.makeText(qCon, toastStr, Toast.LENGTH_LONG);
            } else {
                toast = Toast.makeText(qCon, toastStr, Toast.LENGTH_SHORT);
            }
            if (isCenter) {
                toast.setGravity(Gravity.CENTER, 0, 0);
            }
            toast.show();
            QcLog.w("Toast ==");
        } catch (Exception e) {
            QcLog.w("Exception ==" + e.toString());
        }
    }
}