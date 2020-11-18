/*
 * Created by Francisco Javier Paños Madrona on 6/11/20 18:00
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 14/11/20 14:45
 */
package com.example.murbin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceManager;

import com.example.murbin.firebase.Auth;
import com.example.murbin.presentation.global.fragments.DialogFragment;
import com.example.murbin.services.BackgroundMusic;

/**
 * The activity from which all the activities we create extend,
 * so that they share global variables and methods
 */
public class BaseActivity extends AppCompatActivity {

    /**
     * Constant for ease of use in debugging the class code
     */
    private static final String TAG = BaseActivity.class.getSimpleName();

    private static SharedPreferences pref;
    private final Auth mAuth = new Auth(this);
    private boolean newStatus;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    /**
     * @param permission    Permission requested
     * @param justification Justification message for requesting permission
     * @param requestCode   Response code
     * @param activity      Activity
     */
    protected void requestPermission(final String permission, String justification, final int requestCode, final Activity activity) {
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
     * Shows the dialog with the title and the message received as parameters
     */
    protected void showDialogFragment(String title, String message) {
        FragmentManager fm = getSupportFragmentManager();
        DialogFragment dialogFragment = DialogFragment.newInstance(title, message);
        dialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.StyleDialogFragment);
        dialogFragment.show(fm, "dialog_fragment");
    }

    @Override
    protected void onStart() {
        super.onStart();
//        Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show();
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        newStatus = pref.getBoolean("global_preferences_key_background_music", false);
//        Log.d(TAG, ((newStatus) ? "TRUE" : "FALSE"));
        App.getInstance().changeServiceStatus(BackgroundMusic.class, newStatus);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStop() {
        super.onStop();
//        Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        Toast.makeText(this, "onRestart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle saveState) {
        super.onSaveInstanceState(saveState);
//        Toast.makeText(this, "onSaveInstanceState", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestoreInstanceState(Bundle loadState) {
        super.onRestoreInstanceState(loadState);
//        Toast.makeText(this, "onRestoreInstanceState", Toast.LENGTH_SHORT).show();
    }

}
