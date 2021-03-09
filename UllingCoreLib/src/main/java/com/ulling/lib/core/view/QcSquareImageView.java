package com.ulling.lib.core.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * @author : KILHO
 * @project : UllingMvpSample
 * @date : 2017. 7. 19.
 * @description :
 * @since :
 */
public final class QcSquareImageView extends AppCompatImageView {
    public QcSquareImageView(Context context) {
        super(context);
    }

    public QcSquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public QcSquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
