package com.care.hospital.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.care.hospital.entity.Appointment;
import com.care.hospital.entity.Bill;
import com.care.hospital.entity.BillItem;
import com.care.hospital.entity.Doctor;
import com.care.hospital.entity.Patient;
import com.care.hospital.service.AppointmentService;
import com.care.hospital.service.BillService;
import com.care.hospital.service.DepartmentService;
import com.care.hospital.service.DoctorService;
import com.care.hospital.service.PatientService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/patient")
public class PatientController {
	
	@Autowired
	PatientService patientService;
	@Autowired
	AppointmentService appointmentService;
	@Autowired
	BillService billService;
	@Autowired
	DepartmentService deptService;
	@Autowired 
	DoctorService doctService;	
	
	/*
	 * Used while validating form data to trim empty spaces from the string
	 */
	
	@InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

	
	/*
	 * Validates Patient login using email and password
	 */
	
	@PostMapping("/login")
	public String patientLogin(@RequestParam("email") String email, @RequestParam("password") String password, Model model, HttpSession session, HttpServletResponse response) {
			
		response.setHeader("Cache-Control", "no-store");
		
		Patient loggedInPatient = patientService.findPatientByEmailAndPassword(email, password);	
		
		if(loggedInPatient != null && (loggedInPatient.getEmail().equalsIgnoreCase(email) && loggedInPatient.getPassword().equals(password))){
			session.setAttribute("loggedInPatient", loggedInPatient);
			session.setAttribute("loggedInPatientId", loggedInPatient.getPatientId());
			return "patienthome";
		}
		else {
			model.addAttribute("error", "Invalid Username or Password");
			return "patientLoginForm";
		}	
	}
	
	/*
	 * Returns patient's Homepage
	 */
	
	@GetMapping("/home")
	public String homePage(HttpServletResponse response, HttpSession session) {
		response.setHeader("Cache-Control", "no-store");
		if( session.getAttribute("loggedInPatientId") == null) {
			return "patientLoginForm";
		}
		return "patienthome";
	}
	
	/*
	 * Removes session attributes, Invalidates session and returns HomePage
	 */
	
	@GetMapping("/logout")
    public String logout(HttpSession session) {
		session.removeAttribute("loggedInPatient");
		session.invalidate();
        return "redirect:/hospital/home";  
    }
	

	/*
	 * Validates the Patient entity using Spring Validation. Valid entry of the entity is registered 
	 */
	
	@PostMapping("/register")
	public String  patientRegister(Model model,@Valid @ModelAttribute("patient") Patient patient, BindingResult theBindingResult) {
		
		if(theBindingResult.hasErrors()) {
			return "patientRegistrationForm";
		}
		
		Patient newPatient = patientService.registerNewPatient(patient);
		
		if(newPatient != null) {		
			model.addAttribute("regsuccess", "Registration Successful! Please log in");		
			return "patientLoginForm";
		}
		else {
			model.addAttribute("patient", new Patient());
			model.addAttribute("emailexists", "Email Id already Registered");
			return "patientRegistrationForm";
		}	
	}
	
	
	/*
	 * Lists the appointments of a patient with respect to patientId
	 */
	
	@GetMapping("/showAppointments")
	public String showAppointments(Model model, HttpSession session, HttpServletResponse response) {
		
		response.setHeader("Cache-Control", "no-store");
		
		if( session.getAttribute("loggedInPatientId") == null) {
			return "patientLoginForm";
		}

		Integer patientId = (Integer) session.getAttribute("loggedInPatientId");
		List<Appointment> appointments = appointmentService.getAppointmentsByPatientId(patientId);
		model.addAttribute("appointments", appointments);		
		return "appointments";
	}
	
	/*
	 * Cancel any scheduled Appointment and redirect to appointments page 
	 */
	
	@GetMapping("/cancelAppointment")
	public String cancelAppointment(@RequestParam("appointmentId") Integer apptId) {
		
		
		appointmentService.cancelAppointment(apptId);
		
		return "redirect:/patient/showAppointments";
	}
	
	/*
	 * Show List of Departments to Book an Appointment
	 */
	
	@GetMapping("/showDepartments")
	
	public String showDepartments(Model model, HttpSession session, HttpServletResponse response) {
		
		response.setHeader("Cache-Control", "no-store");
		
		if( session.getAttribute("loggedInPatientId") == null) {
			return "patientLoginForm";
		}

		model.addAttribute("departments", deptService.getAllDepartments());
		return "showdepartments";
	}
	
	
	/*
	 * Show List of doctors based on department to Book an APpointment
	 */
	
