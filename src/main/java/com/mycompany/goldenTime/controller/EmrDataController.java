package com.mycompany.goldenTime.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mycompany.goldenTime.command.ActualListCommand;
import com.mycompany.goldenTime.command.CongestionCommand;
import com.mycompany.goldenTime.command.NeedListCommand;

@Controller
public class EmrDataController {
	
	@Autowired
	ActualListCommand actualListCommand;
	@Autowired
	NeedListCommand needCommand;
	@Autowired
	CongestionCommand congestionCommand;
	
	@RequestMapping("/doctor")
	public String doctor() {
		return "doctor";
	}
		
	@RequestMapping("/emrData")
	public @ResponseBody Map<String, Object> emrData() {
		System.out.println("emrData mapping, emrData() 출력");
		 Map<String, Object> jsonData = new HashMap<String, Object>();
		    jsonData.put("actualData", actualListCommand.actualListCommand());
		    jsonData.put("needData", needCommand.needList());
		    jsonData.put("congestData", congestionCommand.congestionList());
		return jsonData;
	}
}