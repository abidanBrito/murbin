/*
 * Created by Francisco Javier Paños Madrona on 24/11/20 8:24
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 23/11/20 10:55
 */

package com.example.murbin.presentation.auth;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import com.example.murbin.R;
import com.example.murbin.firebase.Auth;
import com.example.murbin.presentation.auth.AuthEmailActivity;
import com.example.murbin.presentation.auth.fragments.AuthEmalFragmentRecoveryPassword;
import com.example.murbin.presentation.zone.general.GeneralMainActivity;

public class AuthEmailFragmentHome extends Fragment implements View.OnClickListener {

    private Auth mAuth;
    private AuthEmailActivity authEmailActivity;

    private String m_email = "";
    private String m_password = "";

    private Toolbar mToolbar;
    private EditText m_et_email, m_et_password;
    private TextView m_tv_remember_password;
    private Button m_btn_enter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = new Auth(getActivity());
        setHasOptionsMenu(true); // For onOptionsItemSelected to work
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.auth_email_fragment_home, container, false);
        initializeLayoutElements(view);

        return view;
    }

    /**
     * Method to start the layout elements
     *
     * @param view View
     */
    private void initializeLayoutElements(View view) {
        authEmailActivity = (AuthEmailActivity) getActivity();

        // Toolbar menu
        mToolbar = view.findViewById(R.id.auth_email_activity_toolbar);
        authEmailActivity.setSupportActionBar(mToolbar);
        if (authEmailActivity.getSupportActionBar() != null) {
            authEmailActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            authEmailActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
            authEmailActivity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
            authEmailActivity.getSupportActionBar().setTitle("");
        }

        m_et_email = view.findViewById(R.id.auth_email_activity_et_email);
        m_et_password = view.findViewById(R.id.auth_email_activity_et_password);
        m_tv_remember_password = view.findViewById(R.id.auth_email_activity_tv_remember_password);
        m_btn_enter = view.findViewById(R.id.auth_email_activity_btn_enter);

        // setOnClickListener
        m_tv_remember_password.setOnClickListener(this);
        m_btn_enter.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.auth_email_activity_tv_remember_password: {
                authEmailActivity.replaceFragments(R.id.auth_email_activity_fragment, AuthEmalFragmentRecoveryPassword.class);

                break;
            }

            case R.id.auth_email_activity_btn_enter: {
                if (checkForm()) {
                    mAuth.signInWithEmailAndPassword(m_email, m_password);
                }

                break;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(getActivity(), GeneralMainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Check if form is correctly
     *
     * @return boolean
     */
    private boolean checkForm() {
        boolean result = true;

        m_email = m_et_email.getText().toString();
        m_password = m_et_password.getText().toString();

        if (m_email.equals("")) {
            m_et_email.setError("Debes indicar un email.");
            result = false;
        }
        if (m_password.equals("")) {
            m_et_password.setError("Debes indicar la contraseña.");
            result = false;
        }

        return result;
    }

}
