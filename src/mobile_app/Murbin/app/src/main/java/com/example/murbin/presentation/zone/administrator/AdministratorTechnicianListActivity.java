/*
 * Created by Francisco Javier Paños Madrona on 23/11/20 10:39
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 23/11/20 10:39
 */

package com.example.murbin.presentation.zone.administrator;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.example.murbin.App;
import com.example.murbin.BaseActivity;
import com.example.murbin.R;
import com.example.murbin.firebase.Auth;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdministratorTechnicianListActivity extends BaseActivity {

    private final Auth mAuth = new Auth(this);

    private ViewGroup mContainer;
    private Toolbar mToolbar;
    private BottomNavigationView mBottomNavigationView;
    private String mMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.administrator_technicians_list);
        initializeLayoutElements();
    }

    /**
     * Method to start the layout elements
     */
    private void initializeLayoutElements() {
        // Toolbar menu
        mToolbar = findViewById(R.id.administrator_technicians_list_toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }

        mContainer = findViewById(R.id.administrator_technicians_list_container);

        // BottomNavigationView menu
        mBottomNavigationView = findViewById(R.id.administrator_technicians_list_bottom_navigation);
        if (App.getInstance().getCurrentUser() != null && App.getInstance().getCurrentUser().getRole().equals(App.ROLE_ROOT)) {
            mBottomNavigationView.getMenu().clear();
            mBottomNavigationView.inflateMenu(R.menu.root_main_bottom_navigation);
            mBottomNavigationView.setSelectedItemId(R.id.root_main_bottom_navigation_technician);
        } else {
            mBottomNavigationView.setSelectedItemId(R.id.administrator_main_bottom_navigation_technician);
        }
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
        getMenuInflater().inflate(R.menu.administrator_technicians_list_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.administrator_technicians_list_menu_create: {
                Intent intent = new Intent(AdministratorTechnicianListActivity.this, AdministratorTechnicianCreateActivity.class);
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
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                int id = item.getItemId();
                if (id == R.id.administrator_main_bottom_navigation_home
                        || id == R.id.root_main_bottom_navigation_home) {
                    intent = new Intent(AdministratorTechnicianListActivity.this, AdministratorMainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else if (id == R.id.administrator_main_bottom_navigation_subzones
                        || id == R.id.root_main_bottom_navigation_subzones) {
                    intent = new Intent(AdministratorTechnicianListActivity.this, AdministratorSubzoneListActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else if (id == R.id.administrator_main_bottom_navigation_technician
                        || id == R.id.root_main_bottom_navigation_technician) {
                    // Empty activity
                } else if (id == R.id.root_main_bottom_navigation_administrator) {
                    intent = new Intent(AdministratorTechnicianListActivity.this, RootAdministratorListActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }

                return true;
            }
        });

        // Actions when pressing an option already selected from the lower navigation
        mBottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.administrator_main_bottom_navigation_home
                        || id == R.id.root_main_bottom_navigation_home) {
                    // Empty activity
                } else if (id == R.id.administrator_main_bottom_navigation_subzones
                        || id == R.id.root_main_bottom_navigation_subzones) {
                    // Empty action
                } else if (id == R.id.administrator_main_bottom_navigation_technician
                        || id == R.id.root_main_bottom_navigation_technician) {
                    // Empty action
                } else if (id == R.id.root_main_bottom_navigation_administrator) {
                    // Empty action
                }
            }
        });
    }

}
