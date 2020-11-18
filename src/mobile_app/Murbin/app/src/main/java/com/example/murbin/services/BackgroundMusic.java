/*
 * Created by Francisco Javier Paños Madrona on 17/11/20 18:17
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 17/11/20 18:17
 */

package com.example.murbin.services;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.murbin.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Service to play background music using MediaPlayer
 *
 * https://developer.android.com/guide/topics/media/mediaplayer?hl=es#java
 */
public class BackgroundMusic extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

    /**
     * Constant for ease of use in debugging the class code
     */
    private static final String TAG = BackgroundMusic.class.getSimpleName();
    private int currentTrack = 0;
    private NotificationManager notificationManager;
    private MediaPlayer mMediaPlayer;
    private List<Integer> mPlayList;

    @Override
    public void onCreate() {
//        Toast.makeText(this, TAG + " created", Toast.LENGTH_SHORT).show();
        mPlayList = new ArrayList<Integer>();

        // Add the tracks to the playlist
        mPlayList.add(R.raw.one_track);
        mPlayList.add(R.raw.two_track);
        mPlayList.add(R.raw.three_track);
        mPlayList.add(R.raw.four_track);
        mPlayList.add(R.raw.five_track);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int runningId) {
//        Toast.makeText(this, TAG + " running", Toast.LENGTH_SHORT).show();
        mMediaPlayer = MediaPlayer.create(this, mPlayList.get(currentTrack));
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnCompletionListener(this);

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
//        Toast.makeText(this, TAG + " onCompletion", Toast.LENGTH_SHORT).show();
        if (currentTrack == (mPlayList.size() - 1)) {
            currentTrack = 0;
        } else {
            currentTrack++;
        }

        // Plays the next song in the playlist at the end of the one currently playing
        mp = MediaPlayer.create(this, mPlayList.get(currentTrack));
        mp.setOnPreparedListener(this);
        mp.setOnCompletionListener(this);

    }

    @Override
    public void onDestroy() {
//        Toast.makeText(this, TAG + " stopped", Toast.LENGTH_SHORT).show();
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
}
