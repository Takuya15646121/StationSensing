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

import com.example.user.myapplication.sensing.CSVRecorder;
import com.example.user.myapplication.sensing.SensorListenerImpl;
import com.example.user.myapplication.sensing.Recorder;

/**
 * Created by user on 2016/06/06.
 */
public class SensingService extends Service{


    private static final long INERTIAL_FREQUENCY_MILLIS = 20;
    private static final long LOCATION_FREQUENCY_MILLIS = 1000;

    private List<Recorder> sensorRecorders;
    private Messenger messenger;
    private SensorManager sensorManager;
    private List<SensorEventListener> sensorListeners;

    @Override
    public void onCreate(){
        super.onCreate();
        this.sensorRecorders = new ArrayList<>();
        this.sensorListeners = new ArrayList<>();
        messenger = new Messenger((new SensingHandler((getApplicationContext()))));
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent){
        //ファイル名
        //センサ種類
        //最初のアノテーション
        String filePath = joinDate(intent.getStringExtra(getString(R.string.file_name)),true);

        int[] checkedIdList = intent.getIntArrayExtra(getString(R.string.sensor_type));

        String firstAnnotation = intent.getStringExtra(getString(R.string.annotation));

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        for(int checkedId:checkedIdList){
            AvailableSensorData sensorData = AvailableSensorData.getMatchedInstance(checkedId);
            if(sensorData==null){
                continue;
            }
            try {
                Recorder recorder = new CSVRecorder(filePath+ getString(R.string.delimiter)+sensorData.getName());
                recorder.open();

                recorder.setAnnotation(firstAnnotation);
                sensorRecorders.add(recorder);
                SensorEventListener listener = new SensorListenerImpl(recorder,sensorData.getType(),INERTIAL_FREQUENCY_MILLIS);
                sensorListeners.add(listener);
                sensorManager.registerListener(listener,sensorManager.getDefaultSensor(sensorData.getType()),SensorManager.SENSOR_DELAY_FASTEST);
            } catch (IOException e) {
                e.printStackTrace();
            }

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
        for(Recorder recorder: sensorRecorders){
            recorder.close();
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
                    for(Recorder recorder:SensingService.this.sensorRecorders){
                        recorder.setAnnotation(message.obj.toString());
                    }
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
