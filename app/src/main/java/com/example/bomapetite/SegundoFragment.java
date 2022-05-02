package com.example.bomapetite;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.bomapetite.popular.PopularesAdapter;
import com.example.bomapetite.adapterPlatos.AdaptadorPlatos;

import com.example.bomapetite.popular.Categorias;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SegundoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SegundoFragment extends Fragment implements SearchView.OnQueryTextListener {
    ArrayList<Categorias> localDataSet;
    ArrayList<Platos> listaPlatos;
    ArrayList<Platos> listaCarrito;
    int totalCarrito = 1;
    TextView carrito,numCarrito;
    RecyclerView recycler;
    RecyclerView recyclerPlatos;
    SearchView searchView;
    AdaptadorPlatos adaptadorPlatos;
    PopularesAdapter adapter;
    Button botonCarrito;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SegundoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SegundoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SegundoFragment newInstance(String param1, String param2) {
        SegundoFragment fragment = new SegundoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_segundo, container, false);

        recycler=(RecyclerView) view.findViewById(R.id.categoriasRecycler);
        recyclerPlatos=(RecyclerView) view.findViewById(R.id.recyclerPlatos2);
        recycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recyclerPlatos.setLayoutManager(new LinearLayoutManager(getContext()));
        listaPlatos = new ArrayList<Platos>();
        localDataSet = new ArrayList<Categorias>();
        llenar();
        llenarPlatos();
        botonCarrito = view.findViewById(R.id.botonCarrito);
        carrito = view.findViewById(R.id.buttonCheckout);
        carrito.setVisibility(View.INVISIBLE);
        numCarrito = view.findViewById(R.id.numCarrito);
        numCarrito.setVisibility(View.INVISIBLE);
        adapter = new PopularesAdapter(localDataSet, new PopularesAdapter.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(Categorias item) {
                adaptadorPlatos.filtrarCategoria(item.getTitle().toLowerCase());
               /*
                listaPlatosFiltar.addAll(listaPlatos);
                List<Platos> coleccion = listaPlatosFiltar.stream().filter(i -> i.getCategoria().toLowerCase()
                        .contains(item.getTitle().toLowerCase())).collect(Collectors.toList());
                listaPlatosFiltar.clear();
                listaPlatosFiltar.addAll(coleccion);

                */

            }
        });

        adaptadorPlatos = new AdaptadorPlatos(listaPlatos, new AdaptadorPlatos.OnItemClickListener() {
                @Override
                public void agregar(Platos item) {
                    numCarrito.setVisibility(View.VISIBLE);
                    carrito.setVisibility(View.VISIBLE);
                    if (listaCarrito == null) {
                        listaCarrito = new ArrayList<>();
                    }
                    listaCarrito.add(item);
                    totalCarrito = 0;

                    for (Platos m : listaCarrito) {
                        totalCarrito = totalCarrito + m.getTotalCarrito();
                    }

                    carrito.setText("Carrito (" + totalCarrito + ") platos");
                    numCarrito.setText(""+totalCarrito);
                    if (totalCarrito<=0){
                        numCarrito.setVisibility(View.INVISIBLE);
                        carrito.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void actualizar(Platos item) {
                    if (listaCarrito.contains(item)) {
                        int index = listaCarrito.indexOf(item);
                        listaCarrito.remove(index);
                        listaCarrito.add(index, item);
                        if(totalCarrito<=10){
                            listaCarrito.get(index).setCantidad((int) (listaCarrito.get(index).obtenerCantidad()+1));
                        }
                        totalCarrito = 0;

                        for (Platos m : listaCarrito) {
                            totalCarrito = totalCarrito + m.getTotalCarrito();
                        }
                        carrito.setText("Carrito (" + totalCarrito + ") platos");
                        numCarrito.setText(""+totalCarrito);
                    }
                    if (totalCarrito<=0){
                        numCarrito.setVisibility(View.INVISIBLE);
                        carrito.setVisibility(View.INVISIBLE);

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
                        carrito.setText("Carrito (" + totalCarrito + ") platos");
                        numCarrito.setText(""+totalCarrito);
                    }
                    if (totalCarrito<=0){
                        numCarrito.setVisibility(View.INVISIBLE);
                        carrito.setVisibility(View.INVISIBLE);
                    }
                }
            });

        recycler.setAdapter(adapter);
        recyclerPlatos.setAdapter(adaptadorPlatos);
        searchView=view.findViewById(R.id.buscar);
        searchView.setOnQueryTextListener(this);

        carrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),Carrito.class);
                intent.putExtra("carrito",listaCarrito);
                startActivity(intent);

            }
        });

        botonCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),Carrito.class);
                intent.putExtra("carrito",listaCarrito);
                startActivity(intent);
            }
        });
        return view;
    }


    private void llenar() {
        localDataSet.add(new Categorias("PIZZA",R.drawable.ic_baseline_local_pizza_24));
        localDataSet.add(new Categorias("BURGUER",R.drawable.ic_baseline_lunch_dining_24));
        localDataSet.add(new Categorias("KEBAB",R.drawable.ic_baseline_kebab_dining_24));
        localDataSet.add(new Categorias("CHINO",R.drawable.ic_baseline_ramen_dining_24));
        localDataSet.add(new Categorias("CAFE",R.drawable.ic_baseline_local_cafe_24));

    }
    private void llenarPlatos() {
        listaPlatos.add(new Platos("Pizza Hawaiana"," Tomate\nMozarela\n" +
                "  Jamón york\n  Piña",
                R.drawable.pizzahawaiana,"Pizzeria Carlos","pizza",1, 12.5f,1));
        listaPlatos.add(new Platos("Pizza Peperoni"," Tomate\n" +
                "  Mozarela\n  Peperoni",
                R.drawable.peperoni,"Pizzeria Carlos","pizza",1, 9.5f,1));
        listaPlatos.add(new Platos("Whopper","  Lechuga\nTomate\n" +
                "  Pepinillo\n  Carne 125gr",
                R.drawable.whopper,"Burger King","burguer",1, 12f,1));
        listaPlatos.add(new Platos("Barbacoa","  Salsa barbacoa\nMozarela\n" +
                "  Carne picada\n  Bacon",
                R.drawable.bbq,"Telepizza","pizza",1, 12f,1));
        listaPlatos.add(new Platos("Durum Mixto","  Ternera\nPollo\n" +
                "Lechuga\n  Salsa roja y blanca",
                R.drawable.durum,"King Doner","kebab",1, 5f,1));
        listaPlatos.add(new Platos("Batido Nutella Kitkat","  Leche\n Nata\n" +
                "  Nutella\n  Kitkat",
                R.drawable.batido,"Coffe Charger","cafe",1, 5f,1));
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.filtrar(newText);
        adaptadorPlatos.filtrar(newText);
        return false;
    }


}