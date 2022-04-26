package uk.ac.tees.aad.B1212361;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Consult extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;

    String dept = "Gynecologists";
    String[] list;
    EditText messages;
    Spinner spiner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        messages = findViewById(R.id.description);
        spiner = findViewById(R.id.spinner);


        list = new String[]{"Gynecologists","orthopedic","Dermatologists","Dental"};

        ArrayAdapter adapter  = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,list);

        spiner.setAdapter(adapter);

        spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dept = list[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Button consult =  findViewById(R.id.consult_button);
        consult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(messages.getText().length()<20)
                {
                    messages.setError("Min 20 char required");
                    return;
                }
                Date date = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                String strDate = dateFormat.format(date);
                DatabaseReference referece = firebaseDatabase.getReference("Consult");
                referece.child(firebaseAuth.getCurrentUser().getUid().toString())
                        .child(strDate)
                        .setValue(new ConsultObj(dept,messages.getText().toString()))
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        startActivity(new Intent(getApplicationContext(),confirmation3.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Unable to consult",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
