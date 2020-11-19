/*
 * Created by Francisco Javier Paños Madrona on 6/11/20 18:00
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 14/11/20 14:45
 */

package com.example.murbin.presentation.zone.administrator;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.example.murbin.BaseActivity;
import com.example.murbin.R;
import com.example.murbin.firebase.Auth;
import com.example.murbin.presentation.auth.AuthEmailActivity;
import com.example.murbin.presentation.global.PreferencesActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdministratorMainActivity extends BaseActivity {

    /**
     * Constant for ease of use in debugging the class code
     */
    private static final String TAG = AdministratorMainActivity.class.getSimpleName();

    private final Auth mAuth = new Auth(this);

    private Toolbar mToolbar;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.administrator_main_activity);
        initializeLayoutElements();
    }

    /**
     * Method to start the layout elements
     */
    private void initializeLayoutElements() {
        // Toolbar menu
        mToolbar = findViewById(R.id.administrator_main_activity_toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }

        //BottomNavigationView menu
        bottomNavigationView = findViewById(R.id.administrator_main_activity_bottom_navigation);
        actionsBottomNavigationView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.administrator_main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.administrator_main_menu_settings: {
                Intent intent = new Intent(this, PreferencesActivity.class);
                startActivity(intent);

                break;
            }

            case R.id.administrator_main_menu_logout: {
                mAuth.signOut();
                Intent intent = new Intent(AdministratorMainActivity.this, AuthEmailActivity.class);
                startActivity(intent);

                break;
            }
        }

        return super.onOptionsItemSelected(item);

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
                    case R.id.administrator_main_bottom_navigation_home:
                        Toast.makeText(AdministratorMainActivity.this, "Menú Home pulsado", Toast.LENGTH_SHORT).show();

                        break;

                    case R.id.administrator_main_bottom_navigation_subzones:
                        Toast.makeText(AdministratorMainActivity.this, "Menú Subzonas pulsado", Toast.LENGTH_SHORT).show();

                        break;

                    case R.id.administrator_main_bottom_navigation_technician:
                        Toast.makeText(AdministratorMainActivity.this, "Menú Técnicos pulsado", Toast.LENGTH_SHORT).show();

                        break;

                    case R.id.administrator_main_bottom_navigation_config:
                        Toast.makeText(AdministratorMainActivity.this, "Menú Configuración pulsado", Toast.LENGTH_SHORT).show();

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
                    case R.id.administrator_main_bottom_navigation_home:
                        Toast.makeText(AdministratorMainActivity.this, "Menú Home ya está pulsado", Toast.LENGTH_SHORT).show();

                        break;

                    case R.id.administrator_main_bottom_navigation_subzones:
                        Toast.makeText(AdministratorMainActivity.this, "Menú Subzonas ya está pulsado", Toast.LENGTH_SHORT).show();

                        break;

                    case R.id.administrator_main_bottom_navigation_technician:
                        Toast.makeText(AdministratorMainActivity.this, "Menú Técnicos ya está pulsado", Toast.LENGTH_SHORT).show();

                        break;

                    case R.id.administrator_main_bottom_navigation_config:
                        Toast.makeText(AdministratorMainActivity.this, "Menú Configuración ya está pulsado", Toast.LENGTH_SHORT).show();

                        break;
                }
            }
        });
    }

}