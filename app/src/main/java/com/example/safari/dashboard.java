package com.example.safari;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;

import com.example.safari.Model.RequestManagerForModels;
import com.example.safari.Model.Results;
import com.example.safari.Model.ResultsForModels;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;


import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.IntStream;

public class dashboard extends AppCompatActivity{


    SQLiteDatabase sqlData;
    Database database;

    RecyclerView recyclerView;
    Adapter adapter;
    Button user_logout, addCar_Button;
    TextView addSomeNewCars;
    Spinner spinner2;
    List<FinalCarMakeAndCarModelAdder> arr = new LinkedList<>();
    String finalMake_name = null, finalModel_name = null;
    Dialog dialog;
    ImageView imageView,addSomeNewCarsLogo;

    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    FirebaseStorage mStorage;
    String email;
    public int increment = 0;


    @SuppressLint({"ResourceType", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        addSomeNewCars = findViewById(R.id.addSomeNewCars);
        addSomeNewCarsLogo = findViewById(R.id.addSomeNewCarsLogo);


        email  =  getIntent().getStringExtra("email");

        database = new Database(this);

       SQLiteDatabase db =  database.getReadableDatabase();


       String query = "SELECT * FROM userGarage WHERE username = '" + email + "';";


        Cursor cursor = db.rawQuery(query, null);
             cursor.moveToFirst();

             if(cursor.moveToFirst()) {

                 if (Objects.equals(email, cursor.getString(0))) {

                     while (true) {


                         String make_name = cursor.getString(1);
                         String model_name = cursor.getString(2);
                         String img = cursor.getString(3);


                         Uri uri = Uri.parse(img);


                         arr.add(new FinalCarMakeAndCarModelAdder(make_name, model_name, uri));

                         addSomeNewCars.setVisibility(View.GONE);
                         addSomeNewCarsLogo.setVisibility(View.GONE);
                         recyclerView = findViewById(R.id.recyclerview);
                         recyclerView.setHasFixedSize(true);
                         recyclerView.setLayoutManager(new GridLayoutManager(dashboard.this, 1));
                         adapter = new Adapter(dashboard.this, arr);
                         recyclerView.setAdapter(adapter);


                         if (!cursor.moveToNext())
                             break;

                     }

                 }

             }



        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference().child("User");
        mStorage = FirebaseStorage.getInstance();

        spinner2 = findViewById(R.id.spinner2);


        addCar_Button = findViewById(R.id.addCar_Button);

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.progress_bar);



        if(dialog.getWindow()!=null){
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        dialog.show();

        user_logout = findViewById(R.id.user_logout);

        RequestManager requestManager = new RequestManager(this);

        requestManager.getData(listener);


        user_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder alert = new AlertDialog.Builder(dashboard.this);

                alert.setTitle("Are You are Sure you want to Logout?");

                alert.setNegativeButton("Cancel", null);

                alert.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        startActivity(new Intent(dashboard.this, MainActivity.class));

                    }
                });

                alert.show();
            }
        });

    }

    private final OnFetchDataListener listener = new OnFetchDataListener() {
        @Override
        public void OnFetchData(int Count, String Message, String SearchCriteria, List<Results> Results, String message) {

            display(Results);

        }

        @Override
        public void OnError(String message) {

        }
    };

    private void display(List<Results> results) {

        Spinner spinner1 = findViewById(R.id.spinner1);

        ArrayAdapter<Results> spinnerAdapter1 = new ArrayAdapter<Results>(this, android.R.layout.simple_spinner_item, results);

        spinnerAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dialog.dismiss();

        spinner1.setAdapter(spinnerAdapter1);


        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                 dialog.show();

                // String make_id = String.valueOf(results.get(position).getMake_ID();

                String make_id = String.valueOf(results.get(position).getMake_ID());

                finalMake_name = results.get(position).getMake_Name();

                RequestManagerForModels requestManagerForModels = new RequestManagerForModels(dashboard.this);
                requestManagerForModels.getData(onFetchModelListener, make_id, "json");


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private final OnFetchModelListener onFetchModelListener = new OnFetchModelListener() {
        @Override
        public void OnFetchModels(int Count, String Message, String SearchCriteria, List<ResultsForModels> Results, String message) {

            displayModels(Results);


        }

        @Override
        public void OnError(String message) {

            Toast.makeText(dashboard.this,"Error!!",Toast.LENGTH_SHORT).show();

        }
    };


    private void displayModels(List<ResultsForModels> resultsForModels) {


        ArrayAdapter<ResultsForModels> spinnerAdapter2 = new ArrayAdapter<ResultsForModels>(this, android.R.layout.simple_spinner_item, resultsForModels);

        spinnerAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

       dialog.dismiss();

        spinner2.setAdapter(spinnerAdapter2);


        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                finalModel_name = resultsForModels.get(position).getModel_Name();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        addCar_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Uri uri = null;

               Uri uri =  Uri.parse("res:///" + R.drawable.placeholderimage);

                arr.add(new FinalCarMakeAndCarModelAdder(finalMake_name, finalModel_name, uri));

                System.out.println(arr);

                addSomeNewCars.setVisibility(View.GONE);
                addSomeNewCarsLogo.setVisibility(View.GONE);
                recyclerView = findViewById(R.id.recyclerview);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new GridLayoutManager(dashboard.this, 1));
                adapter = new Adapter(dashboard.this, arr);
                recyclerView.setAdapter(adapter);

                imageView = adapter.car_Image;




                 database = new Database(dashboard.this);
                sqlData = database.getWritableDatabase();

                ContentValues cv = new ContentValues();

                cv.put("username",email);
                cv.put("make_name",finalMake_name);
                cv.put("model_name",finalModel_name);

                IntStream a = null;

                    sqlData = database.getReadableDatabase();

                    Random random = new Random();
                     a = random.ints(1,9);

                cv.put("image",uri.toString() + a);


                sqlData.insert("userGarage",null,cv);

                Toast.makeText(dashboard.this,"Car Added in your Garage",Toast.LENGTH_SHORT).show();


            }

        }

        );

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode== Activity.RESULT_OK){
            Uri uri = data.getData();
            imageView = adapter.car_Image;


            dialog.show();


            for (int i = 0; i < arr.size(); i++) {

                if(i==adapter.adtPoSi){
                    arr.get(i).img = uri;
                    imageView.setImageURI(uri);

                     sqlData = SQLiteDatabase.openOrCreateDatabase("/data/data/com.example.safari/databases/garage.db",null);


                    String query2 = "SELECT * FROM userGarage WHERE username = ?";

                   Cursor cursor = sqlData.rawQuery(query2, new String[]{(email)});


                    cursor.moveToPosition(i);


                   String toBeChanged =  cursor.getString(3);



                    String fileUri = String.valueOf(uri);

                    String query = "UPDATE userGarage SET image = ? WHERE image = ?";
                    sqlData.execSQL(query, new String[]{fileUri, toBeChanged});

                      cursor.close();

                    break;



                }

            }




            recyclerView.setAdapter(adapter);


            dialog.dismiss();


        }
        else if(resultCode== ImagePicker.RESULT_ERROR){
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }



    }

    @Override
    public void onBackPressed() {

        Dialog dialog1 = new Dialog(dashboard.this);

        AlertDialog alertDialog = new AlertDialog.Builder(dashboard.this)
                .setTitle("Are you sure you want to go back? You will be logged out!")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        startActivity(new Intent(dashboard.this,MainActivity.class));

                    }
                })
                        .setNegativeButton("No",null).show();


    }
}