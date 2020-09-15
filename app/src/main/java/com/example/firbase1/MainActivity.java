package com.example.firbase1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
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

public class MainActivity extends AppCompatActivity {

    private EditText emailSignText, passwordSignText;
    private Button signInButton;
    private ProgressBar signProgressBar;
    private TextView signUpText;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        emailSignText = findViewById(R.id.emailTextId);
        passwordSignText = findViewById(R.id.passwordTextId);
        signUpText = findViewById(R.id.signUpTextId);
        signInButton = findViewById(R.id.signInButtonId);
        signProgressBar = findViewById(R.id.signprogressBarid);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void userLogin() {
        String email = emailSignText.getText().toString();
        String password = passwordSignText.getText().toString();
        if(email.isEmpty())
        {
            emailSignText.setError("Enter an Email Address");
            emailSignText.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            emailSignText.setError("Enter a valid Email Address");
            emailSignText.requestFocus();
            return;
        }
        if(password.isEmpty())
        {
            passwordSignText.setError("Enter an Password");
            passwordSignText.requestFocus();
            return;

        }
        if(password.length()<6)
        {
            passwordSignText.setError("Password Length must be 6 digit");
            passwordSignText.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                signProgressBar.setVisibility(View.VISIBLE);
                if(task.isSuccessful())
                {
                    finish();
                    Intent intent = new Intent(getApplicationContext(),Join.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    signProgressBar.setVisibility(View.GONE);
                }
                else {
                    signProgressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this, "Login Unseccesfull", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}