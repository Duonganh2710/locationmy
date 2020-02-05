package com.example.locationmy;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;
import com.google.android.gms.maps.model.StreetViewPanoramaOrientation;
import com.google.android.gms.maps.model.StreetViewSource;

public class StreetView extends AppCompatActivity implements OnStreetViewPanoramaReadyCallback {
    private ImageButton igbPreviousMyGoogleMapsClass;
    private SupportStreetViewPanoramaFragment streetViewFragment;

    private StreetViewPanorama myStreetViewPanorama;
    private boolean secondLocation = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.streetview);
        Init();
        streetViewFragment.getStreetViewPanoramaAsync(StreetView.this);
        igbPreviousMyGoogleMapsClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StreetView.this, MyGoogleMaps.class);
                startActivity(intent);
            }
        });
    }
    private void Init(){
        streetViewFragment = (SupportStreetViewPanoramaFragment) getSupportFragmentManager()
                .findFragmentById(R.id.layoutStreetView);
        igbPreviousMyGoogleMapsClass = (ImageButton) findViewById(R.id.PreviusIcon);
    }


    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
        myStreetViewPanorama = streetViewPanorama;
        if (secondLocation){
            streetViewPanorama.setPosition(new LatLng(51.52887, -0.1726073), StreetViewSource.OUTDOOR);
        }else {
            streetViewPanorama.setPosition(new LatLng(51.52887, -0.1726073));
        }
        streetViewPanorama.setStreetNamesEnabled(true);
        streetViewPanorama.setPanningGesturesEnabled(true);
        streetViewPanorama.setZoomGesturesEnabled(true);
        streetViewPanorama.setUserNavigationEnabled(true);
        streetViewPanorama.animateTo(
                new StreetViewPanoramaCamera.Builder().orientation(new StreetViewPanoramaOrientation(20 ,20))
                .zoom(streetViewPanorama.getPanoramaCamera().zoom)
                .build(), 200);
        streetViewPanorama.setOnStreetViewPanoramaCameraChangeListener
                ((StreetViewPanorama.OnStreetViewPanoramaCameraChangeListener) panoramaChangeListener);

    }
    private StreetViewPanorama.OnStreetViewPanoramaChangeListener panoramaChangeListener =
            new StreetViewPanorama.OnStreetViewPanoramaChangeListener() {
                @Override
                public void onStreetViewPanoramaChange(
                        StreetViewPanoramaLocation streetViewPanoramaLocation) {


                    Toast.makeText(getApplicationContext(), "Lat: " + streetViewPanoramaLocation.position.latitude + " Lng: " + streetViewPanoramaLocation.position.longitude, Toast.LENGTH_SHORT).show();

                }
            };

}
