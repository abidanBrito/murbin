/*
 * Created by Francisco Javier Paños Madrona on 17/11/20 18:17
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 17/11/20 18:17
 */

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
import java.util.List;
import java.util.Random;

/**
 * Service to play background music using MediaPlayer
 * <p>
 * https://developer.android.com/guide/topics/media/mediaplayer?hl=es#java
 */
public class BackgroundMusicService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

    private static final String CHANNEL_ID = "MURBIN_Channel";
    private static final int NOTIFICATION_ID = 1001;

    private NotificationManager mNotificationManager;
    private MediaPlayer mMediaPlayer;
    private List<Integer> mPlayList;
    private int mCurrentTrack = 0;

    @Override
    public int onStartCommand(Intent intent, int flags, int runningId) {
        initialize();
        addNotification();
        playRandomTrack();

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        playRandomTrack();
    }

    @Override
    public void onDestroy() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        mNotificationManager.cancel(NOTIFICATION_ID);
    }

    /**
     * Initialize playlist
     */
    public void initialize() {
        mPlayList = new ArrayList<Integer>();

        // Add the tracks rock to the playlist
        mPlayList.add(R.raw.rock_1);
        mPlayList.add(R.raw.rock_2);
        mPlayList.add(R.raw.rock_3);
        mPlayList.add(R.raw.rock_4);
        mPlayList.add(R.raw.rock_5);
        mPlayList.add(R.raw.rock_6);
        // Add the tracks blues to the playlist
        mPlayList.add(R.raw.blues_1);
        mPlayList.add(R.raw.blues_2);
        mPlayList.add(R.raw.blues_3);
        mPlayList.add(R.raw.blues_4);
    }

    /**
     * Run mMediaPlayer
     */
    public void run() {
        mMediaPlayer = MediaPlayer.create(this, mPlayList.get(mCurrentTrack));
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnCompletionListener(this);
    }

    /**
     * Notification for service
     */
    private void addNotification() {
        mNotificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    CHANNEL_ID, getString(R.string.BackgroundMusic_notification_channel_title),
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription(getString(R.string.BackgroundMusic_notification_channel_description));
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
                        .setContentTitle(getString(R.string.BackgroundMusic_notification_channel_title))
                        .setContentText(getString(R.string.BackgroundMusic_notification_channel_description))
                        .setContentIntent(pendingIntentGlobalPreferences);

        startForeground(NOTIFICATION_ID, notification.build());
    }

    /**
     * Play the next track in the playlist
     */
    public void playNextTrack() {
        if (mCurrentTrack == (mPlayList.size() - 1)) {
            mCurrentTrack = 0;
        } else {
            mCurrentTrack++;
        }

        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
        }
        run();
    }

    /**
     * Play random track in the playlist
     */
    public void playRandomTrack() {
        Random random = new Random();
        mCurrentTrack = random.nextInt(mPlayList.size());

        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
        }
        run();
    }
}
