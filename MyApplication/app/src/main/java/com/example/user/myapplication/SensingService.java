package com.example.user.myapplication;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.example.user.myapplication.routeextraction.RouteExtractorImpl;
import com.example.user.myapplication.routeextraction.Station;
import com.example.user.myapplication.scheduler.RouteScheduler;
import com.example.user.myapplication.scheduler.RouteSchedulerImpl;
import com.example.user.myapplication.sensing.CSVRecorder;
import com.example.user.myapplication.sensing.SensorListenerImpl;
import com.example.user.myapplication.sensing.Recorder;

/**
 * Created by user on 2016/06/06.
 */
public class SensingService extends Service{

    private static final long INERTIAL_FREQUENCY_MILLIS = 20;
    private static final long LOCATION_FREQUENCY_MILLIS = 1000;

    private Messenger messenger;
    private SensorManager sensorManager;
    private List<SensorEventListener> sensorListeners;

    @Override
    public void onCreate(){
        super.onCreate();
        this.sensorListeners = new ArrayList<>();
        messenger = new Messenger((new SensingHandler((getApplicationContext()))));
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent){
        //ファイル名
        //センサ種類
        //最初のアノテーション

        String startName = intent.getCharSequenceExtra(getString(R.string.start_name)).toString();
        String endName = intent.getCharSequenceExtra(getString(R.string.end_name)).toString();

        Station[] route = new RouteExtractorImpl(this).getUsingRoute(startName,endName);
        RouteScheduler scheduler = new RouteSchedulerImpl(route);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        AvailableSensorData[] sensorDatas = new AvailableSensorData[]{AvailableSensorData.ACC,AvailableSensorData.MAG};
        for(AvailableSensorData sensorData:sensorDatas) {
            SensorEventListener listener = new SensorListenerImpl(sensorData.getType(), INERTIAL_FREQUENCY_MILLIS);
            sensorListeners.add(listener);
            sensorManager.registerListener(listener, sensorManager.getDefaultSensor(sensorData.getType()), SensorManager.SENSOR_DELAY_FASTEST);
        }


        return messenger.getBinder();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        for(SensorEventListener listener: sensorListeners){
            if(listener instanceof SensorListenerImpl){
                ((SensorListenerImpl) listener).shutdown();
            }
            sensorManager.unregisterListener(listener);
        }


    }


    /**
     * クライアントから送られてきたメッセージを受け取るハンドラクラス<br>
     */
    class SensingHandler extends Handler{
        private Context context;

        public SensingHandler(Context cont){
            context = cont;
        }

        @Override
        public void handleMessage(Message message){
            Log.d("handleMessage",message.toString());
            switch (message.what){
                case HandlerTypes.ANNOTATION_CHANGE:
                   break;
                case HandlerTypes.WALKING_TYPE_CHANGE:
                    /*これに対応するラジオグループを作成した場合は記述*/
                    break;
                default:
                    break;
            }
        }

    }

    /**
     * ファイル名にYYYYmmDDhhMMで日付を付加する
     * @param target ファイル名
     * @param isJoin 日付を付加するかどうか
     * @return 日付を付加したファイル名(文字列型)
     */
    private String joinDate(String target,boolean isJoin){
        if(isJoin){
            //現在日時取得
            Calendar c = Calendar.getInstance();

            //フォーマットを指定
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
            String result = target +getString(R.string.delimiter) +sdf.format(c.getTime());
            return result;
        }
        return target;
    }

}
