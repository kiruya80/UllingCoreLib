/*
 * Copyright (c) 2016. iUlling Corp.
 * Created By Kil-Ho Choi
 */

package com.ulling.firebasetest.fcm;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import android.util.Log;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

  private static final String TAG = "MyFirebaseIIDService";

  @Override
  public void onTokenRefresh() {
    Log.d(TAG, "onTokenRefresh ======================");
    String refreshedToken = FirebaseInstanceId.getInstance().getToken();
    Log.d(TAG, "Refreshed token: " + refreshedToken);
    sendRegistrationToServer(refreshedToken);
  }

  private void sendRegistrationToServer(String token) {
    // TODO: Implement this method to send token to your app server.
  }
}
