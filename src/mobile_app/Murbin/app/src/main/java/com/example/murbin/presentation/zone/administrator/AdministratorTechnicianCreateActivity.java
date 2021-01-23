/*
 * Created by Francisco Javier Paños Madrona on 6/12/20 12:17
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 6/12/20 12:17
 */

package com.example.murbin.presentation.zone.administrator;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.example.murbin.App;
import com.example.murbin.BaseActivity;
import com.example.murbin.R;
import com.example.murbin.data.UsersDatabaseCrud;
import com.example.murbin.firebase.Auth;
import com.example.murbin.models.User;
import com.example.murbin.presentation.global.fragments.SubzoneCheckboxesDialogFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class AdministratorTechnicianCreateActivity extends BaseActivity implements View.OnClickListener {

    private final Auth mAuth = new Auth(this);

    private ViewGroup mContainer;
    private Toolbar mToolbar;
    private BottomNavigationView mBottomNavigationView;
    private String mMessage;
    private EditText m_et_name, m_et_email, m_et_pass;
    private Button m_btn_cancel, m_btn_save, m_btn_spinner, m_et_subzones;
    private Spinner m_spinner_subzone;
    private UsersDatabaseCrud mUsersDatabaseCrud;
    private User mUser;
    private List<String> listSubzones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.administrator_technicians_create_formulary);
        listSubzones = new ArrayList<>();
        initializeLayoutElements();
    }

    /**
     * Method to start the layout elements
     */
    private void initializeLayoutElements() {
        // Toolbar menu
        mToolbar = findViewById(R.id.administrator_technicians_create_toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
            getSupportActionBar().setTitle("");
        }

        mContainer = findViewById(R.id.administrator_technicians_create_activity_container);

        m_et_name = findViewById(R.id.administrator_technicians_create_et_name);
        m_et_email = findViewById(R.id.administrator_technicians_create_et_email);
        m_et_pass = findViewById(R.id.administrator_technicians_create_et_pass);

        m_btn_cancel = findViewById(R.id.administrator_technicians_create_btn_crear);
        m_btn_save = findViewById(R.id.administrator_technicians_create_btn_cancelar);
        // Al hacer click sobre este boton se abre el spinner.
        m_btn_spinner = findViewById(R.id.administrator_technicians_create_spinner_subzones);

        m_btn_cancel.setOnClickListener(this);
        m_btn_save.setOnClickListener(this);
        m_btn_spinner.setOnClickListener(this);

        // BottomNavigationView menu
        mBottomNavigationView = findViewById(R.id.administrator_main_activity_bottom_navigation);
        if (App.getInstance().getCurrentUser() != null && App.getInstance().getCurrentUser().getRole().equals(App.ROLE_ROOT)) {
            mBottomNavigationView.getMenu().clear();
            mBottomNavigationView.inflateMenu(R.menu.root_main_bottom_navigation);
            mBottomNavigationView.setSelectedItemId(R.id.root_main_bottom_navigation_technician);
        } else {
            mBottomNavigationView.setSelectedItemId(R.id.administrator_main_bottom_navigation_technician);
        }
        actionsBottomNavigationView();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.containsKey("message")) {
                mMessage = extras.getString("message", "");
                if (mMessage != null && !mMessage.isEmpty()) {
                    App.getInstance().snackMessage(mContainer, R.color.black, mMessage, this);
                }
            }
        }

        mUsersDatabaseCrud = new UsersDatabaseCrud();
    }

    /**
     * Method for bottom navigation functionality
     */
    public void actionsBottomNavigationView() {
        // Actions when pressing an option in the lower navigation
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                int id = item.getItemId();
                if (id == R.id.administrator_main_bottom_navigation_home
                        || id == R.id.root_main_bottom_navigation_home) {
                    intent = new Intent(AdministratorTechnicianCreateActivity.this, AdministratorMainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else if (id == R.id.administrator_main_bottom_navigation_subzones
                        || id == R.id.root_main_bottom_navigation_subzones) {
                    intent = new Intent(AdministratorTechnicianCreateActivity.this, AdministratorSubzoneListActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else if (id == R.id.administrator_main_bottom_navigation_technician
                        || id == R.id.root_main_bottom_navigation_technician) {
                    // Empty activity
                } else if (id == R.id.root_main_bottom_navigation_administrator) {
                    intent = new Intent(AdministratorTechnicianCreateActivity.this, RootAdministratorListActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }

                return true;
            }
        });

        // Actions when pressing an option already selected from the lower navigation
        mBottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.administrator_main_bottom_navigation_home
                        || id == R.id.root_main_bottom_navigation_home) {
                    // Empty activity
                } else if (id == R.id.administrator_main_bottom_navigation_subzones
                        || id == R.id.root_main_bottom_navigation_subzones) {
                    // Empty action
                } else if (id == R.id.administrator_main_bottom_navigation_technician
                        || id == R.id.root_main_bottom_navigation_technician) {
                    // Empty action
                } else if (id == R.id.root_main_bottom_navigation_administrator) {
                    // Empty action
                }
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.administrator_technicians_create_btn_cancelar: {
                Intent intent = new Intent(AdministratorTechnicianCreateActivity.this, AdministratorTechnicianListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("message", "Acción cancelada.");
                startActivity(intent);

                break;
            }
            case R.id.administrator_technicians_create_btn_crear: {
                if (checkForm()) {
                    String name = m_et_name.getText().toString();
                    String email = m_et_email.getText().toString();
                    String pass = m_et_pass.getText().toString();
                    mUser = new User(App.ROLE_TECHNICIAN, "", name, email, pass, listSubzones, null);
                    mUsersDatabaseCrud.create(mUser, documentId -> {
                        mUser.setUid(documentId);
                        mUsersDatabaseCrud.update(documentId, mUser.parseToMap(), response -> {
                            if (response) {
                                Intent intent = new Intent(AdministratorTechnicianCreateActivity.this, AdministratorTechnicianListActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("message", "Técnico creado correctamente.");
                                startActivity(intent);
                            } else {
                                App.getInstance().snackMessage(
                                        mContainer,
                                        R.color.black,
                                        "Error desconocido al crear el técnico.",
                                        AdministratorTechnicianCreateActivity.this
                                );
                            }
                        });
                    });
                }

                break;
            }
            case R.id.administrator_technicians_create_spinner_subzones: {
                FragmentManager fm = getSupportFragmentManager();
                SubzoneCheckboxesDialogFragment subzoneCheckboxesDialogFragment = new SubzoneCheckboxesDialogFragment("AdministratorTechnicianCreateActivity");
                subzoneCheckboxesDialogFragment.show(fm, "subzoneCheckboxesDialogFragment");

                break;
            }
        }
    }

    /**
     * Check if form is correctly
     *
     * @return boolean
     */
    private boolean checkForm() {
        boolean result = true;

        String name = m_et_name.getText().toString();
        //String surname = m_et_surname.getText().toString();
        //String email = m_et_email.getText().toString();

        if (name.equals("") || name.length() < 3) {
            m_et_name.setError("Debes indicar un nombre y de al menos 3 carácteres.");
            result = false;
        }
        /*if (surname.equals("") || surname.length() < 3) {
            m_et_surname.setError("Debes indicar los apellidos y de al menos 3 carácteres.");
            result = false;
        }
        if (email.equals("") || !App.isValidEmail(email)) {
            m_et_email.setError("Debes indicar un email válido.");
            result = false;
        }*/

        return result;
    }

    /**
     * @param listSubzones List<String>
     */
    public void setListSubzones(List<String> listSubzones) {
        this.listSubzones = listSubzones;
        if (!listSubzones.isEmpty()) {
            m_btn_spinner.setHint(R.string.AdministratorSubzoneCreateActivity_alert_dialog_location_message);
        } else {
            m_btn_spinner.setHint(R.string.global_string_location);
        }
    }
}
