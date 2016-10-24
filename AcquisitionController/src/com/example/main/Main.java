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
		String start = "���";
		String end = "�l����";
		
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
		File accFile = new File("data/���-�l����-���[�U1-201609101425-acc.csv");
		File magFile = new File("data/���-�l����-���[�U1-201609101425-mag.csv");
		SensingListener sensingListener = new SensingListener(accFile, magFile);
		
		int i=0;
		//���ނ̎��s
		while(true){
		int classificationResult = sensingListener.stepOver(movingCls, wrapper);
		if(classificationResult == 3){
			canLocate = controller.canAcquisition(false, sensingListener.getTime());
		}else if(classificationResult ==2){
			canLocate = controller.canAcquisition(classificationResult == 2, sensingListener.getTime());
			if(canLocate){
				System.out.println("�擾�ł��܂�"+Integer.toString(i++));
			}
		}
		
		}
		
	}
}
