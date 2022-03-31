package devpaul.business.empresaexample.controlador;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import devpaul.business.empresaexample.modelo.Pedido;
import devpaul.business.empresaexample.R;
import devpaul.business.empresaexample.vista.activities.carrito.CarritoDetailActivity;


public class ViewAdapterPedido extends RecyclerView.Adapter<ViewAdapterPedido.ViewHolderPedido> {

    String TAG = "AdapterPedido";
    Context context;

    DatabaseReference mDatabase;
    FirebaseAuth mAuth;

    ArrayList<Pedido> list;

    public ViewAdapterPedido(Context context, ArrayList<Pedido> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolderPedido onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_pedido, parent, false);
        return new ViewHolderPedido(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolderPedido holder, int position) {

        Pedido pedido = list.get(position);

        holder.idPedido.setText(pedido.getIdPedido());
        holder.nombreCl.setText(pedido.getNombre() + "\r" + pedido.getApellido());
        holder.distritoCl.setText(pedido.getDistrito());
        double a1 = pedido.getTotalOrder();
        holder.totalCL.setText(a1 + "\r" + "Soles");
        holder.fechaCl.setText(pedido.getTime());
        holder.estadoCl.setText(pedido.getStatus());

        holder.deleteIB.setOnClickListener(view -> {
            showDialogDelete(pedido);
        });

        holder.itemView.setOnClickListener(view -> {
            gotoPedidoDetail(pedido);
        });

     /*   holder.itemView.setOnLongClickListener(view -> {
            showDialogDelete(pedido);
            return false;
        });*/

    }

    private void showDialogDelete(Pedido pedido){

        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Deseas eliminar esta orden?")
                .setContentText("No podrás recuperar esta información!")
                .setCancelText("Cancelar").setCancelClickListener(Dialog::dismiss)
                .setConfirmText("Si borralo!")
                .setConfirmClickListener(sDialog -> {
                     new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Orden Eliminada!").show();
                        deleteLongCLickOrder(pedido);
                        sDialog.dismiss();
                        refreshEvents();
        }).show();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void refreshEvents() {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }


    private void deleteLongCLickOrder(Pedido pedido) {

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        mDatabase.child("Users").child(currentUser.getUid()).child("Pedidos").child(pedido.getIdPedido()).removeValue();


    }

    private void gotoPedidoDetail(Pedido pedido) {
        Intent i = new Intent(context, CarritoDetailActivity.class);
        i.putExtra("idPedido", pedido.getIdPedido());
        context.startActivity(i);
        Log.v(TAG, "idPedido=" + pedido.getIdPedido());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolderPedido extends RecyclerView.ViewHolder {

        TextView idPedido, nombreCl, distritoCl, totalCL, fechaCl, estadoCl;
        ImageButton deleteIB;

        public ViewHolderPedido(@NonNull View itemView) {
            super(itemView);

            idPedido = itemView.findViewById(R.id.idPedido);
            nombreCl = itemView.findViewById(R.id.textview_nombre_cliente);
            distritoCl = itemView.findViewById(R.id.textview_direccion_cliente);
            totalCL = itemView.findViewById(R.id.textview_total_cliente);
            fechaCl = itemView.findViewById(R.id.text_noticia_fecha);
            estadoCl = itemView.findViewById(R.id.textview_orden_cliente);
            deleteIB = itemView.findViewById(R.id.ib_delete);

        }
    }


}
