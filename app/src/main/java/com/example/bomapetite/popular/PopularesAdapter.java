package com.example.bomapetite.popular;

import com.example.bomapetite.R;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PopularesAdapter extends RecyclerView.Adapter<PopularesAdapter.ViewHolder> {

    ArrayList<Categorias> localDataSet;

    ArrayList<Categorias> original;
    PopularesAdapter.OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(Categorias item);
    }
    public PopularesAdapter(ArrayList<Categorias> localDataSet,PopularesAdapter.OnItemClickListener listener) {
        this.localDataSet = localDataSet;

        original=new ArrayList<>();
        original.addAll(localDataSet);
        this.listener=listener;
    }
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nombreCategoria;
        ImageView imageView;
        ConstraintLayout constraintLayout;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            nombreCategoria=view.findViewById(R.id.nombreCategoria);
            imageView=view.findViewById(R.id.imageView3);
            constraintLayout=view.findViewById(R.id.mainCategoria);

        }
        public void bindData(final Categorias item){
            imageView.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View v) {
                   listener.onItemClick(item);
                }
            });

        }


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void filtrar(String txtBuscar){
        int longitud = txtBuscar.length();
            if (longitud==0){
                localDataSet.clear();
                localDataSet.addAll(original);
            }else{
                List<Categorias> coleccion = localDataSet.stream().filter(i -> i.getTitle().toLowerCase()
                .contains(txtBuscar.toLowerCase())).collect(Collectors.toList());
                localDataSet.clear();
                localDataSet.addAll(coleccion);
            }
            notifyDataSetChanged();
    }


    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.viewholder, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        viewHolder.nombreCategoria.setText(localDataSet.get(position).getTitle());
        viewHolder.imageView.setImageResource(localDataSet.get(position).getImgProduct());
        viewHolder.bindData(localDataSet.get(position));


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}

