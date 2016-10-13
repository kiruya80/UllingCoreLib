/*
 * Copyright (c) 2016. iUlling Corp.
 * Created By Kil-Ho Choi
 */

package com.ulling.firebasetest.fcm;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ulling.firebasetest.R;

public class FcmMainActivity extends AppCompatActivity {
  private static final String TAG = "MainActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_fcm_main);
    if (getIntent().getExtras() != null) {
      for (String key : getIntent().getExtras().keySet()) {
        Object value = getIntent().getExtras().get(key);
//                UtilLog.eLog();
        Log.d(TAG, "Key: " + key + " Value: " + value);
      }
    }
    // [END handle_data_extras]
    Button subscribeButton = (Button) findViewById(R.id.subscribeButton);
    subscribeButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // 토픽 생성, 몇시간후 생성됨
        FirebaseMessaging.getInstance().subscribeToTopic("news");
        // [END subscribe_topics]
        // Log and toast
        String msg = getString(R.string.msg_subscribed);
        Log.d(TAG, msg);
        Toast.makeText(FcmMainActivity.this, msg, Toast.LENGTH_SHORT).show();
      }
    });
    Button logTokenButton = (Button) findViewById(R.id.logTokenButton);
    logTokenButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // Get token
        String token = FirebaseInstanceId.getInstance().getToken();
        // Log and toast
        String msg = getString(R.string.msg_token_fmt, token);
        Log.d(TAG, msg);
        Toast.makeText(FcmMainActivity.this, msg, Toast.LENGTH_SHORT).show();
      }
    });
  }
}
