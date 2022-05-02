package com.example.bomapetite;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TercerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TercerFragment extends Fragment {
    EditText nombre,apellido,email,direccion,telefono;
    Button actualizar;
TextView contraseña;
    String url = "https://stalky-compressors.000webhostapp.com/crud/listar.php";
    String url2 = "https://stalky-compressors.000webhostapp.com/crud/actualizar.php";
    Usuarios usuarios;
    private SharedPreferences datosInicioSesion;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TercerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TercerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TercerFragment newInstance(String param1, String param2) {
        TercerFragment fragment = new TercerFragment();
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
        View view = inflater.inflate(R.layout.fragment_tercer, container, false);
        datosInicioSesion = this.getActivity().getSharedPreferences("bonappetit", Context.MODE_PRIVATE);

        nombre=view.findViewById(R.id.nombreEditar);
        apellido=view.findViewById(R.id.apellidoEditar);
        email=view.findViewById(R.id.emailRegistrar);
        contraseña=view.findViewById(R.id.passwordEditar);
        direccion=view.findViewById(R.id.Direcccion);
        telefono=view.findViewById(R.id.numTelefono);

        actualizar=view.findViewById(R.id.actualizar);
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizar();
            }
        });
        retrieveData();

        return view ;
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
                                    String apellido = object.getString("apellido");
                                    String email = object.getString("email");
                                    String contraseña = object.getString("contraseña");
                                    String direccion = object.getString("direccion");
                                    String telefono = object.getString("telefono");


                                    usuarios = new Usuarios(nombre,apellido,email,contraseña,direccion,telefono);
                                  //  employeeArrayList.add(usuarios);
                                   // adapter.notifyDataSetChanged();

                                }

                                nombre.setText(usuarios.getNombre());
                                apellido.setText(usuarios.getApellidos());
                                email.setText(usuarios.getCorreo());
                                contraseña.setText(usuarios.getContraseña());
                                if(!usuarios.getDireccion().equalsIgnoreCase("null")){
                                    direccion.setText(usuarios.getDireccion());
                                    telefono.setText(usuarios.getTelefono());
                                }else{
                                    direccion.setHint("*Direccion Obligatoria");
                                    telefono.setHint("*Telegono Obligatorio");
                                }

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
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                String emailUsuario=datosInicioSesion.getString("email","");
                params.put("email", emailUsuario);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);

    }
    public void actualizar(){

        StringRequest request = new StringRequest(Request.Method.POST, url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.equalsIgnoreCase("datos actualizados")) {
                            Toast.makeText(getContext(), "datos actualizados", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(getContext(), SegundoFragment.class));

                        } else {
                            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                String emailUsuario=datosInicioSesion.getString("email","");
                String direc=direccion.getText().toString();
                String tel=telefono.getText().toString();
                params.put("email", emailUsuario);
                params.put("direccion", direc);
                params.put("telefono", tel);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);

    }
}