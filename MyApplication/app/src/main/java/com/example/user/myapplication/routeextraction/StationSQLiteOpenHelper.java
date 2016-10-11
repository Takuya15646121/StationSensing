package com.example.user.myapplication.routeextraction;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 2016/10/11.
 */
public class StationSQLiteOpenHelper extends SQLiteOpenHelper{

    private static final String DB = "";
    private static final int DB_VERSION = 1;

    private static volatile SQLiteOpenHelper helper = null;

    public static SQLiteOpenHelper getHelperObject(Context context){
        if(helper == null){
            SQLiteOpenHelper myHelper = new StationSQLiteOpenHelper(context);
            helper = myHelper;
        }
        return helper;
    }


    private StationSQLiteOpenHelper(Context context) {
        super(context,DB,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

