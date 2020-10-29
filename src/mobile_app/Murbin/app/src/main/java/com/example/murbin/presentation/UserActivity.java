package com.example.murbin.presentation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.murbin.R;

public class UserActivity extends AppCompatActivity {

    /*
      Constant for ease of use in debugging the class code
     */
    private static final String TAG = UserActivity.class.getSimpleName();

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity);

        mToolbar = findViewById(R.id.user_activity_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_atras);
        getSupportActionBar().setTitle("");
    }
}