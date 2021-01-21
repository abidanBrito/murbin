/*
 * Created by Francisco Javier Paños Madrona on 6/11/20 18:00
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 14/11/20 14:45
 */

package com.example.murbin;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.ViewGroup;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.murbin.data.UsersDatabaseCrud;
import com.example.murbin.firebase.Auth;
import com.example.murbin.models.User;
import com.example.murbin.presentation.zone.general.GeneralMainActivity;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

/**
 * Through this class, we can access the elements that we indicate
 * in it globally from other parts of the application
 */
public class App extends Application {

    public static final String DEFAULT_TAG = "APP_MURBIN";
    public static final int PERMIT_REQUEST_CODE_LOAD_MAP = 3010;
    public static final String ROLE_ROOT = "root";
    public static final String ROLE_ADMINISTRATOR = "administrator";
    public static final String ROLE_TECHNICIAN = "technician";
    public static String[] ARRAY_PERMISSIONS_LOAD_MAP = {
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
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

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        currentUser = user;
    }

    /**
     * Check if email is valid
     *
     * @param email Email for check
     * @return boolean
     */
    public static boolean isValidEmail(CharSequence email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Runs only when you start the application for the first time
    }

    /**
     * Check if you have all the permissions received in the array of strings.
     *
     * @param context     Context of the caller
     * @param permissions Permissions array string
     * @return boolean
     */
    public boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {

                    return false;
                }
            }
        }

        return true;
    }

    /**
     * It checks if the required permissions have been granted and if not, it asks the user for
     * them, indicating the reason.
     *
     * @param permissions   Permissions requested
     * @param justification Justification message for requesting permission
     * @param requestCode   Response code
     * @param activity      Activity
     */
    public void requestPermission(final Activity activity, String justification,
                                  final int requestCode, final String... permissions) {
        if (!hasPermissions(this, permissions)) {
            ActivityCompat.requestPermissions(activity, permissions, requestCode);
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
        Intent serviceIntent = new Intent(instance.getApplicationContext(), serviceClass);
        if (newStatus) {
            if (!isServiceRunning(serviceClass)) {
                startService(serviceIntent);
            }
        } else {
            stopService(serviceIntent);
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
        Snackbar sb = Snackbar.make(cl, message, BaseTransientBottomBar.LENGTH_LONG);
        sb.getView().setBackgroundColor(ContextCompat.getColor(context, color));
        sb.show();
    }

    /**
     * When run, it redirects to the user's zone based on their role.
     */
    public void toZoneUserLogged() {
        Auth mAuth = new Auth();
        if (mAuth.isLogged()) {
            UsersDatabaseCrud mUsersDatabaseCrud = new UsersDatabaseCrud();
            mUsersDatabaseCrud.read(mAuth.getCurrentUser().getUid(), user -> {
                if (user != null) {
                    App.getInstance().setCurrentUser(user);
                    mAuth.checkRole(App.getInstance().getCurrentUser().getRole());
                }
            });
        } else {
            redirectActivity(GeneralMainActivity.class);
        }
    }

    /**
     * Redirect to corresponding activity
     *
     * @param activity Activity where it will be redirected
     */
    public void redirectActivity(Class<?> activity) {
        Intent intent;
        intent = new Intent(getContext(), activity);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK
        );
        getContext().startActivity(intent);
    }

}
