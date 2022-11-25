package com.example.loginsingup;

import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    EditText Name, Mail, ContactNumber, Loca, Birthd;

    String name,email,number,loc,birth;
    UserModel userModel;
    FirebaseAuth mAuth;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        Name = findViewById(R.id.Name);
        Loca = findViewById(R.id.Loca);



        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase.getInstance().getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds:snapshot.getChildren()) {
                    if(ds.child("email").getValue().equals(mAuth.getCurrentUser().getEmail().toString())) {

                        name = ds.child("userName").getValue(String.class);
                        userModel.setLocation(snapshot.child("location").getValue().toString());
                        userModel.setPhoneNumber(snapshot.child("phoneNumber").getValue().toString());
                        userModel.setUserName(snapshot.child("userName").getValue().toString());
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}