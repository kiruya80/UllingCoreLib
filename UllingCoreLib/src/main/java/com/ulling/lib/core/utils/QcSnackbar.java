package com.ulling.lib.core.utils;

import android.view.View;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;

/**
 * Created by P100651 on 2017-07-11.
 */
public class QcSnackbar {

    private static Snackbar mSnackbar;

    public static Snackbar make(@NonNull View view, @NonNull CharSequence text,
        boolean longDuration) {
        if (longDuration) {
            mSnackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG);
        } else {
            mSnackbar = Snackbar.make(view, text, Snackbar.LENGTH_SHORT);
        }
        return mSnackbar;
    }

    public static void show(@NonNull CharSequence actionStr, View.OnClickListener listener) {
        mSnackbar.setAction(actionStr, listener).show();
    }
}
