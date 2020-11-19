/*
 * Created by Francisco Javier Paños Madrona on 6/11/20 18:00
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 14/11/20 14:45
 */

package com.example.murbin.presentation.zone.user;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
import com.example.murbin.presentation.global.PreferencesActivity;
import com.example.murbin.presentation.zone.user.fragments.UserFragmentHome;
import com.example.murbin.presentation.zone.user.fragments.UserFragmentMap;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserMainActivity extends BaseActivity {

    /**
     * Constant for ease of use in debugging the class code
     */
    private static final String TAG = UserMainActivity.class.getSimpleName();
    Toolbar mToolbar;
    BottomNavigationView bottomNavigationView;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_main_activity);
        initializeLayoutElements();
    }

    /**
     * Method to start the layout elements
     */
    private void initializeLayoutElements() {
        // Toolbar menu
        mToolbar = findViewById(R.id.user_main_activity_toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }

        //BottomNavigationView menu
        bottomNavigationView = findViewById(R.id.user_main_activity_bottom_navigation);
        actionsBottomNavigationView();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_main_menu, menu);

        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.user_main_menu_account: {
                Intent intent = new Intent(UserMainActivity.this, AuthEmailActivity.class);
                startActivity(intent);

                break;
            }

            case R.id.user_main_menu_settings: {
                Intent i = new Intent(this, PreferencesActivity.class);
                startActivity(i);

                break;
            }

            case R.id.user_main_menu_about_murbin: {
                showDialogFragment(
                        getString(R.string.about_dialog_fragment_tv_title_default),
                        getString(R.string.about_dialog_fragment_tv_message_default));

                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == App.PERMIT_REQUEST_CODE_ACCESS_FINE_LOCATION) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadMap();
            } else {
                Toast.makeText(this, "Sin el permiso, no puedo realizar la " +
                        "acción", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadMap() {
        if (ActivityCompat.checkSelfPermission(UserMainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Create new fragment and transaction
            fragment = new UserFragmentMap();
            FragmentTransaction transaction_map = getSupportFragmentManager().beginTransaction();
            transaction_map.replace(R.id.user_main_activity_fragment, fragment);
            transaction_map.addToBackStack(null);
            transaction_map.commit();
        } else {
            App.getInstance().requestPermission(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    "Sin el permiso, no puedo mostrar el Mapa",
                    App.PERMIT_REQUEST_CODE_ACCESS_FINE_LOCATION,
                    UserMainActivity.this
            );
        }
    }

    /**
     * Method for bottom navigation functionality
     */
    public void actionsBottomNavigationView() {
        // Actions when pressing an option in the lower navigation
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.user_main_bottom_navigation_home:
//                        Toast.makeText(UserMainActivity.this, "Menú Home pulsado", Toast.LENGTH_SHORT).show();
                        // Create new fragment and transaction
                        fragment = new UserFragmentHome();
                        FragmentTransaction transaction_home = getSupportFragmentManager().beginTransaction();
                        transaction_home.replace(R.id.user_main_activity_fragment, fragment);
                        transaction_home.addToBackStack(null);
                        transaction_home.commit();

                        break;

                    case R.id.user_main_bottom_navigation_map:
//                        Toast.makeText(UserMainActivity.this, "Menú Mapa pulsado", Toast.LENGTH_SHORT).show();
                        loadMap();

                        break;

                    case R.id.user_main_bottom_navigation_data:
                        Toast.makeText(UserMainActivity.this, "Menú Histórico pulsado", Toast.LENGTH_SHORT).show();
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
                    case R.id.user_main_bottom_navigation_home:
//                        Toast.makeText(UserMainActivity.this, "Menú Home ya está pulsado", Toast.LENGTH_SHORT).show();
                        // None action

                        break;

                    case R.id.user_main_bottom_navigation_map:
//                        Toast.makeText(UserMainActivity.this, "Menú Mapa ya está pulsado", Toast.LENGTH_SHORT).show();
                        // None action

                        break;

                    case R.id.user_main_bottom_navigation_data:
                        Toast.makeText(UserMainActivity.this, "Menú Histórico ya está pulsado", Toast.LENGTH_SHORT).show();
                        // None action

                        break;
                }
            }
        });
    }
}