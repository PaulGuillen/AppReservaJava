package devpaul.business.empresaexample.vista.Fragments.CafeteriaPackage;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import devpaul.business.empresaexample.controlador.ViewHolderCafeteria;
import devpaul.business.empresaexample.modelo.Cafeteria;
import devpaul.business.empresaexample.R;


public class CafeteriaFragment extends Fragment {

    private RecyclerView mRecyclerView, mRecyclerView2, mRecyclerView3, mRecyclerView4;
    private FirebaseDatabase mFirebaseDatabse;
    private DatabaseReference mRef, mRef2, mRef3, mRef4;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cafeteria, container, false);

        mRecyclerView = view.findViewById(R.id.first_recycler_view);
        mRecyclerView2 = view.findViewById(R.id.second_recycler_view);
        mRecyclerView3 = view.findViewById(R.id.third_recycler_view);
        mRecyclerView4 = view.findViewById(R.id.fourth_recycler_view);


        Toolbar toolbar = view.findViewById(R.id.toolbar_main);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);

       /* ImageButton recargar = view.findViewById(R.id.img_principal);
        recargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().finish();
                getActivity().overridePendingTransition(0, 0);
                startActivity(getActivity().getIntent());
                getActivity().overridePendingTransition(0, 0);
            }
        });*/

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView2.setLayoutManager(layoutManager2);

        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView3.setLayoutManager(layoutManager3);

        LinearLayoutManager layoutManager4 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView4.setLayoutManager(layoutManager4);

        mFirebaseDatabse = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabse.getReference("Cafeteria").child("Ensaladas");
        mRef2 = mFirebaseDatabse.getReference("Cafeteria").child("Bebidas");
        mRef3 = mFirebaseDatabse.getReference("Cafeteria").child("Desayunos");
        mRef4 = mFirebaseDatabse.getReference("Cafeteria").child("Aperitivos");

        EnsaladasData();
        BebidasData();
        DesayunosData();
        AperitivosData();

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    private void EnsaladasData() {
        if (isOnline()) {
            FirebaseRecyclerAdapter<Cafeteria, ViewHolderCafeteria> firebaseRecyclerAdapter =
                    new FirebaseRecyclerAdapter<Cafeteria, ViewHolderCafeteria>(
                            Cafeteria.class,
                            R.layout.item_cafeteria,
                            ViewHolderCafeteria.class,
                            mRef) {
                @Override
                protected void populateViewHolder(ViewHolderCafeteria viewHolderCafeteria, Cafeteria model, int i) {
                    viewHolderCafeteria.setDetails(requireActivity().getApplicationContext(), model.getTitulo(), model.getImagen(),
                            model.getPrecio(), model.getDescripcion(), model.getEstado());
                }

                @Override
                public ViewHolderCafeteria onCreateViewHolder(ViewGroup parent, int viewType) {
                    ViewHolderCafeteria viewHolderCafeteria = super.onCreateViewHolder(parent, viewType);
                    viewHolderCafeteria.setOnClickListener(new ViewHolderCafeteria.ClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {

                            String mTittle = getItem(position).getTitulo();
                            String mImage = getItem(position).getImagen();
                            String mPric = getItem(position).getPrecio();
                            String mDesc = getItem(position).getDescripcion();
                            String mEst = getItem(position).getEstado();

                            //Pasar la informacion al nuevo activity con un intent
                            Intent intent = new Intent(view.getContext(), CafeteriaPosteriorActivity.class);
                            intent.putExtra("titulo", mTittle);
                            intent.putExtra("imagen", mImage);
                            intent.putExtra("precio", mPric);
                            intent.putExtra("descripcion", mDesc);
                            intent.putExtra("estado", mEst);

                            startActivity(intent);
                        }

                        @Override
                        public void onItemLongClick(View view, int position) {

                        }
                    });
                    return viewHolderCafeteria;
                }
            };
            mRecyclerView.setAdapter(firebaseRecyclerAdapter);
        } else {
            try {
                final AlertDialog alertDialog = new AlertDialog.Builder(requireActivity()).create();

                alertDialog.setTitle("Información");
                alertDialog.setMessage("Parece que no estas conectado a internet - no podemos encontrar " +
                        " ninguna red");
                alertDialog.setIcon(android.R.drawable.ic_dialog_alert);

                alertDialog.show();
            } catch (Exception e) {
                Toast.makeText(getActivity(), "" + e, Toast.LENGTH_SHORT).show();
            }
        }


    }

    private void BebidasData() {
        if (isOnline()) {
            FirebaseRecyclerAdapter<Cafeteria, ViewHolderCafeteria> firebaseRecyclerAdapter2 = new
                    FirebaseRecyclerAdapter<Cafeteria, ViewHolderCafeteria>(
                            Cafeteria.class,
                            R.layout.item_cafeteria,
                            ViewHolderCafeteria.class,
                            mRef2

                    ) {
                        @Override
                        protected void populateViewHolder(ViewHolderCafeteria viewHolderCafeteria, Cafeteria model, int i) {
                            viewHolderCafeteria.setDetails(requireActivity().getApplicationContext(), model.getTitulo(), model.getImagen(),
                                    model.getPrecio(), model.getDescripcion(), model.getEstado());
                        }

                        @Override
                        public ViewHolderCafeteria onCreateViewHolder(ViewGroup parent, int viewType) {
                            ViewHolderCafeteria viewHolderCafeteria = super.onCreateViewHolder(parent, viewType);
                            viewHolderCafeteria.setOnClickListener(new ViewHolderCafeteria.ClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {

                                    String mTittle = getItem(position).getTitulo();
                                    String mImage = getItem(position).getImagen();
                                    String mPric = getItem(position).getPrecio();
                                    String mDesc = getItem(position).getDescripcion();
                                    String mEst = getItem(position).getEstado();

                                    //Pasar la informacion al nuevo activity con un intent
                                    Intent intent = new Intent(view.getContext(), CafeteriaPosteriorActivity.class);
                                    intent.putExtra("titulo", mTittle);
                                    intent.putExtra("imagen", mImage);
                                    intent.putExtra("precio", mPric);
                                    intent.putExtra("descripcion", mDesc);
                                    intent.putExtra("estado", mEst);

                                    startActivity(intent);
                                }

                                @Override
                                public void onItemLongClick(View view, int position) {

                                }
                            });
                            return viewHolderCafeteria;
                        }
                    };
            mRecyclerView2.setAdapter(firebaseRecyclerAdapter2);
        } else {
            try {
                final AlertDialog alertDialog = new AlertDialog.Builder(requireActivity()).create();

                alertDialog.setTitle("Información");
                alertDialog.setMessage("Parece que no estas conectado a internet - no podemos encontrar " +
                        " ninguna red");
                alertDialog.setIcon(android.R.drawable.ic_dialog_alert);

                alertDialog.show();
            } catch (Exception e) {
                Toast.makeText(getActivity(), "" + e, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void DesayunosData() {
        if (isOnline()) {
            FirebaseRecyclerAdapter<Cafeteria, ViewHolderCafeteria> firebaseRecyclerAdapter3 = new
                    FirebaseRecyclerAdapter<Cafeteria, ViewHolderCafeteria>(
                            Cafeteria.class,
                            R.layout.item_cafeteria,
                            ViewHolderCafeteria.class,
                            mRef3

                    ) {
                        @Override
                        protected void populateViewHolder(ViewHolderCafeteria viewHolderCafeteria, Cafeteria model, int i) {
                            viewHolderCafeteria.setDetails(requireActivity().getApplicationContext(), model.getTitulo(), model.getImagen(),
                                    model.getPrecio(), model.getDescripcion(), model.getEstado());
                        }

                        @Override
                        public ViewHolderCafeteria onCreateViewHolder(ViewGroup parent, int viewType) {
                            ViewHolderCafeteria viewHolderCafeteria = super.onCreateViewHolder(parent, viewType);
                            viewHolderCafeteria.setOnClickListener(new ViewHolderCafeteria.ClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {

                                    String mTittle = getItem(position).getTitulo();
                                    String mImage = getItem(position).getImagen();
                                    String mPric = getItem(position).getPrecio();
                                    String mDesc = getItem(position).getDescripcion();
                                    String mEst = getItem(position).getEstado();

                                    //Pasar la informacion al nuevo activity con un intent
                                    Intent intent = new Intent(view.getContext(), CafeteriaPosteriorActivity.class);
                                    intent.putExtra("titulo", mTittle);
                                    intent.putExtra("imagen", mImage);
                                    intent.putExtra("precio", mPric);
                                    intent.putExtra("descripcion", mDesc);
                                    intent.putExtra("estado", mEst);

                                    startActivity(intent);
                                }

                                @Override
                                public void onItemLongClick(View view, int position) {

                                }
                            });
                            return viewHolderCafeteria;
                        }
                    };
            mRecyclerView3.setAdapter(firebaseRecyclerAdapter3);
        } else {
            try {
                final AlertDialog alertDialog = new AlertDialog.Builder(requireActivity()).create();

                alertDialog.setTitle("Información");
                alertDialog.setMessage("Parece que no estas conectado a internet - no podemos encontrar " +
                        " ninguna red");
                alertDialog.setIcon(android.R.drawable.ic_dialog_alert);

                alertDialog.show();
            } catch (Exception e) {
                Toast.makeText(getActivity(), "" + e, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void AperitivosData() {
        if (isOnline()) {
            FirebaseRecyclerAdapter<Cafeteria, ViewHolderCafeteria> firebaseRecyclerAdapter4 = new
                    FirebaseRecyclerAdapter<Cafeteria, ViewHolderCafeteria>(
                            Cafeteria.class,
                            R.layout.item_cafeteria,
                            ViewHolderCafeteria.class,
                            mRef4

                    ) {
                        @Override
                        protected void populateViewHolder(ViewHolderCafeteria viewHolderCafeteria, Cafeteria model, int i) {
                            viewHolderCafeteria.setDetails(requireActivity().getApplicationContext(), model.getTitulo(), model.getImagen(),
                                    model.getPrecio(), model.getDescripcion(), model.getEstado());
                        }

                        @Override
                        public ViewHolderCafeteria onCreateViewHolder(ViewGroup parent, int viewType) {
                            ViewHolderCafeteria viewHolderCafeteria = super.onCreateViewHolder(parent, viewType);
                            viewHolderCafeteria.setOnClickListener(new ViewHolderCafeteria.ClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {

                                    String mTittle = getItem(position).getTitulo();
                                    String mImage = getItem(position).getImagen();
                                    String mPric = getItem(position).getPrecio();
                                    String mDesc = getItem(position).getDescripcion();
                                    String mEst = getItem(position).getEstado();

                                    //Pasar la informacion al nuevo activity con un intent
                                    Intent intent = new Intent(view.getContext(), CafeteriaPosteriorActivity.class);
                                    intent.putExtra("titulo", mTittle);
                                    intent.putExtra("imagen", mImage);
                                    intent.putExtra("precio", mPric);
                                    intent.putExtra("descripcion", mDesc);
                                    intent.putExtra("estado", mEst);

                                    startActivity(intent);
                                }

                                @Override
                                public void onItemLongClick(View view, int position) {

                                }
                            });
                            return viewHolderCafeteria;
                        }
                    };
            mRecyclerView4.setAdapter(firebaseRecyclerAdapter4);
        } else {
            try {
                final AlertDialog alertDialog = new AlertDialog.Builder(requireActivity()).create();

                alertDialog.setTitle("Información");
                alertDialog.setMessage("Parece que no estas conectado a internet - no podemos encontrar " +
                        " ninguna red");
                alertDialog.setIcon(android.R.drawable.ic_dialog_alert);

                alertDialog.show();
            } catch (Exception e) {
                Toast.makeText(getActivity(), "" + e, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) requireActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
            Toast.makeText(getActivity(), "Sin conexion a internet!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}
