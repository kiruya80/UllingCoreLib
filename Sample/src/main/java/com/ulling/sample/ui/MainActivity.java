package com.ulling.sample.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.ulling.lib.core.listener.OnSingleClickListener;
import com.ulling.lib.core.ui.QcBaseActivity;
import com.ulling.lib.core.utils.QcLog;
import com.ulling.lib.core.utils.QcToast;
import com.ulling.sample.R;
import com.ulling.sample.databinding.ActivityMainBinding;

import org.jetbrains.annotations.Nullable;

public class MainActivity extends QcBaseActivity {
    private ActivityMainBinding viewBinding;


    @Override
    public int needGetLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public boolean needInitToOnCreate() {
        return false;
    }

    @Override
    public void optGetSavedInstanceState(@Nullable Bundle savedInstanceState) {

    }


    @Override
    public void needResetData() {

    }

    @Override
    public void needUIBinding() {
        viewBinding =  (ActivityMainBinding)getViewDataBinding();


    }

    @Override
    public void needUIEventListener() {
        viewBinding.btn1.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                QcToast.getInstance().show("버튼 1");
            }
        });
//        viewBinding.btn1.setOnClickListener((OnSingleClickListener) v->
//                QcToast.getInstance().show("버튼 1");
//        });
        viewBinding.btn2.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                QcToast.getInstance().show("버튼 2");
            }
        });
    }

    @Override
    public void needSubscribeUiFromViewModel() {

    }

    @Override
    public void needSubscribeUiClear() {

    }

    @Override
    public void needOnShowToUser() {

    }


    @Override
    public void optGetIntent(@Nullable Intent intent) {

    }
}