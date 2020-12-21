package com.example.idflist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private FirebaseAuth mAuth;
    private EditText edName, edEmail, edPhoneNumber,edPassword;
    private Spinner spinner;
    private String type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        edName = findViewById(R.id.name);
        edEmail = findViewById(R.id.email);
        edPhoneNumber = findViewById(R.id.phoneNumber);
        edPassword = findViewById(R.id.password);

        spinner = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.Type, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        if(text.equals("Commander")){
            type = "Commander";
        }
        else
            type = "Soldier";

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void registerUser(){
        final String name = edName.getText().toString().trim();
        final String email = edEmail.getText().toString().trim();
        final String password = edPassword.getText().toString().trim();
        final String phoneNumber = edPhoneNumber.getText().toString().trim();

        if(name.isEmpty()){
            edName.setError("Full name is empty");
            edName.requestFocus();
            return;
        }
        if(email.isEmpty()){
            edEmail.setError("Email name is empty");
            edEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edEmail.setError("Email not valid");
            edEmail.requestFocus();
        }
        if(phoneNumber.isEmpty()){
            edPhoneNumber.setError("Full phone number is empty");
            edPhoneNumber.requestFocus();
            return;
        }
        if(password.isEmpty()){
            edPassword.setError("Password name is empty");
            edPassword.requestFocus();
            return;
        }
        if(password.length() < 6){
            edPassword.setError("Min characters 6");
            edPassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            final User user = new User(FirebaseAuth.getInstance().getCurrentUser().getUid(), name, phoneNumber,email, type);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    //PROGRES BAR FALSE;
                                    if(task.isSuccessful()){
                                        Toast.makeText(Register.this, "Register succeed", Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        Toast.makeText(Register.this, "Register failed! Try again.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        } else{
                            Toast.makeText(Register.this, "Register failed! Try again2.", Toast.LENGTH_LONG).show();
                            //progress bar false;
                        }
                    }
                });
    }

    public void createUser(View view) {
        registerUser();
    }
}