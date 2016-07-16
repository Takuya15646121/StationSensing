package com.example.user.myapplication.recordbutton;

import android.view.View;
import android.widget.TextView;

/**
 * Created by user on 2016/06/10.
 */
class TextChangeListener implements View.OnClickListener{

    private final TextView first;
    private final TextView second;
    private final int id;

    public TextChangeListener(TextView first,TextView second,int id){
        this.first = first;
        this.second = second;
        this.id = id;
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==id){
            CharSequence firstText = first.getText();
            CharSequence secondText = second.getText();

            first.setText(secondText);
            second.setText(firstText);

        }
    }
}
