package com.example.murbin.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.murbin.R;
import com.google.firebase.auth.FirebaseAuth;

public class LoginMenuActivity extends AppCompatActivity {

    /*
      Constant for ease of use in debugging the class code
     */
    private static final String TAG = LoginMenuActivity.class.getSimpleName();

    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    Button btn_login_provider_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkIfValidatedUser();

        setContentView(R.layout.login_menu_activity);

        btn_login_provider_email = findViewById(R.id.login_menu_activity_btn_provider_email);
        btn_login_provider_email.setOnClickListener(v -> login_with_email());
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

    public void login_with_email() {
        Intent intent = new Intent(this, LoginWithEmailActivity.class);
        startActivity(intent);
    }
}
