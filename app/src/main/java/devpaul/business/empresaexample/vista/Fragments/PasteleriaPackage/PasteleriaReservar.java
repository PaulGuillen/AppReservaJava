package devpaul.business.empresaexample.vista.Fragments.PasteleriaPackage;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import devpaul.business.empresaexample.modelo.Pedido;
import devpaul.business.empresaexample.R;
import devpaul.business.empresaexample.vista.activities.metodopago.MPagoEfectivo;
import devpaul.business.empresaexample.vista.activities.metodopago.MPagoVirtual;

@SuppressWarnings("deprecation")
public class PasteleriaReservar extends AppCompatActivity {

    String TAG = "PasteleriaReservar";
    //Distrito
    TextView textViewDistrito;
    ArrayList<String> arrayList;
    Dialog dialog;

    //TextViews from last activity
    EditText datoPro, datoTipo, datoPre;

    //Checkboxs
    CheckBox cbEfec, cbTarj;

    //Button
    Button btnComprar;

    //IamgeButton
    ImageButton btnHora, btnFecha;

    //Datos Personales
    EditText datoNom, datoApe, datoTelCel, datoTelOpc, datoDir;

    //Fecha y Hora
    EditText datoFec, datoHor;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    int año, mes, dia, hora, minutos;
    private static final int TIPO_DIALOGO = 0;
    private static DatePickerDialog.OnDateSetListener SelectrFecha;

