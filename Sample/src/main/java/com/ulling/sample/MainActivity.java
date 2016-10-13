/*
 * Copyright (c) 2016. iUlling Corp.
 * Created By Kil-Ho Choi
 */

package com.ulling.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ulling.lib.util.QcBackPressClose;
import com.ulling.lib.util.QcLog;
import com.ulling.lib.util.QcToast;

/**
 *
 */
public class MainActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    QcLog.DEBUG_MODE = true;
    QcLog.e("TSET !!!!1");
    QcLog.d("TSET !!!!1");
    QcLog.i("TSET !!!!1");
    QcLog.v("TSET !!!!1");
    QcLog.w("TSET !!!!1");
//        QcLog.nativeHeap();
    QcToast.with(getApplication(), "Toast 111111");
  }

  /**
   *
   * @param dfef
   */
  private void setfdfef(int dfef) {

  }

  /**
   *
   */
  @Override
  public void onBackPressed() {
    if (QcBackPressClose.with(getApplicationContext()).isBackPress()) {
      super.onBackPressed();
    }
  }
}
