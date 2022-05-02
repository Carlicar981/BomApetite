package com.example.bomapetite;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.bomapetite.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng pizzaCarlos = new LatLng(41.6239823, -4.7752409);
        LatLng burguerking = new LatLng(41.6318908, -4.7587393);
        LatLng telepizza = new LatLng(41.6239819, -4.7818338);
        LatLng coffe = new LatLng(41.6425582, -4.7625101);
        LatLng kingdoner = new LatLng(41.6343269,-4.749196);

        mMap.addMarker(new MarkerOptions().position(pizzaCarlos)
                .title("Pizzeria Carlos")
                .draggable(true)
                .snippet("Tlf: 621312311"));
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.addMarker(new MarkerOptions().position(burguerking)
                .title("Burguer king")
                .draggable(true)
                .snippet("Tlf: 62423221")
        );
        mMap.addMarker(new MarkerOptions().position(telepizza)
                .title("Telepizza")
                .draggable(true)
                .snippet("Tlf: 60800022")
        );
        mMap.addMarker(new MarkerOptions().position(coffe)
                .title("Coffe Charguer")
                .draggable(true)
                .snippet("Tlf: 62390403")
        );
        mMap.addMarker(new MarkerOptions().position(kingdoner)
                .title("King doner")
                .draggable(true)
                .snippet("Tlf: 64829824")
        );

        //Cambia el tipo de visibilidad
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(pizzaCarlos));
    }
}