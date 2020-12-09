/*
 * Created by Francisco Javier Paños Madrona on 15/11/20 15:43
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 15/11/20 15:43
 */

package com.example.murbin.presentation.zone.administrator.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.murbin.App;
import com.example.murbin.R;
import com.example.murbin.data.adapters.AdministratorsListAdapter;
import com.example.murbin.models.User;
import com.example.murbin.presentation.zone.administrator.AdministratorTechnicianEditActivity;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class AdministratorsListFragment extends Fragment {

    public AdministratorsListAdapter administratorsListAdapter;

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
        administratorsListAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        administratorsListAdapter.stopListening();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        administratorsListAdapter.stopListening();
    }

    /**
     * Method to start the layout elements
     *
     * @param view View
     */
    private void initializeLayoutElements(View view) {
        Query query = FirebaseFirestore.getInstance().collection("users")
                .whereEqualTo("role", App.ROLE_ADMINISTRATOR);

        FirestoreRecyclerOptions<User> options = new FirestoreRecyclerOptions.Builder<User>()
                .setQuery(query, User.class).build();

        administratorsListAdapter = new AdministratorsListAdapter(options, getContext());
        administratorsListAdapter.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = recyclerView.getChildAdapterPosition(v);
                String id = administratorsListAdapter.getId(position);
                Intent intent = new Intent(App.getContext(), AdministratorTechnicianEditActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        recyclerView = view.findViewById(R.id.global_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(administratorsListAdapter);

        administratorsListAdapter.startListening();
    }
}
