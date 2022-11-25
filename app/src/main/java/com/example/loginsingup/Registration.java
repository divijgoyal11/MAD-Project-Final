package com.example.loginsingup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PerformanceHintManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.internal.RegisterListenerMethod;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.net.PasswordAuthentication;

public class Registration extends AppCompatActivity {

    TextView To_Login;
    EditText Location,PersonName,Phone,EmailAddress,R_Password;
    Button R_Button;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String phoneNumber = "";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        To_Login = findViewById(R.id.To_Login);
        Location = findViewById(R.id.Location);
        PersonName = findViewById(R.id.PersonName);
        Phone = findViewById(R.id.Phone);
        EmailAddress = findViewById(R.id.EmailAddress);
        R_Password = findViewById(R.id.R_Password);
        R_Button = findViewById(R.id.R_Button);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();



        To_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Registration.this, MainActivity.class));

            }
        });


        R_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerforAuth();
            }
        });
    }
        private void PerforAuth() {
            String location=Location.getText().toString();
            String name=PersonName.getText().toString();
            String phone=Phone.getText().toString();
            String email=EmailAddress.getText().toString();
            String password=R_Password.getText().toString();

            userModel = new UserModel(location, name, phone, email);
            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful())
                    {
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(Registration.this, "Data Stored", Toast.LENGTH_SHORT).show();
                    }else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(Registration.this, "+task.getException()", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            if (!email.matches(emailPattern))
            {
                EmailAddress.setError("Enter Correct Email");
            }else if(password.isEmpty() || password.length()<6)
            {
                R_Password.setError("Enter Correct Password");
            }else
            {
                progressDialog.setMessage("Please Wait...");
                progressDialog.setTitle("Registering");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            progressDialog.dismiss();
                            sendUserToNextActivity();
                            Toast.makeText(Registration.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        }else
                        {
                            progressDialog.dismiss();
                            Toast.makeText(Registration.this, "+task.getException()", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }

    private void sendUserToNextActivity() {
        Intent intent= new Intent(Registration.this,Dash.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
