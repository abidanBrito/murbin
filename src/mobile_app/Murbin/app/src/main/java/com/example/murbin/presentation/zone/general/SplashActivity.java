/*
 * Created by Francisco Javier Paños Madrona on 6/11/20 18:00
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 21/01/21 15:05
 */

package com.example.murbin.presentation.zone.general;

import android.os.Bundle;
import android.util.Log;

import com.example.murbin.App;
import com.example.murbin.BaseActivity;
import com.example.murbin.R;

public class SplashActivity extends BaseActivity {

    int timeout = 2000; // in milliseconds (1 seconds == 1000)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(timeout);
                } catch (InterruptedException e) {
//                    Log.d(App.DEFAULT_TAG, "Error: " + e.getMessage());
                } finally {
                    App.getInstance().toZoneUserLogged();
                }
            }
        };

        timer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}
