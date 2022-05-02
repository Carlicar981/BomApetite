package com.example.bomapetite;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class LoginFragment extends Fragment implements View.OnClickListener {
EditText email,pass;
TextView olvidarPass;
Button login;
String str_email,str_password;
String url = "https://stalky-compressors.000webhostapp.com/crud/login.php";
private SharedPreferences datosInicioSesion;
float v=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_fragment,container,false);

        email= root.findViewById(R.id.email);
        olvidarPass= root.findViewById(R.id.olvidarPass);
        pass= root.findViewById(R.id.password);
        login= root.findViewById(R.id.entrar);

        datosInicioSesion = this.getActivity().getSharedPreferences("bonappetit", Context.MODE_PRIVATE);
        email.setText(datosInicioSesion.getString("email",""));
        pass.setText(datosInicioSesion.getString("password",""));

        email.setTranslationX(800);
        pass.setTranslationX(800);
        olvidarPass.setTranslationX(800);
        login.setTranslationX(800);

        email.setAlpha(v);
        pass.setAlpha(v);
        olvidarPass.setAlpha(v);
        login.setAlpha(v);

        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        pass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        olvidarPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        login.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(v);
            }
        });
        return root;

    }
    public void login(View view) {

        if(email.getText().toString().equals("")){
            Toast.makeText(getContext(), "Enter Email", Toast.LENGTH_SHORT).show();
        }
        else if(pass.getText().toString().equals("")){
            Toast.makeText(getContext(), "Enter Password", Toast.LENGTH_SHORT).show();
        }
        else{


            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Por favor espera...");

            progressDialog.show();

            str_email = email.getText().toString().trim();
            str_password = pass.getText().toString().trim();


            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();

                    if(response.equalsIgnoreCase("ingresaste correctamente")){

                        email.setText("");
                        pass.setText("");
                        startActivity(new Intent(getContext(),Navegacion.class));
                        Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();

                        SharedPreferences.Editor edit = datosInicioSesion.edit();
                        edit.putString("email",str_email);
                        edit.putString("password",str_password);
                        edit.commit();
                    }
                    else{
                        Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                    }

                }
            },new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("email",str_email);
                    params.put("contraseña",str_password);
                    return params;

                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(request);




        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.entrar:
                Intent open2 =new Intent(getActivity(),Navegacion.class);
                startActivity(open2);

                break;

        }
    }
}