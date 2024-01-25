package com.care.hospital.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.care.hospital.entity.Appointment;
import com.care.hospital.entity.Bill;
import com.care.hospital.entity.BillItem;
import com.care.hospital.entity.Doctor;
import com.care.hospital.entity.Item;
import com.care.hospital.service.AppointmentService;
import com.care.hospital.service.BillItemService;
import com.care.hospital.service.BillService;
import com.care.hospital.service.DoctorService;
import com.care.hospital.service.ItemService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/doctor")
public class DoctorController {
	
	@Autowired
	DoctorService doctorService;
	@Autowired
	AppointmentService appointmentService;
	@Autowired
	ItemService itemService;
	@Autowired
	BillService billService;
	
	
	/*
	 * Validates Doctor login using email and password provided
	 */
	
	@PostMapping("/login")
	public String doctorLogin(@RequestParam("email") String email, @RequestParam("password") String password, Model model, HttpSession session, HttpServletResponse response) {
			
		response.setHeader("Cache-Control", "no-store");
		
		Doctor loggedInDoctor = doctorService.findDoctorByEmailAndPassword(email, password);	
		
		if(loggedInDoctor != null && (loggedInDoctor.getEmail().equalsIgnoreCase(email) && loggedInDoctor.getPassword().equals(password))){
			session.setAttribute("loggedInDoctor", loggedInDoctor);
			session.setAttribute("loggedInDoctorId", loggedInDoctor.getDoctorId());
			return "doctorhome";
		}
		else {
			model.addAttribute("error", "Invalid Username or Password");
			return "doctorLoginForm";
		}	
	}
	
	
	/*
	 * Removes session attributes, invalidates session and redirects to HomePage
	 */
	
	@GetMapping("/logout")
    public String logout(HttpSession session) {
		session.removeAttribute("loggedInDoctor");
		session.invalidate();
        return "redirect:/hospital/home";  
    }
	
	/*
	 * Return's doctor's Home page
	 */
	
	@GetMapping("/home")
	public String homePage(HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-store");
		return "doctorhome";
	}
	
	/*
	 * List all appointments related to doctor
	 */
	
	@GetMapping("/showAppointments")
	public String showAppointments(Model model, HttpSession session, HttpServletResponse response) {
		
		response.setHeader("Cache-Control", "no-store");
		
		if( session.getAttribute("loggedInDoctorId") == null) {
			return "doctorLoginForm";
		}

		Integer doctorId = (Integer) session.getAttribute("loggedInDoctorId");
		List<Appointment> appointments = appointmentService.getAppointmentsByDoctorId(doctorId);
		model.addAttribute("docappointments", appointments);		
		return "docappointments";
	}
	
	
	/*
	 * Update the status of the Appointment to Scheduled to Completed
	 */
	
	@GetMapping("/updateAppointmentStatus")
	public String updateAppointmentStatus(@RequestParam("appointmentId") Integer apptId) {
		appointmentService.updateStatus(apptId);
		return "redirect:/doctor/showAppointments";
	}
	
	
	/*
	 * Create Prescription(Bill)
	 */
	
	@GetMapping("/createPrescription")
	public String createPrescription(@RequestParam("appointmentId") Integer apptId, Model model, HttpServletResponse response) {
		
		response.setHeader("Cache-Control", "no-store");
		
		List<Item> allItems = itemService.getAllItems();
		model.addAttribute("allItems", allItems);
		
		Bill availableBill = billService.getBillByAppointmentId(apptId);
		
		Appointment appointment = appointmentService.getAppointmentById(apptId);
		
		Bill bill;
		
		if(availableBill == null) {
			bill = new Bill();
			bill.setAppointment(appointment);
			bill.setStatus("Pending");
			bill.setPatient(appointment.getPatient());
			bill = billService.save(bill);
		}
		else {
			bill = availableBill;
		}
			
		model.addAttribute("billId", bill.getBillId());
		
		model.addAttribute("addedItems", bill.getBillItems());
	
		return "createPrescription";
		
	}
	
	
	/*
	 * Add item to the created Bill
	 */
	
	@PostMapping("/addItem")
	public String addItem(@RequestParam("itemName") String itemName, @RequestParam("quantity") Integer quantity, 
			@RequestParam("billId") Integer billId, Model model, RedirectAttributes attr, HttpServletResponse response) {
		
		response.setHeader("Cache-Control", "no-store");
		
		Bill bill = billService.getBillById(billId);
		
		Item item = itemService.getItemByName(itemName);
		BillItem billItem = new BillItem(item,quantity);
		billItem.setBill(bill);
		
		bill.addItem(billItem);
		
		billService.save(bill);
	
	    attr.addFlashAttribute("addedItems", bill.getBillItems());

	    return "redirect:/doctor/createPrescription?appointmentId="+bill.getAppointment().getAppointmentId();
	}
	
	/*
	 * Save Bill and redirect to Appointments Page
	 */
	
	@GetMapping("/savePrescription")
	public String savePrescription() {
		return "redirect:/doctor/showAppointments";
	}
	

}
