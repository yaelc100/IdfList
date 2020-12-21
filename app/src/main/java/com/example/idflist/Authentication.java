package com.example.idflist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Authentication extends AppCompatActivity {
    public EditText edPassword, edEmail;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword );
        mAuth = FirebaseAuth.getInstance();
    }

    public void userRegister(View view) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    public void Auth(View view) {
        userLogin();
    }

    private void userLogin() {
        String email = edEmail.getText().toString().trim();
        String password = edPassword.getText().toString().trim();

        if(email.isEmpty()){
            edEmail.setError("Email required");
            edEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edEmail.setError("Email not valid");
            edEmail.requestFocus();
        }

        if(password.isEmpty()){
            edPassword.setError("Age name is empty");
            edPassword.requestFocus();
            return;
        }
        if(password.length() < 6){
            edPassword.setError("Min characters 6");
            edPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //redirect profile
                    startActivity(new Intent(Authentication.this, ImageActivity.class));
                }
                else{
                    Toast.makeText(Authentication.this, "login failed! Try again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}