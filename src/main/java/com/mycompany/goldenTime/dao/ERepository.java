package com.mycompany.goldenTime.dao;

import java.util.List;

import com.mycompany.goldenTime.model.ActualVO;
import com.mycompany.goldenTime.model.CongestionVO;
import com.mycompany.goldenTime.model.NeedVO;
import com.mycompany.goldenTime.model.RealTimeEmrVO;
import com.mycompany.goldenTime.model.EmrInfoVO;

public interface ERepository {
	public List<ActualVO> actualList();
	public List<NeedVO> needList();
	public List<CongestionVO> congestionList();
	public List<RealTimeEmrVO> realTimeList();
	public List<EmrInfoVO> emrInfo(String area);

}
