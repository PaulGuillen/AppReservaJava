package devpaul.business.empresaexample.vista.Fragments.PasteleriaPackage;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

import devpaul.business.empresaexample.R;

public class PasteleriaPosterior extends AppCompatActivity {

    TextView mTitleTv,mDetailTv,mPriceTv,mFragmentTv,mTypeTv;
    ImageView mImageTv;
    Bitmap bitmap;

    Button mSaveBtn,mShareBtn,mReservarBtn;
    private  static final int WRITE_EXTERNAL_STORAGE_CODE = 1;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasteleria_posterior);

        mTypeTv = findViewById(R.id.textType);
        mTitleTv = findViewById(R.id.titleTv);
        mDetailTv = findViewById(R.id.descriptionTv);
        mImageTv = findViewById(R.id.imageView);
        mPriceTv = findViewById(R.id.priceTv);
        mFragmentTv = findViewById(R.id.fragmentTv);
        mSaveBtn = findViewById(R.id.saveBtn);
        mShareBtn = findViewById(R.id.shareBtn);
        mReservarBtn = findViewById(R.id.reservarBtn);

        //desactivar rotacion pantalla
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        String image = getIntent().getStringExtra("imagen");
        String tittle = getIntent().getStringExtra("titulo");
        String desc = getIntent().getStringExtra("descripcion");
        String pric = getIntent().getStringExtra("precio");
        String frag = getIntent().getStringExtra("masdescripcion");


        mTitleTv.setText(tittle);
        mDetailTv.setText(desc);
        Picasso.get().load(image).placeholder(R.drawable.ic_charging)
                .error(R.drawable.ic_charging).into(mImageTv);
        mPriceTv.setText(pric);
        mFragmentTv.setText(frag);



        //Boton guardar
        mSaveBtn.setOnClickListener(view -> {
            //si la version es marshmello tenemos que perdir permiso
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_DENIED){
                    String [] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    //Mostrar un mensaje para permitir el guardado de la imagen
                    requestPermissions(permission,WRITE_EXTERNAL_STORAGE_CODE);
                }
                else{
                    //permiso aceptado guardar imagen
                    saveImage();
                }
            }
            else{
                //Sistema es menor que marshmello, guardar imagen
                saveImage();
            }
        });
        //Boton compartir
        mShareBtn.setOnClickListener(view -> shareImage());
        //

        //Boton wallpaper
        mReservarBtn.setOnClickListener(view -> enviarReserva());

    }

    private void enviarReserva() {
        String image = getIntent().getStringExtra("imagen");
        String newType = mTypeTv.getText().toString();
        String newTitle = mTitleTv.getText().toString();
        String newPrice = mPriceTv.getText().toString();

       Intent i = new Intent(this, PasteleriaReservar.class);
        i.putExtra("tipo",newType);
        i.putExtra("precio",newPrice);
        i.putExtra("titulo",newTitle);
        i.putExtra("imagen",image);
        startActivity(i);
    }



    @SuppressLint("SetWorldReadable")
    private void shareImage() {
        try {
            //Obtener el titulo y la descripcion y guardarlo en un string s
            bitmap = ((BitmapDrawable)mImageTv.getDrawable()).getBitmap();
            String s = mTitleTv.getText().toString() + "\n" + mDetailTv.getText().toString();

            File file = new File(getExternalCacheDir(), "sample.png");
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,fOut);
            fOut.flush();
            fOut.close();
            file.setReadable(true,false);
            //Intent para comparte la imagen y texto
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Intent.EXTRA_TEXT,s);//Poner el textto
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            intent.setType("image/png");
            startActivity(Intent.createChooser(intent,"Compartir vía"));
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    private void saveImage() {
        bitmap = ((BitmapDrawable)mImageTv.getDrawable()).getBitmap();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());
        //Camino hacia el storage externo
        File path = Environment.getExternalStorageDirectory();
        //Crear una folder llamado "Firebase o con el nombre dle negocio"
        File dir = new File(path+"/Panaderia Brot's/");
        dir.mkdirs();
        //Nombre de la imagen
        String imageName = timeStamp+ ".PNG";
        File file = new File(dir,imageName);
        OutputStream out;
        try {
            out =  new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,out);
            out.flush();
            out.close();
            Toast.makeText(this,"Guardado en"+dir,Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case WRITE_EXTERNAL_STORAGE_CODE:{
                //si la respuesta del codigo es cancelada el resultado del array sera vacio
                if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //Permiso concedido entonces guardar imagen
                    saveImage();
                }
                else{
                    //Permiso denegado
                    Toast.makeText(this,"Acepte el permiso para guardar la imagen",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
