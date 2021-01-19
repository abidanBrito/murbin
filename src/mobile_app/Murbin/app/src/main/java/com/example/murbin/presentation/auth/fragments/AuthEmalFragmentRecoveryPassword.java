/*
 * Created by Francisco Javier Paños Madrona on 24/11/20 8:25
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 18/11/20 16:42
 */

package com.example.murbin.presentation.auth.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.murbin.App;
import com.example.murbin.R;
import com.example.murbin.firebase.Auth;
import com.example.murbin.presentation.auth.AuthEmailActivity;

public class AuthEmalFragmentRecoveryPassword extends Fragment implements View.OnClickListener {

    private final Auth mAuth = new Auth(getActivity());
    private AuthEmailActivity authEmailActivity;

    private String m_email = "";

    private Toolbar mToolbar;
    private EditText m_et_email;
    private Button m_btn_enter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); // For onOptionsItemSelected to work
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.auth_email_fragment_recovery_password, container, false);

        initializeLayoutElements(view);

        return view;
    }

    /**
     * Method to start the layout elements
     */
    private void initializeLayoutElements(View view) {
        authEmailActivity = (AuthEmailActivity) getActivity();

        // Toolbar menu
        mToolbar = view.findViewById(R.id.auth_email_recovery_password_fragment_toolbar);
        authEmailActivity.setSupportActionBar(mToolbar);
        if (authEmailActivity.getSupportActionBar() != null) {
            authEmailActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            authEmailActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
            authEmailActivity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
            authEmailActivity.getSupportActionBar().setTitle("");
        }

        m_et_email = view.findViewById(R.id.auth_email_recovery_password_fragment_et_email);
        m_btn_enter = view.findViewById(R.id.auth_email_recovery_password_fragment_btn_enter);

        // setOnClickListener
        m_btn_enter.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            authEmailActivity.replaceFragments(R.id.auth_email_activity_fragment, AuthEmailFragmentHome.class);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.auth_email_recovery_password_fragment_btn_enter) {
            if (checkForm()) {
                mAuth.sendPasswordResetEmail(m_email);
            }
        }
    }
    
    /**
     * Check if form is correctly
     *
     * @return boolean
     */
    private boolean checkForm() {
        boolean result = true;

        m_email = m_et_email.getText().toString();

        if (m_email.equals("") || !App.isValidEmail(m_email)) {
            m_et_email.setError("Debes indicar un email.");
            result = false;
        }

        return result;
    }
}
