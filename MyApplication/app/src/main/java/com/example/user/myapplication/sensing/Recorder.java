package com.example.user.myapplication.sensing;

/**
 * データ書き込みに関するインタフェース<br>
 * 初期verとしてCSVRecorderを実装．将来的にはjsonファイルなどを取り扱いたい．
 * Created by user on 2016/06/06.
 */
public interface Recorder {

    /**
     * 記録時のラベルの変更を受け付けるメソッド
     * @param annotation
     */
    public void setAnnotation(String annotation);

    /**
     * ファイルクローズ処理のためのメソッド
     */
    public void close();

    /**
     * ファイルオープン処理のためのメソッド
     */
    public void open();

    /**
     * ファイル書き込み処理のためのメソッド<br>
     * Object配列で与える
     * @param objects 書き込む要素の集合
     */
    public void record(Object... objects);


}