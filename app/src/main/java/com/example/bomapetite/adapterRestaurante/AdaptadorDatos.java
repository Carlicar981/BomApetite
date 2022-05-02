package com.example.bomapetite.adapterRestaurante;

import android.os.TestLooperManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bomapetite.R;

import java.util.ArrayList;

public class AdaptadorDatos extends RecyclerView.Adapter<AdaptadorDatos.ViewHolderDatos> {
    ArrayList<Restaurante> listDatos;
    final AdaptadorDatos.OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(Restaurante item);

    }
    public AdaptadorDatos(ArrayList<Restaurante> listDatos, AdaptadorDatos.OnItemClickListener listener) {
        this.listDatos = listDatos;
        this.listener=listener;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item,null,false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {

        holder.dato.setText(listDatos.get(position).getNombre());
        holder.info.setText(listDatos.get(position).getInfo());
        holder.foto.setImageResource(listDatos.get(position).getFoto());
        holder.bindData(listDatos.get(position));

    }

    @Override
    public int getItemCount() {
        return listDatos.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView dato,info;
        ImageView foto;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            dato = (TextView) itemView.findViewById(R.id.idDato);
            info = (TextView) itemView.findViewById(R.id.idDescripcion);
            foto = (ImageView) itemView.findViewById(R.id.imageView7);


        }
        void bindData(final Restaurante item){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });

        }
    }
}
