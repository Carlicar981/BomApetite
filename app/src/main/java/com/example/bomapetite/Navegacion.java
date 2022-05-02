package com.example.bomapetite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Navegacion extends AppCompatActivity {
    PrimerFragment primerFragment = new PrimerFragment();
    SegundoFragment segundoFragment = new SegundoFragment();
    TercerFragment tercerFragment = new TercerFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navegacion);
        BottomNavigationView navigationView = findViewById(R.id.botonNavegar);
        navigationView.setOnNavigationItemSelectedListener(mOnnavigation);
        loadFragment(primerFragment);

    }
    private final BottomNavigationView.OnNavigationItemSelectedListener mOnnavigation = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.primera:
                    loadFragment(primerFragment);
                    return true;
                case R.id.segunda:
                    loadFragment(segundoFragment);
                    return true;
                case R.id.tercera:
                    loadFragment(tercerFragment);
                    return true;
            }
            return false;
        }
    };

    public void  loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame,fragment);
        transaction.commit();
    }
}