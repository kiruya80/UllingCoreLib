package com.ulling.sample.ui.ui.main

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.lifecycle.Observer
import com.ulling.lib.core.ui.QcBaseFragment
import com.ulling.lib.core.utils.QcLog
import com.ulling.sample.R
import com.ulling.sample.ui.ui.User

class MainFragment : QcBaseFragment() {

//    companion object {
//        fun newInstance() = MainFragment()
//    }

    private lateinit var viewModel: MainViewModel
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
//                              savedInstanceState: Bundle?): View {
//        return inflater.inflate(R.layout.main_fragment, container, false)
//    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
//    }

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
    }

    override fun needUIEventListener() {
    }

//    private lateinit var itemSelector: Selector

    // Use the 'by activityViewModels()' Kotlin property delegate
    // from the fragment-ktx artifact
//    private val model: MainViewModel by activityViewModels()

    override fun needSubscribeUiFromViewModel() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

//        val model: MyViewModel by viewModels()
        viewModel.getUsers().observe(this, Observer<List<User>>{ users ->
            // update UI
            QcLog.e("user ==== " + users.toString())
        })
    }

    override fun needSubscribeUiClear() {
    }

    override fun needOnShowToUser() {
    }

    override fun optGetIntent(intent: Intent?) {
    }

}