package com.mycompany.goldenTime.model;

import org.springframework.stereotype.Component;

@Component
public class CDataVO {
	//emergency, transport, accidents 테이블 통합
	
	private int year;
	private int month;
	private String region;
	private int patient_119;
	private int patient_ER;
	private int TA;
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public int getPatient_119() {
		return patient_119;
	}
	public void setPatient_119(int patient_119) {
		this.patient_119 = patient_119;
	}
	public int getPatient_ER() {
		return patient_ER;
	}
	public void setPatient_ER(int patient_ER) {
		this.patient_ER = patient_ER;
	}
	public int getTA() {
		return TA;
	}
	public void setTA(int tA) {
		this.TA = tA;
	}
	
	@Override
	public String toString() {
		return year + "년 " + month + "월 Data";
	}

}
