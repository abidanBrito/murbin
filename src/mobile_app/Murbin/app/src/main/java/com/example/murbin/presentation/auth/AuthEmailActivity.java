/*
 * Created by Francisco Javier Paños Madrona on 15/11/20 15:43
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 15/11/20 15:43
 */

package com.example.murbin.presentation.auth;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.murbin.BaseActivity;
import com.example.murbin.R;
import com.example.murbin.firebase.Auth;

public class AuthEmailActivity extends BaseActivity {

    /**
     * Constant for ease of use in debugging the class code
     */
    private static final String TAG = AuthEmailActivity.class.getSimpleName();

    private final Auth mAuth = new Auth(this);

    private final String email = "";
    private final String password = "";

    private ViewGroup container;
    private Toolbar toolbar;
    private EditText et_email, et_password;
    private TextView tv_remember_password;
    private Button btn_enter;

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.isLogged()) {
            // logic - Redirect based on user role
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
    }

    /**
     * It receives the identifier of the resource of the containing fragment and
     * the class of fragment to which it wants to change.
     *
     * @param fragmentContainerId Identifier of the resource of the containing fragment
     * @param fragmentClass       Class of fragment
     */
    public void replaceFragments(int fragmentContainerId, Class<?> fragmentClass) {
        try {
            Fragment fragment = (Fragment) fragmentClass.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(fragmentContainerId, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
