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
		//TODO:�����ɃX�L�b�g������
		String start = "����";
		String end = "�r��";
		
		//�w���̓o�^
		RouteDataExtractor rde = new RouteDataExtractor();
		rde.initStationIdList(start, end);
		List<Integer> idList = rde.getStationList();
		for(int id:idList){
			System.out.println(id);
		}
		
		//���ʐ��䕔���̏�����
		AcquisitionController controller = new AcquisitionControllerImpl(idList, 4);
		boolean canLocate = controller.canAcquisition(true, System.currentTimeMillis());
		
		//���ޕ����̏�����
		File clsFile = new File("model/weka.model");
		File arffFile = new File("model/frame.arff");
		MovingClassifier movingCls = new MovingClassifier(clsFile, arffFile);
		WindowWrapper wrapper = new WindowWrapper(128, 0.5, 50);
		
		//�t�@�C�����X�i�̏�����
		File accFile = new File("");
		File magFile = new File("");
		SensingListener sensingListener = new SensingListener(accFile, magFile);
		
		//���ނ̎��s
		int classificationResult = sensingListener.stepOver(movingCls, wrapper);
		canLocate = controller.canAcquisition(classificationResult == 1, sensingListener.getTime());
		if(canLocate){
			System.out.println("�擾�ł��܂�");
		}
		
	}
}
