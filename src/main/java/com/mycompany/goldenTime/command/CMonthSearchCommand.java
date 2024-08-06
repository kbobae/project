package com.mycompany.goldenTime.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.mycompany.goldenTime.dao.CRepository;
import com.mycompany.goldenTime.model.CDataVO;
import com.mycompany.goldenTime.model.CRegressionVO;
import com.mycompany.goldenTime.service.CService;

@Component
public class CMonthSearchCommand {   
	
	@Autowired
	@Qualifier("CService")
	CService service;
	
	public float execute(int year, int month) {
		System.out.println("monthSearchCommand 실행");
		System.out.println("현재 년/월 :  " + year + "/" + month);
		
		// 서비스를 통해 월별 혼잡도 검색
        float congestionValue = service.getMonthlyCongestion(year, month);
		
        System.out.println("혼잡도 : " + congestionValue);
        return congestionValue;
	}

}
