package com.example.bomapetite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bomapetite.Config.Config;
import com.example.bomapetite.adapter.AdaptadorCarrito;
import com.example.bomapetite.adapterPlatos.AdaptadorPlatos;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Carrito extends AppCompatActivity {
    TextView titulo, botonCheck, textCarrito;
    RecyclerView recycler;
    ArrayList<Platos> listaCarrito;
    int totalCarrito = 0;
    float precio = 0;
    private SharedPreferences datosInicioSesion;

    private static final int PAYPAL_CODE = 7171;
    private static PayPalConfiguration config = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAYPAL_CLIENT_ID);
    ArrayList<Platos> lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        datosInicioSesion = Carrito.this.getSharedPreferences("bonappetit", Context.MODE_PRIVATE);
        setContentView(R.layout.activity_carrito);
        textCarrito = findViewById(R.id.textCarrito);
        lista = (ArrayList<Platos>) getIntent().getSerializableExtra("carrito");
        if (lista == null || lista.size() < 0) {
            textCarrito.setText("NO HAY PRODUCTOS EN EL CARRITO");
        } else {
            textCarrito.setVisibility(View.INVISIBLE);
            titulo = findViewById(R.id.carrito);
            botonCheck = findViewById(R.id.buttonCheckout3);
            recycler = (RecyclerView) findViewById(R.id.recyclerCarrito);
            recycler.setLayoutManager(new LinearLayoutManager(this));
            AdaptadorCarrito adaptadorPlatos = new AdaptadorCarrito(lista);
            recycler.setAdapter(adaptadorPlatos);

            for (int i = 0; i < lista.size(); i++) {
                precio += lista.get(i).precio();

            }
            botonCheck.setText("PAGAR " + (precio+4) + " €");
            botonCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < lista.size(); i++) {
                        insertData(lista.get(i).getNombre(),lista.get(i).getCantidad());
                    }


                }
            });
        }

    }
    private void insertData(String plato, String cantidad) {
        String emailUsuario=datosInicioSesion.getString("email","");
        SimpleDateFormat formatter   =   new   SimpleDateFormat   ("HH:mm:ss");
        Date curDate =  new Date(System.currentTimeMillis());
        // Obtener la hora actual
        String   str   = String.valueOf(System.currentTimeMillis());
        System.out.println(str);
                StringRequest request = new StringRequest(Request.Method.POST, "https://stalky-compressors.000webhostapp.com/crud/carrito.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if (response.equalsIgnoreCase("datos insertados")) {
                                    //Toast.makeText(Carrito.this, "datos insertados", Toast.LENGTH_SHORT).show();

                                        startActivity(new Intent(Carrito.this, Navegacion.class));
                                        procesarpago();
                                        sendMail();
                                } else {
                                    Toast.makeText(Carrito.this, response, Toast.LENGTH_SHORT).show();

                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Carrito.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }

                ) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> params = new HashMap<String, String>();

                            params.put("email", emailUsuario);
                            params.put("fecha", str);
                            params.put("plato", plato);
                            params.put("cantidad", cantidad);

                            return params;

                    }
                };


                RequestQueue requestQueue = Volley.newRequestQueue(Carrito.this);
                requestQueue.add(request);
            }

    private void sendMail() {
        String platos = "";
        for (int i = 0; i < lista.size(); i++) {
        platos+=" "+lista.get(i).getNombre()+" "+lista.get(i).getCantidad();
        };
        String mail=datosInicioSesion.getString("email","");
        String message = "BON APPETIT te da las Gracias\n\n" +
                " Enhorabuena tu pedido esta en camino.\n" +
                " Pedido:\n"+platos;

        String subject = "BON APÉTTIT";

        //Send Mail
        JavaMailAPI javaMailAPI = new JavaMailAPI(Carrito.this,mail,subject,message);

        javaMailAPI.execute();

    }
    private void procesarpago() {

        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(precio)), "EUR", "RESERVAS DEPORTIVAS",
                PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        startActivityForResult(intent, PAYPAL_CODE);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYPAL_CODE) {
            if (resultCode == RESULT_OK) {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null) {
                    try {
                        String paymentDetails = confirmation.toJSONObject().toString(4);
                        startActivity(new Intent(this, PrimerFragment.class).putExtra("PaymentDetails", paymentDetails)
                                .putExtra("PaymentAmount", precio).putExtra("IdVendedor", Config.PAYPAL_CLIENT_ID));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


            }
        }
    }
}