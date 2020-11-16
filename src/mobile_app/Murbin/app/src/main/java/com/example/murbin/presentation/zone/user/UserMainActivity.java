/*
 * Created by Francisco Javier Paños Madrona on 6/11/20 18:00
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 14/11/20 14:45
 */

package com.example.murbin.presentation.zone.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.murbin.BaseActivity;
import com.example.murbin.R;
import com.example.murbin.presentation.auth.AuthEmailActivity;
import com.example.murbin.presentation.zone.user.fragments.UserHomeFragment;
import com.example.murbin.presentation.zone.user.fragments.UserMapFragment;
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
                    case R.id.bottom_main_menu_home:
//                        Toast.makeText(UserMainActivity.this, "Menú Home pulsado", Toast.LENGTH_SHORT).show();
                        // Create new fragment and transaction
                        fragment = new UserHomeFragment();
                        FragmentTransaction transaction_home = getSupportFragmentManager().beginTransaction();
                        transaction_home.replace(R.id.user_main_activity_fragment, fragment);
                        transaction_home.addToBackStack(null);
                        transaction_home.commit();

                        break;

                    case R.id.bottom_main_menu_map:
//                        Toast.makeText(UserMainActivity.this, "Menú Mapa pulsado", Toast.LENGTH_SHORT).show();
                        // Create new fragment and transaction
                        fragment = new UserMapFragment();
                        FragmentTransaction transaction_map = getSupportFragmentManager().beginTransaction();
                        transaction_map.replace(R.id.user_main_activity_fragment, fragment);
                        transaction_map.addToBackStack(null);
                        transaction_map.commit();

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
                    case R.id.bottom_main_menu_home:
//                        Toast.makeText(UserMainActivity.this, "Menú Home ya está pulsado", Toast.LENGTH_SHORT).show();
                        // None action

                        break;

                    case R.id.bottom_main_menu_map:
//                        Toast.makeText(UserMainActivity.this, "Menú Mapa  ya está pulsado", Toast.LENGTH_SHORT).show();
                        // None action

                        break;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_main_menu, menu);

        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.user_main_menu_account) {
            Intent intent = new Intent(UserMainActivity.this, AuthEmailActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);

    }

}