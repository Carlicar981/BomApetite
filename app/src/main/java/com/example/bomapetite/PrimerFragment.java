package com.example.bomapetite;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bomapetite.adapterRestaurante.AdaptadorDatos;
import com.example.bomapetite.adapterRestaurante.Restaurante;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PrimerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrimerFragment extends Fragment {
    ArrayList<Restaurante> listDatos;
    ArrayList<Platos> listPlatos;
    ArrayList<Platos> listPlatosEnviar;
    RecyclerView recycler;
    Restaurante restaurante;
    String url = "https://stalky-compressors.000webhostapp.com/crud/restaurantes.php";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PrimerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PrimerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PrimerFragment newInstance(String param1, String param2) {
        PrimerFragment fragment = new PrimerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrieveData();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_primer, container, false);
        // Inflate the layout for this fragment

        recycler = (RecyclerView) view.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        listDatos = new ArrayList<Restaurante>();
        listPlatos = new ArrayList<Platos>();
        listPlatosEnviar = new ArrayList<Platos>();
        //llenarRestaurantes();

        System.out.println(R.drawable.poke);
        llenarPlatos();

        return view;
    }

    private void mostrarDescripcion(Restaurante item) {
        Intent intent = new Intent(getContext(), DescripcionActivity.class);
        intent.putExtra("Restaurante",item);
        for (int i = 0;i<listPlatos.size();i++){
            if (item.getNombre().equalsIgnoreCase(listPlatos.get(i).getTipo())){
                listPlatosEnviar.add(listPlatos.get(i));
            }
        }
        intent.putExtra("Platos",listPlatosEnviar);
        startActivity(intent);
        listPlatosEnviar.clear();
    }

    private void llenarRestaurantes() {
        listDatos.add(new Restaurante("Pizzeria Carlos","  C/Juan de Valladolid\n" +
                "  Tel: 983 32 45 56\n  pizzeria.carlos@gmail.com\n  Horario: 12:00 - 23:00",
                "descipcion",R.drawable.pizzahawaiana));
        listDatos.add(new Restaurante("Restaurante 2","  adsadasdadasdadadad\n  " +
                "aadsadasdadasdadadadaa\n  dsadasdadasdadadada\n  dsadasdadasdadadada",
                "descipcion",R.drawable.poke));
        listDatos.add(new Restaurante("Restaurante 3","  adsadasdadasdadadad\n  " +
                "aadsadasdadasdadadadaa\n  dsadasdadasdadadada\n  dsadasdadasdadadada",
                "descipcion",R.drawable.poke));
        listDatos.add(new Restaurante("Restaurante 4","  adsadasdadasdadadad\n  " +
                "aadsadasdadasdadadadaa\n  dsadasdadasdadadada\n  dsadasdadasdadadada",
                "descipcion",R.drawable.poke));
        listDatos.add(new Restaurante("Restaurante 5","  adsadasdadasdadadad\n  " +
                "aadsadasdadasdadadadaa\n  dsadasdadasdadadada\n  dsadasdadasdadadada",
                "descipcion",R.drawable.poke));
        listDatos.add(new Restaurante("Restaurante 6","  adsadasdadasdadadad\n  " +
                "aadsadasdadasdadadadaa\n  dsadasdadasdadadada\n  dsadasdadasdadadada",
                "descipcion",R.drawable.poke));
    }


    private void llenarPlatos() {
        listPlatos.add(new Platos("Pizza Hawaiana","  Tomate\n Mozarela\n" +
                "  Jamón york\n  Piña",
                R.drawable.pizzahawaiana,"Pizzeria Carlos","pizza",1, 12.5f,1));
        listPlatos.add(new Platos("Pizza Peperoni"," Tomate\n" +
                "  Mozarela\n  Peperoni",
                R.drawable.peperoni,"Pizzeria Carlos","pizza",1, 9.5f,1));
        listPlatos.add(new Platos("Whopper","  Lechuga\n Tomate\n" +
                "  Pepinillo\n  Carne 125gr",
                R.drawable.whopper,"Burger King ","burguer",1, 5f,1));
        listPlatos.add(new Platos("Barbacoa","  Salsa barbacoa\n Mozarela\n" +
                "  Carne picada\n  Bacon",
                R.drawable.bbq,"Telepizza","pizza",1, 12f,1));
        listPlatos.add(new Platos("Durum Mixto","  Ternera\n Pollo\n" +
                "  Lechuga\n  Salsa roja y blanca",
                R.drawable.durum,"King Doner","kebab",1, 5f,1));
        listPlatos.add(new Platos("Batido Nutella Kitkat","  Leche\n Nata\n" +
                "  Nutella\n  Kitkat",
                R.drawable.batido,"Coffe Charger","cafe",1, 5f,1));


    }
    public void retrieveData(){

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // employeeArrayList.clear();
                        try{

                            JSONObject jsonObject = new JSONObject(response);
                            String exito = jsonObject.getString("exito");
                            JSONArray jsonArray = jsonObject.getJSONArray("datos");

                            if(exito.equals("1")){


                                for(int i=0;i<jsonArray.length();i++){

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String nombre = object.getString("nombre");
                                    String info = object.getString("info");
                                    String descripcion = object.getString("descripcion");
                                    int foto = object.getInt("foto");


                                    restaurante = new Restaurante(nombre,info+"\n",descripcion,foto);
                                    listDatos.add(restaurante);
                                }

                                AdaptadorDatos adapter = new AdaptadorDatos(listDatos, new AdaptadorDatos.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(Restaurante item) {
                                        mostrarDescripcion(item);
                                    }
                                });
                                recycler.setAdapter(adapter);
                            }

                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);

    }
}