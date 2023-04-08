package com.example.safari.Model;

import java.io.Serializable;

public class Results implements Serializable {

   public int Make_ID;

   public String Make_Name;

    public int getMake_ID() {
        return Make_ID;
    }

    public void setMake_ID(int make_ID) {
        Make_ID = make_ID;
    }

    public String getMake_Name() {
        return Make_Name;
    }

    public void setMake_Name(String make_Name) {
        Make_Name = make_Name;
    }

    public String toString(){

        return Make_Name;
    }
}
