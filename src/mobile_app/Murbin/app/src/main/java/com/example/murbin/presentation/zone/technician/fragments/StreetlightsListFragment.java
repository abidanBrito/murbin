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
import com.example.murbin.data.StreetlightsDatabaseCrud;
import com.example.murbin.data.adapters.StreetlightsListAdapter;
import com.example.murbin.models.Streetlight;
import com.example.murbin.presentation.zone.technician.TechnicianStreetlightEditActivity;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class StreetlightsListFragment extends Fragment {

    public StreetlightsListAdapter streetlightsListAdapter;

    private String mIdSubzone;
    private RecyclerView recyclerView;
    private StreetlightsDatabaseCrud streetlightsDatabaseCrud;

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
        FirestoreRecyclerOptions<Streetlight> options = new FirestoreRecyclerOptions.Builder<Streetlight>()
                .setQuery(streetlightsDatabaseCrud.getStreetlights(), Streetlight.class).build();

        streetlightsListAdapter = new StreetlightsListAdapter(options, getContext());
        streetlightsListAdapter.setOnItemClickListener(v -> {
            int position = recyclerView.getChildAdapterPosition(v);
            String idStreetlight = streetlightsListAdapter.getId(position);
            Intent intent = new Intent(getContext(), TechnicianStreetlightEditActivity.class);
            intent.putExtra("idSubzone", mIdSubzone);
            intent.putExtra("idStreetlight", idStreetlight);
            startActivity(intent);
        });

        recyclerView = view.findViewById(R.id.global_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(streetlightsListAdapter);

        streetlightsListAdapter.startListening();
    }

    /**
     * @param idSubzoneP Id from parent Subzone
     */
    public void settings(String idSubzoneP) {
//        Log.d(App.DEFAULT_TAG, "Recibido idSubzone: " + mIdSubzone);
        mIdSubzone = idSubzoneP;
        streetlightsDatabaseCrud = new StreetlightsDatabaseCrud(mIdSubzone);
    }
}
