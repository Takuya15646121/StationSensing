package com.example.user.myapplication.recordbutton;

import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by user on 2016/06/06.
 */
final class CheckBoxGroup {
    private final List<CheckBox> checkList;

    CheckBoxGroup(CheckBox[] checkBoxes){
        this.checkList = Arrays.asList(checkBoxes);
    }

    final boolean[] getCheckedResult(){
        boolean[] checkedResult = new boolean[checkList.size()];

        for(int i=0;i<checkedResult.length;i++){
            checkedResult[i] = checkList.get(i).isChecked();
        }

        return checkedResult;

    }

    final List<Integer> getCheckedId(){
        List<Integer> checkedIdList = new ArrayList<>();
        for(CheckBox check:checkList){
            if(check.isChecked()){
                checkedIdList.add(check.getId());
            }
        }
        return checkedIdList;
    }



}
