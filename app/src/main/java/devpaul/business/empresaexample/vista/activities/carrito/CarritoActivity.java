package devpaul.business.empresaexample.vista.activities.carrito;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import devpaul.business.empresaexample.controlador.ViewAdapterPedido;
import devpaul.business.empresaexample.modelo.Pedido;
import devpaul.business.empresaexample.R;

public class CarritoActivity extends AppCompatActivity {

    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    RecyclerView recyclerView;
    ViewAdapterPedido myAdapter;
    ArrayList<Pedido> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter = new ViewAdapterPedido(this, list);
        recyclerView.setAdapter(myAdapter);

        mDatabase.child("Users").child(currentUser.getUid()).child("Pedidos").orderByChild("time").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Pedido pedido = dataSnapshot.getValue(Pedido.class);
                    list.add(pedido);
                }

                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}