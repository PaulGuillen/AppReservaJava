package devpaul.business.empresaexample.vista.Fragments.InicioPackage;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import devpaul.business.empresaexample.controlador.ViewHolderNoticias;
import devpaul.business.empresaexample.modelo.Noticia;
import devpaul.business.empresaexample.R;
import devpaul.business.empresaexample.vista.MainActivity;

@SuppressWarnings("deprecation")
public class InicioFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private FirebaseDatabase mFirebaseDatabse;
    private DatabaseReference mRef;
    ProgressDialog progressDialog;
    ImageButton iSetiings;
    private FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_inicio, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        progressDialog = new ProgressDialog(requireActivity());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        mFirebaseDatabse = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabse.getReference("Noticia");

        iSetiings = view.findViewById(R.id.btn_settings);
        iSetiings.setOnClickListener(view1 -> {
            firebaseAuth.signOut();
            Intent intent = new Intent(requireActivity(), MainActivity.class);
            Toast.makeText(requireActivity(), "Gracias por visitarnos", Toast.LENGTH_SHORT).show();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//makesure user cant go back
            startActivity(intent);
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onStart() {
        super.onStart();
        if (isOnline()){

                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_dialog);
                Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
                FirebaseRecyclerAdapter<Noticia, ViewHolderNoticias> firebaseRecyclerAdapter =
                        new FirebaseRecyclerAdapter<Noticia, ViewHolderNoticias>(
                                Noticia.class,
                                R.layout.item_noticias,
                                ViewHolderNoticias.class,
                                mRef
                        ) {
                            @Override
                            protected void populateViewHolder(ViewHolderNoticias viewHolderNoticias
                                    , Noticia model, int position) {
                                viewHolderNoticias.setDetails(requireActivity()
                                        .getApplicationContext(), model.getTitulo(), model.getDescripcion(), model.getFecha());
                                progressDialog.dismiss();
                            }
                        };
                mRecyclerView.setAdapter(firebaseRecyclerAdapter);
        }else{
            try {
                final AlertDialog alertDialog= new AlertDialog.Builder(requireActivity()).create();

                alertDialog.setTitle("Información");
                alertDialog.setMessage("Parece que no estas conectado a internet - no podemos encontrar " +
                        " ninguna red");
                alertDialog.setIcon(android.R.drawable.ic_dialog_alert);

                alertDialog.show();
            } catch (Exception e) {
                Toast.makeText(getActivity(), ""+e, Toast.LENGTH_SHORT).show();
            }
        }

    }

    private boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) requireActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            Toast.makeText(getActivity(), "Sin conexion a internet!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }





}
