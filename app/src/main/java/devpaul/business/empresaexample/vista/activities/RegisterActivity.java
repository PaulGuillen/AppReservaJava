package devpaul.business.empresaexample.vista.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import devpaul.business.empresaexample.modelo.Usuario;
import devpaul.business.empresaexample.R;
import devpaul.business.empresaexample.vista.MainActivity;

@SuppressWarnings("deprecation")
public class RegisterActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    String TAG = "RegisterActivity";
    ProgressDialog progressDialog;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //desactivar rotacion pantalla
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        progressDialog = new ProgressDialog(this);
        Button btnRegistro = findViewById(R.id.btn_registrar);

        btnRegistro.setOnClickListener(view -> registrarUsuario());

    }


    private void registrarUsuario(){

        EditText edtNombre = findViewById(R.id.editext_nombre);
        EditText edtApellido = findViewById(R.id.editext_apellido);
        EditText edtCorreo = findViewById(R.id.editext_correo);
        EditText edtPassword = findViewById(R.id.TextPassword);
        EditText edtConfirmPassword = findViewById(R.id.text_confirm_password);

        String name = edtNombre.getText().toString();
        String lastname = edtApellido.getText().toString();
        String email = edtCorreo.getText().toString();
        String password = edtPassword.getText().toString();
        String confirmpassword = edtConfirmPassword.getText().toString();

        if (validarFormulario(name, lastname, email, password, confirmpassword)){
            Usuario user = new Usuario(name,lastname,email);

            //Para obtener datos del modelo
            String texto = user.toString();

            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_dialog);
            Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            String UID =  task.getResult().getUser().getUid();
                            Log.d(TAG, "createUserWithEmail:success" + texto);
                            mDatabase.child("Users").child(UID).setValue(user);
                            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            vistaMainActivity();

                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }


    }

    public boolean validarFormulario(String name, String lastname, String email, String password, String confirmPassword){

        if (name.isEmpty()){
            Toast.makeText(this, "Debes ingresar el nombre", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (lastname.isEmpty()) {
            Toast.makeText(this, "Debes ingresar el apellido", Toast.LENGTH_SHORT).show();
            return false;
        }


        if (email.isEmpty()) {
            Toast.makeText(this, "Debes ingresar el correo", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.isEmpty()) {
            Toast.makeText(this, "Debes ingresar el contraseña", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (confirmPassword.isEmpty()) {
            Toast.makeText(this, "Debes ingresar el la confirmacion de contraseña", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!password.equals(confirmPassword)){
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void vistaMainActivity(){
        Intent i = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(i);
    }

}