package com.example.safari;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;


import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.viewHolder> {

    Context context;
    List<FinalCarMakeAndCarModelAdder> array;
    public ImageView car_Image;
    Dialog dialog;
    public  int adtPoSi;
    SQLiteDatabase db;
    Database database;

    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    FirebaseStorage mStorage;

    public Adapter(){};


    public Adapter(Context context, List<FinalCarMakeAndCarModelAdder> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recyler_view,parent,false);

        return new viewHolder(view);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        holder.car_Make.setText(array.get(position).car_Make);
        holder.car_Model.setText(array.get(position).car_Model);
        holder.image.setImageURI(array.get(position).img);

    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{

       public TextView car_Make, car_Model;
       public Button addCar_Image_Button,deleteCar_Button;
       public ImageView image;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            dialog = new Dialog(context);
            dialog.setContentView(R.layout.progress_bar);



            if(dialog.getWindow()!=null){
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }



            car_Make = itemView.findViewById(R.id.car_make);
            car_Model = itemView.findViewById(R.id.car_model);
            car_Image = itemView.findViewById(R.id.car_Image);
            image = itemView.findViewById(R.id.car_Image);
           // car_Image.setScaleType(ImageView.ScaleType.CENTER_CROP);

            addCar_Image_Button = itemView.findViewById(R.id.addCar_Image_Button);
            deleteCar_Button = itemView.findViewById(R.id.deleteCar_Button);

            mDatabase = FirebaseDatabase.getInstance();
            mRef = mDatabase.getReference().child("User");
            mStorage = FirebaseStorage.getInstance();



            addCar_Image_Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    adtPoSi = getAdapterPosition();


                    ImagePicker.with((Activity) context)
                            .crop()
                            .compress(1024)
                            .maxResultSize(240, 240)
                            .start();






                }
            });





            deleteCar_Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    AlertDialog.Builder alert = new AlertDialog.Builder(context);

                    alert.setTitle("Are You are Sure you want to Delete?");

                    alert.setNegativeButton("Cancel",null);

                    alert.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            array.remove(getAdapterPosition());

                            notifyItemRemoved(getAdapterPosition());
                            notifyItemRangeChanged(getAdapterPosition(),array.size()-getAdapterPosition());

                        }
                    });

                    alert.show();



                    database = new Database(context);

                     db =  database.getWritableDatabase();

                    String query = "delete from userGarage where make_name =  '" + array.get(getAdapterPosition()).car_Make + "';";

                  db.execSQL(query);

                    db.close();




                }
            });



        }


    }



}

