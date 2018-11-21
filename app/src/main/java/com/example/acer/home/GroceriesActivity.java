package com.example.acer.home;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * This class contains the code to display notifications on expiry of food
 * @author  Theja Manasa
 * @version 1.0
 * @since   19 November, 2018
 * @references https://developer.android.com/training/notify-user/build-notification
 */
public class GroceriesActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_grocery);

    }
// create notification
    private void addNotification(View view) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.app_icon)
                        .setContentTitle("expirydate of" +"")
                        .setContentText("Tap to show in detail")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                .setAutoCancel(true);
//explicit activity trigger on tap of notification

        Intent notificationIntent = new Intent(this, AddGrocery.class);
        PendingIntent activity = PendingIntent.getActivity(this,  (int) System.currentTimeMillis(), notificationIntent, 0);

        builder.setContentIntent(activity);

        // show notification
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}
