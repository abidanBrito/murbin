package com.example.murbin.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.murbin.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    /*
      Constant for ease of use in debugging the class code
     */
    private static final String TAG = LoginActivity.class.getSimpleName();

    private static final int RC_SIGN_IN = 9999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        login();
    }

    private void login() {
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        if (usuario != null) {
            if (usuario.isEmailVerified()) {
                Intent i = new Intent(this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK
                );
                startActivity(i);
            } else {
                Intent i = new Intent(this, UserActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK
                );
                startActivity(i);
            }
        } else {
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.EmailBuilder().build(),
                    new AuthUI.IdpConfig.GoogleBuilder().build());
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().
                            setAvailableProviders(providers).setIsSmartLockEnabled(false).build(),
                    RC_SIGN_IN);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                login();
                finish();
            } else {
                /*
                 @TODO: Recordar añadir los mensajes que faltan y pasarlos al ficheor string.xml
                 */
                String s = "";
                IdpResponse response = IdpResponse.fromResultIntent(data);
                if (response == null) s = "Cancelado";
                else switch (response.getError().getErrorCode()) {
                    case ErrorCodes.ANONYMOUS_UPGRADE_MERGE_CONFLICT:
                        break;
                    case ErrorCodes.DEVELOPER_ERROR:
                        s = "Error desarrollador";
                        break;
                    case ErrorCodes.EMAIL_LINK_CROSS_DEVICE_LINKING_ERROR:
                        break;
                    case ErrorCodes.EMAIL_LINK_DIFFERENT_ANONYMOUS_USER_ERROR:
                        break;
                    case ErrorCodes.EMAIL_LINK_PROMPT_FOR_EMAIL_ERROR:
                        break;
                    case ErrorCodes.EMAIL_LINK_WRONG_DEVICE_ERROR:
                        break;
                    case ErrorCodes.EMAIL_MISMATCH_ERROR:
                        break;
                    case ErrorCodes.ERROR_GENERIC_IDP_RECOVERABLE_ERROR:
                        break;
                    case ErrorCodes.ERROR_USER_DISABLED:
                        break;
                    case ErrorCodes.INVALID_EMAIL_LINK_ERROR:
                        break;
                    case ErrorCodes.NO_NETWORK:
                        s = "Sin conexión a Internet";
                        break;
                    case ErrorCodes.PLAY_SERVICES_UPDATE_CANCELLED:
                        break;
                    case ErrorCodes.PROVIDER_ERROR:
                        s = "Error en proveedor";
                        break;
                    case ErrorCodes.UNKNOWN_ERROR:
                        break;
                    default:
                        s = "Otros errores de autentificación";
                }

                /*
                 @TODO: Redirigir Eliminar Toast y redirigir, que no se quede el layout en blanco.
                 */
                Toast.makeText(this, s, Toast.LENGTH_LONG).show();
            }
        }
    }
}