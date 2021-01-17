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
import com.example.murbin.data.UsersDatabaseCrud;
import com.example.murbin.presentation.auth.AuthEmailActivity;
import com.example.murbin.presentation.zone.administrator.AdministratorMainActivity;
import com.example.murbin.presentation.zone.general.GeneralMainActivity;
import com.example.murbin.presentation.zone.technician.TechnicianMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Date;

/**
 * Class to manage Firebase Authentication
 */
public class Auth {

//    private static final int RC_SIGN_IN = 1001; // For Google (Not used actually)

    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final UsersDatabaseCrud userCrud = new UsersDatabaseCrud();
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
     * Return current user logged
     *
     * @return FirebaseUser
     */
    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }

    /**
     * Try to log in with the email and password passed as parameters
     *
     * @param email    User validated email
     * @param password User's validated password
     */
    public void signInWithEmailAndPassword(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (isLogged()) {
                    mFirebaseUser = mAuth.getCurrentUser();
                    userCrud.read(mFirebaseUser.getUid(), user -> {
//                        Log.d(App.DEFAULT_TAG, mFirebaseUser.getUid());
                        App.setCurrentUser(user);
                        Log.d(App.DEFAULT_TAG, "App.getCurrentUser(): " + App.getCurrentUser());
                        // Update lastAccess field
                        user.setLastAccess(new Date(System.currentTimeMillis()));
                        userCrud.update(user.getUid(), user.parseToMap(), response -> {
                            if (response) {
                                checkRole(App.getCurrentUser().getRole());
                            } else {
                                Intent intent = new Intent(App.getContext(), GeneralMainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("message", "No se ha podido verificar tu identidad correctamente.");
                                App.getContext().startActivity(intent);
                            }
                        });
                    });
                }
            } else {
                App.getInstance().snackMessage(activity.findViewById(R.id.auth_email_activity_container), R.color.black, "Datos de acceso incorrectos.", App.getContext());
            }
        });
    }

    /**
     * Send a password reset email
     *
     * @param email User validated email
     */
    public void sendPasswordResetEmail(String email) {
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            Intent intent = new Intent(App.getContext(), AuthEmailActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("message", "Sí el email esta registrado recibirás un correo para restablecer la contraseña.");
            App.getContext().startActivity(intent);
        });
    }

    /**
     * Disconnect the user
     */
    public void signOut() {
        mAuth.signOut();
        App.setCurrentUser(null);
    }

    /**
     * Check the role and redirect to the corresponding activity when logging in
     *
     * @param role Role of the logged in user
     */
    public void checkRole(String role) {
        switch (role) {
            case "root":
            case "administrator": {
                redirectActivity(AdministratorMainActivity.class);
                break;
            }
            case "technician": {
                redirectActivity(TechnicianMainActivity.class);
                break;
            }
            default: {
                redirectActivity(GeneralMainActivity.class);
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
        Intent intent;
        intent = new Intent(App.getContext(), activity);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK
        );
        App.getContext().startActivity(intent);
    }

}
