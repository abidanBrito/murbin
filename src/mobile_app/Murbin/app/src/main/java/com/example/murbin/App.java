/*
 * Created by Francisco Javier Paños Madrona on 6/11/20 18:00
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 14/11/20 14:45
 */

package com.example.murbin;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;

import com.example.murbin.presentation.global.PreferencesActivity;
import com.example.murbin.presentation.zone.administrator.AdministratorMainActivity;
import com.example.murbin.presentation.zone.scientific.ScientificMainActivity;
import com.example.murbin.presentation.zone.technician.TechnicianMainActivity;
import com.example.murbin.presentation.zone.user.UserMainActivity;
import com.example.murbin.services.BackgroundMusic;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

/**
 * Through this class, we can access the elements that we indicate
 * in it globally from other parts of the application
 */
public class App extends Application {

    /**
     * Constant for ease of use in debugging the class code
     */
    private static final String TAG = App.class.getSimpleName();

    private static App instance;

    public App() {
        instance = this;
    }

    public static App getInstance() {
        return instance;
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }

    /**
     * Starts or stops the class of the service received as a parameter,
     * depending on the Boolean parameter received as well.
     *
     * @param serviceClass Class of service
     * @param newStatus    Value true to start or false to stop
     */
    public void changeServiceStatus(Class<?> serviceClass, boolean newStatus) {
        if (newStatus) {
            if (!isServiceRunning(serviceClass)) {
                startService(new Intent(instance.getApplicationContext(),
                        serviceClass));
            }
        } else {
            stopService(new Intent(instance.getApplicationContext(),
                    BackgroundMusic.class));
        }
    }

    /**
     * Check if the class of the service passed as reference is running or not.
     *
     * @param serviceClass Class of service
     * @return boolean
     */
    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {

                return true;
            }
        }

        return false;
    }

    /**
     * Method to display a message with snack bar
     * More info: https://material.io/components/snackbars
     *
     * @param cl      The container of the layout where the message will be displayed
     * @param color   The background color where the message is displayed
     * @param message The message to be displayed.
     * @param context The context
     */
    public void snackMessage(ViewGroup cl, int color, String message, Context context) {
        Snackbar sb = Snackbar.make(cl, message, BaseTransientBottomBar.LENGTH_SHORT);
        sb.getView().setBackgroundColor(ContextCompat.getColor(context, color));
        sb.show();
    }

}
