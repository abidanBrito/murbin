/*
 * Created by Francisco Javier Paños Madrona on 17/11/20 13:11
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 17/11/20 13:10
 */

package com.example.murbin.presentation.global;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.example.murbin.App;
import com.example.murbin.BaseActivity;
import com.example.murbin.R;
import com.example.murbin.models.User;
import com.example.murbin.presentation.global.fragments.GlobalPreferencesFragment;
import com.example.murbin.presentation.zone.administrator.AdministratorMainActivity;
import com.example.murbin.presentation.zone.general.GeneralMainActivity;
import com.example.murbin.presentation.zone.technician.TechnicianMainActivity;

public class GlobalPreferencesActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preferences_activity);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.preferences_activity_settings, new GlobalPreferencesFragment())
                    .commit();
        }

        initializeLayoutElements();
    }

    /**
     * Method to start the layout elements
     */
    private void initializeLayoutElements() {
        Toolbar toolbar = findViewById(R.id.preferences_activity_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
            getSupportActionBar().setTitle(getText(R.string.preferences_activity_title));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            User user = App.getCurrentUser();
            Class<?> redirectActivityClass = GeneralMainActivity.class;

            if (user != null) {
                switch (user.getRole()) {
                    case "root":
                    case "administrator":
                        redirectActivityClass = AdministratorMainActivity.class;
                        break;
                    case "technician":
                        redirectActivityClass = TechnicianMainActivity.class;
                        break;
                    default:
                        redirectActivityClass = GeneralMainActivity.class;
                        break;
                }
            }

            Intent intent = new Intent(GlobalPreferencesActivity.this, redirectActivityClass);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
