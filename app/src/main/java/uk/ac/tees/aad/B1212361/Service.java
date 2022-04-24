package uk.ac.tees.aad.B1212361;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Service extends AppCompatActivity {


    Button medical_emer;
    Button emer;
    Button news;
    Button consult;
    Button mediServeice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        medical_emer = findViewById(R.id.medical_emer);
        emer = findViewById(R.id.emer);
        news = findViewById(R.id.news);
        consult = findViewById(R.id.medical_cons);
        mediServeice = findViewById(R.id.medical_ser);

        View.OnClickListener medical_emer_list = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
        View.OnClickListener emer_list = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
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


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        finishAffinity();
    }
}
