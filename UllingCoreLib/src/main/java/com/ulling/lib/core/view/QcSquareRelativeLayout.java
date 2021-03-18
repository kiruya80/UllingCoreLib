package com.ulling.lib.core.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * @author : KILHO
 * @project : UllingMvpSample
 * @date : 2017. 7. 19.
 * @description :
 * @since :
 */
public final class QcSquareRelativeLayout extends RelativeLayout {

    public QcSquareRelativeLayout(Context context) {
        super(context);
    }

    public QcSquareRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public QcSquareRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
