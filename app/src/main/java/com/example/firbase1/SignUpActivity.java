package com.example.firbase1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUpActivity extends AppCompatActivity {

    private EditText emailSignUpText, passwordSignUpText;
    private Button signUpButton;
    private ProgressBar signUpProgressBar;
    private TextView signInText;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        setTitle("Sign Up");
        emailSignUpText = findViewById(R.id.emailsignupId);
        passwordSignUpText = findViewById(R.id.passwordsignupId);
        signInText = findViewById(R.id.signInTextId);
        signUpButton = findViewById(R.id.signUpButtonId);
        signUpProgressBar = findViewById(R.id.signUpProgressBarid);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userResister();
            }
        });
        signInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void userResister() {
        String email = emailSignUpText.getText().toString();
        String password = passwordSignUpText.getText().toString();

        if(email.isEmpty())
        {
            emailSignUpText.setError("Enter an Email Address");
            emailSignUpText.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            emailSignUpText.setError("Enter a valid Email Address");
            emailSignUpText.requestFocus();
            return;
        }
        if(password.isEmpty())
        {
            passwordSignUpText.setError("Enter an Password");
            passwordSignUpText.requestFocus();
            return;

        }
        if(password.length()<6)
        {
            passwordSignUpText.setError("Password Length must be 6 digit");
            passwordSignUpText.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                signUpProgressBar.setVisibility(View.VISIBLE);
                if (task. isSuccessful()) {
                    signUpProgressBar.setVisibility(View.GONE);
                    Toast.makeText(SignUpActivity.this, "Registered is Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                } else {
                    signUpProgressBar.setVisibility(View.GONE);
                    if(task.getException() instanceof FirebaseAuthUserCollisionException)
                    {
                        signUpProgressBar.setVisibility(View.GONE);
                        Toast.makeText(SignUpActivity.this, "User already Register", Toast.LENGTH_SHORT).show();
                    }
                   else
                    {
                        signUpProgressBar.setVisibility(View.GONE);
                        Toast.makeText(SignUpActivity.this, "Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}