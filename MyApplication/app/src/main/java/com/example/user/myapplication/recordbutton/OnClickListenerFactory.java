package com.example.user.myapplication.recordbutton;

import android.app.Activity;
import android.content.ServiceConnection;
import android.view.View;
import android.widget.TextView;

/**
 * Created by user on 2016/06/12.
 */
public class OnClickListenerFactory {
    private final int recordButtonId;
    private final int stopButtonId;

    public OnClickListenerFactory(int recordButtonId, int stopButtonId){
        this.recordButtonId = recordButtonId;
        this.stopButtonId = stopButtonId;
    }

    public View.OnClickListener createRecordListener(Activity activity, ServiceConnection conn){
        return new RecordListener(activity,conn);
    }

    public View.OnClickListener createTextChangeListener(TextView edit1, TextView edit2, int buttonId){
        return new TextChangeListener(edit1,edit2,buttonId);
    }


}
