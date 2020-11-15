/*
 * Created by Francisco Javier Paños Madrona on 6/11/20 18:00
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 14/11/20 14:45
 */

package com.example.murbin.presentation.profiles.fragments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.murbin.App;
import com.example.murbin.R;
import com.example.murbin.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

public class ProfileFragment extends Fragment {

    /**
     * Constant for ease of use in debugging the class code
     */
    private static final String TAG = ProfileFragment.class.getSimpleName();

    App aplicacion = (App) getContext();

    @Override
    public View onCreateView(LayoutInflater inflador, ViewGroup contenedor, Bundle savedInstanceState) {
        View view = inflador.inflate(R.layout.profile_fragment, contenedor, false);
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        final TextView profile_fragment_tv_notification = view.findViewById(R.id.profile_fragment_tv_notification);
        final Button profile_fragment_btn_email_verification = view.findViewById(R.id.profile_fragment_btn_email_verification);

        if (firebaseUser != null) {
            StringBuilder providers = new StringBuilder();
            for (UserInfo provider : firebaseUser.getProviderData()) {
                providers.append(provider.getProviderId()).append(", ");
            }

            UserModel userModel = new UserModel(
                    null,
                    firebaseUser.getUid(),
                    firebaseUser.getDisplayName(),
                    null,
                    firebaseUser.getEmail(),
                    firebaseUser.getPhoneNumber(),
                    providers.toString(),
                    firebaseUser.getPhotoUrl()
            );

            final NetworkImageView profile_fragment_niv_picture = view.findViewById(R.id.profile_fragment_niv_picture);
            final TextView profile_fragment_tv_name = view.findViewById(R.id.profile_fragment_tv_name);
            final TextView profile_fragment_tv_email = view.findViewById(R.id.profile_fragment_tv_email);
            final TextView profile_fragment_tv_providers = view.findViewById(R.id.profile_fragment_tv_providers);
            final TextView profile_fragment_tv_phone = view.findViewById(R.id.profile_fragment_tv_phone);
            final TextView profile_fragment_tv_uid = view.findViewById(R.id.profile_fragment_tv_uid);

            /*
              If the user has not validated their email, they are prompted to do so and a button
              is provided so that they can receive the verification email.
             */
            if (!firebaseUser.isEmailVerified()) {
                profile_fragment_tv_notification.setTextColor(Color.RED);
                profile_fragment_tv_notification.setText(getResources().getString(R.string.profile_fragment_tv_email_verification));
                profile_fragment_tv_notification.setVisibility(View.VISIBLE);

                profile_fragment_btn_email_verification.setBackgroundColor(Color.BLACK);
                profile_fragment_btn_email_verification.setTextColor(Color.WHITE);
                profile_fragment_btn_email_verification.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        firebaseUser.sendEmailVerification();
                        profile_fragment_btn_email_verification.setClickable(false);
                        profile_fragment_btn_email_verification.setText(getResources().getString(R.string.profile_fragment_btn_email_verification_sended));
                        profile_fragment_btn_email_verification.setBackgroundColor(Color.GREEN);
                        profile_fragment_btn_email_verification.setTextColor(Color.WHITE);
                    }
                });
                profile_fragment_btn_email_verification.setVisibility(View.VISIBLE);
            }

            if (userModel.getPicture() != null) {
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                ImageLoader pictureReader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(10);

                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }

                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }
                });

                profile_fragment_niv_picture.setImageUrl(userModel.getPicture().toString(), pictureReader);
                profile_fragment_niv_picture.setVisibility(View.VISIBLE);
            }

            profile_fragment_tv_name.setText(userModel.getName());
            profile_fragment_tv_email.setText(userModel.getEmail());
            profile_fragment_tv_providers.setText(userModel.getProvider());
            profile_fragment_tv_phone.setText(userModel.getPhone());
            profile_fragment_tv_uid.setText(userModel.getUid());
        } else {
            profile_fragment_tv_notification.setText(getString(R.string.profile_fragment_error_1));
            profile_fragment_tv_notification.setVisibility(View.VISIBLE);
        }

        return view;
    }
}