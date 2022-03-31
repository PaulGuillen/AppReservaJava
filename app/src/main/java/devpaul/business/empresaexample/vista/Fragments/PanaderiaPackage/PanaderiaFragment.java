package devpaul.business.empresaexample.vista.Fragments.PanaderiaPackage;


import static android.content.Context.MODE_PRIVATE;

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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Objects;

import devpaul.business.empresaexample.controlador.ViewHolderPanaderia;
import devpaul.business.empresaexample.modelo.Panaderia;
import devpaul.business.empresaexample.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PanaderiaFragment extends Fragment {

    private SharedPreferences mSharedPref;
    private RecyclerView mRecyclerView;
    private FirebaseDatabase mFirebaseDatabase;
    private ProgressDialog progressDialog;
    private DatabaseReference mRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_panaderia, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar_main);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);

        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);

        mSharedPref = requireActivity().getSharedPreferences("SortSettings", MODE_PRIVATE);

      /*  ImageButton recargar = view.findViewById(R.id.img_principal);
        recargar.setOnClickListener(view1 -> {
            requireActivity().finish();
            requireActivity().overridePendingTransition(0, 0);
            startActivity(requireActivity().getIntent());
            requireActivity().overridePendingTransition(0, 0);
        });
*/
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference("Panaderia");

        return  view;
    }

    private void firebaseSearch(String searchText){
        String query = searchText.toLowerCase();

        Query firebaseSearchQuery = mRef.orderByChild("buscador").startAt(query).endAt(query+"\uf8ff");
        FirebaseRecyclerAdapter<Panaderia, ViewHolderPanaderia> firebaseRecyclerAdapter = new
                FirebaseRecyclerAdapter<Panaderia, ViewHolderPanaderia>(
                        Panaderia.class,
                        R.layout.item_panaderia,
                        ViewHolderPanaderia.class,
                        firebaseSearchQuery
                ) {
                    @Override
                    protected void populateViewHolder(ViewHolderPanaderia viewHolderPanaderia, Panaderia model, int i) {
                        viewHolderPanaderia.setDetails(requireActivity().getApplicationContext(), model.getTitulo(), model.getImagen(),model.getPrecio(),
                                model.getDescripcion(),model.getEstado());
                        progressDialog.dismiss();
                    }

                    @Override
                    public ViewHolderPanaderia onCreateViewHolder(ViewGroup parent, int viewType) {
                        ViewHolderPanaderia viewHolderPanaderia = super.onCreateViewHolder(parent, viewType);
                        viewHolderPanaderia.setOnClickListener(new ViewHolderPanaderia.ClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                String mTittle = getItem(position).getTitulo();
                                String mImage = getItem(position).getImagen();
                                String mPric = getItem(position).getPrecio();
                                String mDesc = getItem(position).getDescripcion();
                                String mEst = getItem(position).getEstado();

                                //Pasar la informacion al nuevo activity con un intent
                                Intent intent = new Intent(view.getContext(), PanaderiaPosteriorActivity.class);
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
                        return viewHolderPanaderia;
                    }
                };
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }
    @SuppressWarnings("deprecation")
    @Override
    public void onStart() {
        super.onStart();
        if (isOnline()){
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_dialog);
            Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
            FirebaseRecyclerAdapter<Panaderia, ViewHolderPanaderia> firebaseRecyclerAdapter = new
                    FirebaseRecyclerAdapter<Panaderia, ViewHolderPanaderia>(
                            Panaderia.class,
                            R.layout.item_panaderia,
                            ViewHolderPanaderia.class,
                            mRef
                    ) {
                        @Override
                        protected void populateViewHolder(ViewHolderPanaderia viewHolderPanaderia, Panaderia model, int i) {
                            viewHolderPanaderia.setDetails(getActivity().getApplicationContext(), model.getTitulo(), model.getImagen(),model.getPrecio(),
                                    model.getDescripcion(),model.getEstado());
                            progressDialog.dismiss();
                        }

                        @Override
                        public ViewHolderPanaderia onCreateViewHolder(ViewGroup parent, int viewType) {
                            ViewHolderPanaderia viewHolderPanaderia = super.onCreateViewHolder(parent, viewType);
                            viewHolderPanaderia.setOnClickListener(new ViewHolderPanaderia.ClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {

                                    String mTittle = getItem(position).getTitulo();
                                    String mImage = getItem(position).getImagen();
                                    String mPric = getItem(position).getPrecio();
                                    String mDesc = getItem(position).getDescripcion();
                                    String mEst = getItem(position).getEstado();

                                    //Pasar la informacion al nuevo activity con un intent
                                    Intent intent = new Intent(view.getContext(), PanaderiaPosteriorActivity.class);
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
                            return viewHolderPanaderia;
                        }
                    };
            mRecyclerView.setLayoutManager(new GridLayoutManager(requireActivity().getApplicationContext(),2));
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

    @SuppressWarnings("deprecation")
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        requireActivity().getMenuInflater().inflate(R.menu.menu_navigation,menu);
        MenuItem item = menu.findItem(R.id.action_search);SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
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



}
