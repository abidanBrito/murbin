/*
 * Created by Francisco Javier Paños Madrona on 18/11/20 12:09
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 18/11/20 12:09
 */

package com.example.murbin.presentation.auth;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.murbin.App;
import com.example.murbin.R;
import com.example.murbin.firebase.Auth;

public class AuthEmailFragmentHome extends Fragment implements View.OnClickListener {

    /**
     * Constant for ease of use in debugging the class code
     */
    private static final String TAG = AuthEmailFragmentHome.class.getSimpleName();

    private final Auth mAuth = new Auth(getActivity());
    private AuthEmailActivity authEmailActivity;

    private String email = "";
    private String password = "";

    private ViewGroup container;
    private Toolbar toolbar;
    private EditText et_email, et_password;
    private TextView tv_remember_password;
    private Button btn_enter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
     */
    private void initializeLayoutElements(View view) {
        authEmailActivity = (AuthEmailActivity) getActivity();

        // Toolbar menu
        toolbar = view.findViewById(R.id.auth_email_activity_toolbar);
        authEmailActivity.setSupportActionBar(toolbar);
        if (authEmailActivity.getSupportActionBar() != null) {
            authEmailActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            authEmailActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
            authEmailActivity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
            authEmailActivity.getSupportActionBar().setTitle("");
        }

        container = view.findViewById(R.id.auth_email_activity_container);
        et_email = view.findViewById(R.id.auth_email_activity_et_email);
        et_password = view.findViewById(R.id.auth_email_activity_et_password);
        tv_remember_password = view.findViewById(R.id.auth_email_activity_tv_remember_password);
        btn_enter = view.findViewById(R.id.auth_email_activity_btn_enter);

        // setOnClickListener
        tv_remember_password.setOnClickListener(this);
        btn_enter.setOnClickListener(this);
    }

    /**
     * Check if the data in the login form is correct
     *
     * @return boolean
     */
    private boolean checkFormLogin() {
        email = et_email.getText().toString();
        password = et_password.getText().toString();

        if (email.isEmpty()) {
            // Show error on screen below form field
            App.getInstance().snackMessage(container, R.color.principal, "Introduce un correo", authEmailActivity);
        } else if (password.isEmpty()) {
            // Show error on screen below form field
            App.getInstance().snackMessage(container, R.color.principal, " Introduce una contraseña", authEmailActivity);
        } else {

            return true;
        }

        return false;
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
                if (checkFormLogin()) {
                    mAuth.signInWithEmailAndPassword(email, password);
                } else {
                    // Show error
                    App.getInstance().snackMessage(container, R.color.principal, " Debes indicar email y password", authEmailActivity);
                }

                break;
            }
        }
    }

}
