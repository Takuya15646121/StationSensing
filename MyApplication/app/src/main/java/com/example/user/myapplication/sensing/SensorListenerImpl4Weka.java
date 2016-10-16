package com.example.user.myapplication.sensing;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import tuat.daily.phonepos.feature.name.Axis;
import tuat.daily.phonepos.window.window.Window;

/**
 * 慣性センサ値処理クラス<br>
 * センサ値を一定間隔でポーリングし，その値を指定のファイルに保存する<br>
 *
 * Created by user on 2016/06/06.
 */
public class SensorListenerImpl4Weka implements SensorEventListener {

    private volatile SensorEvent accLatestEvent;
    private volatile SensorEvent magLatestEvent;
    private ScheduledExecutorService scheduledExecutor;

    /**
     * コンストラクタ
     *
     * @param inertialFrequency センサの取得間隔．Sensor.DELAY_FASTEST以下で指定する
     */
    public SensorListenerImpl4Weka(long inertialFrequency){

        scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutor.scheduleAtFixedRate(
                new SensorRecordThread()
                ,0
                ,inertialFrequency//スケジューリング間隔を素直にInertialFrequencyとして設定
                ,TimeUnit.MILLISECONDS
        );

    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            accLatestEvent = event;
        }
        if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            magLatestEvent = event;
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * スケジューラをシャットダウンする<br>
     * 呼び出し元から責任をもって呼び出す<br>
     */
    public void shutdown(){
        scheduledExecutor.shutdown();
    }


    /**
     * センサデータを一定間隔で記録するためのスレッドクラス
     */
    class SensorRecordThread implements Runnable{

        private final long freqMillis;
        private long oldTime;
        private List<Window> accWindow;
        private List<Window> magWindow;

        /**
         * コンストラクタ．引数として指定周波数(long型)を受け取る
         * @param freqMillis 指定周波数(ミリ秒)
         */
        public SensorRecordThread(long freqMillis){
            this.freqMillis = freqMillis * (long)Math.pow(10,6);//event.timestampがナノ秒単位なので、単位を揃える
            this.oldTime = 0;
        }

        public SensorRecordThread(){
            this.freqMillis = 0;//重複を防ぎ、かつisRecordableメソッドを素通りさせる
            this.oldTime = 0;

        }

        private void initWindow(){
            this.accWindow = new ArrayList<>();
            this.accWindow.add(new Window(Axis.ACC_LINEAR_X,128));
            this.accWindow.add(new Window(Axis.ACC_LINEAR_Y,128));
            this.accWindow.add(new Window(Axis.ACC_LINEAR_Z,128));
            this.accWindow.add(new Window(Axis.ACC_LINEAR_3AXIS,128));

            this.magWindow = new ArrayList<>();
            this.magWindow.add((new Window(Axis.MAG_RAW_X,128)));
            this.magWindow.add((new Window(Axis.MAG_RAW_Y,128)));
            this.magWindow.add((new Window(Axis.MAG_RAW_Z,128)));
            this.magWindow.add((new Window(Axis.MAG_RAW_3AXIS,128)));
        }

        @Override
        public void run() {
            SensorEvent event = SensorListenerImpl4Weka.this.accLatestEvent;
            if(event == null){
                return;
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
