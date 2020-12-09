/*
 * Created by Francisco Javier Paños Madrona on 17/11/20 13:07
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 17/11/20 13:07
 */

package com.example.murbin.presentation.global.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.example.murbin.App;
import com.example.murbin.R;
import com.example.murbin.services.BackgroundMusicService;

public class GlobalPreferencesFragment extends PreferenceFragmentCompat {

    private static final String TAG_Preferences = "global_preferences";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.global_preferences, TAG_Preferences);

        final SwitchPreferenceCompat musicStatus = findPreference("global_preferences_key_background_music");

        if (musicStatus != null) {
            musicStatus.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    App.getInstance().changeServiceStatus(BackgroundMusicService.class, (Boolean) newValue);

                    return true;
                }
            });
        }
    }

}
