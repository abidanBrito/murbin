package com.example.murbin.presentation.zone.technician;

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
import com.example.murbin.models.Streetlight;
import com.example.murbin.presentation.auth.AuthEmailActivity;
import com.example.murbin.presentation.global.GlobalPreferencesActivity;
import com.example.murbin.presentation.zone.administrator.AdministratorMainActivity;
import com.example.murbin.presentation.zone.administrator.AdministratorSubzoneListActivity;
import com.example.murbin.presentation.zone.administrator.AdministratorTechnicianListActivity;
import com.example.murbin.presentation.zone.administrator.RootAdministratorListActivity;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class TechnicianSubzoneActivity extends BaseActivity {
    private final Auth mAuth = new Auth(this);

    private Toolbar mToolbar;
    private BottomNavigationView mBottomNavigationView;
    private ViewGroup mContainer;
    private TextView mTitle, mCount;
    private ConstraintLayout mStreetlights;
    private String mMessage, mId;
    public static MqttClient client = null;
    private ConstraintLayout botonOnOff;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.technician_subzone_main);
        initializeLayoutElements();

        // MQTT
        try {
            Log.i(Mqtt.TAG, "Conectando al broker " + Mqtt.broker);
            client = new MqttClient(Mqtt.broker, Mqtt.clientId,
                    new MemoryPersistence());
            client.connect();
        } catch (MqttException e) {
            Log.e(Mqtt.TAG, "Error al conectar.", e);
        }

        botonOnOff = findViewById(R.id.technician_subzone_status_container);
        botonOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.i(Mqtt.TAG, "Cambiar estado de la farola: " + "TOGGLE");
                    MqttMessage message = new MqttMessage("TOGGLE".getBytes());
                    message.setQos(Mqtt.qos);
                    message.setRetained(false);
                    client.publish(Mqtt.topicRoot+"TOGGLE", message);
                } catch (MqttException e) {
                    Log.e(Mqtt.TAG, "Error al publicar.", e);
                }
            }
        });

        mStreetlights = findViewById(R.id.technician_subzone_streetlights_container);
        mStreetlights.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(App.getContext(), TechnicianStreetlightsActivity.class);
                intent.putExtra("id", mId);
                startActivity(intent);
            }
        });
    }

    /**
     * Method to start the layout elements
     */
    private void initializeLayoutElements() {
        // Toolbar menu
        mToolbar = findViewById(R.id.technician_subzone_activity_toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
            getSupportActionBar().setTitle("");
        }

        mTitle = findViewById(R.id.technician_subzone_activity_tv_title);
        mContainer = findViewById(R.id.technician_subzone_activity_toolbar);

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
                mTitle.setText(mId);
                Log.e("mId", mId);
            }
        }

        Task<QuerySnapshot> query = FirebaseFirestore.getInstance().collection("zones")
                .document("Gandia").collection("subzones")
                .document(mId).collection("streetlights").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                count++;
                            }
                            mCount = findViewById(R.id.technician_subzone_streetlights);
                            mCount.setText(String.valueOf(count));
                        }
                    }
                });
        query.isSuccessful();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.technician_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override public void onDestroy() {
        try {
            Log.i(Mqtt.TAG, "Desconectado");
            client.disconnect();
        } catch (MqttException e) {
            Log.e(Mqtt.TAG, "Error al desconectar.", e);
        }
        super.onDestroy();
    }
}
