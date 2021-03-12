package com.ulling.sample.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ulling.lib.core.ui.QcBaseActivity
import com.ulling.lib.core.utils.QcFragmentUtils
import com.ulling.sample.R
import com.ulling.sample.ui.main.MainFragment

class MainActivity2 : QcBaseActivity() {


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