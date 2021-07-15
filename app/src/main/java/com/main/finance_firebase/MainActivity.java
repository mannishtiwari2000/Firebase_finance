package com.main.finance_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        Button login = findViewById(R.id.button_login);
        Button Sign = findViewById(R.id.button_sign);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        Sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sign();
            }
        });

    }

    private void login() {
        firebaseAuth.signInWithEmailAndPassword("mannishtiwari96@gmail.com", "passwords").addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(MainActivity.this, Home.class));
                    finish();
                    Toast.makeText(MainActivity.this, "Login Successfully ", Toast.LENGTH_SHORT).show();
                } else {
                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(MainActivity.this, "Please check password ", Toast.LENGTH_SHORT).show();
                    } else if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                        Toast.makeText(MainActivity.this, "Please check Email ", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }

    private void Sign() {

        firebaseAuth.createUserWithEmailAndPassword("mannishtiwari196@gmail.com", "passwords")
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Model_Data model_data = new Model_Data("Manish", "mannishtiwari96@gmail.com", "88517254");

                            FirebaseDatabase.getInstance().getReference("User")
                                    .child(FirebaseAuth.getInstance().getUid())
                                    .setValue(model_data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {


                                    Toast.makeText(MainActivity.this, "Registration Successfully Complete", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    if (e instanceof FirebaseAuthUserCollisionException) {
                                        Toast.makeText(MainActivity.this, "Your gmail are already Registered", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });
    }

}