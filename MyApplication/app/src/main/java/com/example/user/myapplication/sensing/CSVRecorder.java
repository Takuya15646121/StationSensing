package com.example.user.myapplication.sensing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * CSVファイルの記録クラス<br>
 * TODO:Webアップロード可能にする
 * Created by user on 2016/06/06.
 */
public class CSVRecorder implements Recorder {

    private static final String EXTENTION = ".csv";
    private BufferedWriter sensorDataWriter;
    private String annotation = "None";

    /**
     * コンストラクタ．ファイル名(拡張子は除く)受け取り，BufferedWriterを初期化する
     * @param fileName ファイル名(拡張子は除いた絶対パスで指定)
     * @throws IOException
     */
    public CSVRecorder(String fileName) throws IOException {
        File file = new File(fileName+EXTENTION);
        File parentDir = file.getParentFile();
        if(!parentDir.exists()){
            parentDir.mkdir();
        }
        file.createNewFile();
        this.sensorDataWriter = new BufferedWriter(new FileWriter(file,false));
    }


    @Override
    public void setAnnotation(String annotation) {
       this.annotation = annotation;
    }

    @Override
    public void close() {
        try {
            sensorDataWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void open() {

    }

    @Override
    public void record(Object... objects) {
        StringBuilder builder = new StringBuilder();
        for(Object obj:objects){
            if(builder.length() > 0){
                builder.append(",");
            }
            builder.append(obj.toString());
        }
        builder.append(",");
        builder.append(annotation);
        try {
            sensorDataWriter.write(builder.toString());
            sensorDataWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
