package devpaul.business.empresaexample.vista;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import devpaul.business.empresaexample.R;
import devpaul.business.empresaexample.vista.activities.descripcion.DescripcionActivity;
import devpaul.business.empresaexample.vista.activities.RegisterActivity;

@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    ProgressDialog progressDialog;

    String TAG = "MainActivity";
    TextView resetPassword;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //desactivar rotacion pantalla
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        progressDialog = new ProgressDialog(this);

        resetPassword = findViewById(R.id.textview_reenviar_password);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Button btnRegistrarVista = findViewById(R.id.btn_registrar);
        Button btnIniciarVista = findViewById(R.id.btn_iniciar_sesion);

        btnRegistrarVista.setOnClickListener(view -> callRegistrarVista());

        btnIniciarVista.setOnClickListener(view -> registrarUsuario());

        resetPassword.setOnClickListener(view -> {
            showDialog();
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            callDescripcionVista();
        }
    }

    public void registrarUsuario() {

        EditText correo = findViewById(R.id.TextCorreo);
        EditText contraseña = findViewById(R.id.TextPassword);

        String email = correo.getText().toString();
        String password = contraseña.getText().toString();

        if (email.isEmpty()){
            Toast.makeText(this, "Correo vacío", Toast.LENGTH_SHORT).show();
        } else if(password.isEmpty()){
            Toast.makeText(this, "Contraseña vacía", Toast.LENGTH_SHORT).show();
        }else{
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_dialog);
            Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(MainActivity.this, "Usuario encontrado.", Toast.LENGTH_SHORT).show();
                            callDescripcionVista();
                            progressDialog.dismiss();
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Usuario no encontrado.", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
        }

    }

    public void callDescripcionVista() {
        Intent i = new Intent(MainActivity.this, DescripcionActivity.class);
        startActivity(i);
    }

    public void callRegistrarVista() {
        Intent i = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(i);
    }

    public void showDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_resetpassword);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        EditText emailAddress = dialog.findViewById(R.id.edt_correo);

        Button dialogButton = dialog.findViewById(R.id.btn_enviar);
        dialogButton.setOnClickListener(v ->{
            String email = emailAddress.getText().toString();
            auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Correo enviado", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Correo enviado.");
                            dialog.dismiss();
                        }
                    });

        });

        Button dialogClose = dialog.findViewById(R.id.btn_close);
        dialogClose.setOnClickListener(view -> {
            dialog.dismiss();
        });
        dialog.show();


    }

}
