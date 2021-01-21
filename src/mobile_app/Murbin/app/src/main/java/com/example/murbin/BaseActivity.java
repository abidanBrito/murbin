/*
 * Created by Francisco Javier Paños Madrona on 6/11/20 18:00
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 14/11/20 14:45
 */
package com.example.murbin;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import com.example.murbin.presentation.global.fragments.DialogFragment;
import com.example.murbin.presentation.global.fragments.MapDialogFragment;
import com.example.murbin.services.BackgroundMusicService;

/**
 * The activity from which all the activities we create extend,
 * so that they share global variables and methods
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    public void onBackPressed() {
        // Very important for safety
        // Leave empty to avoid pressing the back button on the device hardware
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean newStatus = pref.getBoolean("global_preferences_key_background_music", false);
        App.getInstance().changeServiceStatus(BackgroundMusicService.class, newStatus);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle saveState) {
        super.onSaveInstanceState(saveState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle loadState) {
        super.onRestoreInstanceState(loadState);
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

    /**
     * Shows the Map Dialog
     */
    protected void showMapDialogFragment() {
        FragmentManager fm = getSupportFragmentManager();
        MapDialogFragment mapDialogFragment = new MapDialogFragment();
        mapDialogFragment.show(fm, "map_dialog_fragment");
    }

    /**
     * Change the location input
     * @param mapStatus -> If the map was acepted: true, and if the map was rejected: false
     * @param createOrEdit -> If the function is called in a create or in a edit activity ("create", "edit")
     * @param userType -> If the function is called in the admin dashboard or the technician dashboard ("administrator", "technician")
     **/
    protected void changeLocationInput(boolean mapStatus, String createOrEdit, String userType){
        Button locationBtn = null;
        switch(createOrEdit){
            case "create": {
                if (userType == "administrator"){ // AdministratorSubzoneCreate
                    locationBtn = findViewById(R.id.administrator_subzone_create_btn_location);
                } else if(userType == "technician"){ // TechnicianStreetlightCreate
                    locationBtn = findViewById(R.id.technician_streetlight_create_btn_location);
                }
            }
            case "edit": {
                if (userType == "administrator"){ // AdministratorSubzoneEdit
                    locationBtn = findViewById(R.id.administrator_subzone_edit_btn_location);
                } else if(userType == "technician"){ // TechnicianStreetlightEdit
                    locationBtn = findViewById(R.id.technician_streetlight_edit_btn_location);
                }
            }
        }

        if(mapStatus == true){
            if(userType == "administrator"){
                locationBtn.setHint("Subzona marcada");
            } else if (userType == "technician"){
                locationBtn.setHint("Farola marcada");
            }
        } else {
            locationBtn.setHint("Localización");
        }

    }

}
