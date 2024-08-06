package com.mycompany.goldenTime.command;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mycompany.goldenTime.dao.ERepository;
import com.mycompany.goldenTime.model.RealTimeEmrVO;

@Component
public class RealTimeCommand {
	
	@Autowired
	ERepository repository;
	
	public List<RealTimeEmrVO> realTimeList() {
		return repository.realTimeList();
	}
	
	

}