package com.example.murbin.presentation;

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
import com.example.murbin.CustomApplication;
import com.example.murbin.R;
import com.example.murbin.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

public class UserFragment extends Fragment {

    /*
      Constant for ease of use in debugging the class code
     */
    private static final String TAG = UserFragment.class.getSimpleName();

    CustomApplication aplicacion = (CustomApplication) getContext();

    @Override
    public View onCreateView(LayoutInflater inflador, ViewGroup contenedor, Bundle savedInstanceState) {
        View view = inflador.inflate(R.layout.user_fragment, contenedor, false);
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        final TextView user_fragment_tv_notification = view.findViewById(R.id.user_fragment_tv_notification);
        final Button user_fragment_btn_email_verification = view.findViewById(R.id.user_fragment_btn_email_verification);

        if (firebaseUser != null) {
            StringBuilder providers = new StringBuilder();
            for (UserInfo provider : firebaseUser.getProviderData()) {
                providers.append(provider.getProviderId()).append(", ");
            }

            UserModel userModel = new UserModel(
                    firebaseUser.getPhotoUrl(),
                    firebaseUser.getDisplayName(),
                    firebaseUser.getEmail(),
                    firebaseUser.getPhoneNumber(),
                    providers.toString(),
                    firebaseUser.getUid()
            );

            final NetworkImageView user_fragment_niv_picture = view.findViewById(R.id.user_fragment_niv_picture);
            final TextView user_fragment_tv_name = view.findViewById(R.id.user_fragment_tv_name);
            final TextView user_fragment_tv_email = view.findViewById(R.id.user_fragment_tv_email);
            final TextView user_fragment_tv_providers = view.findViewById(R.id.user_fragment_tv_providers);
            final TextView user_fragment_tv_phone = view.findViewById(R.id.user_fragment_tv_phone);
            final TextView user_fragment_tv_uid = view.findViewById(R.id.user_fragment_tv_uid);

            /*
              If the user has not validated their email, they are prompted to do so and a button
              is provided so that they can receive the verification email.
             */
            if (!firebaseUser.isEmailVerified()) {
                user_fragment_tv_notification.setTextColor(Color.RED);
                user_fragment_tv_notification.setText(R.string.user_fragment_tv_email_verification);
                user_fragment_tv_notification.setVisibility(View.VISIBLE);

                user_fragment_btn_email_verification.setBackgroundColor(Color.BLACK);
                user_fragment_btn_email_verification.setTextColor(Color.WHITE);
                user_fragment_btn_email_verification.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        firebaseUser.sendEmailVerification();
                        user_fragment_btn_email_verification.setClickable(false);
                        user_fragment_btn_email_verification.setText(R.string.user_fragment_btn_email_verification_sended);
                        user_fragment_btn_email_verification.setBackgroundColor(Color.GREEN);
                        user_fragment_btn_email_verification.setTextColor(Color.WHITE);
                    }
                });
                user_fragment_btn_email_verification.setVisibility(View.VISIBLE);
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

                user_fragment_niv_picture.setImageUrl(userModel.getPicture().toString(), pictureReader);
                user_fragment_niv_picture.setVisibility(View.VISIBLE);
            }

            user_fragment_tv_name.setText(userModel.getName());
            user_fragment_tv_email.setText(userModel.getEmail());
            user_fragment_tv_providers.setText(userModel.getProvider());
            user_fragment_tv_phone.setText(userModel.getPhone());
            user_fragment_tv_uid.setText(userModel.getUid());
        } else {
            user_fragment_tv_notification.setText(R.string.user_fragment_error_1);
            user_fragment_tv_notification.setVisibility(View.VISIBLE);
        }

        return view;
    }
}