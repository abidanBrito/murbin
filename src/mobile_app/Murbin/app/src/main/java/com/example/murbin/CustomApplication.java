package com.example.murbin;

import android.app.Application;

/**
 * A través de esta clase, podremos acceder a los elementos que
 * indiquemos en ella de manera global desde otras partes de la app.
 */
public class CustomApplication extends Application {

    /*
      Constant for ease of use in debugging the class code
     */
    private static final String TAG = CustomApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
    }

}
