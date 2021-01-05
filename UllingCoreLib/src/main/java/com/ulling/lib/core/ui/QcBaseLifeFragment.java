package com.ulling.lib.core.ui;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ulling.lib.core.utils.QcLog;

import static android.app.Activity.RESULT_OK;

/**
 * 기본 프레그먼트
 * 필수적인 요소들만 개발자가
 * 오버라이드하여 사용할수 있게 만든 베이스 프레그먼트
 */
public abstract class QcBaseLifeFragment extends Fragment {
    public String TAG = getClass().getSimpleName();
    public Context qCon;
    public String APP_NAME;
    public QcBaseLifeActivity baseActivity;
    public int sectionPosition = 0;
    public static final String ARG_SECTION_POSITION = "ARG_SECTION_POSITION";
    public boolean fragStrt = false;

    /**
     * 레이아웃 내 설정한 아이디들을 바인딩하는 클래스
     * <p>
     * 프레그먼트와 맞는 뷰데이터바인딩 클래스로 형변환 해야함
     */
    private ViewDataBinding rootViewBinding;

    /**
     * 바인딩된 뷰데이터바인딩 가져오기
     * <p>
     * Binding클래스 이름의 생성은 파스칼표기법 기준으로 변경됩니다.
     * 예를들어 ted_park_activity.xml 파일은 TedParkActivityBinding 클래스를 생성시킵니다.
     */
    public ViewDataBinding getViewBinding() {
        return rootViewBinding;
    }

    /**
     * 필수
     * need~ 시작
     */

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
    protected abstract int needGetLayoutId();


    /**
     * 1-1.
     * <p>
     * 프레그먼트 UI 데이터 초기화
     */
    protected abstract void needInitToOnCreate();

    /**
     * 데이터 전달시 가져오기
     * LiveData로 활용하능한지는 체크해봐야함 !!
     * 또한 데이터가 필요한지도 확인 필요
     */
    protected abstract void optGetArgument(Bundle arguments, Bundle savedInstanceState);

    /**
     * 1-2.
     * <p>
     * 프레그먼트 UI 데이터 리셋
     */
    protected abstract void needResetData();

    /**
     * 3.
     * <p>
     * UI에서 필요한 데이터 바인딩
     * View객체에 접근하여 데이터 연결한다.
     */
//    protected abstract void needUIInflate();
    protected abstract void needUIBinding();

    /**
     * 4.
     * 접근한 View에 이벤트에 따른 동작 설정
     * 버튼 및 기타 UI이벤트 설정
     */
    protected abstract void needUIEventListener();


    /**
     * 6.
     * <p>
     * 데이터모델로부터 변화되는 데이터를 구독하고
     * 데이터를 UI에 연결한다
     */
    protected abstract void needSubscribeUiFromViewModel();

    /**
     * 7. 뷰모델 연결 중지
     */
    protected abstract void needSubscribeUiClear();


