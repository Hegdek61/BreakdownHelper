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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Button createButton;
    Button loginButton;
    EditText emailInput;
    EditText passwordInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        createButton = findViewById(R.id.create_account_button);
        loginButton  =  findViewById(R.id.register_acoount_create);
        emailInput = findViewById(R.id.email_input_login);
        passwordInput = findViewById(R.id.password_input_login);


        View.OnClickListener redirectToRegister = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        };
        createButton.setOnClickListener(redirectToRegister);


        View.OnClickListener loginWithDetails = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndLogin();
             }
        };
        loginButton.setOnClickListener(loginWithDetails);

    }

    private void validateAndLogin() {
        boolean error = false;
        if(emailInput.getText().toString().length()<5)
        {
            emailInput.setError("Enter Correct Email");
            error = true;
        }
        if (passwordInput.getText().toString().length()<6)
        {
            passwordInput.setError("min of 6 Char allowed");
            error = true;
        }

        if(!error)
        {
            mAuth.signInWithEmailAndPassword(emailInput.getText().toString(), passwordInput.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("SUCCESS", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                startActivity(new Intent(getApplicationContext(),Service.class));
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("FAILED", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(getApplicationContext(), "Authentication failed.",
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
