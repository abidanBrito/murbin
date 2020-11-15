/*
 * Created by Francisco Javier Paños Madrona on 6/11/20 18:00
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 14/11/20 14:45
 */

package com.example.murbin.presentation.zone.user;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.murbin.BaseActivity;
import com.example.murbin.R;
import com.example.murbin.uses_cases.UsesCasesAuth;

public class UserMainActivity extends BaseActivity {

    /**
     * Constant for ease of use in debugging the class code
     */
    private static final String TAG = UserMainActivity.class.getSimpleName();

    private UsesCasesAuth usesCasesAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_main_activity);

        Toolbar toolbar = findViewById(R.id.user_main_activity_toolbar);
        setSupportActionBar(toolbar);
    }


}