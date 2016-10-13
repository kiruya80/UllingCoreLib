/*
 * Copyright (c) 2016. iUlling Corp.
 * Created By Kil-Ho Choi
 */

package com.ulling.firebasetest.dynamiclink;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ulling.firebasetest.R;

/**
 * Created by P100651 on 2016-09-30.
 */
public class DeepLinkActivity extends AppCompatActivity {
  public String TAG = getClass().getSimpleName();
  public final Context mCtx = DeepLinkActivity.this;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_fcm_main);
    if (getIntent().getExtras() != null) {
      for (String key : getIntent().getExtras().keySet()) {
        Object value = getIntent().getExtras().get(key);
        Log.d(TAG, "Key: " + key + " Value: " + value);
      }
    }
  }
}

