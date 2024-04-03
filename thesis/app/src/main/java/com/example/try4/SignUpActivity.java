package com.example.try4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {


    private FirebaseAuth auth;
    private EditText signUpPass, signUpEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();
        signUpEmail=findViewById(R.id.Sign_Up_email);
        signUpPass=findViewById(R.id.Sign_Up_pass);
        Button sighUpButton = findViewById(R.id.sign_up_button);
        TextView signInRedirectText = findViewById(R.id.SignIn_RedirectText);

        sighUpButton.setOnClickListener(v -> {
            String user = signUpEmail.getText().toString().trim();
            String pass= signUpPass.getText().toString().trim();

            if(user.isEmpty()){
                signUpEmail.setError("Email cannot be empty");

            }
            if(pass.isEmpty()){
                signUpPass.setError("Password cannot be empty");

            }else{
                auth.createUserWithEmailAndPassword(user,pass).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(SignUpActivity.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUpActivity.this,SignInActivity.class));

                    }else {
                        Toast.makeText(SignUpActivity.this, "Sign Up Failed"+ Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });
        signInRedirectText.setOnClickListener(v -> startActivity(new Intent(SignUpActivity.this,SignInActivity.class)));



    }
}