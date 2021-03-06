/*
 * Created by Francisco Javier Paños Madrona on 9/12/20 19:03
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 9/12/20 17:41
 */

package com.example.murbin.presentation.zone.technician;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.example.murbin.App;
import com.example.murbin.BaseActivity;
import com.example.murbin.R;
import com.example.murbin.firebase.Auth;
import com.example.murbin.models.User;
import com.example.murbin.presentation.auth.AuthEmailActivity;
import com.example.murbin.presentation.global.GlobalPreferencesActivity;
import com.example.murbin.presentation.zone.technician.fragments.SubzonesListFragment;

public class TechnicianMainActivity extends BaseActivity {

    private final Auth mAuth = new Auth(this);

    private Toolbar mToolbar;
    private ViewGroup mContainer;
    private String mMessage;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(App.DEFAULT_TAG, "TechnicianMainActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.technician_main_activity);
        initializeLayoutElements();
        if (savedInstanceState == null) {
            SubzonesListFragment subzonesListFragment = new SubzonesListFragment();
            subzonesListFragment.settings(App.getInstance().getCurrentUser());
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.technician_subzone_fragment, subzonesListFragment);
        }
    }

    /**
     * Method to start the layout elements
     */
    private void initializeLayoutElements() {
        // Toolbar menu
        mToolbar = findViewById(R.id.technician_main_activity_toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }

        mContainer = findViewById(R.id.technician_main_activity_container);

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
        getMenuInflater().inflate(R.menu.technician_main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.technician_main_menu_settings: {
                Intent intent = new Intent(this, GlobalPreferencesActivity.class);
                startActivity(intent);

                break;
            }

            case R.id.technician_main_menu_logout: {
                mAuth.signOut();
                Intent intent = new Intent(this, AuthEmailActivity.class);
                startActivity(intent);

                break;
            }
        }

        return super.onOptionsItemSelected(item);

    }

}