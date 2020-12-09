/*
 * Created by Francisco Javier Paños Madrona on 15/11/20 15:43
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 15/11/20 15:43
 */

package com.example.murbin.presentation.zone.administrator.fragments;

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
import com.example.murbin.data.adapters.SubzonesListAdapter;
import com.example.murbin.data.adapters.TechniciansListAdapter;
import com.example.murbin.models.Subzone;
import com.example.murbin.models.User;
import com.example.murbin.presentation.zone.administrator.AdministratorSubzoneEditActivity;
import com.example.murbin.presentation.zone.administrator.AdministratorTechnicianEditActivity;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class SubzonesListFragment extends Fragment {

    public SubzonesListAdapter subzonesListAdapter;

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
        subzonesListAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        subzonesListAdapter.stopListening();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        subzonesListAdapter.stopListening();
    }

    /**
     * Method to start the layout elements
     *
     * @param view View
     */
    private void initializeLayoutElements(View view) {
        Query query = FirebaseFirestore.getInstance().collection("zones")
                .document("Gandia").collection("subzones");

        FirestoreRecyclerOptions<Subzone> options = new FirestoreRecyclerOptions.Builder<Subzone>()
                .setQuery(query, Subzone.class).build();

        subzonesListAdapter = new SubzonesListAdapter(options, getContext());
        subzonesListAdapter.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = recyclerView.getChildAdapterPosition(v);
                String id = subzonesListAdapter.getId(position);
                Intent intent = new Intent(App.getContext(), AdministratorSubzoneEditActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        recyclerView = view.findViewById(R.id.global_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(subzonesListAdapter);

        subzonesListAdapter.startListening();
    }
}
