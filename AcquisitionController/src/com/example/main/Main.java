package com.example.main;

import java.io.File;
import java.util.List;

import com.example.acqusition.AcquisitionControllerImpl;
import com.example.controller.AcquisitionController;
import com.example.moving.MovingClassifier;
import com.example.moving.SensingListener;
import com.example.moving.WindowWrapper;
import com.example.mysqltest.RouteDataExtractor;

public class Main {
	public static void main(String[] args){
		//TODO:ここにスキットを書く
		String start = "清瀬";
		String end = "池袋";
		
		//駅情報の登録
		RouteDataExtractor rde = new RouteDataExtractor();
		rde.initStationIdList(start, end);
		List<Integer> idList = rde.getStationList();
		for(int id:idList){
			System.out.println(id);
		}
		
		//測位制御部分の初期化
		AcquisitionController controller = new AcquisitionControllerImpl(idList, 4);
		boolean canLocate = controller.canAcquisition(true, System.currentTimeMillis());
		
		//分類部分の初期化
		File clsFile = new File("model/weka.model");
		File arffFile = new File("model/frame.arff");
		MovingClassifier movingCls = new MovingClassifier(clsFile, arffFile);
		WindowWrapper wrapper = new WindowWrapper(128, 0.5, 50);
		
		//ファイルリスナの初期化
		File accFile = new File("");
		File magFile = new File("");
		SensingListener sensingListener = new SensingListener(accFile, magFile);
		
		//分類の実行
		int classificationResult = sensingListener.stepOver(movingCls, wrapper);
		canLocate = controller.canAcquisition(classificationResult == 1, sensingListener.getTime());
		if(canLocate){
			System.out.println("取得できます");
		}
		
	}
}
