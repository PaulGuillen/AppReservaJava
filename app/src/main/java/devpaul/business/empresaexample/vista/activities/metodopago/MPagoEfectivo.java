package devpaul.business.empresaexample.vista.activities.metodopago;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import devpaul.business.empresaexample.R;
import devpaul.business.empresaexample.vista.activities.descripcion.DescripcionActivity;

public class MPagoEfectivo extends AppCompatActivity {

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpago_efectivo);
        //desactivar rotacion pantalla
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        String nombreObtenido = getIntent().getStringExtra("nombre");
        String apellidoObtenido = getIntent().getStringExtra("apellido");
        String celularObtenido = getIntent().getStringExtra("celular");

        TextView txtName = findViewById(R.id.textview_name);
        TextView txtLastname = findViewById(R.id.textview_lastname);
        TextView txtPhone = findViewById(R.id.textview_celular);
        Button btnFinalizar = findViewById(R.id.btn_finish);
        btnFinalizar.setOnClickListener(view -> {
            Intent i = new Intent(this, DescripcionActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        });

        txtName.setText(nombreObtenido);
        txtLastname.setText(apellidoObtenido);
        txtPhone.setText(celularObtenido);
    }

}