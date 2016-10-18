package com.example.main;

import java.util.List;

import com.example.acqusition.AcquisitionControllerImpl;
import com.example.controller.AcquisitionController;
import com.example.mysqltest.RouteDataExtractor;

public class Main {
	public static void main(String[] args){
		//TODO:‚±‚±‚ÉƒXƒLƒbƒg‚ğ‘‚­
		String start = "´£";
		String end = "’r‘Ü";
		
		//‰wî•ñ‚Ì“o˜^
		RouteDataExtractor rde = new RouteDataExtractor();
		rde.initStationIdList(start, end);
		List<Integer> idList = rde.getStationList();
		for(int id:idList){
			System.out.println(id);
		}
		
		//‘ªˆÊ§Œä•”•ª‚Ì‰Šú‰»
		AcquisitionController controller = new AcquisitionControllerImpl(idList, 4);
		boolean canLocate = controller.canAcquisition(true, System.currentTimeMillis());
		
		
		
		
	}
}
