/*
 * Copyright (c) 2016. iUlling Corp.
 * Created By Kil-Ho Choi
 */

package com.ulling.lib.fcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

public class QcNotificationJellyBean {
  private static QcNotificationJellyBean SINGLE_U;
  public static final int TYPE_Bar_cancel = 0;
  public static final int TYPE_Bar_cancelClick = 1;
  public static final int TYPE_Bar_cancelNever = 2;

  private QcNotificationJellyBean() {
  }

  public static WQcNotificationJellyBean with(Context pCon, int iconSmallResourceID, Intent intent, int UNotification_TYPE,
                                              int notiId, String title, String content) {
    if (SINGLE_U == null) {
      SINGLE_U = new QcNotificationJellyBean();
    }
    return SINGLE_U.get(pCon, iconSmallResourceID, intent, UNotification_TYPE, notiId, title, content);
  }

  public static WQcNotificationJellyBean with(Context pCon, int iconSmallResourceID, Intent intent, int UNotification_TYPE,
                                              int notiId, RemoteViews romoteView) {
    if (SINGLE_U == null) {
      SINGLE_U = new QcNotificationJellyBean();
    }
    return SINGLE_U.get(pCon, iconSmallResourceID, intent, UNotification_TYPE, notiId, romoteView);
  }

  public static WQcNotificationJellyBean with(Context pCon, int iconSmallResourceID, Class<?> _class, int UNotification_TYPE,
                                              int notiId, String title, String content) {
    if (SINGLE_U == null) {
      SINGLE_U = new QcNotificationJellyBean();
    }
    if (_class != null) {
      return SINGLE_U.get(pCon, iconSmallResourceID, new Intent(pCon, _class), UNotification_TYPE, notiId, title, content);
    } else {
      return null;
    }
  }

  public static WQcNotificationJellyBean with(Context pCon, int iconSmallResourceID, Class<?> _class, int UNotification_TYPE,
                                              int notiId, RemoteViews romoteView) {
    if (SINGLE_U == null) {
      SINGLE_U = new QcNotificationJellyBean();
    }
    if (_class != null) {
      return SINGLE_U.get(pCon, iconSmallResourceID, new Intent(pCon, _class), UNotification_TYPE, notiId, romoteView);
    } else {
      return null;
    }
  }

  private WQcNotificationJellyBean get(Context pCon, int iconSmallResourceID, Intent intent, int UNotification_TYPE,
                                       int notiId, String title, String content) {
    return new WQcNotificationJellyBean(pCon, iconSmallResourceID, intent, UNotification_TYPE, notiId, title, content);
  }

  private WQcNotificationJellyBean get(Context pCon, int iconSmallResourceID, Intent intent, int UNotification_TYPE,
                                       int notiId, RemoteViews romoteView) {
    return new WQcNotificationJellyBean(pCon, iconSmallResourceID, intent, UNotification_TYPE, notiId, romoteView);
  }

  public class WQcNotificationJellyBean {
    private NotificationManager notificationManager;
    private Context pCon;
    private NotificationCompat.Builder compatBuilder;
    private Intent intent;
    private int iconSmallResourceID;
    private String title;
    private String content;
    private RemoteViews romoteView;
    private int UNotification_TYPE;
    private int notiId = 0;

    public WQcNotificationJellyBean(Context pCon, int iconSmallResourceID, Intent intent, int UNotification_TYPE, int notiId, String title, String content) {
      notificationManager = (NotificationManager) pCon.getSystemService(Context.NOTIFICATION_SERVICE);
      this.pCon = pCon;
      this.iconSmallResourceID = iconSmallResourceID;
      this.intent = intent;
      this.UNotification_TYPE = UNotification_TYPE;
      this.notiId = notiId;
      this.title = title;
      this.content = content;
      initNotification();
    }

