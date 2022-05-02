package com.example.bomapetite;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrarFragment extends Fragment {
    EditText Enombre,Eapellido,Eemail,Epass;
    Button crearCuenta;
    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.registrar_fragment,container,false);
        Enombre = root.findViewById(R.id.nombre);
        Eapellido = root.findViewById(R.id.apellido);
        Epass = root.findViewById(R.id.passwordRegistrar);
        Eemail = root.findViewById(R.id.emailRegistrar);
        crearCuenta = root.findViewById(R.id.registrar);
        crearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });
        return root;
    }
    public static boolean ValidarMail(String email) {
        // Patron para validar el email
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher mather = pattern.matcher(email);
        return mather.find();
    }
    private void insertData() {

        final String nombre = Enombre.getText().toString().trim();
        final String correo = Eemail.getText().toString().trim();
        final String password = Epass.getText().toString().trim();
        final String apellido = Eapellido.getText().toString().trim();


        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("cargando...");

        if (nombre.isEmpty()) {
            Toast.makeText(getContext(), "ingrese nombre", Toast.LENGTH_SHORT).show();
            return;
        } else if (correo.isEmpty()) {
            Toast.makeText(getContext(), "Ingrese correo", Toast.LENGTH_SHORT).show();
            return;
        } else if (password.isEmpty()) {
            Toast.makeText(getContext(), "Ingrese contraseña", Toast.LENGTH_SHORT).show();
            return;
        } else if (apellido.isEmpty()) {
            Toast.makeText(getContext(), "Ingrese apellido", Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (ValidarMail(correo) == true) {


                progressDialog.show();
                StringRequest request = new StringRequest(Request.Method.POST, "https://stalky-compressors.000webhostapp.com/crud/insertar.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if (response.equalsIgnoreCase("datos insertados")) {
                                    Toast.makeText(getContext(), "datas insertados", Toast.LENGTH_SHORT).show();


                                    progressDialog.dismiss();
                                    startActivity(new Intent(getContext(), MainActivity.class));
                                    sendMail();
                                } else {
                                    Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }

                ) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> params = new HashMap<String, String>();

                        params.put("nombre", nombre);
                        params.put("apellido", apellido);
                        params.put("email", correo);
                        params.put("contraseña", password);

                        return params;
                    }
                };


                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(request);

            } else {
                Toast.makeText(getContext(), "El email NO es valido.", Toast.LENGTH_SHORT).show();
                return;
            }


        }
    }
    private void sendMail() {

        String mail =  Eemail.getText().toString();
        String password = Epass.getText().toString().trim();
        String message = "BON APPETIT te da la bienvenida\n\n" +
                "Gracias por registrarte en BON APPETIT.\n" +
                "Estamos encantados de poder llevarte a casa los platos " +
                "mas exquisitos de tu ciudad.\n\n" +
                "Tus datos:\n" +
                "EMAIL: "+mail+"\n" +
                "CONTRASEÑA: "+password;
        String subject = "BON APÉTTIT";

        //Send Mail
        JavaMailAPI javaMailAPI = new JavaMailAPI(getContext(),mail,subject,message);

        javaMailAPI.execute();

    }

}
