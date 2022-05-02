package com.example.bomapetite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bomapetite.MapsActivity;
import com.example.bomapetite.Platos;
import com.example.bomapetite.R;
import com.example.bomapetite.adapterPlatos.AdaptadorPlatos;
import com.example.bomapetite.adapterRestaurante.Restaurante;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DescripcionActivity extends AppCompatActivity {
TextView tituloDescripcion,tituloRestaurante,botonCheck;
ImageView imageView;
Button mapa;
RecyclerView recycler;
ArrayList<Platos> listaCarrito;
int totalCarrito = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion);

        ArrayList<Platos> lista=(ArrayList<Platos>) getIntent().getSerializableExtra("Platos");
       // System.out.println(lista.get(0).getNombre()+" "+lista.size());
        Restaurante restaurante = (Restaurante) getIntent().getSerializableExtra("Restaurante");
        tituloDescripcion=findViewById(R.id.tituloDescripcion);
        tituloRestaurante=findViewById(R.id.tituloRestaurante);
        mapa=findViewById(R.id.descripcionRestaurante);
        botonCheck=findViewById(R.id.buttonCheckout2);
        imageView=findViewById(R.id.imageView4);
        botonCheck.setVisibility(View.INVISIBLE);
        tituloRestaurante.setText(restaurante.getNombre());
        tituloDescripcion.setText(restaurante.getDescipcion());

       mapa.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(DescripcionActivity.this, MapsActivity.class);
               startActivity(intent);
           }
       });

       imageView.setImageResource(restaurante.getFoto());

       recycler=(RecyclerView) findViewById(R.id.recyclerPlatos);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        AdaptadorPlatos adaptadorPlatos = new AdaptadorPlatos(lista, new AdaptadorPlatos.OnItemClickListener() {

            public void agregar(Platos item) {
                botonCheck.setVisibility(View.VISIBLE);
                if (listaCarrito == null) {
                    listaCarrito = new ArrayList<>();
                }
                listaCarrito.add(item);
                totalCarrito = 0;

                for (Platos m : listaCarrito) {
                    totalCarrito = totalCarrito + m.getTotalCarrito();
                }
                botonCheck.setText("Carrito (" + totalCarrito + ") platos");
            }

            @Override
            public void actualizar(Platos item) {
                if (listaCarrito.contains(item)) {
                    int index = listaCarrito.indexOf(item);
                    listaCarrito.remove(index);
                    listaCarrito.add(index, item);
                    listaCarrito.get(index).setCantidad((int) (listaCarrito.get(index).obtenerCantidad()+1));
                    totalCarrito = 0;

                    for (Platos m : listaCarrito) {
                        totalCarrito = totalCarrito + m.getTotalCarrito();
                    }
                    botonCheck.setText("Carrito (" + totalCarrito + ") platos");
                }
            }

            @Override
            public void borrar(Platos item) {
                int index = listaCarrito.indexOf(item);
                listaCarrito.get(index).setCantidad((int) (listaCarrito.get(index).obtenerCantidad()-listaCarrito.get(index).obtenerCantidad())+1);

                if (listaCarrito.contains(item)) {
                    listaCarrito.remove(item);
                    totalCarrito = 0;

                    for (Platos m : listaCarrito) {
                        totalCarrito = totalCarrito - m.getTotalCarrito();
                    }
                    //botonCheck.setText("Carrito (" + totalCarrito + ") platos");
                    if (totalCarrito<=0){

                        botonCheck.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
        recycler.setAdapter(adaptadorPlatos);

        botonCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Carrito.class);
                intent.putExtra("carrito",listaCarrito);
                startActivity(intent);

            }
        });
    }
}