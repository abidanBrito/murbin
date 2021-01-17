/*
 * Created by Francisco Javier Paños Madrona on 6/11/20 18:00
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 14/11/20 14:45
 */

package com.example.murbin.presentation.zone.general;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.murbin.App;
import com.example.murbin.BaseActivity;
import com.example.murbin.R;
import com.example.murbin.presentation.auth.AuthEmailActivity;
import com.example.murbin.presentation.global.GlobalPreferencesActivity;
import com.example.murbin.uses_cases.UsesCasesZoneGeneral;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class GeneralMainActivity extends BaseActivity {

    private Toolbar mToolbar;
    private BottomNavigationView bottomNavigationView;
    private Fragment fragment;
    private UsesCasesZoneGeneral usesCasesZoneGeneral;
    private ViewGroup mContainer;
    private String mMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        App.getInstance().toZoneUserLogged();
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

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.containsKey("message")) {
                mMessage = extras.getString("message", "");
                if (mMessage != null && !mMessage.isEmpty()) {
                    App.getInstance().snackMessage(mContainer, R.color.black, mMessage, this);
                }
            }
        }
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
                Intent intent = new Intent(this, GlobalPreferencesActivity.class);
                startActivity(intent);

                break;
            }

            case R.id.general_main_menu_about_murbin: {
                showDialogFragment(
                        getString(R.string.dialog_fragment_tv_title_default),
                        getString(R.string.dialog_fragment_tv_message_default)
                );

                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == App.PERMIT_REQUEST_CODE_LOAD_MAP) {
            usesCasesZoneGeneral.showMessageIfPermissionsAreMissing(this, mContainer, permissions, grantResults);
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
                    case R.id.general_main_bottom_navigation_home:
                        usesCasesZoneGeneral.loadHome(GeneralMainActivity.this);

                        break;

                    case R.id.general_main_bottom_navigation_map:
                        usesCasesZoneGeneral.checkPermissionLoadMap(GeneralMainActivity.this);

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
}