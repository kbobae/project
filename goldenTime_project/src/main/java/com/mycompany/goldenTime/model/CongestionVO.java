package com.mycompany.goldenTime.model;

import org.springframework.stereotype.Component;

@Component
public class CongestionVO {
	
	private int year;
	private double congestion;

	public CongestionVO() {
		
	}
	
	public CongestionVO(int year, double congestion) {
		this.year = year;
		this.congestion = congestion;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public double getCongestion() {
		return congestion;
	}

	public void setCongestion(double congestion) {
		this.congestion = congestion;
	}
}