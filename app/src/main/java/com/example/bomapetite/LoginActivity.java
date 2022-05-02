package com.example.bomapetite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;


public class LoginActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    FloatingActionButton google,facebook,twitter;
    float v=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        System.out.println(R.drawable.pizzacarlos);
        tabLayout= findViewById(R.id.tablaLayout);
        viewPager= findViewById(R.id.view_pager);
        google= findViewById(R.id.google);
        facebook= findViewById(R.id.facebook);
        twitter= findViewById(R.id.twitter);

        tabLayout.setTabTextColors(Color.parseColor("#FF000000"),Color.parseColor("#35481E"));

        tabLayout.addTab(tabLayout.newTab().setText("Entrar"));
        tabLayout.addTab(tabLayout.newTab().setText("Registrar"));
        tabLayout.setTabGravity(tabLayout.GRAVITY_FILL);

        final LoginAdapter adapter = new LoginAdapter(getSupportFragmentManager(),this,tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        facebook.setTranslationY(300);
        google.setTranslationY(300);
        twitter.setTranslationY(300);
        tabLayout.setTranslationY(300);

        facebook.setAlpha(v);
        google.setAlpha(v);
        twitter.setAlpha(v);
        tabLayout.setAlpha(v);

        facebook.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        google.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        twitter.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(800).start();
        tabLayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/carlicar98"));
              startActivity(intent);
            }
        });
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/carlostg.gomez"));
                startActivity(intent);
            }
        });

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","bonappetit.tfg@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Android APP - BON APETTIT ");
                startActivity(Intent.createChooser(emailIntent,  "HOLA"));
            }
        });
    }
}