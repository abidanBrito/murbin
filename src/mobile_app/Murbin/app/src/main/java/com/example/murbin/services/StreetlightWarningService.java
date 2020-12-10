package com.example.murbin.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.murbin.R;
import com.example.murbin.presentation.global.GlobalPreferencesActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Calendar;

public class StreetlightWarningService extends Service {
    private static final String CHANNEL_ID = "MURBIN_Notification";
    private static final int NOTIFICATION_ID = 100;

    private NotificationManager mNotificationManager;

    @Override
    public int onStartCommand(Intent intent, int flags, int runningId) {
        initialize();

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        mNotificationManager.cancel(NOTIFICATION_ID);
    }

    public void initialize() {
        Calendar rightNow = Calendar.getInstance();
        int currentHourIn24Format = rightNow.get(Calendar.HOUR_OF_DAY);

        if(currentHourIn24Format >= 22){
            addNotification();
        }
    }

    private void addNotification() {
        mNotificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    CHANNEL_ID, "Alarma",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Ha anochecido, comprueba la farola.");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }

        // For load Global Preferences
        Intent intentGlobalPreferences = new Intent(this, GlobalPreferencesActivity.class);
        PendingIntent pendingIntentGlobalPreferences = PendingIntent.getActivity(this, 0, intentGlobalPreferences, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notification =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setOngoing(true)
                        .setShowWhen(false)
                        .setSmallIcon(android.R.drawable.ic_media_play)
                        .setContentTitle("Alarma")
                        .setContentText("Ha anochecido, comprueba la farola.")
                        .setContentIntent(pendingIntentGlobalPreferences);

        startForeground(NOTIFICATION_ID, notification.build());
    }
}
