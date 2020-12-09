/*
 * Created by Francisco Javier Paños Madrona on 21/11/20 20:44
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 21/11/20 20:44
 */

package com.example.murbin.uses_cases;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.murbin.App;
import com.example.murbin.R;
import com.example.murbin.presentation.zone.general.fragments.GeneralFragmentMap;

public class UsesCasesZoneGeneral {

    /**
     * Constructor empty
     */
    public UsesCasesZoneGeneral() {
        //
    }

    /**
     * It checks if permission has been granted for geolocation, if permission has been given,
     * it is shown, if it is not requested again or a message appears for the user to give it
     * manually.
     */
    public void checkPermissionLoadMap(Context context) {
        if (ActivityCompat.checkSelfPermission(App.getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            loadMap(context);
        } else {
            App.getInstance().requestPermission(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    context.getString(R.string.UsesCasesZoneGeneral_permission_justification_map),
                    App.PERMIT_REQUEST_CODE_ACCESS_FINE_LOCATION,
                    (Activity) context
            );
        }
    }

    /**
     * Change fragment to GeneralFragmentMap
     */
    public void loadMap(Context context) {
        Fragment fragment = new GeneralFragmentMap();
        FragmentTransaction transaction_map = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
        transaction_map.replace(R.id.general_main_activity_fragment, fragment);
        transaction_map.addToBackStack(null);
        transaction_map.commit();
    }
}
