/*
 * Created by Francisco Javier Paños Madrona on 15/11/20 21:05
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 15/11/20 20:56
 */

package com.example.murbin.firebase;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.murbin.App;
import com.example.murbin.R;
import com.example.murbin.presentation.auth.AuthEmailActivity;
import com.example.murbin.presentation.zone.administrator.AdministratorMainActivity;
import com.example.murbin.presentation.zone.scientific.ScientificMainActivity;
import com.example.murbin.presentation.zone.technician.TechnicianMainActivity;
import com.example.murbin.presentation.zone.user.UserMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Class to manage Firebase Authentication
 */
public class Auth {

    /**
     * Constant for ease of use in debugging the class code
     */
    private static final String TAG = AuthEmailActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 1001;

    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser mFirebaseUser;

    private Activity activity = null;

    /**
     * Constructor Default
     */
    public Auth() {
        // Empty
    }

    /**
     * Constructor
     *
     * @param activity Activity from where it has been called
     */
    public Auth(Activity activity) {
        this.activity = activity;
    }

    /**
     * Check if the current user is logged in or not
     *
     * @return boolean False [not logged in], True [logged in]
     */
    public boolean isLogged() {
        return (mAuth.getCurrentUser() != null);
    }

    /**
     * Try to log in with the email and password passed as parameters
     *
     * @param email    User validated email
     * @param password User's validated password
     */
    public void signInWithEmailAndPassword(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if (isLogged()) {
//                        mFirebaseUser = mAuth.getCurrentUser();
//                        Log.d(TAG, "Nombre usuario: " + mFirebaseUser.getDisplayName());
                        // Recovery firestore data user and send role correctly
                        checkRole("administrator");
                    } else {
                        if (activity != null) {
                            App.getInstance().snackMessage(activity.findViewById(R.id.auth_email_activity_container), R.color.principal, "Usuario no logueado?", App.getInstance().getBaseContext());
                        }
                    }
                    // Logic
                } else {
                    Exception exception = task.getException();
//                    Log.d(TAG, "Excepción Task: " + exception.getMessage());
                    if (activity != null) {
//                        App.getInstance().snackMessage(activity.findViewById(R.id.auth_email_activity_container), R.color.principal, "Datos de acceso incorrectos", App.getInstance().getBaseContext());
                        App.getInstance().snackMessage(activity.findViewById(R.id.auth_email_activity_container), R.color.principal, "Error: " + exception.getMessage(), App.getInstance().getBaseContext());

                    }
                }
            }
        });
    }

    /**
     * Send a password reset email
     *
     * @param email User validated email
     */
    public void sendPasswordResetEmail(String email) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Mensaje de restablecimiento enviado");
                            if (activity != null) {
                                App.getInstance().snackMessage(activity.findViewById(R.id.auth_email_activity_container), R.color.principal, "Mensaje de restablecimiento enviado", App.getInstance().getBaseContext());
                            }
                        } else {
                            Exception exception = task.getException();
//                            Log.d(TAG, "Excepción Task: " + exception.getMessage());
                            if (activity != null) {
                                App.getInstance().snackMessage(activity.findViewById(R.id.auth_email_activity_container), R.color.principal, "Error: " + exception.getMessage(), App.getInstance().getBaseContext());
                                App.getInstance().snackMessage(activity.findViewById(R.id.auth_email_activity_container), R.color.principal, "Sí el email esta en nuestro sistema recibirás un mensaje de restablecimiento enviado.", App.getInstance().getBaseContext());
                            }
                        }
                    }
                });
    }

    /**
     * Disconnect the user
     */
    public void signOut() {
        mAuth.signOut();
    }

    /**
     * Check the role and redirect to the corresponding activity when logging in
     *
     * @param role Role of the logged in user
     */
    public void checkRole(String role) {
        switch (role) {
            case "scientific": {
                redirectActivity(ScientificMainActivity.class);
                break;
            }
            case "technician": {
                redirectActivity(TechnicianMainActivity.class);
                break;
            }
            case "administrator": {
                redirectActivity(AdministratorMainActivity.class);
                break;
            }
            default: {
                redirectActivity(UserMainActivity.class);
                break;
            }
        }
    }

    /**
     * Redirect to corresponding activity
     *
     * @param activity Activity where it will be redirected
     */
    public void redirectActivity(Class<?> activity) {
        Intent i;
        i = new Intent(App.getInstance().getBaseContext(), activity);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK
        );
        App.getInstance().getBaseContext().startActivity(i);
    }

}
