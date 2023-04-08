package com.example.safari;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
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

public class MainActivity extends AppCompatActivity {

    Button client_login_login_button,client_login_register_button;
    TextInputEditText client_login_username,client_login_password;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    Dialog dialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.progress_bar);

        if(dialog.getWindow()!=null){
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }


        client_login_username =        findViewById(R.id.client_login_username);
        client_login_password =        findViewById(R.id.client_login_password);
        client_login_login_button =    findViewById(R.id.client_login_login_Button);
        client_login_register_button = findViewById(R.id.client_login_register_Button);

        mAuth  = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        client_login_register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, user_register_page.class));


            }
        });

        client_login_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show();

                String username = client_login_username.getText().toString();
                String password = client_login_password.getText().toString();

                mAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(!task.isSuccessful()){
                            Toast.makeText(MainActivity.this,"Your Not found! Click on Register ",Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                        else{
                                  dialog.dismiss();
                            startActivity(new Intent(MainActivity.this,dashboard.class).putExtra("email",username));

                        }

                    }
                });



            }
        });




    }

    @Override
    public void onBackPressed() {


        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                .setTitle("Do You really want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);

                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        finishAffinity();


                        finish();






                    }
                })
                .setNegativeButton("No", null).show();

    }

}