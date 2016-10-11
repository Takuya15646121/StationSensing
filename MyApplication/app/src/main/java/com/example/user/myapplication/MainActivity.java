package com.example.user.myapplication;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.myapplication.annotation.AnnotationListener;
import com.example.user.myapplication.recordbutton.OnClickListenerFactory;

import java.util.List;

/**
 * メイン UI<br>
 *     TODO:記録状態の可視化
 *     TODO:履歴から呼び出せるようにする
 *     TODO:着座・起立などの条件を同時保存しておけるようにする
 */
public class MainActivity extends AppCompatActivity implements ServiceConnection{

    private Messenger messenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OnClickListenerFactory clickListenerFactory = new OnClickListenerFactory(R.id.button_record,R.id.button_stop);

        View.OnClickListener recordListener = clickListenerFactory.createRecordListener(this,this);
        ((Button)findViewById(R.id.button_record)).setOnClickListener(recordListener);
        Button stopButton = (Button)findViewById(R.id.button_stop);
        stopButton.setOnClickListener(recordListener);
        stopButton.setEnabled(false);

        Button buttonChange = (Button)findViewById(R.id.button_change);
        View.OnClickListener onClickChange =clickListenerFactory.createTextChangeListener(
                        (TextView)findViewById(R.id.editText_start),
                        (TextView)findViewById(R.id.editText_stop),
                        R.id.button_change
                );
        buttonChange.setOnClickListener(onClickChange);


    }

    @Override
    protected void onResume(){
        super.onResume();

        //記録中に画面が再表示された場合に停止ボタンをアクティブにする
        if(isServiceAlive(SensingService.class)){
            Button stopButton = ((Button)findViewById(R.id.button_stop));
            stopButton.setEnabled(true);
        }

    }


    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {

        messenger = new Messenger(service);

        Toast.makeText(this,"ServiceStart", Toast.LENGTH_SHORT).show();

    }

    /**
     * 呼ばれるタイミングは予期しないエラーでサービスが落ちた時
     * @param name
     */
    @Override
    public void onServiceDisconnected(ComponentName name) {
        Toast.makeText(this,"ServiceEnd", Toast.LENGTH_SHORT).show();
    }


    protected boolean isServiceAlive(Class<?> cls){
        ActivityManager am = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningService = am.getRunningServices(Integer.MAX_VALUE);
        for(ActivityManager.RunningServiceInfo i:runningService){
            if(cls.getName().equals(i.service.getClassName())){
                return true;
            }
        }
        return false;
    }
}
