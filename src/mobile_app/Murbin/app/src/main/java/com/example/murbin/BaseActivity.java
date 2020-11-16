/*
 * Created by Francisco Javier Paños Madrona on 6/11/20 18:00
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 14/11/20 14:45
 */

package com.example.murbin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.murbin.presentation.zone.administrator.AdministratorMainActivity;
import com.example.murbin.presentation.zone.scientific.ScientificMainActivity;
import com.example.murbin.presentation.zone.technical.TechnicalMainActivity;
import com.example.murbin.presentation.zone.user.UserMainActivity;

/**
 * The activity from which all the activities we create extend,
 * so that they share global variables and methods
 */
public class BaseActivity extends AppCompatActivity {

    /**
     * Constant for ease of use in debugging the class code
     */
    private static final String TAG = BaseActivity.class.getSimpleName();

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

//    @Override
//    protected void onStart() {
//        super.onStart();
//        Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show();
//    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
//    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
//    }

//    @Override
//    public void onStop() {
//        super.onStop();
//        Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
//    }

//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        Toast.makeText(this, "onRestart", Toast.LENGTH_SHORT).show();
//    }

//    @Override
//    protected void onDestroy() {
//        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
//        super.onDestroy();
//    }

//    @Override
//    protected void onSaveInstanceState(Bundle saveState) {
//        super.onSaveInstanceState(saveState);
//        Toast.makeText(this, "onSaveInstanceState", Toast.LENGTH_SHORT).show();
//    }

//    @Override
//    protected void onRestoreInstanceState(Bundle loadState) {
//        super.onRestoreInstanceState(loadState);
//        Toast.makeText(this, "onRestoreInstanceState", Toast.LENGTH_SHORT).show();
//    }

}
