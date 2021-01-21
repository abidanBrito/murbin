package com.example.murbin.presentation.zone.administrator;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;

import com.example.murbin.App;
import com.example.murbin.BaseActivity;
import com.example.murbin.R;
import com.example.murbin.firebase.Auth;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class AdministratorSubzoneSelectActivity extends BaseActivity {
    private final Auth mAuth = new Auth(this);

    private ViewGroup mContainer;
    private Toolbar mToolbar;
    private BottomNavigationView mBottomNavigationView;
    private String mMessage;
    private Button btnCancel, btnSave;
    private List<String> selectedSubzones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.administrator_select_subzone);
        initializeLayoutElements();
    }

    /**
     * Method to start the layout elements
     */
    private void initializeLayoutElements() {
        // Toolbar menu
        mToolbar = findViewById(R.id.administrator_select_toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }

        btnCancel = findViewById(R.id.administrator_select_subzone_btn_cancelar);
        btnSave = findViewById(R.id.administrator_select_subzone_btn_aceptar);
        mContainer = findViewById(R.id.administrator_select_container);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.containsKey("message")) {
                mMessage = extras.getString("message", "");
                if (mMessage != null && !mMessage.isEmpty()) {
                    App.getInstance().snackMessage(mContainer, R.color.black, mMessage, this);
                }
            }
        }


        btnCancel.setOnClickListener(v -> finish());

        btnSave.setOnClickListener(v -> {
            //Intent intent = new Intent(AdministratorSubzoneSelectActivity.this, .class);
            //startActivity(intent);
        });

    }
}
