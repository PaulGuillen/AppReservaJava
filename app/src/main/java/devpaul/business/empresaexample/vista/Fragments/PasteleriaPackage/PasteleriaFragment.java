package devpaul.business.empresaexample.vista.Fragments.PasteleriaPackage;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Objects;

import devpaul.business.empresaexample.controlador.ViewHolderPasteleria;
import devpaul.business.empresaexample.modelo.Pasteleria;
import devpaul.business.empresaexample.R;
import devpaul.business.empresaexample.vista.activities.carrito.CarritoActivity;

@SuppressWarnings("deprecation")
public class PasteleriaFragment extends Fragment {

    private LinearLayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private DatabaseReference mRef;
    private ProgressDialog progressDialog;
    private SharedPreferences mSharedPref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pasteleria, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);

        Toolbar toolbar = view.findViewById(R.id.toolbar_main);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);

        //Menu Options
 /*       mSharedPref = requireActivity().getSharedPreferences("SortSettings", MODE_PRIVATE);
        String mSorting = mSharedPref.getString("Ordenar", "actuales");

        if (mSorting.equals("actuales")) {
            mLayoutManager = new LinearLayoutManager(requireActivity().getApplicationContext());
            //esto cargara los items de nuevos al comienzo
            mLayoutManager.setReverseLayout(true);
            mLayoutManager.setStackFromEnd(true);
        } else if (mSorting.equals("antiguos")) {
            mLayoutManager = new LinearLayoutManager(requireActivity().getApplicationContext());
            //esto cargara los items viejos al comienzo
            mLayoutManager.setReverseLayout(false);
            mLayoutManager.setStackFromEnd(false);
        }*/

        mLayoutManager = new LinearLayoutManager(requireActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

     /*   ImageButton recargar = view.findViewById(R.id.img_principal);
        recargar.setOnClickListener(view1 -> {
            requireActivity().finish();
            requireActivity().overridePendingTransition(0, 0);
            startActivity(requireActivity().getIntent());
            requireActivity().overridePendingTransition(0, 0);
        });*/


        FirebaseDatabase mFirebaseDatabse = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabse.getReference("Pasteles");
        return view;
    }

    private void firebaseSearch(String searchText){

        String query = searchText.toLowerCase();
        Query firebaseSearchQuery = mRef.orderByChild("buscador").startAt(query).endAt(query+"\uf8ff");
        FirebaseRecyclerAdapter<Pasteleria, ViewHolderPasteleria> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Pasteleria, ViewHolderPasteleria>(
                Pasteleria.class,
                R.layout.item_pasteleria,
                ViewHolderPasteleria.class,
                firebaseSearchQuery
        ) {
            @Override
            protected void populateViewHolder(ViewHolderPasteleria viewHolderPasteleria, Pasteleria model, int i) {
                viewHolderPasteleria.setDetails(requireActivity()
                                .getApplicationContext(), model.getTitulo(), model.getDescripcion(), model.getImagen()
                        , model.getPrecio(), model.getMasdescripcion());
            }

            @Override
            public ViewHolderPasteleria onCreateViewHolder(ViewGroup parent, int viewType) {
                ViewHolderPasteleria viewHolderPasteleria = super.onCreateViewHolder(parent, viewType);
                viewHolderPasteleria.setOnClickListener(new ViewHolderPasteleria.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        String mTittle = getItem(position).getTitulo();
                        String mDesc = getItem(position).getDescripcion();
                        String mImage = getItem(position).getImagen();
                        String mPric = getItem(position).getPrecio();
                        String mMDescp = getItem(position).getMasdescripcion();

                        Intent intent = new Intent(view.getContext(), PasteleriaPosterior.class);
                        intent.putExtra("titulo", mTittle);
                        intent.putExtra("descripcion", mDesc);
                        intent.putExtra("masdescripcion", mMDescp);
                        intent.putExtra("precio", mPric);
                        intent.putExtra("imagen", mImage);

                        startActivity(intent);

                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                });

                return viewHolderPasteleria;
            }
        };

        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isOnline()){
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_dialog);
            Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
            FirebaseRecyclerAdapter<Pasteleria, ViewHolderPasteleria> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Pasteleria, ViewHolderPasteleria>(
                    Pasteleria.class,
                    R.layout.item_pasteleria,
                    ViewHolderPasteleria.class,
                    mRef
            ) {
                @Override
                protected void populateViewHolder(ViewHolderPasteleria viewHolderPasteleria, Pasteleria model, int i) {
                    viewHolderPasteleria.setDetails(requireActivity()
                                    .getApplicationContext(), model.getTitulo(), model.getDescripcion(), model.getImagen()
                            , model.getPrecio(), model.getMasdescripcion());
                    progressDialog.dismiss();
                }

                @Override
                public ViewHolderPasteleria onCreateViewHolder(ViewGroup parent, int viewType) {
                    ViewHolderPasteleria viewHolderPasteleria = super.onCreateViewHolder(parent, viewType);
                    viewHolderPasteleria.setOnClickListener(new ViewHolderPasteleria.ClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {

                            String mTittle = getItem(position).getTitulo();
                            String mDesc = getItem(position).getDescripcion();
                            String mImage = getItem(position).getImagen();
                            String mPric = getItem(position).getPrecio();
                            String mMDescp = getItem(position).getMasdescripcion();

                            //Pasar la informacion al nuevo activity con un intent
                            Intent intent = new Intent(view.getContext(), PasteleriaPosterior.class);
                            intent.putExtra("titulo", mTittle);
                            intent.putExtra("descripcion", mDesc);
                            intent.putExtra("masdescripcion", mMDescp);
                            intent.putExtra("precio", mPric);
                            intent.putExtra("imagen", mImage);

                            startActivity(intent);

                        }

                        @Override
                        public void onItemLongClick(View view, int position) {

                        }
                    });

                    return viewHolderPasteleria;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();

        requireActivity().getMenuInflater().inflate(R.menu.menu_navigation,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setMaxWidth(900);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                firebaseSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                firebaseSearch(newText);
                return false;
            }
        });

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_search) {
            return true;
/*            case R.id.action_sort:
                showSortDialog();
                return true;*/
         /*   case R.id.action_view_cart:
                vistaCarrito();
                return true;*/
        }
        return super.onOptionsItemSelected(item);

    }

/*
    private void showSortDialog() {
        //Las opciones que se veran en el dialog
        String[] sortOptions = {"Actuales", "Antiguos"};
        //Crear un dialogo de alerta
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Ordenar por").setIcon(R.drawable.ic_sort).setItems(sortOptions, (dialog, which) -> {
            if (which == 0) {
                //ordenar por actuales
                SharedPreferences.Editor editor = mSharedPref.edit();
                editor.putString("Ordenar", "actuales");
                editor.apply();//aplicar y guaardar el valor
                requireActivity().recreate();


            } else if (which == 1) {
                //ordenar por los antiguos
                SharedPreferences.Editor editor = mSharedPref.edit();
                editor.putString("Ordenar", "antiguos");
                editor.apply();//aplicar y guaardar el valor
                requireActivity().recreate();
            }
        });

        builder.show();
    }
*/

    private void vistaCarrito(){
        Intent i = new Intent(requireContext(), CarritoActivity.class);
        startActivity(i);
    }
}
