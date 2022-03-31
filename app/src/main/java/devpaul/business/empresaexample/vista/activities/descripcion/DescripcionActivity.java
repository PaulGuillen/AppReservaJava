package devpaul.business.empresaexample.vista.activities.descripcion;

import static com.google.android.material.bottomnavigation.LabelVisibilityMode.*;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

import devpaul.business.empresaexample.R;
import devpaul.business.empresaexample.vista.activities.carrito.CarritoActivity;
import devpaul.business.empresaexample.vista.Fragments.CafeteriaPackage.CafeteriaFragment;
import devpaul.business.empresaexample.vista.Fragments.InicioPackage.InicioFragment;
import devpaul.business.empresaexample.vista.Fragments.PanaderiaPackage.PanaderiaFragment;
import devpaul.business.empresaexample.vista.Fragments.PasteleriaPackage.PasteleriaFragment;

@SuppressWarnings("deprecation")
public class DescripcionActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    @SuppressLint({"SourceLockedOrientationActivity", "WrongConstant"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion);
        progressDialog = new ProgressDialog(this);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view ->
                vistaCarrito());
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

        //desactivar rotacion pantalla
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setLabelVisibilityMode(LABEL_VISIBILITY_LABELED);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new InicioFragment()).commit();
    }

    private void vistaCarrito(){
        Intent i = new Intent(this, CarritoActivity.class);
        startActivity(i);
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NonConstantResourceId")
    private final OnNavigationItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment = null;
                switch (item.getItemId()){
                    case R.id.nav_inicio:
                        selectedFragment = new InicioFragment();
                        break;
                    case R.id.nav_pasteleria:
                        selectedFragment = new PasteleriaFragment();
                        break;
                    case R.id.nav_panaderia:
                        selectedFragment = new PanaderiaFragment();
                        break;
                    case R.id.nav_cafeteria:
                        selectedFragment = new CafeteriaFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        Objects.requireNonNull(selectedFragment)).commit();
                return true;
            };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    @Override
    protected void onStart() {
        super.onStart();
        progressDialog.dismiss();
    }
}
