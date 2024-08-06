package com.mycompany.goldenTime.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DoctorController {
	//doctor.jsp 로 연결 
	@GetMapping("/doctor")
	public String connectDoctor(Model model) {
		return "/doctor";
	}
	@GetMapping("/person")
	public String connectPerson(Model model) {
		return "/person";
	}
}
