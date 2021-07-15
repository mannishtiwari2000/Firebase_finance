package com.main.finance_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class Home extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    TextView t;
    // creating a variable for our
    // Database Reference for Firebase.
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button data = findViewById(R.id.button_data);
        t = findViewById(R.id.textView_show);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("User");
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getdata();
            }
        });

    }

    private void getdata() {
databaseReference.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull  DataSnapshot snapshot) {
//        String value_data = snapshot.getValue(String.class);
//        t.setText(value_data);

        Model_Data modelData =snapshot.getValue(Model_Data.class);
        t.setText(modelData.getName());
        Toast.makeText(Home.this,modelData.getName(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {
        Toast.makeText(Home.this,error.getMessage(),Toast.LENGTH_LONG).show();
    }
});

    }
}