    public WQcNotificationJellyBean(Context pCon, int iconSmallResourceID, Intent intent, int UNotification_TYPE, int notiId, RemoteViews romoteView) {
      notificationManager = (NotificationManager) pCon.getSystemService(Context.NOTIFICATION_SERVICE);
      this.pCon = pCon;
      this.iconSmallResourceID = iconSmallResourceID;
      this.intent = intent;
      this.UNotification_TYPE = UNotification_TYPE;
      this.notiId = notiId;
      this.romoteView = romoteView;
      initNotification();
    }

    public NotificationCompat.Builder getBuild() {
      return compatBuilder;
    }

    private WQcNotificationJellyBean initNotification() {
      if (intent == null)
        return this;
      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
      PendingIntent pendingIntent = PendingIntent.getActivity(pCon, notiId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
      Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
      compatBuilder = new NotificationCompat.Builder(pCon);
      compatBuilder.setWhen(System.currentTimeMillis())
          .setFullScreenIntent(pendingIntent, true)
          .setContentIntent(pendingIntent)
          .setSound(defaultSoundUri)
          .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
      setBar(UNotification_TYPE);
      compatBuilder.setSmallIcon(iconSmallResourceID);
      if (title != null)
        compatBuilder.setContentTitle(title);
      if (content != null)
        compatBuilder.setContentText(content);
      if (romoteView != null)
        compatBuilder.setContent(romoteView);
      return this;
    }

    private void setBar(int UNotification_TYPE) {
      if (UNotification_TYPE == TYPE_Bar_cancel) {
        compatBuilder.setAutoCancel(false);
        compatBuilder.setOngoing(false);
      } else if (UNotification_TYPE == TYPE_Bar_cancelClick) {
        compatBuilder.setAutoCancel(true);
        compatBuilder.setOngoing(false);
      } else if (UNotification_TYPE == TYPE_Bar_cancelNever) {
        compatBuilder.setAutoCancel(false);
        compatBuilder.setOngoing(true);
      }
    }

    public WQcNotificationJellyBean forBigTextStyle(String bigTitle, String bigText, String summaryText) {
      if (compatBuilder != null) {
        NotificationCompat.BigTextStyle bigStyle = new NotificationCompat.BigTextStyle(compatBuilder);
        bigStyle.setBigContentTitle(bigTitle);
        bigStyle.bigText(bigText);
        if (summaryText != null && !"".equals(summaryText))
          bigStyle.setSummaryText(summaryText);
        compatBuilder.setStyle(bigStyle);
      }
      return this;
    }

    public WQcNotificationJellyBean forBigPictureStyle(String expanedTitle, String expanedContent, Bitmap bigPictureBitmap) {
      if (compatBuilder != null && bigPictureBitmap != null) {
        NotificationCompat.BigPictureStyle bigStyle = new NotificationCompat.BigPictureStyle(compatBuilder);
        bigStyle.bigPicture(bigPictureBitmap);
        bigStyle.setBigContentTitle(expanedTitle);
        bigStyle.setSummaryText(expanedContent);
        compatBuilder.setStyle(bigStyle);
      }
      return this;
    }

    public WQcNotificationJellyBean forLargeIconBm(Bitmap laregIconBm) {
      if (compatBuilder != null && laregIconBm != null)
        compatBuilder.setLargeIcon(laregIconBm);
      return this;
    }

    public WQcNotificationJellyBean forTicker(String ticker) {
      if (compatBuilder != null && ticker != null)
        compatBuilder.setContentTitle(ticker);
      return this;
    }

    public WQcNotificationJellyBean forShow() {
      if (compatBuilder != null && compatBuilder != null)
        notificationManager.notify(notiId, compatBuilder.build());
      return this;
    }

    public WQcNotificationJellyBean forClose(int notiId_) {
      if (notificationManager != null)
        notificationManager = (NotificationManager) pCon.getSystemService(Context.NOTIFICATION_SERVICE);
      notificationManager.cancel(notiId_);
      return this;
    }

    public WQcNotificationJellyBean forAllClose() {
      if (notificationManager != null)
        notificationManager = (NotificationManager) pCon.getSystemService(Context.NOTIFICATION_SERVICE);
      notificationManager.cancelAll();
      return this;
    }
  }
}
