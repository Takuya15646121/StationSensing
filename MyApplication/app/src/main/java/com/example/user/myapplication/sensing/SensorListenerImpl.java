package com.example.user.myapplication.sensing;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 慣性センサ値処理クラス<br>
 * センサ値を一定間隔でポーリングし，その値を指定のファイルに保存する<br>
 *
 * Created by user on 2016/06/06.
 */
public class SensorListenerImpl implements SensorEventListener {

    private final int sensorType;
    private volatile SensorEvent latestEvent;
    private ScheduledExecutorService scheduledExecutor;

    /**
     * コンストラクタ
     *
     * @param sensorType 使用するセンサのタイプ．Sensor.TYPE_XXXXで指定する
     * @param inertialFrequency センサの取得間隔．Sensor.DELAY_FASTEST以下で指定する
     */
    public SensorListenerImpl(int sensorType, long inertialFrequency){
        this.sensorType = sensorType;
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
        if(event.sensor.getType()==sensorType){
            latestEvent = event;
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

        @Override
        public void run() {
            SensorEvent event = SensorListenerImpl.this.latestEvent;
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
