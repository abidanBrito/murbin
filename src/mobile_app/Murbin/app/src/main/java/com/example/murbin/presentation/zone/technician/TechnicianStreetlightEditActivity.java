package com.example.murbin.presentation.zone.technician;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.example.murbin.App;
import com.example.murbin.BaseActivity;
import com.example.murbin.R;
import com.example.murbin.data.StreetlightsDatabaseCrud;
import com.example.murbin.data.UsersDatabaseCrud;
import com.example.murbin.firebase.Auth;
import com.example.murbin.models.User;
import com.example.murbin.presentation.zone.administrator.AdministratorMainActivity;
import com.example.murbin.presentation.zone.administrator.AdministratorSubzoneListActivity;
import com.example.murbin.presentation.zone.administrator.AdministratorTechnicianListActivity;
import com.example.murbin.presentation.zone.administrator.RootAdministratorListActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TechnicianStreetlightEditActivity extends BaseActivity implements View.OnClickListener {

    private final Auth mAuth = new Auth(this);

    private ViewGroup mContainer;
    private Toolbar mToolbar;
    private String mMessage;
    private Button m_btn_cancel, m_btn_save, m_btn_location;
    private StreetlightsDatabaseCrud mStreetlightsDatabaseCrud;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.technician_streetlight_edit_formulary);
        initializeLayoutElements();
    }

    /**
     * Method to start the layout elements
     */
    private void initializeLayoutElements() {

        mContainer = findViewById(R.id.technician_streetlight_create_container);

        m_btn_cancel = findViewById(R.id.technician_streetlight_edit_btn_cancelar);
        m_btn_save = findViewById(R.id.technician_streetlight_edit_btn_guardar);
        m_btn_location = findViewById(R.id.technician_streetlight_edit_btn_location);

        m_btn_cancel.setOnClickListener(this);
        m_btn_save.setOnClickListener(this);
        m_btn_location.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.containsKey("message")) {
                mMessage = extras.getString("message", "");
                if (mMessage != null && !mMessage.isEmpty()) {
                    App.getInstance().snackMessage(mContainer, R.color.black, mMessage, this);
                }
            }
        }

        mStreetlightsDatabaseCrud = new StreetlightsDatabaseCrud();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.technician_streetlight_edit_btn_location: {
                Log.d(App.DEFAULT_TAG, "Pulsado: " + id);
                showMapDialogFragment();

                break;
            }

            case R.id.technician_streetlight_edit_btn_cancelar: {
                finish();
                break;
            }
            case R.id.technician_streetlight_edit_btn_guardar: {
                /*if (checkForm()) {
                    mUser = new User(App.ROLE_TECHNICIAN, "", name, email, pass, subzone, null);
                    mUsersDatabaseCrud.create(mUser, documentId -> {
                        mUser.setUid(documentId);
                        mUsersDatabaseCrud.update(documentId, mUser.parseToMap(), response -> {
                            if (response) {
                                Intent intent = new Intent(this, AdministratorTechnicianListActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("message", "Técnico creado correctamente.");
                                startActivity(intent);
                            } else {
                                App.getInstance().snackMessage(
                                        mContainer,
                                        R.color.black,
                                        "Error desconocido al crear el técnico.",
                                        this
                                );
                            }
                        });
                    });
                }*/

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

        return result;
    }
}
