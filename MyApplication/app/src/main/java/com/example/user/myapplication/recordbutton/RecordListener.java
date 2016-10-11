package com.example.user.myapplication.recordbutton;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.user.myapplication.AvailableSensorData;
import com.example.user.myapplication.RecordingProperty;
import com.example.user.myapplication.R;
import com.example.user.myapplication.SensingService;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 記録開始/停止処理を行うクラス<br>
 * 記録開始時にサービスをバインドしてセンシングを行う<br>
 * TODO:R.id.XXXXでハードコーディングされているので改善する
 * Created by user on 2016/06/06.
 */
class RecordListener implements View.OnClickListener{


    private final Activity activity;
    private final ServiceConnection connection;
    private final TextView startText;
    private final TextView endText;
    private final Button recordButton;
    private final Button stopButton;

    /**
     * コンストラクタ(引数がとても多いので分散させる
     * @param activity
     * @param conn
     *
     */
    public RecordListener(Activity activity,ServiceConnection conn){
        this.activity = activity;
        this.connection = conn;

        int[] editTextId = RecordingProperty.getParentDirNameId();
        this.startText = (TextView)activity.findViewById(editTextId[0]);
        this.endText = (TextView) activity.findViewById(editTextId[1]);

        this.recordButton = (Button) activity.findViewById(RecordingProperty.RECORD_BUTTON_ID);
        this.stopButton = (Button)activity.findViewById(RecordingProperty.STOP_BUTTON_ID);


    }

    @Override
    public void onClick(View v) {
        int recordId = recordButton.getId();
        int stopId = stopButton.getId();
        if(v.getId() == recordId){
            if(isServiceAlive(SensingService.class)==false){
                Intent intent = new Intent(activity,SensingService.class);


                CharSequence startName = this.startText.getText();
                CharSequence endName = this.endText.getText();

                intent.putExtra(this.activity.getString(R.string.start_name),startName);
                intent.putExtra(this.activity.getString(R.string.end_name),endName);

                activity.bindService(intent,connection, Context.BIND_AUTO_CREATE);
                recordButton.setEnabled(false);
                stopButton.setEnabled(false);

            }
        }

        if(v.getId() == stopId){
            if(isServiceAlive(SensingService.class)==true){
                activity.unbindService(connection);
                recordButton.setEnabled(true);
                stopButton.setEnabled(false);
            }
        }

    }

    private CheckBox[] getSensorCheckBoxArray(Activity activity){
        AvailableSensorData[] sensorDataArray = AvailableSensorData.values();
        List<CheckBox> checkBoxes = new ArrayList<>();
        for(AvailableSensorData sensorData:sensorDataArray){
            checkBoxes.add((CheckBox)activity.findViewById(sensorData.getId()));
        }
        return checkBoxes.toArray(new CheckBox[0]);
    }

    private TextView[] getEditTextView(Activity activity,int[] fileNameId){
        List<TextView> textViews = new ArrayList<>();
        for(int id:fileNameId){
            textViews.add((TextView)activity.findViewById(id));
        }
        return textViews.toArray(new TextView[0]);
    }

    protected int[] getPrimitiveArray(Integer[] data){
        if(data.length==0){
            return null;
        }
        int[] primitiveArray = new int[data.length];
        for(int i=0;i<primitiveArray.length;i++){
            primitiveArray[i] = data[i];
        }
        return primitiveArray;
    }


    private File makeTargetDir(String targetDirName){

        File externalStorage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File parentDir = joinDir(externalStorage,activity.getString(R.string.folder));
        File targetDir = joinDir(parentDir,targetDirName);
        return targetDir;

    }

    protected File joinDir(File parent,String target){

        if(target==null){
            return parent;
        }

        File targetDir = new File(parent,target);
        if(targetDir.exists()){
            return targetDir;
        }
        targetDir.mkdir();
        return targetDir;
    }



    protected boolean isServiceAlive(Class<?> cls){
        ActivityManager am = (ActivityManager)activity.getSystemService(activity.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningService = am.getRunningServices(Integer.MAX_VALUE);
        for(ActivityManager.RunningServiceInfo i:runningService){
            if(cls.getName().equals(i.service.getClassName())){
                return true;
            }
        }
        return false;
    }

}
