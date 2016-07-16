package com.example.user.myapplication.annotation;

import android.app.Activity;
import android.os.Messenger;
import android.support.annotation.CallSuper;

/**
 * Created by user on 2016/06/13.
 */
public class AnnotationPushingNotifyingListener extends AnnotationListener{
    /**
     * コンストラクタ．
     * @param activity メインUIのオブジェクト
     * @param message  バインド先とのMessengerオブジェクト
     * @param annotationType サービスのHandlerに送信する際のkey
     */
    public AnnotationPushingNotifyingListener(Activity activity, Messenger message, int annotationType) {
        super(activity, message,annotationType);
        throw new UnsupportedOperationException();
    }

    /**
     * OnCheckedChangedメソッドがコールされた際に呼ばれる
     * @param annotation 変化したアノテーション
     */
    @CallSuper
    protected void sendCheckedChange(CharSequence annotation){
        super.sendCheckedChange(annotation);
        sendChangeByPushing(annotation);
    }

    /**
     * 変化したアノテーションを他端末にプッシュする
     * HttpRequest等によって送る
     * @param annotation
     */
    private void sendChangeByPushing(CharSequence annotation){
        throw new UnsupportedOperationException();
    }
}
