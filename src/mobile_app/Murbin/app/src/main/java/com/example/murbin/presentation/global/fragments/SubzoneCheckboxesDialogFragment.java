/*
 * Created by Francisco Javier Paños Madrona on 17/01/21 14:21
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 17/01/21 14:21
 */

package com.example.murbin.presentation.global.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.example.murbin.R;
import com.example.murbin.presentation.zone.administrator.AdministratorTechnicianCreateActivity;
import com.example.murbin.presentation.zone.administrator.AdministratorTechnicianEditActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * DialogFragment
 * https://developer.android.com/reference/android/app/DialogFragment.html#BasicDialog
 */
public class SubzoneCheckboxesDialogFragment extends androidx.fragment.app.DialogFragment implements View.OnClickListener {

    public static List<String> listSubzones;
    private final String originActivity;
    private Button m_btn_validate, m_btn_cancel;

    /**
     * Constructor
     */
    public SubzoneCheckboxesDialogFragment(String originActivity) {
        listSubzones = new ArrayList<>();
        this.originActivity = originActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.administrator_select_subzone, container, false);

        m_btn_validate = view.findViewById(R.id.administrator_select_subzone_btn_aceptar);
        m_btn_validate.setOnClickListener(this);
        m_btn_cancel = view.findViewById(R.id.administrator_select_subzone_btn_cancelar);
        m_btn_cancel.setOnClickListener(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.administrator_dialog_fragment_map_btn_validate) {
            callSetListSubzonesCorrectly();
            dismiss();
        }
        if (id == R.id.administrator_dialog_fragment_map_btn_cancel) {
            listSubzones.clear();
            callSetListSubzonesCorrectly();
            dismiss();
        }
    }

    /**
     *
     */
    private void callSetListSubzonesCorrectly() {
        switch (this.originActivity) {
            case "AdministratorSubzoneCreateActivity": {
//                Log.d(App.DEFAULT_TAG, String.valueOf(areaSubzone.getArea().size()));
                ((AdministratorTechnicianCreateActivity) getActivity()).setListSubzones(listSubzones);
                break;
            }
            case "AdministratorSubzoneEditActivity": {
//                Log.d(App.DEFAULT_TAG, String.valueOf(areaSubzone.getArea().size()));
                ((AdministratorTechnicianEditActivity) getActivity()).setListSubzones(listSubzones);
                break;
            }
        }
    }
}