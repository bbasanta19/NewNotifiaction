package com.andorid.newnotification;


import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static android.app.Notification.VISIBILITY_PRIVATE;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO: Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.
        Log.d("From: ", remoteMessage.getFrom());
        Log.d("Message Body: ", remoteMessage.getNotification().getBody());

        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.cat);
//        Bitmap myBitmap = BitmapFactory.decodeResource(Resources.getSystem(), R.mipmap.cat);
        Bitmap myBitmap = ((BitmapDrawable)drawable).getBitmap();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "New Notification")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody())
                .setLargeIcon(myBitmap)
                .setStyle(new NotificationCompat.BigPictureStyle()
                            .bigPicture(myBitmap)
                            .bigLargeIcon(null))
                .setTicker("You have a new notification")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVisibility(VISIBILITY_PRIVATE);

        NotificationManager notificationManager = (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        notificationManager.notify((int) System.currentTimeMillis(), mBuilder.build());
        Intent intent = new Intent("NEW_NOTIFICATION");
        //intent.setAction("NEW_NOTIFICATION");
        intent.putExtra("message", remoteMessage.getNotification().getBody());
        //LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        sendBroadcast(intent);
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        //String refreshedToken =
    }
}
