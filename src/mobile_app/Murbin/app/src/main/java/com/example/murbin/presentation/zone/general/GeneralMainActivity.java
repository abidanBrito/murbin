/*
 * Created by Francisco Javier Paños Madrona on 6/11/20 18:00
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 14/11/20 14:45
 */

package com.example.murbin.presentation.zone.general;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.murbin.App;
import com.example.murbin.BaseActivity;
import com.example.murbin.R;
import com.example.murbin.presentation.auth.AuthEmailActivity;
import com.example.murbin.presentation.global.GlobalPreferencesActivity;
import com.example.murbin.presentation.zone.general.fragments.GeneralFragmentHome;
import com.example.murbin.presentation.zone.general.fragments.GeneralFragmentMap;
import com.example.murbin.uses_cases.UsesCasesZoneGeneral;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class GeneralMainActivity extends BaseActivity {

    private Toolbar mToolbar;
    private BottomNavigationView bottomNavigationView;
    private Fragment fragment;
    private UsesCasesZoneGeneral usesCasesZoneGeneral;
    private ViewGroup mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getInstance().toZoneUserLogged();
        setContentView(R.layout.general_main_activity);
        usesCasesZoneGeneral = new UsesCasesZoneGeneral();
        initializeLayoutElements();
    }

    /**
     * Method to start the layout elements
     */
    private void initializeLayoutElements() {
        // Toolbar menu
        mToolbar = findViewById(R.id.general_main_activity_toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }

        mContainer = findViewById(R.id.general_main_activity_container);

        // BottomNavigationView menu
        bottomNavigationView = findViewById(R.id.general_main_activity_bottom_navigation);
        actionsBottomNavigationView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.general_main_menu, menu);

        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.general_main_menu_account: {
                Intent intent = new Intent(GeneralMainActivity.this, AuthEmailActivity.class);
                startActivity(intent);

                break;
            }

            case R.id.general_main_menu_settings: {
                Intent i = new Intent(this, GlobalPreferencesActivity.class);
                startActivity(i);

                break;
            }

            case R.id.general_main_menu_about_murbin: {
                showDialogFragment(
                        getString(R.string.dialog_fragment_tv_title_default),
                        getString(R.string.dialog_fragment_tv_message_default));

                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == App.PERMIT_REQUEST_CODE_ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                usesCasesZoneGeneral.checkPermissionLoadMap(GeneralMainActivity.this);
            } else {
                App.getInstance().snackMessage(mContainer, R.color.black,
                        getString(R.string.GeneralMainActivity_permission_error_1),
                        GeneralMainActivity.this);
            }
        }
    }

    /**
     * Method for bottom navigation functionality
     */
    public void actionsBottomNavigationView() {
        // Actions when pressing an option in the lower navigation
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            //@SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.toString()) {
                    //case R.id.general_main_bottom_navigation_home:
                    case "Home":
                        fragment = new GeneralFragmentHome();
                        FragmentTransaction transaction_home = getSupportFragmentManager().beginTransaction();
                        transaction_home.replace(R.id.general_main_activity_fragment, fragment);
                        transaction_home.addToBackStack(null);
                        transaction_home.commit();

                        break;

                    //case R.id.general_main_bottom_navigation_map:
                    case "Mapa":
                        usesCasesZoneGeneral.checkPermissionLoadMap(GeneralMainActivity.this);
                        //Toast.makeText(GeneralMainActivity.this, "Menú Histórico pulsado", Toast.LENGTH_SHORT).show();
                        //checkPermissionLoadMap(GeneralMainActivity.this);
                        break;
                }

                return true;
            }
        });

        // Actions when pressing an option already selected from the lower navigation
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.general_main_bottom_navigation_home:
                        // None action

                        break;

                    case R.id.general_main_bottom_navigation_map:
                        // None action

                        break;
                }
            }
        });
    }

    /*public void loadMap(Context context) {
        Fragment fragment = new GeneralFragmentMap();
        FragmentTransaction transaction_map = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
        transaction_map.replace(R.id.general_main_activity_fragment, fragment);
        transaction_map.addToBackStack(null);
        transaction_map.commit();
        if (ActivityCompat.checkSelfPermission(GeneralMainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Create new fragment and transaction
            fragment = new GeneralFragmentMap();
            FragmentTransaction transaction_map = getSupportFragmentManager().beginTransaction();
            transaction_map.replace(R.id.general_main_activity_fragment, fragment);
            transaction_map.addToBackStack(null);
            transaction_map.commit();
        } else {
            App.getInstance().requestPermission(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    "Sin el permiso, no puedo mostrar el Mapa",
                    App.PERMIT_REQUEST_CODE_ACCESS_FINE_LOCATION,
                    GeneralMainActivity.this
            );
        }
    }

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
    }*/
}