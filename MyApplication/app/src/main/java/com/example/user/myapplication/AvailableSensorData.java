package com.example.user.myapplication;

import android.app.Activity;
import android.hardware.Sensor;
import android.support.annotation.Nullable;

/**
 * Created by user on 2016/06/09.
 */
public enum AvailableSensorData {
    ACC(Sensor.TYPE_ACCELEROMETER,"acc"),
    MAG(Sensor.TYPE_MAGNETIC_FIELD,"mag"),
    GYRO(Sensor.TYPE_GYROSCOPE,"gyro");
    ;
    private final int type;
    private final String name;

    private AvailableSensorData(int type,String name){
        this.type = type;
        this.name = name;
    }

    public int getType(){
        return this.type;
    }

    public String getName(){
        return this.name;
    }


    @Nullable
    public static AvailableSensorData getMatchedInstance(int id){


        return null;
    }

}
