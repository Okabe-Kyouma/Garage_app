package com.example.safari;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class user_register_page extends AppCompatActivity {

    TextInputEditText name,age,email,phoneNumber,pass,Re_pass;
    Button registerMe;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register_page);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("saFAri-User Registration");

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        phoneNumber = findViewById(R.id.phoneNumber);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        Re_pass = findViewById(R.id.Re_password);

        registerMe = findViewById(R.id.register_me);

        registerMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PerForAuth();



            }
        });



    }

    private void PerForAuth() {


        String user_Name = email.getText().toString();
        String pass_Word = pass.getText().toString();


         if(user_Name!=null && pass_Word!=null){

             mAuth.createUserWithEmailAndPassword(user_Name,pass_Word).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task) {

                     if(task.isSuccessful()){
                         Toast.makeText(user_register_page.this,"Registration SuccessFull",Toast.LENGTH_SHORT).show();
                         sendUserToNextActivity();

                     }
                     else{
                         Toast.makeText(user_register_page.this,"Error",Toast.LENGTH_SHORT).show();

                         System.out.println(task.getResult());

                     }


                 }
             });

         }


    }

    private void sendUserToNextActivity() {

       // startActivity(new Intent(this,dashboard.class));

        Intent intent = new Intent(this,dashboard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

    }
}