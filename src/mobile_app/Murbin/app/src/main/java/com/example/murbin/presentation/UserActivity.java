package com.example.murbin.presentation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.murbin.CustomApplication;
import com.example.murbin.R;

public class UserActivity extends AppCompatActivity {

    /*
      Constant for ease of use in debugging the class code
     */
    private static final String TAG = UserActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity);
    }
}