/*
 * Created by Francisco Javier Paños Madrona on 21/11/20 20:43
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 21/11/20 20:43
 */

package com.example.murbin.uses_cases;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.murbin.R;
import com.example.murbin.presentation.zone.general.fragments.GeneralFragmentMap;

public class UsesCasesZoneAdministrator {

    /**
     * Constructor empty
     */
    public UsesCasesZoneAdministrator() {
        //
    }

    /**
     * Change fragment to GeneralFragmentMap
     */
    public void loadEdit(Context context) {
        Fragment fragment = new GeneralFragmentMap();
        FragmentTransaction transaction_map = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
        transaction_map.replace(R.id.general_main_activity_fragment, fragment);
        transaction_map.addToBackStack(null);
        transaction_map.commit();
    }
}
