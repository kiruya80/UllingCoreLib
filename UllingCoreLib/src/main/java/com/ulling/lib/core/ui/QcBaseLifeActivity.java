package com.ulling.lib.core.ui;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.ulling.lib.core.utils.QcLog;

/**
 * view(화면 뷰) - viewmodel(view에서 이벤트에 따라 행동 및 model과 연동) - model(실데이터 소스)
 * <p>
 * View — ViewModeld에게 유저의 동작을 알려줍니다.
 * <p>
 * <p>
 * 뷰는 소비자로 공급자(뷰모델)이 누구인지 알아야한다
 * <p>savedInstanceState
 * ex) View는 앱에서 유저 인터페이스의 실질적인 부분입니다.
 * Activity , Fragment 안드로이드 View 도 이 View가 될 수 있습니다.
 * 이 View의 onResume() onPause() 에서 이벤트 소스를 바인딩, 언바인딩 하게됩니다.
 */
public abstract class QcBaseLifeActivity extends AppCompatActivity {
    public String TAG = getClass().getSimpleName();
    public Context qCon;
    public String APP_NAME;
    public boolean start = false;

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

//    public int needGetLayoutIdForLand() {
//        return 0;
//    }

    /**
     * 1-1.
     * <p>
     * 프레그먼트 UI 데이터 초기화
     */
    protected abstract boolean needInitToOnCreate();

    /**
     * 데이터 전달시 가져오기
     * LiveData로 활용하능한지는 체크해봐야함 !!
     * 또한 데이터가 필요한지도 확인 필요
     */
    protected abstract void optGetSavedInstanceState(Bundle savedInstanceState);

    /**
     * 인텐트 가져오기
     *
     * @param intent
     */
    protected abstract void optGetIntent(Intent intent);

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

    protected abstract void needOnShowToUser();

    protected void optOnActivityResultOk(int requestCode, Intent data) {
        QcLog.i("RESULT_OK requestCode == " + requestCode);
//        if (requestCode == REQUEST_ACT) {
//            String resultMsg = data.getStringExtra("result_msg");
//        }
    }

    protected void optOnActivityResultCancle() {
        QcLog.i("RESULT_CANCELED == ");
    }

    /**
     * Lifecycle
     *
     * onCreate() > ( onRestart() )  > onStart() > onResume() > onPause() > onStop() > onDestroy()
     */
    /**
     * Activity에서의 onCreate()와 비슷하나, ui관련 작업은 할 수 없다.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QcLog.i("onCreate");
        if (getApplication() != null)
            qCon = getApplication().getApplicationContext();
        setContentView(needGetLayoutId());

        if (needInitToOnCreate()) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        if (savedInstanceState != null)
            optGetSavedInstanceState(savedInstanceState);
        if (getIntent() != null)
            optGetIntent(getIntent());
        needResetData();

        needUIBinding();
        needUIEventListener();
        needSubscribeUiFromViewModel();
    }


    /**
     * layout id binding
     */
    public ViewDataBinding getViewDataBinding() {
        return DataBindingUtil.setContentView(this, needGetLayoutId());
    }

//    public ViewDataBinding getViewDataBindingForLand() {
//        return DataBindingUtil.setContentView(this, needGetLayoutIdForLand());
//    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        setContentView(needGetLayoutId());
//    }

    /**
     *
     */
    @Override
    public void onRestart() {
        super.onRestart();
        QcLog.i("onRestart == ");
    }

    /**
     * 액티비티가 사용자에게 표시되기 직전에 호출
     * 액티비티가 전경으로 나오면 onResume()
     * 액티비티가 숨겨지면 onStop()
     */
    @Override
    public void onStart() {
        super.onStart();
        QcLog.i("onStart == ");
        needOnShowToUser();
    }

    /**
     * 액티비티가 시작되고 사용자와 상호 작용하기 직전에 호출
     */
    @Override
    public void onResume() {
        super.onResume();
        QcLog.i("onResume == ");
    }

    /**
     * 시스템이 다른 액티비티를 재개하기 직전에 호출
     * CPU를 소모하는 기타 작업들을 중단하는 등 여러 가지 용도에 사용
     * 이 메서드는 무슨 일을 하든 매우 빨리 끝내야함
     */
    @Override
    public void onPause() {
        super.onPause();
        QcLog.i("onPause == ");
//        needSubscribeUiClear();
    }

    /**
     * 액티비티가 더 이상 사용자에게 표시되지 않게 되면 호출
     * 액티비티가 다시 사용자와 상호 작용하면 onRestart()
     */
    @Override
    public void onStop() {
        super.onStop();
        QcLog.i("onStop == ");
    }

    /**
     * 액티비티가 사라지면 onDestroy()
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        QcLog.i("onDestroy == ");
        needResetData();
        needSubscribeUiClear();
    }

    /**
     * //    int REQUEST_ACT = 10;
     * 호출
     * //    Intent intent = new Intent(Activity_A.this, Activity_B.class);
     * //    startActivityForResult(intent, REQUEST_ACT);
     * <p>
     * 돌아오기
     * //    Intent intent = new Intent();
     * //    intent.putExtra("result_msg","결과가 넘어간다 얍!");
     * //    setResult(RESULT_OK, intent);
     * //    finish();
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            optOnActivityResultOk(requestCode, data);
        } else {
            optOnActivityResultCancle();
        }
    }

    /**
     * 전체 화면 소프트키 감추기
     */
    public void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LOW_PROFILE |
                        View.SYSTEM_UI_FLAG_IMMERSIVE
        );
    }

    /**
     * 원래대로 돌리기
     */
    public void showSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
    }
}
