/*
 * Copyright (c) 2016. iUlling Corp.
 * Created By Kil-Ho Choi
 */

package com.ulling.firebasetest.fcm;

import static android.R.id.message;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.ulling.firebasetest.R;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
  private static final String TAG = "MyFirebaseMsgService";

  /**
   * Called when message is received.
   *
   * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
   */
  // [START receive_message]
  @Override
  public void onMessageReceived(RemoteMessage remoteMessage) {
    Log.e(TAG, "onMessageReceived ======================");
    Log.e(TAG, "From ======= " + remoteMessage.getFrom());
    Log.e(TAG, "To ======= " + remoteMessage.getTo());

    // Check if message contains a data payload.
    if (remoteMessage.getData() != null && remoteMessage.getData().size() > 0) {
      // 고급 옵션인 경우 key,value
//            Message data payload: {key=value, key1=value1}
      Log.e(TAG, "Message data payload: " + remoteMessage.getData());
      Map<String, String> data = remoteMessage.getData();
      String title = data.get("title");
      Log.e(TAG, "title == " + title);
      String msg = data.get("message");
      Log.e(TAG, "message == " + message);
      String value = data.get("key");
      Log.e(TAG, "key == " + value);
      String value1 = data.get("key1");
      Log.e(TAG, "key2 == " + value1);

      //추가한것
//            sendNotification(remoteMessage.getData().get("message"));
      sendNotification("getData");
    }
    // Check if message contains a notification payload.
    if (remoteMessage.getNotification() != null) {
      Log.e(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
      sendNotification("getNotification");
    }


//        if (remoteMessage.getMessageId() != null) {
//            Log.e(TAG, "Message getMessageId : " + remoteMessage.getMessageId().toString());
//        }
    if (remoteMessage.getCollapseKey() != null) {
      Log.e(TAG, "Message getCollapseKey : " + remoteMessage.getCollapseKey());
    }
    if (remoteMessage.getMessageType() != null) {
      Log.e(TAG, "Message getMessageType : " + remoteMessage.getMessageType().toString());
    }
  }

  private void sendNotification(String messageBody) {
    Log.d(TAG, "sendNotification =====" + messageBody.toString());
    Intent intent = new Intent(this, FcmMainActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
        PendingIntent.FLAG_ONE_SHOT);
    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
        .setSmallIcon(R.drawable.ic_stat_ic_notification)
        .setContentTitle("FCM Message")
        .setContentText(messageBody)
        .setAutoCancel(true)
        .setSound(defaultSoundUri)
        .setContentIntent(pendingIntent);
    NotificationManager notificationManager =
        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
  }
}
