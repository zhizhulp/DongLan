package com.cn.danceland.myapplication.utils;

import android.app.NotificationManager;
import android.content.Context;


/**
 * Created by zhangsong on 17-9-27.
 */

public class EMNotificationManager {
    private static final String TAG = "NotificationManager";

    private static EMNotificationManager instance = null;
    private static int ID_NOTIFICATION = 0525; // start notification id

    private NotificationManager notificationManager = null;
    private Context context;

    public static EMNotificationManager getInstance(Context context) {
        synchronized (EMNotificationManager.class) {
            if (instance == null) {
                synchronized (EMNotificationManager.class) {
                    instance = new EMNotificationManager(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    public void sendNotification(String message) {
//        if (!EasyUtils.isAppRunningForeground(context)) {
//            try {
//                PackageManager packageManager = context.getPackageManager();
//                // notification title
//                String contentTitle = (String) packageManager
//                        .getApplicationLabel(context.getApplicationInfo());
//                String packageName = context.getApplicationInfo().packageName;
//
//                Uri defaultSoundUri = RingtoneManager
//                        .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//                Intent msgIntent = context.getPackageManager()
//                        .getLaunchIntentForPackage(packageName);
//                PendingIntent pendingIntent = PendingIntent.getActivity(context,
//                        ID_NOTIFICATION, msgIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//                // create and send notification
//                Notification notification = new NotificationCompat.Builder(context)
//                        .setSmallIcon(context.getApplicationInfo().icon)
//                        .setSound(defaultSoundUri)
//                        .setWhen(System.currentTimeMillis())
//                        .setAutoCancel(true)
//                        .setContentTitle(contentTitle)
//                        .setTicker(message)
//                        .setContentText(message)
//                        .setContentIntent(pendingIntent)
//                        .build();
//
//                notificationManager.notify(ID_NOTIFICATION, notification);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }

    private EMNotificationManager(Context context) {
        this.context = context;
        notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
    }
}
