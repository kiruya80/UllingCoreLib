package com.ulling.lib.core.ui

import android.os.Bundle
import com.ulling.lib.core.base.QcBaseApplication
import com.ulling.lib.core.utils.QcLog

/**
 *
 *
 * Class:
 * Created by KILHO on 2021-03-11 오후 3:22
 *
 * @version 1.0.0
 *
 * Description:
 *
 * 사용자에게 보일때 프레그먼트
 *
 **/
abstract class QcBaseShowFragment : QcBaseFragment() {

    /**
     * 뷰 초기화 체크
     */
    var isViewPrepared = false
    // 사용자에게 현재뷰가 보여지는 경우 플래그
//    private boolean isShowToUser;

    // 사용자에게 현재뷰가 보여지는 경우 플래그
    //    private boolean isShowToUser;
    /**
     * 페이져뷰등에서 화면에 현재 프레그먼트가 보일때 실행됨
     */
    abstract override fun needOnShowToUser()

    abstract fun needOnHiddenToUser()

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        // 뷰 초기화가 안된 경우 패스
        if (!isViewPrepared)
            return
        QcLog.i("setUserVisibleHint == $sectionPosition , $isVisibleToUser ,$isViewPrepared")

        if (isVisibleToUser) {
            lazyFetchDataIfPrepared()
        } else {
            needOnHiddenToUser()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isViewPrepared = true
        lazyFetchDataIfPrepared()
    }

    override fun optGetArgument(arguments: Bundle?, savedInstanceState: Bundle?) {
        sectionPosition = arguments?.getInt(QcBaseLifeFragment.ARG_SECTION_POSITION) ?: -1
    }

    open fun lazyFetchDataIfPrepared() {
        QcLog.i("lazyFetchDataIfPrepared   === " +
                userVisibleHint + " , " + sectionPosition + " , " + isViewPrepared)
        QcBaseApplication.CLICK_LAST_RUN_TIME = 0
        if (userVisibleHint && isViewPrepared) {
            // 최초 한번만 로딩하고 이후에는 그대로인 경우 플래그
            needOnShowToUser()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        QcLog.e("onDestroyView == ")
        isViewPrepared = false
        needOnHiddenToUser()
        Runtime.getRuntime().gc()
    }

//    open fun getSectionPosition(): Int {
//        return sectionPosition
//    }
}