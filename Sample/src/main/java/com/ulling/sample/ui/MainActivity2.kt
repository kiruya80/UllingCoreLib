package com.ulling.sample.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ulling.lib.core.utils.QcFragmentUtils
import com.ulling.sample.R
import com.ulling.sample.ui.ui.main.MainFragment

class MainActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)


        if (savedInstanceState == null) {
        var mMainFragment:MainFragment = MainFragment()
//            supportFragmentManager.beginTransaction()
//                    .replace(R.id.container, mMainFragment, mMainFragment.TAG)
//                    .commitAllowingStateLoss()

            QcFragmentUtils.addFragment(supportFragmentManager, mMainFragment, R.id.container, mMainFragment.TAG)
        }


    }
}