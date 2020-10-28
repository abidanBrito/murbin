package com.example.murbin.presentation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.example.murbin.R;
import com.google.firebase.firestore.core.View;

public class LoginEmailActivity extends AppCompatActivity {
    ImageView volver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_with_email_activity);

        // Escuchador del botón goBackButton
        volver = findViewById(R.id.goBackButton);
        volver.setOnClickListener(v -> goBack(null));
    }

    // Vuelve a la vista anterior cuando se pulsa el boton "<"
    public void goBack(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