	@GetMapping("/showDoctors")
	public String showDoctors(  @RequestParam String departmentName, Model model, HttpSession session, HttpServletResponse response) {
		
		response.setHeader("Cache-Control", "no-store");
		
		if( session.getAttribute("loggedInPatientId") == null) {
			return "patientLoginForm";
		}
		model.addAttribute("selectedDept", departmentName);
		model.addAttribute("doctors", doctService.getDoctorsByDeptName(departmentName) );
		return "showdoctors";
	}
	
	/*
	 * Show Future date to book an Appointment
	 */
	
	@GetMapping("/showDate")
	public String showDate(@RequestParam Integer doctorId, @RequestParam String selectedDept, Model model, HttpSession session, HttpServletResponse response ) {
		
		response.setHeader("Cache-Control", "no-store");
		
		if( session.getAttribute("loggedInPatientId") == null) {
			return "patientLoginForm";
		}
		
		Doctor doctor = doctService.getDoctorById(doctorId);
		session.setAttribute("selectedDoctorId", doctorId);
		String doctorName = doctor.getFirstName() + " " + doctor.getLastName();
		model.addAttribute("doctorName", doctorName);
		model.addAttribute("selectedDept", selectedDept);
		return "showdate";
	}
	
	/*
	 * Book An appointment using Department, Doctor, Date
	 */
	@PostMapping("/bookAppointment")
	public String bookAppointment(@RequestParam LocalDate selectedDate , HttpSession session, RedirectAttributes attr) {
		
		
		if( session.getAttribute("loggedInPatientId") == null) {
			return "patientLoginForm";
		}
	
		
		Integer patientId = (Integer) session.getAttribute("loggedInPatientId");
		
		Integer doctorId = (Integer)session.getAttribute("selectedDoctorId");
		
		Doctor doctor =  doctService.getDoctorById(doctorId);
		
		Patient patient = patientService.findById(patientId);
		
		Appointment newAppt = new Appointment();
		
		newAppt.setAppointmentDate(selectedDate);
			
		newAppt.setPatient(patient);
				
		newAppt.setDoctor(doctor);
		
		newAppt.setStatus("Scheduled");
		
		Boolean alreadyExists = appointmentService.checkExistingAppointment(patient, doctor, selectedDate);
		
		int docApptCount = appointmentService.getCountOfAppointments(doctor.getDoctorId(),selectedDate);
		
		//Check If appointment already exists
		
		if(alreadyExists) {
			attr.addFlashAttribute("appointmentalreadyscheduled", "Appointment with " + doctor.getFirstName() + " " +doctor.getLastName()+
				" on " + selectedDate + " is scheduled already");
		}
		
		//Check if doctor is available for consultation
		
		else if(docApptCount >= 3) {
			attr.addFlashAttribute("docNA", "Doctor Is not available on this date. Kindly Select another Date or Consult another Doctor");
			return "redirect:/patient/showDate?doctorId="+doctorId+"&selectedDept="+doctor.getDepartment().getDepartmentName();
		}
		else {
		
		Appointment created = appointmentService.createAppointment(newAppt);
		
		attr.addFlashAttribute("appointmentbooked", "Appointment with id " +  created.getAppointmentId() +" booked Successfully");
		}
		
		return "redirect:/patient/showAppointments";
	}
	
	/*
	 * Shows Bill of an appointment after it is completed
	 */
	
	@GetMapping("/showBill")
	public String generateBill(@RequestParam("billId") Integer billId, Model model) {
		
		double total = 0;
		
		Bill bill = billService.getBillById(billId);
		
		if(bill == null) {
			model.addAttribute("bill" , bill);
			return "bill";
		}
		
		List<BillItem> items = bill.getBillItems();
		
		for(BillItem item : items) {
			total += item.getItem().getPrice() * item.getQuantity();		
		}
		
		bill.setTotal(total);
		
		billService.save(bill);
		
		model.addAttribute("billPatient", bill.getPatient());
		model.addAttribute("billAppt", bill.getAppointment());
		model.addAttribute("billDoc", bill.getAppointment().getDoctor());
		model.addAttribute("billableItems", bill.getBillItems());
		model.addAttribute("bill" , bill);
		return "bill";
	}

}
