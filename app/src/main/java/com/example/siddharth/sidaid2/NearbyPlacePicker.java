package com.example.siddharth.sidaid2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import java.util.Locale;

import br.com.bloder.magic.view.MagicButton;

public class NearbyPlacePicker extends AppCompatActivity {
    int PLACE_PICKER_REQUEST = 1;
    TextView name1;
    TextView address1;
    MagicButton gobtn;
    MagicButton reselect;
    double lat1=0.0;
    double lon2=0.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_place_picker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        name1= (TextView) findViewById(R.id.name);
        address1= (TextView) findViewById(R.id.address);
        gobtn= (MagicButton) findViewById(R.id.go);
        reselect= (MagicButton) findViewById(R.id.reselect);
        PlacePicker.IntentBuilder intentBuilder =
                new PlacePicker.IntentBuilder();
        Intent intent = null;
        try {
            intent = intentBuilder.build(NearbyPlacePicker.this);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
        startActivityForResult(intent, PLACE_PICKER_REQUEST);

        final Intent finalIntent = intent;
        reselect.setMagicButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(finalIntent,PLACE_PICKER_REQUEST);
            }
        });
        gobtn.setMagicButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr="+lat1+","+lon2));
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {

        if (requestCode == PLACE_PICKER_REQUEST
                && resultCode == Activity.RESULT_OK) {

            final Place place = PlacePicker.getPlace(this, data);
            final CharSequence name = place.getName();
            final CharSequence address = place.getAddress();
          final LatLng l=place.getLatLng();
            lat1=l.latitude;
            lon2=l.longitude;
            String attributions = (String) place.getAttributions();
            name1.setText(""+name);
            address1.setText(""+address);

            if (attributions == null) {
                attributions = "";
            }



        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    }


