package uk.ac.tees.aad.B1212361;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Service extends AppCompatActivity {


    Button medical_emer;
    Button emer;
    Button news;
    Button consult;
    Button mediServeice;
    TextView title;
    TextView signout;

    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        medical_emer = findViewById(R.id.medical_emer);
        emer = findViewById(R.id.emer);
        news = findViewById(R.id.news);
        consult = findViewById(R.id.medical_cons);
        mediServeice = findViewById(R.id.medical_ser);
        title = findViewById(R.id.title);
        signout = findViewById(R.id.signout);

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });

        View.OnClickListener medical_emer_list = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MedicalEmergency.class);
                intent.putExtra("latitudeValue",latitude);
                intent.putExtra("longitudeValue",longitude);
                startActivity(intent);
            }
        };
        View.OnClickListener emer_list = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Emergency.class);
                intent.putExtra("latitudeValue",latitude);
                intent.putExtra("longitudeValue",longitude);
                startActivity(intent);
            }
        };

        View.OnClickListener news_lis = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        };
        View.OnClickListener consult_lis = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        };
        View.OnClickListener medical_service_lis = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        };

        medical_emer.setOnClickListener(medical_emer_list);
        emer.setOnClickListener(emer_list);
        news.setOnClickListener(news_lis);
        consult.setOnClickListener(consult_lis);
        mediServeice.setOnClickListener(medical_service_lis);

        fetchLocation();



        // Read from the database
        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                AppUsersDetails value = dataSnapshot.getValue(AppUsersDetails.class);
                title.setText("Hey "+value.getName()+"!");

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
    }

    private void fetchLocation() {


        FusedLocationProviderClient fusedLocationClient = LocationServices
                .getFusedLocationProviderClient(getApplicationContext());

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION)

                !=

                PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(
                this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                        !=
                        PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }
        fusedLocationClient
                .getLastLocation()
                .addOnSuccessListener(this,
                        new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                           latitude=location.getLatitude();
                           longitude=location.getLongitude();
                        }
                    }
                });
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        finishAffinity();
    }
}
