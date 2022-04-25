package uk.ac.tees.aad.B1212361;


import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MedicalEmergency extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap googleMapScreen;
    EditText message ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_emergency);

        message = findViewById(R.id.messageText);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.googleMap);
        mapFragment.getMapAsync(this);

        findViewById(R.id.button_emer_medical).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                intent.putExtra("latitude",getIntent().getDoubleExtra("latitudeValue",0));
                intent.putExtra("longitude",getIntent().getDoubleExtra("longitudeValue",0));
                startActivity(intent);

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMapScreen = googleMap;

        LatLng currentPosition = new LatLng(getIntent().getDoubleExtra("latitudeValue",0), getIntent().getDoubleExtra("longitudeValue",0));

        googleMapScreen.addMarker(new MarkerOptions().position(currentPosition).title("My Location"));

        googleMapScreen.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 15.0f));

    }
}
