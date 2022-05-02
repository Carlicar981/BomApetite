package com.example.bomapetite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {

    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
//Conexion conexion = new Conexion();
//conexion.main();

    }

}