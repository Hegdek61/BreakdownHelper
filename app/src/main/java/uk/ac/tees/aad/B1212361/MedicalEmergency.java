package uk.ac.tees.aad.B1212361;


import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MedicalEmergency extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap googleMapScreen;
    EditText message ;
    Button submit ;
    LatLng currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_emergency);

        message = findViewById(R.id.messageText);
        submit = findViewById(R.id.button_emer_medical);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EmergencyMessage data = new EmergencyMessage(message.getText().toString(),currentPosition.latitude,currentPosition.longitude);

                FirebaseDatabase
                        .getInstance()
                        .getReference("Emergency").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString())
                .setValue(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        startActivity(new Intent(getApplicationContext(),Confirmation.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.googleMap);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMapScreen = googleMap;

        currentPosition = new LatLng(getIntent().getDoubleExtra("latitudeValue",0), getIntent().getDoubleExtra("longitudeValue",0));

        googleMapScreen.addMarker(new MarkerOptions().position(currentPosition).title("My Location"));

        googleMapScreen.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 15.0f));

    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(getApplicationContext(),Service.class));
    }
}
