/*
 * Created by Francisco Javier Paños Madrona on 6/12/20 12:17
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 6/12/20 12:17
 */

package com.example.murbin.presentation.zone.administrator;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.example.murbin.App;
import com.example.murbin.BaseActivity;
import com.example.murbin.R;
import com.example.murbin.data.SubzonesDatabaseCrud;
import com.example.murbin.models.Subzone;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdministratorSubzoneEditActivity extends BaseActivity implements View.OnClickListener {

    private ViewGroup mContainer;
    private Toolbar mToolbar;
    private BottomNavigationView mBottomNavigationView;
    private String m_name;
    private String m_status;
    private String mMessage;
    private String mId;
    private EditText m_et_name;
    private Button m_btn_cancel, m_btn_save;
    private SubzonesDatabaseCrud mSubzonesDatabaseCrud;
    private Subzone mSubzone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.administrator_subzones_edit_activity);
        initializeLayoutElements();
    }

    /**
     * Method to start the layout elements
     */
    private void initializeLayoutElements() {
        // Toolbar menu
        mToolbar = findViewById(R.id.administrator_subzones_edit_activity_toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
            getSupportActionBar().setTitle("");
        }

        mContainer = findViewById(R.id.administrator_subzones_edit_activity_container);

        m_et_name = findViewById(R.id.administrator_subzones_edit_activity_et_name);

        m_btn_cancel = findViewById(R.id.administrator_subzones_edit_activity_btn_cancel);
        m_btn_save = findViewById(R.id.administrator_subzones_edit_activity_btn_save);

        m_btn_cancel.setOnClickListener(this);
        m_btn_save.setOnClickListener(this);

        // BottomNavigationView menu
        mBottomNavigationView = findViewById(R.id.administrator_subzones_edit_activity_bottom_navigation);
        if (App.getCurrentUser().getRole().equals(App.ROLE_ROOT)) {
            mBottomNavigationView.getMenu().clear();
            mBottomNavigationView.inflateMenu(R.menu.root_main_bottom_navigation);
            mBottomNavigationView.setSelectedItemId(R.id.root_main_bottom_navigation_subzones);
        } else {
            mBottomNavigationView.setSelectedItemId(R.id.administrator_main_bottom_navigation_subzones);
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
            if (extras.containsKey("id")) {
                mId = extras.getString("id", "");

            }
        }

        mSubzonesDatabaseCrud = new SubzonesDatabaseCrud();
        mSubzonesDatabaseCrud.read(mId, subzone -> {
            mSubzone = subzone;
            m_et_name.setText(subzone.getName());
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.administrator_subzones_edit_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.administrator_subzones_edit_menu_delete) {
            AlertDialog alertDialog = confirmDeleteDialog();
            alertDialog.show();
        }

        return super.onOptionsItemSelected(item);
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
                    intent = new Intent(AdministratorSubzoneEditActivity.this, AdministratorMainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else if (id == R.id.administrator_main_bottom_navigation_subzones
                        || id == R.id.root_main_bottom_navigation_subzones) {
                    // Empty activity
                } else if (id == R.id.administrator_main_bottom_navigation_technician
                        || id == R.id.root_main_bottom_navigation_technician) {
                    intent = new Intent(AdministratorSubzoneEditActivity.this, AdministratorTechnicianListActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else if (id == R.id.root_main_bottom_navigation_administrator) {
                    intent = new Intent(AdministratorSubzoneEditActivity.this, RootAdministratorListActivity.class);
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
            case R.id.administrator_subzones_edit_activity_btn_cancel: {
                Intent intent = new Intent(AdministratorSubzoneEditActivity.this, AdministratorSubzoneListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("message", "Acción cancelada.");
                startActivity(intent);

                break;
            }
            case R.id.administrator_subzones_edit_activity_btn_save: {
                if (checkForm()) {
                    Subzone updatedSubzone = mSubzone;
                    updatedSubzone.setName(m_name);
                    updatedSubzone.setStatus(true);
                    mSubzonesDatabaseCrud.update(mId, updatedSubzone.parseToMap(), response -> {
                        if (response) {
                            Intent intent = new Intent(AdministratorSubzoneEditActivity.this, AdministratorSubzoneListActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("message", "Subzona editada correctamente.");
                            startActivity(intent);
                        } else {
                            App.getInstance().snackMessage(
                                    mContainer,
                                    R.color.black,
                                    "Error desconocido al editar la subzona.",
                                    AdministratorSubzoneEditActivity.this
                            );
                        }
                    });
                    // Update variable with changes
                    mSubzone = updatedSubzone;
                }

                break;
            }
        }
    }

    /**
     * @return AlertDialog
     */
    private AlertDialog confirmDeleteDialog() {
        return new AlertDialog.Builder(this)
                .setTitle(getString(R.string.AdministratorSubzoneEditActivity_alert_dialog_delete_title))
                .setMessage(getString(R.string.AdministratorSubzoneEditActivity_alert_dialog_delete_message))
                .setIcon(android.R.drawable.stat_sys_warning)
                .setPositiveButton(getString(R.string.AdministratorSubzoneEditActivity_alert_dialog_confirm_text), (dialog, whichButton) -> {
                    mSubzonesDatabaseCrud.delete(mId, response -> {
                        Intent intent = new Intent(AdministratorSubzoneEditActivity.this, AdministratorSubzoneListActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        if (response) {
                            intent.putExtra("message", "Subzona eliminada correctamente.");
                        } else {
                            intent.putExtra("message", "Error desconocido al eliminar subzona.");
                        }
                        startActivity(intent);
                        dialog.dismiss();
                    });
                })
                .setNegativeButton(getString(R.string.AdministratorSubzoneEditActivity_alert_dialog_cancel_text), (dialog, which) -> dialog.dismiss())
                .create();
    }

    /**
     * Check if form is correctly
     *
     * @return boolean
     */
    private boolean checkForm() {
        boolean result = true;

        m_name = m_et_name.getText().toString();

        if (m_name.equals("") || m_name.length() < 3) {
            m_et_name.setError("Debes indicar un nombre y de al menos 3 carácteres.");
            result = false;
        }

        return result;
    }
}
