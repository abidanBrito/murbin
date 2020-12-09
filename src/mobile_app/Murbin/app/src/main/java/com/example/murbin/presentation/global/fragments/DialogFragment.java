/*
 * Created by Francisco Javier Paños Madrona on 17/11/20 11:09
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 17/11/20 11:09
 */

package com.example.murbin.presentation.global.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.murbin.R;

/**
 * DialogFragment
 * https://developer.android.com/reference/android/app/DialogFragment.html#BasicDialog
 */
public class DialogFragment extends androidx.fragment.app.DialogFragment {

    public DialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static DialogFragment newInstance(String title, String message) {
        DialogFragment dialogFragment = new DialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("message", message);
        dialogFragment.setArguments(args);

        return dialogFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            TextView tv_message = view.findViewById(R.id.dialog_fragment_tv_message);
            String title = getArguments().getString("title", "");
            String message = getArguments().getString("message", "");

            if (getDialog() != null) {
                getDialog().setTitle(title);
                tv_message.setText(message);
                getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            }
        }

    }

}