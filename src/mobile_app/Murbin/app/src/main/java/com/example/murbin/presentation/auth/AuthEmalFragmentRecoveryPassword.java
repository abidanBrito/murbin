/*
 * Created by Francisco Javier Paños Madrona on 18/11/20 12:09
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 18/11/20 12:09
 */

package com.example.murbin.presentation.auth;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.murbin.App;
import com.example.murbin.R;
import com.example.murbin.firebase.Auth;

public class AuthEmalFragmentRecoveryPassword extends Fragment implements View.OnClickListener {

    /**
     * Constant for ease of use in debugging the class code
     */
    private static final String TAG = AuthEmalFragmentRecoveryPassword.class.getSimpleName();

    private final Auth mAuth = new Auth(getActivity());
    private AuthEmailActivity authEmailActivity;

    private String email = "";

    private ViewGroup container;
    private Toolbar toolbar;
    private EditText et_email;
    private TextView tv_error_tv_email;
    private Button btn_enter;

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
        toolbar = view.findViewById(R.id.auth_email_recovery_password_fragment_toolbar);
        authEmailActivity.setSupportActionBar(toolbar);
        if (authEmailActivity.getSupportActionBar() != null) {
            authEmailActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            authEmailActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
            authEmailActivity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
            authEmailActivity.getSupportActionBar().setTitle("");
        }

        container = view.findViewById(R.id.auth_email_recovery_password_fragment_container);
        et_email = view.findViewById(R.id.auth_email_recovery_password_fragment_et_email);
        tv_error_tv_email = view.findViewById(R.id.auth_email_recovery_password_fragment_tv_error_et_email);
        btn_enter = view.findViewById(R.id.auth_email_recovery_password_fragment_btn_enter);

        // setOnClickListener
        btn_enter.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Toast.makeText(App.getContext(), "Botón atrás pulsado", Toast.LENGTH_SHORT).show();
            authEmailActivity.replaceFragments(R.id.auth_email_activity_fragment, AuthEmailFragmentHome.class);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.auth_email_recovery_password_fragment_btn_enter) {
            email = et_email.getText().toString();
            if (email.isEmpty()) {
                // Show error on screen below form field
                App.getInstance().snackMessage(container, R.color.principal, "Introduce un correo", App.getContext());
                tv_error_tv_email.setVisibility(View.VISIBLE);
                tv_error_tv_email.setText("Introduce un correo");
            } else {
                mAuth.sendPasswordResetEmail(email);
            }
        }
    }
}
