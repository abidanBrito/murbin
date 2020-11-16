/*
 * Created by Francisco Javier Paños Madrona on 15/11/20 15:43
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 15/11/20 15:43
 */

package com.example.murbin.presentation.auth;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import com.example.murbin.App;
import com.example.murbin.BaseActivity;
import com.example.murbin.R;
import com.example.murbin.firebase.Auth;

public class AuthEmailActivity extends BaseActivity implements View.OnClickListener {

    /**
     * Constant for ease of use in debugging the class code
     */
    private static final String TAG = AuthEmailActivity.class.getSimpleName();

    private final Auth mAuth = new Auth(this);

    private String email = "";
    private String password = "";

    private ViewGroup container;
    private Toolbar toolbar;
    private EditText et_email, et_password;
    private TextView tv_remember_password;
    private Button btn_auth_with_email, btn_auth_with_google;

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.isLogged()) {

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_email_activity);
        initializeLayoutElements();
    }

    /**
     * Method to start the layout elements
     */
    private void initializeLayoutElements() {
        // Toolbar menu
        toolbar = findViewById(R.id.auth_email_activity_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
            getSupportActionBar().setTitle("");
        }

        container = findViewById(R.id.auth_email_activity_container);
        et_email = findViewById(R.id.auth_email_activity_et_email);
        et_password = findViewById(R.id.auth_email_activity_et_password);
        tv_remember_password = findViewById(R.id.auth_email_activity_tv_remember_password);
        btn_auth_with_email = findViewById(R.id.auth_email_activity_btn_auth_with_email);
        btn_auth_with_google = findViewById(R.id.auth_email_activity_btn_auth_with_google);

        // setOnClickListener
        tv_remember_password.setOnClickListener(this);
        btn_auth_with_email.setOnClickListener(this);
        btn_auth_with_google.setOnClickListener(this);
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
            App.getInstance().snackMessage(container, R.color.principal, "Introduce un correo", AuthEmailActivity.this);
        } else if (password.isEmpty()) {
            // Show error on screen below form field
            App.getInstance().snackMessage(container, R.color.principal, " Introduce una contraseña", AuthEmailActivity.this);
        } else {

            return true;
        }

        return false;
    }

    /**
     * Disconnect the user
     */
    protected void signOut() {
        mAuth.signOut();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);

                return true;
        }

        return super.onOptionsItemSelected(item);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.auth_email_activity_tv_remember_password:
                email = et_email.getText().toString();
                if (email.isEmpty()) {
                    // Show error on screen below form field
                    App.getInstance().snackMessage(container, R.color.principal, "Introduce un correo", AuthEmailActivity.this);
                } else {
                    mAuth.sendPasswordResetEmail(email);
                }
                break;

            case R.id.auth_email_activity_btn_auth_with_email:
                if (checkFormLogin()) {
                    mAuth.signInWithEmailAndPassword(email, password);
                } else {
                    // Show error
                    App.getInstance().snackMessage(container, R.color.principal, " Debes indicar email y password", AuthEmailActivity.this);
                }
                break;

            case R.id.auth_email_activity_btn_auth_with_google:
                // Call action Google
                App.getInstance().snackMessage(container, R.color.principal, " Login con Google pulsado", AuthEmailActivity.this);
                break;
        }
    }

}
