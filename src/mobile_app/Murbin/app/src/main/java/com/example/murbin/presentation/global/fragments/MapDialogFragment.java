/*
 * Created by Francisco Javier Paños Madrona on 17/01/21 14:21
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 17/01/21 14:21
 */

package com.example.murbin.presentation.global.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.example.murbin.App;
import com.example.murbin.R;
import com.example.murbin.models.GeoPoint;
import com.example.murbin.uses_cases.UsesCasesZoneAdministrator;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;

/**
 * DialogFragment
 * https://developer.android.com/reference/android/app/DialogFragment.html#BasicDialog
 */
public class MapDialogFragment extends androidx.fragment.app.DialogFragment
        implements OnMapReadyCallback, GoogleMap.OnPolygonClickListener, View.OnClickListener {

    private SupportMapFragment mMapView;
    private Button m_btn_validate;
    private GoogleMap mMap;

    public MapDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.administrator_diaglog_fragment_map, container, false);

        mMapView = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.administrator_diaglog_fragment_map_mapview);
        mMapView.getMapAsync(this);

        m_btn_validate = view.findViewById(R.id.administrator_diaglog_fragment_map_btn_validate);
        m_btn_validate.setOnClickListener(this);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        if (mMapView != null)
            getChildFragmentManager().beginTransaction()
                    .remove(mMapView).commitAllowingStateLoss();
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMapView != null) {
            mMapView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mMapView != null) {
            mMapView.onPause();
        }
    }

    @Override
    public void onDestroy() {
        if (mMapView != null) {
            mMapView.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mMapView != null) {
            mMapView.onLowMemory();
        }
    }

    @Override
    public void onPolygonClick(Polygon polygon) {
//        Log.d(App.DEFAULT_TAG, "Pulsada Zona: " + polygon.getTag().toString());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        GeoPoint geoPoint;
        if (App.getInstance().hasPermissions(getContext(), App.ARRAY_PERMISSIONS_LOAD_MAP)) {
            UsesCasesZoneAdministrator icza = new UsesCasesZoneAdministrator();
            geoPoint = icza.getCurrentLocation(getContext());
        } else {
            geoPoint = new GeoPoint();
        }

        Log.d(App.DEFAULT_TAG, "geoPoint:" + geoPoint.toString());


        mMap = googleMap;
        LatLng centerPoint = new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centerPoint, 12));

        mMap.setOnMapClickListener(
                latLng -> Log.d(App.DEFAULT_TAG, "Latitude:" + latLng.latitude + " - Longitude: " + latLng.longitude)
        );

//        ZonesDatabaseCrud zdc = new ZonesDatabaseCrud();
//        // Retrieve the zone from firestore
//        zdc.read("Gandia3", zone -> {
//            mMap = googleMap;
//
//            Area area = new Area(zone.getFormattedArea());
//            GeoPoint centerZone = area.getCenterArea();
//            LatLng centerPoint = new LatLng(centerZone.getLatitude(), centerZone.getLongitude());
//
//            Polygon polygon = mMap.addPolygon(new PolygonOptions()
//                    .clickable(true)
//                    .addAll(
//                            area.convertToLatLngList()
//                    )
//            );
//            polygon.setTag("Gandia");
//            polygon.setStrokeColor(R.color.black);
//            polygon.setFillColor(R.color.black);
//
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centerPoint, 12));
//            mMap.setOnPolygonClickListener(AdministratorDialogFragmentMap.this);
//
//            mMap.setOnMapClickListener(
//                    latLng -> Log.d(App.DEFAULT_TAG, "Latitude:" + latLng.latitude + " - Longitude: " + latLng.longitude)
//            );
//        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.administrator_diaglog_fragment_map_btn_validate) {
            Log.d(App.DEFAULT_TAG, "Pulsado:" + "administrator_diaglog_fragment_map_btn_validate");
        }
    }
}