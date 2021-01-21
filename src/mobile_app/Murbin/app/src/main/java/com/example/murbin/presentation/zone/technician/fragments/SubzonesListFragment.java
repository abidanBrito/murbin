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
import com.example.murbin.data.SubzonesDatabaseCrud;
import com.example.murbin.data.adapters.SubzonesListAdapter;
import com.example.murbin.models.Subzone;
import com.example.murbin.models.User;
import com.example.murbin.presentation.zone.technician.TechnicianSubzoneActivity;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.List;

public class SubzonesListFragment extends Fragment {

    public SubzonesListAdapter subzonesListAdapter;

    private RecyclerView recyclerView;
    private SubzonesDatabaseCrud subzonesDatabaseCrud;
    private User mUser = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); // For onOptionsItemSelected to work
        subzonesDatabaseCrud = new SubzonesDatabaseCrud();
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
        if (subzonesListAdapter != null) {
            subzonesListAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (subzonesListAdapter != null) {
            subzonesListAdapter.stopListening();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subzonesListAdapter != null) {
            subzonesListAdapter.stopListening();
        }
    }

    /**
     * Method to start the layout elements
     *
     * @param view View
     */
    private void initializeLayoutElements(View view) {
        List<String> listSubzones = App.getInstance().getCurrentUser().getListSubzones();

        FirestoreRecyclerOptions<Subzone> options = new FirestoreRecyclerOptions.Builder<Subzone>()
                .setQuery(subzonesDatabaseCrud.getSubzonesFromTechnicians(listSubzones), Subzone.class).build();

        subzonesListAdapter = new SubzonesListAdapter(options, getContext());
        subzonesListAdapter.setOnItemClickListener(item -> {
            int position = recyclerView.getChildAdapterPosition(item);
            String idSubzone = subzonesListAdapter.getId(position);
            Intent intent = new Intent(App.getContext(), TechnicianSubzoneActivity.class);
            intent.putExtra("idSubzone", idSubzone);
            startActivity(intent);
        });

        recyclerView = view.findViewById(R.id.global_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(subzonesListAdapter);

        subzonesListAdapter.startListening();
    }

    /**
     * @param user User logged
     */
    public void settings(User user) {
        mUser = user;
    }

}
