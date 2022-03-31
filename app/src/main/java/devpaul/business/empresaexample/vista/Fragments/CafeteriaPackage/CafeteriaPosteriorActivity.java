package devpaul.business.empresaexample.vista.Fragments.CafeteriaPackage;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import devpaul.business.empresaexample.R;

public class CafeteriaPosteriorActivity extends AppCompatActivity {

    TextView mTitleTv,mDetailTv,mPriceTv,mStateTv;
    ImageView mImageTv;
    String Url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafeteria_posterior);


        Url = "https://www.recetasdesbieta.com/pan-frances-casero-receta-autentica/";

        mTitleTv = findViewById(R.id.titleTv);
        mDetailTv = findViewById(R.id.descriptionTv);
        mImageTv = findViewById(R.id.imageView);
        mPriceTv = findViewById(R.id.priceTv);
        mStateTv = findViewById(R.id.stateTv);

        //desactivar rotacion pantalla
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        String image = getIntent().getStringExtra("imagen");
        String tittle = getIntent().getStringExtra("titulo");
        String desc = getIntent().getStringExtra("descripcion");
        String pric = getIntent().getStringExtra("precio");
        String esta = getIntent().getStringExtra("estado");


        mTitleTv.setText(tittle);
        mDetailTv.setText(desc);
        Picasso.get().load(image).placeholder(R.drawable.ic_charging)
                .error(R.drawable.ic_charging).into(mImageTv);
        mPriceTv.setText(pric);
        mStateTv.setText(esta);
    }
}
