package devpaul.business.empresaexample.controlador;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import devpaul.business.empresaexample.R;

public class ViewHolderNoticias extends RecyclerView.ViewHolder {

    View mView;

    public ViewHolderNoticias(@NonNull View itemView) {
        super(itemView);

        mView = itemView;
    }

    public void setDetails(Context ctx, String titulo, String descripcion, String fecha) {

        TextView mTituloView = mView.findViewById(R.id.text_noticia_titulo);
        TextView mDescripcionView = mView.findViewById(R.id.text_descripcion_noticia);
        TextView mFechaView = mView.findViewById(R.id.text_noticia_fecha);

        mTituloView.setText(titulo);
        mDescripcionView.setText(descripcion);
        mFechaView.setText(fecha);
    }
}
