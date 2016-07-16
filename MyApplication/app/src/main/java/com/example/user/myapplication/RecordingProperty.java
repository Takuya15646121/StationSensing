package com.example.user.myapplication;

import java.util.Arrays;

/**
 * センサデータ記録時のファイル名、ディレクトリの命名規則を定義したクラス<br>
 * Created by user on 2016/06/12.
 */
public final class RecordingProperty {
    private RecordingProperty(){}

    private static final int[] ALL_EDIT_TEXT_ID = {R.id.editText_start,R.id.editText_stop,R.id.editText_name};
    private static final int[] FILE_NAME_ID={R.id.editText_start,R.id.editText_stop,R.id.editText_name};
    private static final int[] PARENT_DIR_NAME_ID={R.id.editText_start,R.id.editText_stop};

    public static final int RECORD_BUTTON_ID = R.id.button_record;
    public static final int STOP_BUTTON_ID = R.id.button_stop;

    /**
     * ファイル名の命名規則<br>
     * R.id.editXXXXを各要素として指定する
     * @return
     */
    public static int[] getFileNameID(){
        return Arrays.copyOf(FILE_NAME_ID,FILE_NAME_ID.length);
    }

    /**
     * ファイルの親ディレクトリになるフォルダの命名規則<br>
     * R.id.editXXXを各要素として指定する
     * @return
     */
    public static int[] getParentDirNameId(){
        return Arrays.copyOf(PARENT_DIR_NAME_ID,PARENT_DIR_NAME_ID.length);
    }

    public static int[] getAllEditTextId(){
        return Arrays.copyOf(ALL_EDIT_TEXT_ID,ALL_EDIT_TEXT_ID.length);
    }

}
