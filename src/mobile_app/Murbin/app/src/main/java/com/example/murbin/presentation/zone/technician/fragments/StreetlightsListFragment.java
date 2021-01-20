/*
 * Created by Francisco Javier Paños Madrona on 15/11/20 15:43
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 15/11/20 15:43
 */

package com.example.murbin.presentation.zone.technician.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.murbin.App;
import com.example.murbin.R;
import com.example.murbin.data.adapters.StreetlightsListAdapter;
import com.example.murbin.data.adapters.TechniciansListAdapter;
import com.example.murbin.models.Streetlight;
import com.example.murbin.models.Subzone;
import com.example.murbin.models.User;
import com.example.murbin.presentation.zone.administrator.AdministratorSubzoneEditActivity;
import com.example.murbin.presentation.zone.administrator.AdministratorTechnicianEditActivity;
import com.example.murbin.presentation.zone.technician.TechnicianSubzoneActivity;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class StreetlightsListFragment extends Fragment {

    public StreetlightsListAdapter streetlightsListAdapter;

    private String mId;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); // For onOptionsItemSelected to work


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.global_recycler_layout, container, false);
        initializeLayoutElements(view);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        streetlightsListAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        streetlightsListAdapter.stopListening();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        streetlightsListAdapter.stopListening();
    }

    /**
     * Method to start the layout elements
     *
     * @param view View
     */
    private void initializeLayoutElements(View view) {
        Query query = FirebaseFirestore.getInstance().collection("zones")
                .document("Gandia").collection("subzones").document("Grao de Gandia").collection("streetlights");

        FirestoreRecyclerOptions<Streetlight> options = new FirestoreRecyclerOptions.Builder<Streetlight>()
                .setQuery(query, Streetlight.class).build();

        streetlightsListAdapter = new StreetlightsListAdapter(options, getContext());
        streetlightsListAdapter.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = recyclerView.getChildAdapterPosition(v);
                String id = streetlightsListAdapter.getId(position);
                Intent intent = new Intent(getContext(), TechnicianSubzoneActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        recyclerView = view.findViewById(R.id.global_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(streetlightsListAdapter);

        streetlightsListAdapter.startListening();
    }
}
