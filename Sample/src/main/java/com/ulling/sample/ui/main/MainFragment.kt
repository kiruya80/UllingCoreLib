package com.ulling.sample.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ulling.lib.core.listener.OnSingleClickListener
import com.ulling.lib.core.ui.QcBaseFragment
import com.ulling.lib.core.utils.QcLog
import com.ulling.lib.core.utils.QcToast
import com.ulling.sample.R
import com.ulling.sample.data.LoginRepository
import com.ulling.sample.data.LoginResponseParser
import com.ulling.sample.data.User
import com.ulling.sample.databinding.MainFragmentBinding

class MainFragment : QcBaseFragment() {
    var viewBinding : MainFragmentBinding ?= null
//    companion object {
//        fun newInstance() = MainFragment()
//    }

    private lateinit var viewModel: LoginViewModel

    override fun needGetLayoutId(): Int {
        return R.layout.main_fragment
    }

    override fun optGetArgument(arguments: Bundle?, savedInstanceState: Bundle?) {
    }

    override fun needInitToOnCreate(): Boolean {
        return false
    }

    override fun optGetSavedInstanceState(savedInstanceState: Bundle?) {
    }

    override fun needResetData() {
    }

    override fun needUIBinding() {
          viewBinding = rootViewBinding as MainFragmentBinding
    }

    override fun needUIEventListener() {
        viewBinding?.btn1?.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View?) {
                QcLog.e("onSingleClick == =")
                viewModel.login("", "")
            }

        })

        viewBinding!!.btn2.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                QcLog.e("onSingleClick == =")
                viewModel.loginC("", "")
            }
        })

//        viewBinding?.btn1?.apply {
//            setOnClickListener(OnSingleClickListener {
//                QcLog.e("onSingleClick == =")
//            })
//        }

//        btn1.
//        viewModel.login("", "")
    }

//    private lateinit var itemSelector: Selector

    // Use the 'by activityViewModels()' Kotlin property delegate
    // from the fragment-ktx artifact
//    private val model: MainViewModel by activityViewModels()

    override fun needSubscribeUiFromViewModel() {
//        val responseParser: LoginResponseParser = LoginResponseParser()
//        var mLoginRepository: LoginRepository = LoginRepository(responseParser)


        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java).apply { init() }
//        viewModel.init()

//        val model: MyViewModel by viewModels()
//        viewModel.getUsers().observe(this, Observer<List<User>> { users ->
//            // update UI
//            QcLog.e("user ==== " + users.toString())
//        })

    }

    override fun needSubscribeUiClear() {
    }

    override fun needOnShowToUser() {
    }

    override fun optGetIntent(intent: Intent?) {
    }

}