    int totalQuantity = 1;
    Button btnAdd, btnRes;
    TextView quantityText;
    ProgressDialog progressDialog;



    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasteleria_reservar);

        //desactivar rotacion pantalla
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        textViewDistrito = findViewById(R.id.text_view);
        btnComprar = findViewById(R.id.btn_comprar);
        btnHora = findViewById(R.id.btn_hora);
        btnFecha = findViewById(R.id.btn_calendar);
        btnAdd = findViewById(R.id.addValue);
        btnRes = findViewById(R.id.resValue);
        quantityText = findViewById(R.id.textquantity);

        datoNom = findViewById(R.id.campo_nombre);
        datoApe = findViewById(R.id.campo_apellido);
        datoTelCel = findViewById(R.id.campo_celular);
        datoTelOpc = findViewById(R.id.campo_numero_casa);
        datoDir = findViewById(R.id.campo_direccion);

        datoPro = findViewById(R.id.campo_nombre_producto);
        datoTipo = findViewById(R.id.campo_tipo);
        datoPre = findViewById(R.id.campo_precio_prodducto);

        datoFec = findViewById(R.id.campo_fecha);
        datoHor = findViewById(R.id.campo_hora);

        cbEfec = findViewById(R.id.check_efectivo);
        cbTarj = findViewById(R.id.check_tarjeta);

        arrayList = new ArrayList<>();
        arrayList.add("Villa el Salvador");
        arrayList.add("San Juan de Miraflores");
        arrayList.add("Villa Maria del Triunfo");
        arrayList.add("Lurín");

        textViewDistrito.setOnClickListener(view -> {
            dialog = new Dialog(PasteleriaReservar.this);
            dialog.setContentView(R.layout.dialog_searchable_spinner);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

            EditText editText = dialog.findViewById(R.id.edit_text);
            ListView listView = dialog.findViewById(R.id.list_view);

            final ArrayAdapter<String> adapter = new ArrayAdapter<>(PasteleriaReservar.this, android.R.layout.simple_list_item_1, arrayList);
            listView.setAdapter(adapter);

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    adapter.getFilter().filter(charSequence);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            listView.setOnItemClickListener((adapterView, view1, i, l) -> {
                textViewDistrito.setText(adapter.getItem(i));
                /*     String newDistrito = adapter.getItem(i);*/
                dialog.dismiss();
            });

        });

        String tituloobtenido = getIntent().getStringExtra("titulo");
        String tipoobtenido = getIntent().getStringExtra("tipo");
        String precioobt = getIntent().getStringExtra("precio");

        datoPro.setText(tituloobtenido);
        datoPre.setText(precioobt);
        datoTipo.setText(tipoobtenido);

        datoPro.setInputType(InputType.TYPE_NULL);
        datoPre.setInputType(InputType.TYPE_NULL);
        datoTipo.setInputType(InputType.TYPE_NULL);


        Calendar calendar = Calendar.getInstance();


        año = calendar.get(Calendar.YEAR);
        mes = calendar.get(Calendar.MONTH);
        dia = calendar.get(Calendar.DAY_OF_MONTH);

        SelectrFecha = (datePicker, year, monthOfYear, dayOfMonth) -> {
            año = year;
            mes = monthOfYear;
            dia = dayOfMonth;
            mostrarFecha();
        };

        btnAdd.setOnClickListener(view -> {
            sumarQuantity();
        });

        btnRes.setOnClickListener(view -> {
            restarQuantity();
        });


        btnHora.setOnClickListener(view -> {
            mostrarHora();
        });

        btnComprar.setOnClickListener(view -> Comprar());

        getNombreApe();

    }

    private void sumarQuantity() {
        if (totalQuantity < 10) {
            totalQuantity++;
            quantityText.setText(String.valueOf(totalQuantity));

        }
    }

    private void restarQuantity() {
        if (totalQuantity > 1) {
            totalQuantity--;
            quantityText.setText(String.valueOf(totalQuantity));
        }
    }


    @SuppressLint("SetTextI18n")
    private void mostrarFecha() {
        int mesactual = mes + 1;
        datoFec.setText(año + "/" + mesactual + "/" + dia);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 0) {
            return new DatePickerDialog(this, SelectrFecha, año, mes, dia);
        }
        return null;
    }

    @SuppressWarnings("deprecation")
    public void mostrarCalendario(View control) {
        showDialog(TIPO_DIALOGO);
    }

    public void mostrarHora() {
        final Calendar c = Calendar.getInstance();
        hora = c.get(Calendar.HOUR_OF_DAY);
        minutos = c.get(Calendar.MINUTE);

        @SuppressLint("SetTextI18n") TimePickerDialog timePickerDialog = new TimePickerDialog(this, (timePicker, hourOfDay, minute) ->
                datoHor.setText(hourOfDay + ":" + minute), hora, minutos, false);
        timePickerDialog.show();
    }

    private void getNombreApe() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser.getUid());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String name = snapshot.child("nombre").getValue().toString();
                    String lastname = snapshot.child("apellido").getValue().toString();
                    datoNom.setText(name);
                    datoApe.setText(lastname);
                } else {
                    Toast.makeText(getApplicationContext(), "Datos no encontrados", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void Comprar() {

        FirebaseUser currentUser = mAuth.getCurrentUser();

        String newUID = mDatabase.push().getKey();
        String name = datoNom.getText().toString();
        String lastname = datoApe.getText().toString();
        String phone = datoTelCel.getText().toString();
        String optional = datoTelOpc.getText().toString();
        String address = datoDir.getText().toString();
        String district = textViewDistrito.getText().toString();

        String nameProduct = datoPro.getText().toString();
        String typeProduct = datoTipo.getText().toString();
        double priceProduct = Double.parseDouble(datoPre.getText().toString());
        int quantityProduct = Integer.parseInt(quantityText.getText().toString());

        //Operacion total

        double totalPriceOrder = priceProduct * quantityProduct;

        String dateOrder = datoFec.getText().toString();
        String hourOrder = datoHor.getText().toString();


        if (validarFormulario(name, phone, optional, address, district, quantityProduct, dateOrder, hourOrder)) {

            if (!cbEfec.isChecked() && !cbTarj.isChecked()) {
                Toast.makeText(this, "Seleccione un método de pago", Toast.LENGTH_SHORT).show();
            } else if (cbEfec.isChecked() && cbTarj.isChecked()) {
                Toast.makeText(this, "Seleccione solo un método de pago", Toast.LENGTH_SHORT).show();
            } else if (cbEfec.isChecked()) {

                String imagenobt = getIntent().getStringExtra("imagen");

                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_dialog);
                Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
                //Metodo de pago efectivo
                String statusEfec = "Reservado";
                Pedido pedido = new Pedido(name, lastname, newUID, phone, optional, address, district,
                        nameProduct, typeProduct, priceProduct, totalPriceOrder, quantityProduct, dateOrder, hourOrder, statusEfec,imagenobt);
                mDatabase.child("Users").child(currentUser.getUid()).child("Pedidos").child(newUID).setValue(pedido)
                        .addOnSuccessListener(it -> {
                            verCOnfirmacionEfectivo();
                            progressDialog.dismiss();
                        }).addOnFailureListener(e -> {
                });

            } else if (cbTarj.isChecked()) {
                //Metodo de pago tarjeta
                String imagenobt = getIntent().getStringExtra("imagen");
                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_dialog);
                Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
                String statusTarjeta = "Pagando";
                Pedido pedido = new Pedido(name, lastname, newUID, phone, optional, address, district,
                        nameProduct, typeProduct, priceProduct, totalPriceOrder, quantityProduct, dateOrder, hourOrder, statusTarjeta,imagenobt);
                mDatabase.child("Users").child(currentUser.getUid()).child("Pedidos").child(newUID).setValue(pedido)
                        .addOnSuccessListener(it -> {
                            Intent i = new Intent(this, MPagoVirtual.class);
                            Bundle b = new Bundle();
                            b.putDouble("total",totalPriceOrder);
                            i.putExtra("idPedido",newUID);
                            i.putExtras(b);
                            startActivity(i);
                            progressDialog.dismiss();
                        }).addOnFailureListener(e -> {
                });
            }
        }

    }

    private void verCOnfirmacionEfectivo() {

        String name = datoNom.getText().toString();
        String lastname = datoApe.getText().toString();
        String phone = datoTelCel.getText().toString();

        Intent i = new Intent(this, MPagoEfectivo.class);
        i.putExtra("nombre", name);
        i.putExtra("apellido", lastname);
        i.putExtra("celular", phone);
        Log.v(TAG, "nonbre=" + name + "Apellido = "+lastname + "celular = " +phone) ;
        startActivity(i);
    }


    public boolean validarFormulario(String name, String phone, String optional, String address, String district, int quantityProduct, String dateOrder, String hourOrder) {

        if (name.isEmpty()) {
            Toast.makeText(this, "Debes ingresar el nombre", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (phone.isEmpty()) {
            Toast.makeText(this, "Debes ingresar el celular", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (optional.isEmpty()) {
            Toast.makeText(this, "Debes ingresar telefono opcional", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (address.isEmpty()) {
            Toast.makeText(this, "Debes ingresar la direccion", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (district.equals("Selecciona Distrito")) {
            Toast.makeText(this, "Debes seleccionar un distrito", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (quantityProduct <= 0) {
            Toast.makeText(this, "Debes ingresar una cantidad", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (dateOrder.isEmpty()) {
            Toast.makeText(this, "Selecciona la fecha de entrega", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (hourOrder.isEmpty()) {
            Toast.makeText(this, "Selecciona la hora de entrega", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }


}