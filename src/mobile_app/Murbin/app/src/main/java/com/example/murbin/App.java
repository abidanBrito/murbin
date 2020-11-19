/*
 * Created by Francisco Javier Paños Madrona on 6/11/20 18:00
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 14/11/20 14:45
 */

package com.example.murbin;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.ViewGroup;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.murbin.models.User;
import com.example.murbin.services.BackgroundMusic;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

/**
 * Through this class, we can access the elements that we indicate
 * in it globally from other parts of the application
 */
public class App extends Application {

    public static final int PERMIT_REQUEST_CODE_ACCESS_FINE_LOCATION = 3001;

    /**
     * Constant for ease of use in debugging the class code
     */
    private static final String TAG = App.class.getSimpleName();

    private static App instance;

    private static User currentUser = null;

    public App() {
        instance = this;
    }

    public static App getInstance() {
        return instance;
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    /**
     * @param permission    Permission requested
     * @param justification Justification message for requesting permission
     * @param requestCode   Response code
     * @param activity      Activity
     */
    public void requestPermission(final String permission, String justification, final int requestCode, final Activity activity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            new AlertDialog.Builder(activity).
                    setTitle(R.string.permissions_message_title).
                    setMessage(justification).
                    setPositiveButton(R.string.permissions_message_action, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
                        }
                    }).show();
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
        }
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
