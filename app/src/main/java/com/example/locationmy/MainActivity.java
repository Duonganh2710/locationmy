package com.example.locationmy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements LocationListener {

    TextView textViewLocation;
    Button butGetLocation ,butOpenGoogleMaps;
    private static final int REQUEST_PERMISSION = 5000;
    private LocationManager myLocationManager;
    private boolean openGPS;
    private Location myCurrentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Init();
        butGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCurrentLocation = GetCurrentLocation();
                if(myCurrentLocation != null){
                textViewLocation.setText(myCurrentLocation.getLatitude() + " " +myCurrentLocation.getLongitude());
                }
            }
        });
        butOpenGoogleMaps.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intentt = new Intent(MainActivity.this ,MyGoogleMaps.class);
                 if(myCurrentLocation == null)
                     Toast.makeText(MainActivity.this ,"dkfs" ,Toast.LENGTH_LONG).show();
                 intentt.putExtra("Latitude",myCurrentLocation.getLatitude());
                 intentt.putExtra("Longitude",myCurrentLocation.getLongitude());
                 startActivity(intentt);
             }
         });
    }

    private void Init(){
        textViewLocation = (TextView) findViewById(R.id.tv_current_location);
        butGetLocation = (Button) findViewById(R.id.butGetLocation);
        butOpenGoogleMaps = (Button) findViewById(R.id.butOpenGoogleMap);
    }
    public Location GetCurrentLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION);
        }
        myLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        openGPS = myLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(openGPS){
            myLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER ,0,0 ,this);
            Location mlocation = myLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            return mlocation;
        }else
            textViewLocation.setText("You need open GPS");
        return null;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_PERMISSION:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                }else
                    RequestPermisstion();
                break;
            default:
                break;
        }
    }
    private void RequestPermisstion(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSION);
        }
    }
    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}


