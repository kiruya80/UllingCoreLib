package com.ulling.lib.core.ui

import android.os.Bundle

/**
 *
 *
 * Class:
 * Created by KILHO on 2021-03-11 오후 2:30
 *
 * @version 1.0.0
 *
 * Description:
 *
 * 액티비티, 프레그먼트 필수 인터페이스
 **/
interface QcInNeed {
    /**
     * 1-0.
     * <p>
     * 설정한 레이아웃 아이디를 가지고
     * onCreateView 에서 자동으로 바인딩된다
     * rootViewBinding = DataBindingUtil.inflate(inflater, needGetLayoutId(), container, false);
     *
     * @return 레이아웃 아이디 클래스이름을 기준으로 생성
     * <p>
     * ex) LiveDataFragment -> R.layout.frag_user_profile;
     */
    fun needGetLayoutId(): Int

    /**
     * 1-1.
     * <p>
     * 프레그먼트 UI 데이터 초기화
     * 리턴 - 스테이터스바 유무  - true : 전체화면
     */
    fun needInitToOnCreate(): Boolean


    /**
     * 데이터 전달시 가져오기
     * LiveData로 활용하능한지는 체크해봐야함 !!
     * 또한 데이터가 필요한지도 확인 필요
     */
    fun optGetSavedInstanceState(savedInstanceState: Bundle?)

    /**
     * 1-2.
     *
     *
     * 프레그먼트 UI 데이터 리셋
     */
    fun needResetData()

    /**
     * 3.
     *
     *
     * UI에서 필요한 데이터 바인딩
     * View객체에 접근하여 데이터 연결한다.
     */
    fun needUIBinding()

    /**
     * 4.
     * 접근한 View에 이벤트에 따른 동작 설정
     * 버튼 및 기타 UI이벤트 설정
     */
    fun needUIEventListener()

    /**
     * 6.
     *
     *
     * 데이터모델로부터 변화되는 데이터를 구독하고
     * 데이터를 UI에 연결한다
     */
    fun needSubscribeUiFromViewModel()

    /**
     * 7. 뷰모델 연결 중지
     */
    fun needSubscribeUiClear()

    fun needOnShowToUser()
}