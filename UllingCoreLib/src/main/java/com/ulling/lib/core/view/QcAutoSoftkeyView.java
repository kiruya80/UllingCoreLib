package com.ulling.lib.core.view;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import com.ulling.lib.core.R;

/**
 * 전체 화면으로 되는 경우 소프트키 일정시간 뒤 안보이기
 */
public class QcAutoSoftkeyView extends View implements View.OnSystemUiVisibilityChangeListener {

    private final static int HIDE_SECONDS = 5;
    private int visiableTime = 0;

    public enum Direction {both, portrait, landscape}

    private Direction direction;


    private int mLastSystemUiVis;
    private Runnable mNavHider = new Runnable() {
        @Override
        public void run() {
            setNavVisibility(false);
        }

    };

    public QcAutoSoftkeyView(Context context) {
        super(context);
    }

    public QcAutoSoftkeyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getTypeOrientation(context, attrs);

        setOnSystemUiVisibilityChangeListener(this);
        setNavVisibility(false);
    }

    public QcAutoSoftkeyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        getTypeOrientation(context, attrs);

        setOnSystemUiVisibilityChangeListener(this);
        setNavVisibility(false);
    }

    @Override
    public void onSystemUiVisibilityChange(int visibility) {
        int diff = mLastSystemUiVis ^ visibility;
        mLastSystemUiVis = visibility;
        if ((diff & SYSTEM_UI_FLAG_HIDE_NAVIGATION) != 0
            && (visibility & SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0) {
            setNavVisibility(true);
        }
    }

    private void getTypeOrientation(Context context, AttributeSet attrs) {
        TypedArray arr = context.getTheme()
            .obtainStyledAttributes(attrs, R.styleable.QcAutoSoftkeyView, 0, 0);
        try {

            visiableTime = arr.getInteger(R.styleable.QcAutoSoftkeyView_orientation, 0);
            direction = Direction.values()[arr
                .getInt(R.styleable.QcAutoSoftkeyView_orientation, 0)];
        } catch (Exception e) {
        } finally {
            arr.recycle();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        switch (newConfig.orientation) {
            case Configuration.ORIENTATION_PORTRAIT: // 1
                if (Direction.landscape == direction) {
                    setVisiable(true);
                } else if (Direction.portrait == direction) {
                    setVisiable(false);
                } else {
                    setVisiable(false);
                }
                break;
            case Configuration.ORIENTATION_LANDSCAPE: // 2
                if (Direction.portrait == direction) {
                    setVisiable(true);
                } else if (Direction.landscape == direction) {
                    setVisiable(false);
                } else {
                    setVisiable(false);
                }
                break;


        }
    }

    /**
     * @param visible ㄴ true 보이기 ㄴ false 안보이기
     *                <p>
     *                1: 세로 2: 가로
     */
    void setNavVisibility(boolean visible) {
        if (Direction.portrait == direction) {
            if (getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT) {
                setVisiable(visible);
            } else {
                setVisiable(true);
            }
        } else if (Direction.landscape == direction) {
            if (getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
                setVisiable(visible);
            } else {
                setVisiable(true);
            }
        } else {
            setVisiable(visible);
        }
    }

    private void setVisiable(boolean visible) {
        int newVis = SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | SYSTEM_UI_FLAG_LAYOUT_STABLE;
        if (!visible) {
            newVis |= SYSTEM_UI_FLAG_LOW_PROFILE
                | SYSTEM_UI_FLAG_FULLSCREEN
                | SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }

        if (visible) {
            Handler handler = getHandler();
            if (handler != null) {
                handler.removeCallbacks(mNavHider);
                if (visiableTime != 0) {
                    handler.postDelayed(mNavHider, visiableTime * 1000);
                } else {
                    handler.postDelayed(mNavHider, HIDE_SECONDS * 1000);
                }
            }
        }
        setSystemUiVisibility(newVis);
    }

}