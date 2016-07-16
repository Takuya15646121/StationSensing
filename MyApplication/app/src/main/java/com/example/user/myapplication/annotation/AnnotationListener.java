package com.example.user.myapplication.annotation;

import android.app.Activity;
import android.app.ActivityManager;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.CallSuper;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.user.myapplication.HandlerTypes;
import com.example.user.myapplication.R;

/**
 * ラジオボタン変更時のラベル変更を行うためのクラス<br>
 * UIからの変更を受け付け，その結果をサービスへ送信する
 * Created by user on 2016/06/06.
 */
public class AnnotationListener implements RadioGroup.OnCheckedChangeListener {

    private final Messenger messenger;
    private final Activity activity;
    private final int annotationType;
    /**
     * コンストラクタ．
     * @param activity メインUIのオブジェクト
     * @param message バインド先とのMessengerオブジェクト
     */
    public AnnotationListener(Activity activity,Messenger message,int annotationType){
        this.activity = activity;
        this.messenger = message;
        this.annotationType = annotationType;
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        sendCheckedChange(((RadioButton)activity.findViewById(checkedId)).getText());
    }

    @CallSuper
    protected void sendCheckedChange(CharSequence annotation){
        sendChangeToService(annotation);
    }


    private void sendChangeToService(CharSequence annotation){
        try {
            messenger.send(Message.obtain(null, annotationType,annotation));
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }


}
