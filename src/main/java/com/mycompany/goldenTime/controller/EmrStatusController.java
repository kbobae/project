package com.mycompany.goldenTime.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mycompany.goldenTime.command.DistanceCommand;
import com.mycompany.goldenTime.command.EmrInfoCommand;
import com.mycompany.goldenTime.command.RealTimeCommand;
import com.mycompany.goldenTime.model.DistanceToERVO;
import com.mycompany.goldenTime.model.RealTimeEmrVO;
import com.mycompany.goldenTime.model.EmrInfoVO;

@Controller
public class EmrStatusController {
	
	@Autowired
	RealTimeCommand realTimeCommand;
	@Autowired
	EmrInfoCommand emrInfoCommand;
	@Autowired
	DistanceCommand emrRecommandCommand;
	
	@RequestMapping("/emrStatus")
	public String lemrStatus() {
		return "emrStatus";
	}
	
	@RequestMapping("/realTimeEmr")
	public @ResponseBody List<RealTimeEmrVO> realTimeEmr() {
		return realTimeCommand.realTimeList();
	}
	
	@RequestMapping("/emrInfo")
	public @ResponseBody List<EmrInfoVO> emrInfo(@RequestParam("area") String area) {
		List<EmrInfoVO> result = emrInfoCommand.emrInfo(area);
		System.out.println(area + " response");
		return result;
	}
	
	@RequestMapping("/distanceToER")
	public @ResponseBody List<Object> emrRecommand(@RequestBody List<DistanceToERVO> distancesToER) {
		return emrRecommandCommand.emrRecommand(distancesToER);
	}

}
