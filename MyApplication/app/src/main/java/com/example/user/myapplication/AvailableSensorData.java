package com.example.user.myapplication;

import android.app.Activity;
import android.hardware.Sensor;
import android.support.annotation.Nullable;

/**
 * Created by user on 2016/06/09.
 */
public enum AvailableSensorData {
    ACC(Sensor.TYPE_ACCELEROMETER,"acc",R.id.checkBox),
    MAG(Sensor.TYPE_MAGNETIC_FIELD,"mag",R.id.checkBox2),
    GYRO(Sensor.TYPE_GYROSCOPE,"gyro",R.id.checkBox3);
    ;
    private final int type;
    private final String name;
    private final int checkedId;

    private AvailableSensorData(int type,String name,int checkedId){
        this.type = type;
        this.name = name;
        this.checkedId = checkedId;
    }

    public int getType(){
        return this.type;
    }

    public String getName(){
        return this.name;
    }

    public int getId(){
        return this.checkedId;
    }

    @Nullable
    public static AvailableSensorData getMatchedInstance(int id){
        for(AvailableSensorData data:values()){
            if(data.checkedId==id){
                return data;
            }
        }

        return null;
    }

}
