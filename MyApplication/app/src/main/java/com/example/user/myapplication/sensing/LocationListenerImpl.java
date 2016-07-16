package com.example.user.myapplication.sensing;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by user on 2016/06/07.
 */
public class LocationListenerImpl implements LocationListener {

    private final Recorder recorder;
    private Location latestLocation;
    private ScheduledExecutorService scheduledExecutorService;

    public LocationListenerImpl(Recorder recorder, long locFrequency){
        this.recorder = recorder;
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(
                new LocationRecordThread(),
                0
                ,locFrequency
                , TimeUnit.MILLISECONDS
        );
    }

    public void shutdown(){
        scheduledExecutorService.shutdown();
    }

    @Override
    public void onLocationChanged(Location location) {
        this.latestLocation = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    class LocationRecordThread implements Runnable{

        private final long freqMillis;
        private long oldTime;

        public LocationRecordThread(long freqMillis){
            this.freqMillis = freqMillis * (long)Math.pow(10,6);//ミリ秒からナノ秒への単位変換
            this.oldTime = 0;
        }

        public LocationRecordThread(){
            this.freqMillis = 0;//データの重複を排除し、かつisRecordableメソッドを素通りさせる
            this.oldTime = 0;
        }

        @Override
        public void run() {
            if(LocationListenerImpl.this.latestLocation ==null){
                return;
            }
            Location location = LocationListenerImpl.this.latestLocation;
            if(isRecordable(location.getTime())){
                LocationListenerImpl.this.recorder.record(
                        location.getTime(),
                        location.getLatitude(),
                        location.getLongitude(),
                        location.getAltitude(),
                        location.getAccuracy(),
                        location.getSpeed()
                );

            }
        }

        private boolean isRecordable(long newTime){

            if(newTime-oldTime > freqMillis){
                oldTime = newTime;
                return true;
            }
            return false;
        }
    }
}
