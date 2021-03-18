package com.ulling.lib.core.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.ulling.lib.core.base.QcBaseApplication
import com.ulling.lib.core.utils.QcLog

/**
 *
 *
 * Class:
 * Created by KILHO on 2021-03-11 오후 2:39
 *
 * @version 1.0.0
 *
 * Description:
 *
 * 베이스 액티비티
 *
 **/
abstract class QcBaseActivity : AppCompatActivity(), QcInNeed, QcInOption {
    val TAG: String = javaClass.simpleName
    var APP_NAME: String? = null

    var qCon: Context? = null
//    var start = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (application != null)
            qCon = application.applicationContext

        setContentView(needGetLayoutId())

        if (needInitToOnCreate()) {
            // 스테이터스바 유무
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }

        savedInstanceState?.let { optGetSavedInstanceState(it) }

        intent?.let { optGetIntent(it) }

        needResetData()

        needUIBinding()
        needUIEventListener()
        needSubscribeUiFromViewModel()
    }

    /**
     * layout id binding
     */
    open fun getViewDataBinding(): ViewDataBinding? {
        return DataBindingUtil.setContentView(this, needGetLayoutId())
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
//        textView.text = savedInstanceState?.getString(TEXT_VIEW_KEY)
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
//        outState?.run {
//            putString(GAME_STATE_KEY, gameState)
//            putString(TEXT_VIEW_KEY, textView.text.toString())
//        }
        super.onSaveInstanceState(outState)
    }

    /**
     *
     */
    override fun onRestart() {
        super.onRestart()
        QcLog.i("onRestart == ")
    }

    /**
     * 액티비티가 사용자에게 표시되기 직전에 호출
     * 액티비티가 전경으로 나오면 onResume()
     * 액티비티가 숨겨지면 onStop()
     */
    override fun onStart() {
        super.onStart()
        QcLog.i("onStart == ")
        needOnShowToUser()
    }

    /**
     * 액티비티가 시작되고 사용자와 상호 작용하기 직전에 호출
     */
    override fun onResume() {
        super.onResume()
        QcLog.i("onResume == ")
        QcBaseApplication.CLICK_LAST_RUN_TIME = 0
    }

    /**
     * 시스템이 다른 액티비티를 재개하기 직전에 호출
     * CPU를 소모하는 기타 작업들을 중단하는 등 여러 가지 용도에 사용
     * 이 메서드는 무슨 일을 하든 매우 빨리 끝내야함
     */
    override fun onPause() {
        super.onPause()
        QcLog.i("onPause == ")
    }

    /**
     * 액티비티가 더 이상 사용자에게 표시되지 않게 되면 호출
     * 액티비티가 다시 사용자와 상호 작용하면 onRestart()
     */
    override fun onStop() {
        super.onStop()
        QcLog.i("onStop == ")
    }

    /**
     * 액티비티가 사라지면 onDestroy()
     */
    override fun onDestroy() {
        super.onDestroy()
        QcLog.i("onDestroy == ")
        needResetData()
        needSubscribeUiClear()
    }

    /**
     * //    int REQUEST_ACT = 10;
     * 호출
     * //    Intent intent = new Intent(Activity_A.this, Activity_B.class);
     * //    startActivityForResult(intent, REQUEST_ACT);
     *
     *
     * 돌아오기
     * //    Intent intent = new Intent();
     * //    intent.putExtra("result_msg","결과가 넘어간다 얍!");
     * //    setResult(RESULT_OK, intent);
     * //    finish();
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            optOnActivityResultOk(requestCode, data)
        } else {
            optOnActivityResultCancle()
        }
    }

//    override fun onWindowFocusChanged(hasFocus: Boolean) {
//        super.onWindowFocusChanged(hasFocus)
//        if (hasFocus) hideSystemUI()
//    }

    /**
     * 전체 화면 소프트키 감추기
     */
    open fun hideSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    /**
     * 원래대로 돌리기
     */
    open fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

}