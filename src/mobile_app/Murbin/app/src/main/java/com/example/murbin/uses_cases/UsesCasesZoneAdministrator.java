/*
 * Created by Francisco Javier Paños Madrona on 21/11/20 20:43
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 21/11/20 20:43
 */

package com.example.murbin.uses_cases;

import android.content.Context;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.murbin.App;
import com.example.murbin.R;
import com.example.murbin.models.GeoPoint;
import com.example.murbin.presentation.zone.general.fragments.GeneralFragmentMap;
import com.example.murbin.services.GpsTrackerService;

public class UsesCasesZoneAdministrator {
    private GpsTrackerService gpsTrackerService;

    /**
     * Constructor empty
     */
    public UsesCasesZoneAdministrator() {
        //
    }

    /**
     * Change fragment to GeneralFragmentMap
     */
    public void loadEdit(Context context) {
        Fragment fragment = new GeneralFragmentMap();
        FragmentTransaction transaction_map = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
        transaction_map.replace(R.id.general_main_activity_fragment, fragment);
        transaction_map.addToBackStack(null);
        transaction_map.commit();
    }

    /**
     * Get the current location GeoPoint or Geopoint(0,0) if not location.
     *
     * @param context Context of the caller
     * @return GeoPoint
     */
    public GeoPoint getCurrentLocation(Context context) {
        GeoPoint geoPoint;
        gpsTrackerService = new GpsTrackerService(context);
        if (gpsTrackerService.canGetLocation()) {
            double latitude = gpsTrackerService.getLatitude();
            double longitude = gpsTrackerService.getLongitude();
            Log.d(App.DEFAULT_TAG, "Location: lat->" + latitude + " lng->" + longitude);
            geoPoint = new GeoPoint(latitude, longitude);
        } else {
            geoPoint = new GeoPoint();
            gpsTrackerService.showSettingsAlert();
        }

        return geoPoint;
    }
}
