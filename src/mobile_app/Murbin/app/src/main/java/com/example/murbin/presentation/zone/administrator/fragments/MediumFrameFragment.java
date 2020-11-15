/*
 * Created by Francisco Javier Paños Madrona on 6/11/20 18:00
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 14/11/20 14:45
 */

package com.example.murbin.presentation.zone.administrator.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.murbin.App;
import com.example.murbin.R;

public class MediumFrameFragment extends Fragment {

    /**
     * Constant for ease of use in debugging the class code
     */
    private static final String TAG = MediumFrameFragment.class.getSimpleName();

    App aplicacion = (App) getContext();

    @Override
    public View onCreateView(LayoutInflater inflador, ViewGroup contenedor, Bundle savedInstanceState) {
        View view = inflador.inflate(R.layout.administrator_fragment_medium_frame, contenedor, false);

        return view;
    }
}