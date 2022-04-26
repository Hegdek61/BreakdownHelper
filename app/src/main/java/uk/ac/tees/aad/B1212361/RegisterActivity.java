 package uk.ac.tees.aad.B1212361;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

 public class RegisterActivity extends AppCompatActivity {

     FirebaseAuth mAuth;
     FirebaseDatabase firebaseDatabase;
     DatabaseReference databaseReference;
     Button createButton;
     Button loginButton;
     EditText emailInput;
     EditText passwordInput;
     EditText nameInput;
     EditText mobileInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");


        loginButton = findViewById(R.id.login_button_register);
        createButton = findViewById(R.id.register_acoount_create);
        
        emailInput = findViewById(R.id.email_input_login);
        passwordInput = findViewById(R.id.password_input_login);
        nameInput = findViewById(R.id.name_register);
        mobileInput = findViewById(R.id.mobile_register);

        View.OnClickListener loginRedirectListner  =  new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        };
        loginButton.setOnClickListener(loginRedirectListner);
        
        View.OnClickListener registerListner  = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateandRegister();
            }
        };
        
        createButton.setOnClickListener(registerListner);
    }

     private void validateandRegister() {
        boolean err = false;

         if(emailInput.getText().toString().length()<5)
         {
             emailInput.setError("Enter Correct Email");
             err = true;
         }
         if (passwordInput.getText().toString().length()<6)
         {
             passwordInput.setError("min of 6 Char allowed");
             err = true;
         }
         if(nameInput.getText().toString().length()<3)
         {
             nameInput.setError("min of 3 Char allowed");
             err = true;
         }
         if(mobileInput.getText().toString().length()!=10)
         {
             mobileInput.setError("Mobile should be 10 characters");
             err = true;
         }

         if(!err)
         {


             mAuth.createUserWithEmailAndPassword(emailInput.getText().toString(), passwordInput.getText().toString())
                     .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                         @Override
                         public void onComplete(@NonNull Task<AuthResult> task) {
                             if (task.isSuccessful()) {

                                 AppUsersDetails userObj = new AppUsersDetails(nameInput.getText().toString(),mobileInput.getText().toString());


                                 databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString())
                                         .setValue(userObj)
                                         .addOnSuccessListener(new OnSuccessListener<Void>() {
                                             @Override
                                             public void onSuccess(Void aVoid) {
                                                 Toast.makeText(getApplicationContext(),"Accout Created Successfully",Toast.LENGTH_LONG).show();
                                                 startActivity(new Intent(getApplicationContext(),Service.class));
                                             }
                                         })
                                         .addOnFailureListener(new OnFailureListener() {
                                             @Override
                                             public void onFailure(@NonNull Exception e) {
                                                 Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                                                 startActivity(new Intent(getApplicationContext(),Service.class));
                                             }
                                         });

                             } else {
                                 // If sign in fails, display a message to the user.
                                 Log.w("FAIL", "createUserWithEmail:failure", task.getException());
                                 Toast.makeText(getApplicationContext(), task.getException().toString(),
                                         Toast.LENGTH_SHORT).show();
                             }

                         }
                     });

         }

     }

     @Override
     public void onStart() {
         super.onStart();
         FirebaseUser currentUser = mAuth.getCurrentUser();
         if(currentUser != null)
         {
             startActivity(new Intent(getApplicationContext(),Service.class));
         }
     }

     @Override
     public void onBackPressed() {
         super.onBackPressed();
         finish();
         finishAffinity();
     }
 }
