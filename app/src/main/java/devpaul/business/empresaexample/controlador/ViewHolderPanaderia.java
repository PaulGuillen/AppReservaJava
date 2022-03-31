package devpaul.business.empresaexample.controlador;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import devpaul.business.empresaexample.R;

public class ViewHolderPanaderia extends RecyclerView.ViewHolder {

    private View mView;

    public ViewHolderPanaderia(@NonNull View itemView) {
        super(itemView);

        mView = itemView;

        itemView.setOnClickListener(view -> mClickListener.onItemClick(view, getAdapterPosition()));

        itemView.setOnLongClickListener(view -> {
            mClickListener.onItemLongClick(view, getAdapterPosition());
            return true;
        });


    }

    public void setDetails(Context ctx, String titulo, String imagen, String precio, String descripcion, String estado) {

        TextView mTituloView = itemView.findViewById(R.id.tv_titulo_panaderia);
        ImageView mImagenView = itemView.findViewById(R.id.img_imagen_panaderia);
        TextView mPrecioView = itemView.findViewById(R.id.tv_precio_panaderia);
        TextView mDescripcionView = itemView.findViewById(R.id.tv_descripcion_panaderia);
        TextView mEstadoView = itemView.findViewById(R.id.tv_estado_panaderia);

        Picasso.get().load(imagen).placeholder(R.drawable.ic_charging)
                .error(R.drawable.ic_charging).into(mImagenView);
        mTituloView.setText(titulo);
        mPrecioView.setText(precio);
        mDescripcionView.setText(descripcion);
        mEstadoView.setText(estado);

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
