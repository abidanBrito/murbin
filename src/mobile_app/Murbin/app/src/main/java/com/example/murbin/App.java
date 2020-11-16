/*
 * Created by Francisco Javier Paños Madrona on 6/11/20 18:00
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 14/11/20 14:45
 */

package com.example.murbin;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;

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

    /**
     * Redirect to corresponding activity
     *
     * @param activity Activity where it will be redirected
     */
    public void redirectActivity(Class activity) {
        Intent i;
        i = new Intent(getInstance().getBaseContext(), activity);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK
        );
        getInstance().getBaseContext().startActivity(i);
    }
}
