package devpaul.business.empresaexample.controlador;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import devpaul.business.empresaexample.R;


public class ViewHolderPasteleria extends RecyclerView.ViewHolder {

    View mView;

    public ViewHolderPasteleria(@NonNull View itemView) {
        super(itemView);

        mView = itemView;


        itemView.setOnClickListener(view -> mClickListener.onItemClick(view, getAdapterPosition()));

        itemView.setOnLongClickListener(view -> {
            mClickListener.onItemLongClick(view, getAdapterPosition());
            return true;
        });


    }


    public void setDetails(Context ctx, String titulo, String descripcion, String imagen, String precio, String masdescripcion) {

        TextView mTituloView = itemView.findViewById(R.id.tv_titulo);
        TextView mDescripcionView = itemView.findViewById(R.id.tv_descripcion);
        ImageView mImagenView = itemView.findViewById(R.id.img_imagen);
        TextView mPrecioView = itemView.findViewById(R.id.tv_precio);
        TextView mMasDescripcion = itemView.findViewById(R.id.tv_masdescripcion);

        mTituloView.setText(titulo);
        mDescripcionView.setText(descripcion);
        Picasso.get().load(imagen).placeholder(R.drawable.ic_charging)
                .error(R.drawable.ic_charging).into(mImagenView);
        mPrecioView.setText(precio);
        mMasDescripcion.setText(masdescripcion);
    }


    private ClickListener mClickListener;

    public interface ClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public void setOnClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
    }
}
