/*
 * Created by Francisco Javier Paños Madrona on 6/11/20 18:00
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 14/11/20 14:45
 */

package com.example.murbin.presentation.zone.scientific;

import android.os.Bundle;

import com.example.murbin.BaseActivity;
import com.example.murbin.R;

public class ScientificMainActivity extends BaseActivity {

    /**
     * Constant for ease of use in debugging the class code
     */
    private static final String TAG = ScientificMainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scientific_main_activity);
    }

}