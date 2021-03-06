/*
 * Created by Francisco Javier Paños Madrona on 6/11/20 18:00
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 14/11/20 14:45
 */

package com.example.murbin.presentation.zone.administrator;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.murbin.App;
import com.example.murbin.BaseActivity;
import com.example.murbin.R;
import com.example.murbin.comun.Mqtt;
import com.example.murbin.firebase.Auth;
import com.example.murbin.presentation.auth.AuthEmailActivity;
import com.example.murbin.presentation.global.GlobalPreferencesActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import com.example.murbin.comun.Mqtt;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class AdministratorMainActivity extends BaseActivity {
    private TextView brightness, temperature, co2, noise, humidity;
    private final Auth mAuth = new Auth(this);

    private Toolbar mToolbar;
    private BottomNavigationView mBottomNavigationView;
    private ViewGroup mContainer;
    private String mMessage;
    public static MqttClient client = null;
    private ConstraintLayout botonOnOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.administrator_main_activity);
        initializeLayoutElements();

        // MQTT
        try {
//            Log.d(Mqtt.TAG, "Conectando al broker " + Mqtt.broker);
            client = new MqttClient(Mqtt.broker, Mqtt.clientId,
                    new MemoryPersistence());
            client.connect();
        } catch (MqttException e) {
//            Log.d(Mqtt.TAG, "Error al conectar.", e);
        }

        botonOnOff = findViewById(R.id.administrator_main_home_status_container);
        botonOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
//                    Log.d(Mqtt.TAG, "Cambiar estado de la farola: " + "TOGGLE");
                    MqttMessage message = new MqttMessage("TOGGLE".getBytes());
                    message.setQos(Mqtt.qos);
                    message.setRetained(false);
                    client.publish("ycansam/practica/POWER", message);
                } catch (MqttException e) {
//                    Log.d(Mqtt.TAG, "Error al publicar.", e);
                }
            }
        });
    }

    /**
     * Method to start the layout elements
     */
    private void initializeLayoutElements() {
        // Toolbar menu
        mToolbar = findViewById(R.id.administrator_main_activity_toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }

        mContainer = findViewById(R.id.administrator_main_activity_container);

        // BottomNavigationView menu
        mBottomNavigationView = findViewById(R.id.administrator_main_activity_bottom_navigation);
        if (App.getInstance().getCurrentUser() != null && App.getInstance().getCurrentUser().getRole().equals(App.ROLE_ROOT)) {
            mBottomNavigationView.getMenu().clear();
            mBottomNavigationView.inflateMenu(R.menu.root_main_bottom_navigation);
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

        brightness = findViewById(R.id.administrator_main_home_bright);
        temperature = findViewById(R.id.administrator_main_home_temperature);
        co2 = findViewById(R.id.administrator_main_home_co2);
        noise = findViewById(R.id.administrator_main_home_noise);
        humidity = findViewById(R.id.administrator_main_home_humidity);
        getLastMeasures();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.administrator_main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.administrator_main_menu_settings: {
                Intent intent = new Intent(this, GlobalPreferencesActivity.class);
                startActivity(intent);

                break;
            }

            case R.id.administrator_main_menu_logout: {
                mAuth.signOut();
                Intent intent = new Intent(AdministratorMainActivity.this, AuthEmailActivity.class);
                startActivity(intent);

                break;
            }
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
                    // Empty activity
                } else if (id == R.id.administrator_main_bottom_navigation_subzones
                        || id == R.id.root_main_bottom_navigation_subzones) {
                    intent = new Intent(AdministratorMainActivity.this, AdministratorSubzoneListActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else if (id == R.id.administrator_main_bottom_navigation_technician
                        || id == R.id.root_main_bottom_navigation_technician) {
                    intent = new Intent(AdministratorMainActivity.this, AdministratorTechnicianListActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else if (id == R.id.root_main_bottom_navigation_administrator) {
                    intent = new Intent(AdministratorMainActivity.this, RootAdministratorListActivity.class);
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

    @Override public void onDestroy() {
        try {
//            Log.d(Mqtt.TAG, "Desconectado");
            client.disconnect();
        } catch (MqttException e) {
//            Log.d(Mqtt.TAG, "Error al desconectar.", e);
        }
        super.onDestroy();
    }


    private void getLastMeasures() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference sensors;

        sensors = db.collection("streetlights").document("1")
                .collection("sensors");

        sensors.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String value;
                for (QueryDocumentSnapshot document : task.getResult()) {
                    value = document.getData().get("value").toString();
//                        Log.d(App.DEFAULT_TAG, document.getId() + " => " + value);
                    setValueSensor(document.getId(), value);
                }
            } else {
//                Log.d(App.DEFAULT_TAG, "Error getting documents.", task.getException());
            }
        });

    }

    private void setValueSensor(String id, String badFormatValue) {
        String value = badFormatValue.replace("\n", "").replace("\r", "");
        switch (id) {
            case "brightness":
                brightness.setText(value);
                break;
            case "co2":
                co2.setText(value);
                break;
            case "noise":
                noise.setText(value);
                break;
            case "temperature":
                temperature.setText(value);
                break;
            case "humidity":
                humidity.setText(value);
                break;
        }
    }
}