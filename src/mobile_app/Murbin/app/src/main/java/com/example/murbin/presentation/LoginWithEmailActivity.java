package com.example.murbin.presentation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.murbin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginWithEmailActivity extends AppCompatActivity {

    /*
      Constant for ease of use in debugging the class code
     */
    private static final String TAG = LoginWithEmailActivity.class.getSimpleName();

    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private String email = "";
    private String password = "";
    private ViewGroup container;
    private EditText etEmail, etPassword;
    private ProgressDialog dialogue;
    private Toolbar mToolbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_with_email_activity);

        mToolbar = findViewById(R.id.login_with_email_activity_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_atras);
        getSupportActionBar().setTitle("");

        container = findViewById(R.id.login_with_email_activity_container);
        dialogue = new ProgressDialog(this);

        // For the Login with Email
        etEmail = findViewById(R.id.login_with_email_activity_et_email);
        etPassword = findViewById(R.id.login_with_email_activity_et_password);
    }

    private void checkIfValidatedUser() {
        if (auth.getCurrentUser() != null) {
            Intent i = new Intent(this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_CLEAR_TASK
            );
            startActivity(i);
            finish();
        }
    }

    public void loginWithMail(View v) {
        if (checkFormLogin()) {
            dialogue.show();
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        checkIfValidatedUser();
                    } else {
                        dialogue.dismiss();
//                        message(task.getException().getLocalizedMessage());
                        message(String.valueOf(getResources().getString(R.string.login_with_email_activity_error_1)));
                    }
                }
            });
        }
    }

    public void registerWithMail(View v) {
        if (checkFormRegister()) {
            dialogue.show();
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        checkIfValidatedUser();
                    } else {
                        dialogue.dismiss();
                        message(task.getException().getLocalizedMessage());
                    }
                }
            });
        }
    }

    private boolean checkFormLogin() {
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
        if (email.isEmpty()) {
            Snackbar.make(container, "Introduce un correo", Snackbar.LENGTH_SHORT).show();
        } else if (password.isEmpty()) {
            Snackbar.make(container, "Introduce una contraseña", Snackbar.LENGTH_SHORT).show();
        } else {
            return true;
        }
        return false;
    }

    private boolean checkFormRegister() {
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
        if (email.isEmpty()) {
            Snackbar.make(container, "Introduce un correo", Snackbar.LENGTH_SHORT).show();
        } else if (!email.matches(".+@.+[.].+")) {
            Snackbar.make(container, "Correo no válido", Snackbar.LENGTH_SHORT).show();
        } else if (password.isEmpty()) {
            Snackbar.make(container, "Introduce una contraseña", Snackbar.LENGTH_SHORT).show();
        } else if (password.length() < 6) {
            Snackbar.make(container, "Ha de contener al menos 6 caracteres", Snackbar.LENGTH_SHORT).show();
        } else if (!password.matches(".*[0-9].*")) {
            Snackbar.make(container, "Ha de contener un número", Snackbar.LENGTH_SHORT).show();
        } else if (!password.matches(".*[A-Z].*")) {
            Snackbar.make(container, "Ha de contener una letra mayúscul", Snackbar.LENGTH_SHORT).show();
        } else {
            return true;
        }
        return false;
    }

    private void message(String mensaje) {
        Snackbar.make(container, mensaje, Snackbar.LENGTH_LONG).show();
    }
}
