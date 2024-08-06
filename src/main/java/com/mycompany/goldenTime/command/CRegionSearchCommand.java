package com.mycompany.goldenTime.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.mycompany.goldenTime.model.CDataVO;
import com.mycompany.goldenTime.model.CRegressionVO;
import com.mycompany.goldenTime.service.CService;

@Component
public class CRegionSearchCommand {
	
	@Autowired
	@Qualifier("CService")
	CService service;
	
	public float execute(int year, int month, String regionName, String regionEngName) {
		System.out.println("RegionSearchCommand 실행");
//		System.out.println("현재 년/월/지역 : " + year + "/" + month + "/" + region);
		System.out.println("현재 년/월/지역 : " + year + "/" + month + "/" + regionName + "/" + regionEngName);
		
		// 서비스를 통해 지역별 혼잡도 검색
        float congestionValue = service.getRegionCongestion(year, month, regionName, regionEngName);
        
        System.out.println("혼잡도 : " + congestionValue);
        return congestionValue;
	}
	
}
