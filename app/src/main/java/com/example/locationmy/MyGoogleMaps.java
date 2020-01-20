package com.example.locationmy;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
public class MyGoogleMaps extends AppCompatActivity implements OnMapReadyCallback {
    SupportMapFragment myGoogleMaps;
    Location myCurrentLocation;
    GoogleMap myMap;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.googlemaps);
        Init();
        myCurrentLocation = new MainActivity().myCurrentLocation;
        myGoogleMaps.getMapAsync(this);

    }
    private void Init(){
        myGoogleMaps = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        myMap = googleMap;
        LatLng latLng = new LatLng(myCurrentLocation.getLatitude(), myCurrentLocation.getLongitude());
        myMap.addMarker(new MarkerOptions().position(latLng).title("Current Location"));
        myMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }
}
