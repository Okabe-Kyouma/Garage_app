package com.example.safari;

import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

public class FinalCarMakeAndCarModelAdder {

    public String car_Make;

    public String car_Model;

    public Uri img;

    public FinalCarMakeAndCarModelAdder(String car_Make, String car_Model,Uri img) {
        this.car_Make = car_Make;
        this.car_Model = car_Model;
        this.img = img;
    }

    public FinalCarMakeAndCarModelAdder(String car_Make,String car_Model){

        this.car_Make = car_Make;
        this.car_Model = car_Model;

    }
}
