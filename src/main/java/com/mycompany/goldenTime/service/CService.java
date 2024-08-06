package com.mycompany.goldenTime.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mycompany.goldenTime.dao.CRepository;
import com.mycompany.goldenTime.model.CDataVO;
import com.mycompany.goldenTime.model.CRegressionVO;

@Component
public class CService {
	@Autowired
    private CRepository repository;

    public float getMonthlyCongestion(int year, int month) {
        // CRepository를 통해 월별 혼잡도 검색
        CDataVO data = repository.getNationwideData(month);
        CRegressionVO regression = repository.getNationwideRegression();
        return calculateCongestion(regression, data, year, month);
    }

    public float getRegionCongestion(int year, int month, String regionName, String regionEngName) {
        // CRepository를 통해 지역별 혼잡도 검색
        CDataVO data = repository.getRegionData(year, month, regionName);
    	
        if(data == null) {
        	System.out.println("No data found for the given region and date.");
            return 0;
        }
        
        CRegressionVO regression = repository.getRegionRegression(regionEngName);
        if (regression == null) {
            System.out.println("No regression data found for the given region.");
            return 0; 
        }
        return calculateCongestion(regression, data, year, month);
    }

    private float calculateCongestion(CRegressionVO regression, CDataVO data, int year, int month) {

        // 로그 출력
        System.out.println("Regression Data: " + regression);
        System.out.println("Data: " + data);
        
        // 회귀식 계수 가져오기
        float RConstant = regression.getConstant();
        System.out.println("회귀식>>");
		System.out.println("상수 : " + RConstant);
        float RYear = regression.getYear();
        System.out.println("년도 상수 : " + RYear);
        float RMonth = regression.getMonth();
        System.out.println("월 상수 : " + RMonth);
        float RPatient_119 = regression.getPatient_119();
        System.out.println("이송환자수 상수 : " + RPatient_119);
        float RPatient_ER = regression.getPatient_ER();
        System.out.println("응급실이용환자수 상수 : " + RPatient_ER);
        float RTA = regression.getTA();
        System.out.println("교통사고건수 상수 : " + RTA);
		System.out.println();
        
        // 데이터 값 가져오기
        int patient_119 = data.getPatient_119();
        System.out.println("이송환자수 : " + patient_119);
        int patient_ER = data.getPatient_ER();
        System.out.println("응급실이용환자수 : " + patient_ER);
        int TA = data.getTA();
        System.out.println("교통사고건수 : " + TA);
        
        // 혼잡도 계산
        float congestionValue = RConstant + RYear * year + RMonth * month + 
                RPatient_119 * patient_119 + RPatient_ER * patient_ER + RTA * TA;
        
        return congestionValue;
        
    }

}