    /**
     * Lifecycle
     *
     * onAttach() > onCreateView() > onStart() > onStop() > onResume() > onPause() > onDestroyView() > onDetach()
     */
    /**
     * Fragment가 생성된 시점에 호출됩니다.
     * Activity의 onCreate메소드가 아직 완료된 시점이 아니라서 유저 인터페이스와 관련있는 것을 제외한 Fragment에서 사용되는 리소스들이 초기화됩니다.
     * ui관련 작업은 할 수 없다.
     * 유저 인터페이스와 관련된 처리는 onActivityCreated 메소드에서 해주어야 합니다.!!
     * <p>
     * <p>
     * Fragment가 paused 또는 stop되었다가
     * 다시 resume되었을 때 유지하고 싶은 Fragment의 컴포넌트들를 여기서 초기화 해주어야 합니다.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QcLog.i("onCreate");

        if (getActivity() != null)
            qCon = getActivity().getApplicationContext();

        needInitToOnCreate();
        optGetArgument(getArguments(), savedInstanceState);
        needResetData();
    }

    /**
     * Layout을 inflater을하여 View작업을 하는곳이다.
     * <p>
     * onStop / onDestroyView 에서 돌아오는 경우 호출됨
     * <p>
     * Fragment의 유저 인터페이스가 화면에 그려지는 시점에 호출됩니다. (사용자 UI를 처음 그리는 시점에서 호출)
     * XML 레이아웃을 inflate하여 Fragment를 위한 View를 생성하고 Fragment 레이아웃의 root에 해당되는 View를 Activity에게 리턴해야 합니다.
     * inflate란 XML 레이아웃에 정의된 뷰나 레이아웃을 읽어서 메모리상의 view 객체를 생성해주는 겁니다.
     * 여기서 view를 리턴했다면, view가 release될때 onDestroyView()가 호출됩니다.
     * 유저 인터페이스 없는 Fragment의 경우에는 null을 리턴하면 됩니다.
     */
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        QcLog.i("onCreateView == ");
        if (rootViewBinding == null)
            rootViewBinding = DataBindingUtil.inflate(inflater, needGetLayoutId(), container, false);
//        View view = viewBinding.getRoot();
//        needUIDataBinding();
        return rootViewBinding.getRoot();
    }

    /**
     * Activity에서 Fragment를 모두 생성하고 난다음 호출 된다.
     * Activity의 onCreate()에서 setContentView()한 다음이라고 생각 하면 쉽게 이해 될것 같다.
     * 여기서 부터는 ui변경작업이 가능하다.
     * <p>
     * <p>
     * <p>
     * Activity의 onCreate()를 완료되고 fragment의 View 생성이 완료했을때 호출됩니다.
     * Activity와 Fragment의 View가 모두 생성된 시점이라 findViewById()를 사용하여
     * !! View 객체에 접근하는게 가능합니다.
     * inflate된 레이아웃 내에서 R.java에 할당되어있는 주어진 ID를 가지고 view를 찾습니다.
     * !! 또한 Context객체를 요구하는 객체를 초기화 할 수 있습니다.
     * <p>
     * 유저 인터페이스와 관련된 처리는 onActivityCreated 메소드에서 해주어야 합니다.
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        QcLog.e("onActivityCreated == ");
//        optGetArgument();

        if (rootViewBinding != null) {
            baseActivity = (QcBaseLifeActivity) getActivity();
            needUIBinding();
            needUIEventListener();
            needSubscribeUiFromViewModel();
        }

    }

    /**
     * 액티비티가 사용자에게 표시되기 직전에 호출
     * 액티비티가 전경으로 나오면 onResume()
     * 액티비티가 숨겨지면 onStop()
     * <p>
     * Fragment가 사용자에게 보여질때 호출되며 아직 사용자와 상호작용은 불가능한 상태입니다.
     * (fragment가 속한 activity가 start된거랑 관련있습니다.)
     */
    @Override
    public void onStart() {
        super.onStart();
        QcLog.i("onStart == ");
    }

    /**
     * 액티비티가 시작되고 사용자와 상호 작용하기 직전에 호출
     * <p>
     * fragment가 사용자에게 보여지고 실행 중일때 호출되며 사용자와 상호작용할 수 있는 상태입니다. (fragment가 속한 activity가 resume된거랑 관련있습니다.)
     * <p>
     * 보통 onResume에서 자원을 할당하고 onPause에서 자원을 해제해줍니다.
     */
    @Override
    public void onResume() {
        super.onResume();
        QcLog.i("onResume == ");
    }

    /**
     * Fragment를 종료한다는 첫 번째 신호
     * 저장되어야 할 것들을 저장 시켜야함
     * <p>
     * Activity가 pause되어 fragment가 더이상 사용자와 상호작용하지 않을 때이다.
     */
    @Override
    public void onPause() {
        super.onPause();
        QcLog.i("onPause == ");
    }

    /**
     * 액티비티가 더 이상 사용자에게 표시되지 않게 되면 호출
     * 액티비티가 다시 사용자와 상호 작용하면 onRestart()
     * <p>
     * Activity가 stop되었거나 fragment의 operation이 activity에 의해 수정되었을 경우로 fragment가 더이상 사용자에게 보여지지 않을 때입니다.
     */
    @Override
    public void onStop() {
        super.onStop();
        QcLog.i("onStop == ");
    }

    /**
     * 사용자 UI를 제거하는 시점에서 호출
     * View 리소스를 해제 해주는 역할 구현
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        QcLog.i("onDestroyView == ");
    }

    /**
     * 액티비티가 사라지면 onDestroy()
     * <p>
     * fragment를 더 이상 사용하지 않을때 호출되며 activity와 연결이 끊어진 상태는 아니지만 fragment는 동작을 하지 않는 상태입니다.
     * 시스템에서 onDestroy가 항상 호출되는 것을 보장해주지 않습니다.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        QcLog.i("onDestroy == ");
        needResetData();
        needSubscribeUiClear();
    }

    /**
     * Fragment가 Activity에 제거 될 때 호출f
     * <p>
     * fragment가 activity와의 연결이 끊어지기 전에 호출되며 fragment의 view hierarchy가 더 이상 존재하지 않게 됩니다.
     * 부모 activity가 full 라이프사이클을 완료하지 않고 종료되었다면 onDetach()는 호출되지 않습니다.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        QcLog.i("onDetach == ");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            optOnActivityResultOk(requestCode, data);
        } else {
            optOnActivityResultCancle();
        }
    }

    protected void optOnActivityResultOk(int requestCode, Intent data) {
        QcLog.i("RESULT_OK requestCode == " + requestCode);
//        if (requestCode == REQUEST_ACT) {
//            String resultMsg = data.getStringExtra("result_msg");
//        }
    }

    protected void optOnActivityResultCancle() {
        QcLog.i("RESULT_CANCELED == ");
    }

}
