package com.ulling.lib.core.ui;

import android.os.Bundle;

import com.ulling.lib.core.base.QcBaseApplication;
import com.ulling.lib.core.utils.QcLog;

/**
 * 현재 보이는 프레그먼트
 * 보이는 경우
 * needPageVisiableToUser가 호출된다
 */
public abstract class QcBaseShowLifeFragement extends QcBaseLifeFragment {
    /**
     * 뷰 초기화 체크
     */
    public boolean isViewPrepared;
    // 사용자에게 현재뷰가 보여지는 경우 플래그
//    private boolean isShowToUser;


    /**
     * 페이져뷰등에서 화면에 현재 프레그먼트가 보일때 실행됨
     */
    protected abstract void needOnShowToUser();

    protected abstract void needOnHiddenToUser();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // 뷰 초기화가 안된 경우 패스
        if (!isViewPrepared)
            return;

        QcLog.i("setUserVisibleHint == " + sectionPosition + " , " + isVisibleToUser + " ," + isViewPrepared);

        if (isVisibleToUser) {
            lazyFetchDataIfPrepared();
        } else {
            needOnHiddenToUser();
        }
    }

//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        QcLog.e("onViewCreated == ");
//
//        isViewPrepared = true;
//        baseActivity = (QcBaseLifeActivity) getActivity();
//
//        lazyFetchDataIfPrepared();
//    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        isViewPrepared = true;

        lazyFetchDataIfPrepared();
    }

    @Override
    public void optGetArgument(Bundle arguments, Bundle savedInstanceState) {
        if (arguments != null) {
            sectionPosition = arguments.getInt(ARG_SECTION_POSITION);
        } else {
            sectionPosition = -1;
        }
    }

    private void lazyFetchDataIfPrepared() {
        QcLog.i("lazyFetchDataIfPrepared   === " +
                getUserVisibleHint() + " , " + sectionPosition + " , " + isViewPrepared);
        QcBaseApplication.CLICK_LAST_RUN_TIME = 0;

        if (getUserVisibleHint() && isViewPrepared) {
            // 최초 한번만 로딩하고 이후에는 그대로인 경우 플래그
            needOnShowToUser();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        QcLog.e("onDestroyView == ");
        isViewPrepared = false;
        needOnHiddenToUser();

        Runtime.getRuntime().gc();
    }

    public int getSectionPosition() {
        return sectionPosition;
    }
}
