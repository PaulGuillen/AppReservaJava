package devpaul.business.empresaexample.vista.activities.metodopago;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import devpaul.business.empresaexample.R;
import devpaul.business.empresaexample.vista.activities.metodopago.tarjeta.ClientPaymentFormMPVirtualActivity;
public class MPagoVirtual extends AppCompatActivity {

    String TAG = "PagoVirtual";
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpago_virtual);
        //desactivar rotacion pantalla
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        String test = getIntent().getStringExtra("idPedido");
        Bundle b = getIntent().getExtras();
        double result = b.getDouble("total");
        Log.v(TAG,"Total =  " +result +test);

        ImageView IvMercadoPago = findViewById(R.id.imageview_mercadopago);
        IvMercadoPago.setOnClickListener(view -> {
            String idPedido = getIntent().getStringExtra("idPedido");
            Intent i = new Intent(this, ClientPaymentFormMPVirtualActivity.class);
            Bundle bundle = new Bundle();
            bundle.putDouble("total", result);
            i.putExtra("idPedido", idPedido );
            i.putExtras(b);
            startActivity(i);
        });

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

}