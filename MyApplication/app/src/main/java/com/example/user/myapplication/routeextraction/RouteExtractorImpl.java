package com.example.user.myapplication.routeextraction;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by CARROT on 2016/10/11.
 */
public class RouteExtractorImpl implements RouteExtractor{

    private Context context;

    public RouteExtractorImpl(Context context){
        this.context = context;
    }

    @Override
    public Station[] getUsingRoute(String start, String end) {
        SQLiteOpenHelper helper = StationSQLiteOpenHelper.getHelperObject(this.context);
        SQLiteDatabase database = helper.getReadableDatabase();

        /*DBを使って経路を抽出する処理*/

        database.close();

        return new Station[0];
    }
}
