package com.example.locationmy;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MyGoogleMaps extends AppCompatActivity implements OnMapReadyCallback {
    ImageButton igbCurrentLocation, igbRoad, igbSatellite, igbTerrain;
    Button butOpenStreetView;
    SearchView searchViewLocation;

    SupportMapFragment myGoogleMaps;
    GoogleMap myMap;

    String locationSearchView;
    double Latitude = 0,longitude = 0;
    int selectMode = GoogleMap.MAP_TYPE_NONE;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.googlemaps);
        Init();
        onClick();
        myGoogleMaps.getMapAsync(MyGoogleMaps.this);
    }
    private void Init(){
        myGoogleMaps = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        igbCurrentLocation = (ImageButton) findViewById(R.id.ButCurrentLocation);
        igbRoad = (ImageButton) findViewById(R.id.ButRoad);
        igbSatellite = (ImageButton) findViewById(R.id.ButSatellite);
        igbTerrain = (ImageButton) findViewById(R.id.ButTerrain);
        butOpenStreetView = (Button) findViewById(R.id.ButOpenStreetView);
        searchViewLocation = (SearchView) findViewById(R.id.SearchViewLocation);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        myMap = googleMap;
        LatLng latLng = new LatLng(Latitude,longitude);
        myMap.addMarker(new MarkerOptions().position(latLng).title(locationSearchView));
        myMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.setMapType(selectMode);
    }

    private void onClick(){
        igbCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                Latitude = intent.getDoubleExtra("Latitude",0);
                longitude = intent.getDoubleExtra("Longitude",0);
                onMapReady(myMap);
            }
        });
        searchViewLocation.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                locationSearchView = searchViewLocation.getQuery().toString();
                List<Address> addressList = null;
                if(addressList != null || !locationSearchView.equals("")){
                    Geocoder geocoder = new Geocoder(MyGoogleMaps.this);
                    try{
                        addressList = geocoder.getFromLocationName(locationSearchView, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = addressList.get(0);
                    Latitude = address.getLatitude();
                    longitude = address.getLongitude();
                    onMapReady(myMap);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        butOpenStreetView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentt = new Intent(getApplicationContext(), StreetView.class);
                startActivity(intentt);
            }
        });
        igbRoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectMode = GoogleMap.MAP_TYPE_NORMAL;
                onMapReady(myMap);
            }
        });
        igbTerrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectMode = GoogleMap.MAP_TYPE_TERRAIN;
                onMapReady(myMap);
            }
        });
        igbSatellite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectMode = GoogleMap.MAP_TYPE_SATELLITE;
                onMapReady(myMap);
            }
        });

    }

}
