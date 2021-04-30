package com.ulling.sample.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ulling.lib.core.listener.OnSingleClickListener
import com.ulling.lib.core.ui.QcBaseActivity
import com.ulling.lib.core.utils.QcFragmentUtils
import com.ulling.sample.R
import com.ulling.sample.ui.main.MainFragment

/**
 *
 *
 * Class:
 * Created by KILHO on 2021-03-12 오후 5:31
 *
 * @version 1.0.0
 *
 * Description:
 *
 *  코틀린
 *  https://developer.android.com/kotlin/learn?hl=ko
 *
 *  코틀린 멀티플랫폼?
 *  https://kotlinlang.org/
 *  https://kotlinlang.org/lp/mobile/
 *  ㄴ 비지니스로직만 코틀린으로 공유 가능하고 Android, iOS 의 UI 코드는 각 플랫폼 코드를 사용해야한다.
 *  
 *
 *
 **/
class MainActivity2 : QcBaseActivity() {

    val count = 10

    override fun needGetLayoutId(): Int {
        return R.layout.main_activity
    }

    override fun needInitToOnCreate(): Boolean {
        var mMainFragment: MainFragment = MainFragment()
//            supportFragmentManager.beginTransaction()
//                    .replace(R.id.container, mMainFragment, mMainFragment.TAG)
//                    .commitAllowingStateLoss()

        QcFragmentUtils.addFragment(supportFragmentManager, mMainFragment, R.id.container, mMainFragment.TAG)
        return false
    }

    override fun optGetSavedInstanceState(savedInstanceState: Bundle?) {
    }

    override fun needResetData() {
        val answerString: String =
                if (count == 42) {
                    "I have the answer."
                } else if (count > 35) {
                    "The answer is close."
                } else {
                    "The answer eludes me."
                }

        println(answerString)

        val languageName: String? = null

        languageName?.length
        languageName.apply {

        }

        languageName.let {

        }

        languageName.also {

        }

        if (languageName != null) {
            // No need to write languageName?.toUpperCase()
            println(languageName.toUpperCase())
        }
    }

    override fun needUIBinding() {
    }

    override fun needUIEventListener() {
    }

    override fun needSubscribeUiFromViewModel() {
    }

    override fun needSubscribeUiClear() {
    }

    override fun needOnShowToUser() {
    }

    override fun optGetIntent(intent: Intent?) {
    }
}