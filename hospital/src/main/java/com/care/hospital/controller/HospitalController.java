package com.care.hospital.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.care.hospital.entity.Patient;
import com.care.hospital.service.PatientService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/hospital")
public class HospitalController {
	

	@GetMapping("/home")
	public String homePage() {
		return "homepage";
	}
	

	@GetMapping("/aboutUs")
	public String aboutUs() {
		return "AboutUs";
	}
	
	@GetMapping("/contact")
	public String contact() {
		return "Contact";
	}
	
	@GetMapping("/patientLoginForm")
	public String patientLoginForm() {
		return "patientLoginForm";
	}
	
	@GetMapping("/doctorLoginForm")
	public String doctorLoginForm() {
		return "doctorLoginForm";
	}
	
	@PostMapping("/registerNewPatientForm")
	public String patientRegistrationForm(Model model) {
		
		Patient patient = new Patient();	
		model.addAttribute("patient", patient);
		return "patientRegistrationForm";
	}
	
}
