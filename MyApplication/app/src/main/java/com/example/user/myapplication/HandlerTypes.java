package com.example.user.myapplication;

/**
 * Messengerを介してHandler#handleMessegeへメッセージを送信するための引数の集合クラス<br>
 * 将来的にはXMLなどで一元管理すべきか
 * Created by user on 2016/06/10.
 */
public final class HandlerTypes {
    private HandlerTypes(){}

    /**
     * アノテーション(正解ラベル)が変化した際のキータイプ
     */
    public static final int ANNOTATION_CHANGE = 0;

    /**
     * 歩行状態が変化した際のキータイプ
     */
    public static final int WALKING_TYPE_CHANGE = 1;

}
