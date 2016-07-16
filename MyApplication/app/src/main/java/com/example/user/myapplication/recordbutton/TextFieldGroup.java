package com.example.user.myapplication.recordbutton;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.example.user.myapplication.R;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Main UIのテキスト入力部分の集合<br>
 * getJoinedNameメソッドで"-"で結合した文字列を返す<br>
 * Created by user on 2016/06/06.
 */
final class TextFieldGroup {

    private final List<TextView> texts;
    private final Context context;

    TextFieldGroup(Context context,TextView... texts){
        this.texts = Arrays.asList(texts);
        this.context = context;
    }

    @Nullable
    final String getJoinedName(){
        return getJoinedName(getTextIds());
    }

    @Nullable
    final String getJoinedName(int... viewIds){
        StringBuilder sb = new StringBuilder();
        for(int viewId:viewIds){
            for(TextView v:texts){
                if(v.getId() == viewId){
                    if(sb.length()>0){
                        sb.append(context.getString(R.string.delimiter));
                    }
                    sb.append(v.getText());
                }
            }
        }
        String resultStr = sb.toString();
        if(resultStr==null){
            return null;
        }
        return resultStr;
    }

    private int[] getTextIds(){
        int[] ids = new int[texts.size()];
        for(int i=0;i<ids.length;i++){
            ids[i] = texts.get(i).getId();
        }
        return ids;
    }

}
