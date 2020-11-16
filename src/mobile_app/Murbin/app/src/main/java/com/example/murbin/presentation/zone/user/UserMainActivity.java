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
import androidx.core.app.NavUtils;

import com.example.murbin.BaseActivity;
import com.example.murbin.R;
import com.example.murbin.presentation.auth.AuthEmailActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserMainActivity extends BaseActivity {

    /**
     * Constant for ease of use in debugging the class code
     */
    private static final String TAG = UserMainActivity.class.getSimpleName();
    Toolbar mToolbar;
    BottomNavigationView bottomNavigationView;

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
            case R.id.user_main_menu_account:
                Toast.makeText(this, "Menú account pulsado", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UserMainActivity.this, AuthEmailActivity.class);
                startActivity(intent);
                finish();

                return true;
        }

        return super.onOptionsItemSelected(item);

    }

    public void actionsBottomNavigationView() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_main_menu_home:
                        Toast.makeText(UserMainActivity.this, "Menú Home pulsado", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.bottom_main_menu_map:
                        Toast.makeText(UserMainActivity.this, "Menú Mapa pulsado", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
    }

}