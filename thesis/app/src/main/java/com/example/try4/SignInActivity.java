package com.example.try4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText signInEmail,signInPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        auth=FirebaseAuth.getInstance();
        signInEmail=findViewById(R.id.Sign_In_email);
        signInPass = findViewById(R.id.Sign_In_pass);
        Button signInButton = findViewById(R.id.sign_in_button);
        TextView signUpRedirect = findViewById(R.id.SignUp_redirect);

        signInButton.setOnClickListener(v -> {
            String email=signInEmail.getText().toString();
            String pass= signInPass.getText().toString();

            if(!email.isEmpty()&& Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                if(!pass.isEmpty()) {
                    auth.signInWithEmailAndPassword(email, pass)
                            .addOnSuccessListener(this::onSuccess)
                            .addOnFailureListener(this::onFailure);
                }else {
                    signInPass.setError("password cannot be empty");
                }

            }else if(email.isEmpty()){
                signInEmail.setError("Email cannot be empty");

            }else{
                signInEmail.setError("Please enter valid email");

            }

        });

        signUpRedirect.setOnClickListener(v -> startActivity(new Intent(SignInActivity.this, SignUpActivity.class)));
    }

    private void onSuccess(@NonNull AuthResult authResult) {
        showSuccessToast();
        navigateToMainActivity();
        finishActivity();
    }

    private void showSuccessToast() {
        Toast.makeText(SignInActivity.this, R.string.sign_in_successful, Toast.LENGTH_SHORT).show();
    }

    private void navigateToMainActivity() {
        Intent mainActivityIntent = new Intent(SignInActivity.this, MainActivity.class);
        startActivity(mainActivityIntent);
    }

    private void finishActivity() {
        finish();
    }

    private void onFailure(Exception e) {
        Toast.makeText(SignInActivity.this, "Signing In Failed", Toast.LENGTH_SHORT).show();
    }
}