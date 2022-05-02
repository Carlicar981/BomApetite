package com.example.bomapetite.adapterPlatos;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bomapetite.Platos;
import com.example.bomapetite.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdaptadorPlatos extends RecyclerView.Adapter<AdaptadorPlatos.ViewHolder> {

    ArrayList<Platos> listaPlatos;
    ArrayList<Platos> original;
    final AdaptadorPlatos.OnItemClickListener listener;
    public interface OnItemClickListener{
        void agregar(Platos item);
        void actualizar(Platos item);
        void borrar(Platos item);
    }
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView nombre,descripcion,cantidad,precio;
        Button mas, menos, agregar;
        ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            nombre=view.findViewById(R.id.nombrePlato);
            descripcion=view.findViewById(R.id.descripcionPlato);
            cantidad=view.findViewById(R.id.cantidad);
            precio=view.findViewById(R.id.precio);
            imageView=view.findViewById(R.id.imagePlato);
            mas=view.findViewById(R.id.mas);
            menos=view.findViewById(R.id.menos);
            agregar=view.findViewById(R.id.agregar);

        }


    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void filtrar(String txtBuscar){
        int longitud = txtBuscar.length();
        if (longitud==0){
            listaPlatos.clear();
            listaPlatos.addAll(original);
        }else{
            List<Platos> coleccion = listaPlatos.stream().filter(i -> i.getNombre().toLowerCase()
                    .contains(txtBuscar.toLowerCase())).collect(Collectors.toList());
            listaPlatos.clear();
            listaPlatos.addAll(coleccion);
        }
        notifyDataSetChanged();
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void filtrarCategoria(String txtBuscar){
            listaPlatos.clear();
            listaPlatos.addAll(original);
            List<Platos> coleccion = listaPlatos.stream().filter(i -> i.getCategoria().toLowerCase()
                    .contains(txtBuscar.toLowerCase())).collect(Collectors.toList());
            listaPlatos.clear();
            listaPlatos.addAll(coleccion);
        notifyDataSetChanged();
    }
    public AdaptadorPlatos(ArrayList<Platos> listaPlatos,AdaptadorPlatos.OnItemClickListener listener) {
        this.listaPlatos = listaPlatos;
        original=new ArrayList<>();
        original.addAll(listaPlatos);
        this.listener=listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.platos_recycler, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull AdaptadorPlatos.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.nombre.setText(listaPlatos.get(position).getNombre());
        holder.descripcion.setText(listaPlatos.get(position).getDescipcion());
        holder.precio.setText(listaPlatos.get(position).getPrecio()+" €");
        holder.imageView.setImageResource(listaPlatos.get(position).getFoto());
        holder.agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Platos platos = listaPlatos.get(position);
            platos.setTotalCarrito(1);
            listener.agregar(platos);
            holder.cantidad.setVisibility(View.VISIBLE);
            holder.mas.setVisibility(View.VISIBLE);
            holder.menos.setVisibility(View.VISIBLE);
            holder.agregar.setVisibility(View.GONE);
            holder.cantidad.setText(platos.getTotalCarrito()+"");
            }
        });
        holder.mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Platos platos  = listaPlatos.get(position);
                int total = platos.getTotalCarrito();
                total++;
                if(total <= 10 ) {
                    platos.setTotalCarrito(total);
                    listener.actualizar(platos);
                    holder.cantidad.setText(total +"");
                }
            }
        });
        holder.menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Platos platos  = listaPlatos.get(position);
                int total = platos.getTotalCarrito();
                total--;
                /*
                if(total > 0 ) {
                    platos.setTotalCarrito(total);
                    listener.borrar(platos);
                    holder.cantidad.setText(total +"");
                } else {

                 */
                    platos.setTotalCarrito(total);
                    listener.borrar(platos);
                    holder.cantidad.setVisibility(View.INVISIBLE);
                    holder.mas.setVisibility(View.INVISIBLE);
                    holder.menos.setVisibility(View.INVISIBLE);
                    holder.agregar.setVisibility(View.VISIBLE);
                    holder.cantidad.setText(platos.getTotalCarrito()+"");

            }
        });

    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return listaPlatos.size();
    }
}
