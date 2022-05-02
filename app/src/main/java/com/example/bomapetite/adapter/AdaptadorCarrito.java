package com.example.bomapetite.adapter;

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

public class AdaptadorCarrito extends RecyclerView.Adapter<AdaptadorCarrito.ViewHolder> {

    ArrayList<Platos> listaPlatos;

    public AdaptadorCarrito(ArrayList<Platos> listaPlatos) {

        this.listaPlatos = listaPlatos;
    }
    @NonNull
    @Override
    public AdaptadorCarrito.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.carrito_recycler, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorCarrito.ViewHolder holder, int position) {
        holder.nombre.setText(listaPlatos.get(position).getNombre());
        holder.precio.setText(listaPlatos.get(position).getPrecioCantidad()+" €");
       holder.cantidad.setText(listaPlatos.get(position).getCantidad());
    }

    @Override
    public int getItemCount() {
        return listaPlatos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre,cantidad,precio;


        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            nombre=view.findViewById(R.id.nombrePlato2);
            cantidad=view.findViewById(R.id.cantidad2);
            precio=view.findViewById(R.id.precio2);



        }

    }

}


