package com.example.safari;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    public Database(Context context) {
        super(context,"garage.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
          db.execSQL("create Table userGarage(username TEXT,make_name TEXT,model_name TEXT,image TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS garage.db");
        onCreate(db);


    }
}
