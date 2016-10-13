/*
 * Copyright (c) 2016. iUlling Corp.
 * Created By Kil-Ho Choi
 */

package com.ulling.lib.util;

import android.content.Context;
import android.widget.Toast;

public class QcToast {
  private static QcToast SINGLE_U;

  public static WQcToast with(Context qCon, String toastStr) {
    if (SINGLE_U == null) {
      SINGLE_U = new QcToast();
    }
    return SINGLE_U.get(qCon, toastStr, false);
  }

  public static WQcToast with(Context qCon, String toastStr, boolean longDuration) {
    if (SINGLE_U == null) {
      SINGLE_U = new QcToast();
    }
    return SINGLE_U.get(qCon, toastStr, longDuration);
  }

  private WQcToast get(Context qCon, String toastStr, boolean longDuration) {
    return new WQcToast(qCon, toastStr, longDuration);
  }

  private class WQcToast {
    private Toast mToast;

    public WQcToast(Context qCon, String toastStr, boolean longDuration) {
      if (toastStr == null)
        return;
      if ("".equals(toastStr))
        return;
      try {
        mToast = new Toast(qCon);
        mToast.cancel();
        if (longDuration) {
          mToast = Toast.makeText(qCon, toastStr, Toast.LENGTH_LONG);
        } else {
          mToast = Toast.makeText(qCon, toastStr, Toast.LENGTH_SHORT);
        }
        mToast.show();
        QcLog.w("mToast ==");
      } catch (Exception e) {
        QcLog.w("Exception ==" + e.toString());
      }
    }
  }
}