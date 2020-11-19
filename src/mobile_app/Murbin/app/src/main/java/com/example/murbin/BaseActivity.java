/*
 * Created by Francisco Javier Paños Madrona on 6/11/20 18:00
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 14/11/20 14:45
 */
package com.example.murbin;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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

    private final Auth mAuth = new Auth(this);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean newStatus = pref.getBoolean("global_preferences_key_background_music", false);
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

    /**
     * It receives the identifier of the resource of the containing fragment and
     * the class of fragment to which it wants to change.
     *
     * @param fragmentContainerId Identifier of the resource of the containing fragment
     * @param fragmentClass       Class of fragment
     */
    public void replaceFragments(int fragmentContainerId, Class<?> fragmentClass) {
        try {
            Fragment fragment = (Fragment) fragmentClass.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(fragmentContainerId, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
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

}
