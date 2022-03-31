package devpaul.business.empresaexample.vista.activities.carrito;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;



import devpaul.business.empresaexample.R;

public class CarritoDetailActivity extends AppCompatActivity {

    String TAG = "CarritoDetail";
    DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito_detail);

        //desactivar rotacion pantalla
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();


        getAllDataperValue();
    }

    private void getAllDataperValue() {

        TextView textIdPedido = findViewById(R.id.text_id_pedido);
        TextView textNombreApellidos = findViewById(R.id.nombres_del_cliente);
        TextView textNomProduct = findViewById(R.id.text_nom_product);
        ImageView imageProduct = findViewById(R.id.imageview_product);
        TextView textQuanProduct = findViewById(R.id.textview_quantity);
        TextView textPriceProduct = findViewById(R.id.textview_price);
        TextView textStatusOrder = findViewById(R.id.textview_status);

        TextView textPhoneClient = findViewById(R.id.celular_del_cliente);
        TextView textDistrictClient = findViewById(R.id.distrito_del_cliente);
        TextView textDireccionClient = findViewById(R.id.direccion_del_cliente);

        TextView textHourOrder = findViewById(R.id.Hora_orden);
        TextView textTimeOrder = findViewById(R.id.fecha_orden);
        TextView textOrder = findViewById(R.id.total_orden);


        String idPedido = getIntent().getStringExtra("idPedido");

        FirebaseUser currentUser = mAuth.getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser.getUid()).child("Pedidos")
                .child(idPedido);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    String idPedido = snapshot.child("idPedido").getValue().toString();
                    String name = snapshot.child("nombre").getValue().toString();
                    String lastname = snapshot.child("apellido").getValue().toString();
                    String nomProducto = snapshot.child("nomProducto").getValue().toString();
                    String imageUrl = snapshot.child("imagenUrl").getValue().toString();
                    String quantProducto = snapshot.child("quantity").getValue().toString();
                    String priceProduct = snapshot.child("price").getValue().toString();
                    String statusOrder = snapshot.child("status").getValue().toString();
                    String phoneClient = snapshot.child("celular").getValue().toString();
                    String districtClient = snapshot.child("distrito").getValue().toString();
                    String directionClient = snapshot.child("direccion").getValue().toString();
                    String hourOrder = snapshot.child("hour").getValue().toString();
                    String timeOrder = snapshot.child("time").getValue().toString();
                    String totalOrder = snapshot.child("totalOrder").getValue().toString();

                    Picasso.get().load(imageUrl).placeholder(R.drawable.ic_charging)
                            .error(R.drawable.ic_charging).into(imageProduct);
                    textIdPedido.setText(idPedido);
                    textNombreApellidos.setText(name + "\r" +lastname);
                    textNomProduct.setText(nomProducto);
                    textQuanProduct.setText(quantProducto);
                    textPriceProduct.setText(priceProduct);
                    textStatusOrder.setText(statusOrder);
                    textPhoneClient.setText(phoneClient);
                    textDistrictClient.setText(districtClient);
                    textDireccionClient.setText(directionClient);
                    textHourOrder.setText(hourOrder);
                    textTimeOrder.setText(timeOrder);
                    textOrder.setText(totalOrder);


                } else {
                    Toast.makeText(getApplicationContext(), "Datos no encontrados", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }


}
