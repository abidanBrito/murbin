/*
 * Created by Francisco Javier Paños Madrona on 21/11/20 20:44
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 21/11/20 20:44
 */

package com.example.murbin.uses_cases;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.murbin.App;
import com.example.murbin.R;
import com.example.murbin.data.ZonesDatabaseCrud;
import com.example.murbin.models.Area;
import com.example.murbin.models.GeoPoint;
import com.example.murbin.presentation.zone.general.fragments.GeneralFragmentHome;
import com.example.murbin.presentation.zone.general.fragments.GeneralFragmentMap;

import java.util.ArrayList;
import java.util.List;

public class UsesCasesZoneGeneral {

    /**
     * Constructor empty
     */
    public UsesCasesZoneGeneral() {
        //
    }

    /**
     * Change activity to GeneralMainActivity
     */
    public void loadHome(Context context) {
        Fragment fragment = new GeneralFragmentHome();
        FragmentTransaction transaction_home = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
        transaction_home.replace(R.id.general_main_activity_fragment, fragment);
        transaction_home.addToBackStack(null);
        transaction_home.commit();
    }

    /**
     * It checks if permission has been granted for geolocation, if permission has been given,
     * it is shown, if it is not requested again or a message appears for the user to give it
     * manually.
     */
    public void checkPermissionLoadMap(Context context) {
        App.getInstance().requestPermission(
                (Activity) context,
                context.getString(R.string.UsesCasesZoneGeneral_permission_justification_map),
                App.PERMIT_REQUEST_CODE_LOAD_MAP,
                App.ARRAY_PERMISSIONS_LOAD_MAP
        );

        loadMap(context);
    }

    /**
     * Change activity to GeneralMapActivity
     */
    public void loadMap(Context context) {
        Fragment fragment = new GeneralFragmentMap();
        FragmentTransaction transaction_map = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
        transaction_map.replace(R.id.general_main_activity_fragment, fragment);
        transaction_map.addToBackStack(null);
        transaction_map.commit();
    }

    /**
     * Displays a message if some of the group's permissions are not granted.
     *
     * @param context      Context
     * @param container    Container ViewGroup
     * @param permissions  String[]
     * @param grantResults int[]
     */
    public void showMessageIfPermissionsAreMissing(Context context, ViewGroup container, @NonNull String[] permissions, @NonNull int[] grantResults) {
        int permInternet = -1, permFineLocation = -1, permCoarseLocation = -1;

        for (int i = 0; i < grantResults.length; i++) {
            if (permissions[i].equals("android.permission.INTERNET")) {
                permInternet = grantResults[i];
            }
            if (permissions[i].equals("android.permission.ACCESS_FINE_LOCATION")) {
                permFineLocation = grantResults[i];
            }
            if (permissions[i].equals("android.permission.ACCESS_COARSE_LOCATION")) {
                permCoarseLocation = grantResults[i];
            }
        }

        if (permInternet != 0 || permFineLocation != 0 || permCoarseLocation != 0) {
            App.getInstance().snackMessage(container, R.color.black,
                    context.getString(R.string.GeneralMainActivity_permission_error_1),
                    context);
        }
    }

    /**
     * Example polygon Gandia
     *
     * @return Area
     */
    public Area getExampleZone() {
        List<GeoPoint> zone = new ArrayList<>();
        zone.add(new GeoPoint(38.95609110197044, -0.1683313621489413));
        zone.add(new GeoPoint(38.96924675570932, -0.16541601851817633));
        zone.add(new GeoPoint(38.97589623192223, -0.16124228838916732));
        zone.add(new GeoPoint(38.977372484099334, -0.1598905536795825));
        zone.add(new GeoPoint(38.98216449482589, -0.1623510043848997));
        zone.add(new GeoPoint(38.98477485824801, -0.1575444858302122));
        zone.add(new GeoPoint(38.98659496569272, -0.15614959377840743));
        zone.add(new GeoPoint(38.98931357453497, -0.15614959377840743));
        zone.add(new GeoPoint(38.990781246336354, -0.1546046413858293));
        zone.add(new GeoPoint(38.99091466953647, -0.15327426571444258));
        zone.add(new GeoPoint(38.99098138104221, -0.15168639797762618));
        zone.add(new GeoPoint(38.99178191420654, -0.15134307522371993));
        zone.add(new GeoPoint(38.99168184805615, -0.15044185299471602));
        zone.add(new GeoPoint(38.991548426302266, -0.15042039532259688));
        zone.add(new GeoPoint(38.991648492641275, -0.15014144558504805));
        zone.add(new GeoPoint(38.99536797223251, -0.14407666589649537));
        zone.add(new GeoPoint(38.99867846485331, -0.15549819312870694));
        zone.add(new GeoPoint(39.03349035491748, -0.18248274403048326));
        zone.add(new GeoPoint(39.03031847549198, -0.19255056155990102));
        zone.add(new GeoPoint(39.017449148529415, -0.18225087894271352));
        zone.add(new GeoPoint(39.01101748759554, -0.19034071211248893));
        zone.add(new GeoPoint(39.00402901739068, -0.18352452164208));
        zone.add(new GeoPoint(38.99835936039297, -0.19009056931053703));
        zone.add(new GeoPoint(38.99539384196806, -0.1846474935698672));
        zone.add(new GeoPoint(38.990257192295005, -0.18747990628959377));
        zone.add(new GeoPoint(38.98893660636314, -0.196210963711545));
        zone.add(new GeoPoint(38.97609772766649, -0.2018860072507045));
        zone.add(new GeoPoint(38.96982525283177, -0.20772249406711074));
        zone.add(new GeoPoint(38.96182278807201, -0.20030523168250047));
        zone.add(new GeoPoint(38.964025139167695, -0.19421125280066454));
        zone.add(new GeoPoint(38.962957341125644, -0.1892330728690239));
        zone.add(new GeoPoint(38.95898632595248, -0.19004846440955125));
        zone.add(new GeoPoint(38.95686373669334, -0.19173526948866648));
        zone.add(new GeoPoint(38.95592147773506, -0.18992185592651367));
        zone.add(new GeoPoint(38.955765421373265, -0.1703292663148881));
        zone.add(new GeoPoint(38.955730162370706, -0.16939103130544364));

        return new Area(zone);
    }
}
