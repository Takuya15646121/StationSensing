package com.example.user.myapplication.routeextraction;

/**
 * Created by CARROT on 2016/10/11.
 */
public class Station {
    private String name;
    private int id;
    private int lineId;
    private double latitude;
    private double longitude;


    public Station(String name,int id,int lineId,double latitude,double longitude){
        this.name = name;
        this.id = id;
        this.lineId = lineId;
        this.latitude = latitude;
        this.longitude = longitude;
    }


}
