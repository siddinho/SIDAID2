package com.example.siddharth.sidaid2;

import android.*;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import br.com.bloder.magic.view.MagicButton;

public class Emergency extends AppCompatActivity implements OnMapReadyCallback, LocationListener {
    LocationManager locationManager;
    double currentLat;
    double currentLong;
    TextView locationtxt;
    float zoomLevel = (float) 16.0;
    MagicButton Emergencybtn1;
    String EmergencyLocation;
    String Emergencymsg;
    String mno1;
    String mno2;
    String mno3;
    String finalmessage;
    private GoogleMap mMap;
    //Location location;

    //Zoom + Curent Location on map added
    //Check for auto-updation.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // my code


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Emergency.this, EditDetails.class);
                startActivity(i);

            }
        });

//Getting Map Fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationtxt = (TextView) findViewById(R.id.locationtxt);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //Marshmellow Permission Check
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "GPS is Enabled in your device\n\nFetching Current Co-ordinates Please Wait", Toast.LENGTH_LONG).show();
        } else {
            showGPSDisabledAlertToUser();
        }

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        //Getting Emergency Button
        Emergencybtn1 = (MagicButton) findViewById(R.id.emergency1);
        Emergencybtn1.setMagicButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Emergency.this, "Clicked", Toast.LENGTH_LONG).show();
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Toast.makeText(Emergency.this, "GPS is Enabled in your device\n\nFetching Current Co-ordinates Please Wait", Toast.LENGTH_SHORT).show();
                } else {
                    showGPSDisabledAlertToUser();
                    EmergencyLocation = "";
                }
                try {
                    final SQLiteDatabase mydatabase = openOrCreateDatabase("Database", MODE_PRIVATE, null);
                    Cursor resultSet = mydatabase.rawQuery("Select *  from data", null);
                    if (resultSet.getCount() != 0) {
                        resultSet.moveToFirst();
                        Emergencymsg = resultSet.getString(0);
                        mno1 = resultSet.getString(1);
                        mno2 = resultSet.getString(2);
                        mno3 = resultSet.getString(3);
                        finalmessage = Emergencymsg + "\n\nhttp://www.google.com/maps/place/" + currentLat + "," + currentLong + "\nSent Via Pocket Aid ";

//Toast .shortened
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(mno1, null, finalmessage, null, null);
                        Toast.makeText(Emergency.this, "First Msg sent", Toast.LENGTH_SHORT).show();
                        smsManager.sendTextMessage(mno2, null, finalmessage, null, null);
                        Toast.makeText(Emergency.this, "Second Msg sent", Toast.LENGTH_SHORT).show();
                        smsManager.sendTextMessage(mno3, null, finalmessage, null, null);
                        Toast.makeText(Emergency.this, "Third Msg sent", Toast.LENGTH_SHORT).show();
                        /// New Code
                        Toast.makeText(Emergency.this,"Done",Toast.LENGTH_LONG).show();

                        //  Toast.makeText()
                    } else {
                        Toast.makeText(Emergency.this, "Please Enter Guardian Details First", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(Emergency.this, EditDetails.class);
                        startActivity(i);
                    }

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }


            }
        });
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device.
         * This method will only be triggered once the user has installed
         Google Play services and returned to the app.
         */


    }

    //Starting Map fragment
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        onMapReady1(mMap);


       /* if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

        // Add a marker in Sydney and move the camera
        LatLng currentcoordinates = new LatLng(currentLat,currentLong);
        mMap.addMarker(new
                MarkerOptions().position(currentcoordinates).title("You Are Here"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentcoordinates));
*/
    }

    //To get current Location
    @Override
    public void onLocationChanged(Location location) {
        currentLat = location.getLatitude();
        currentLong = location.getLongitude();
        locationtxt.setText("Your Co-rdinates Are\n" + currentLat + "," + currentLong);
        onMapReady1(mMap);// for recalling purpose
        //onMapReady(googlemap);
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

    public void onMapReady1(GoogleMap googleMap) {
        mMap = googleMap;


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

        // Add a marker in Sydney and move the camera
        LatLng currentcoordinates = new LatLng(currentLat, currentLong);

        //This goes up to 21
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel))

        mMap.addMarker(new
                MarkerOptions().position(currentcoordinates).title("You Are Here"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentcoordinates, zoomLevel));

    }


    //Incase GPS is OFF

    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To\n\n Enable GPS",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

}
