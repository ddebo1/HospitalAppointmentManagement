package com.care.hospital.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.care.hospital.dao.AppointmentRepository;
import com.care.hospital.entity.Appointment;
import com.care.hospital.entity.Doctor;
import com.care.hospital.entity.Patient;

import jakarta.transaction.Transactional;


@Service
public class AppointmentService {
	
	@Autowired
	AppointmentRepository appointmentRepo;
	
	public List<Appointment> getAppointmentsByPatientId(Integer id){
		List<Appointment> appointments = appointmentRepo.findByPatientPatientId(id);
		return appointments;
	}
	
	public void cancelAppointment(Integer id) {
		
		Optional<Appointment> optAppointment = appointmentRepo.findById(id);
		
		if(optAppointment.isPresent()) {
			Appointment appointment = optAppointment.get();
			appointment.setStatus("Cancelled");
			appointmentRepo.save(appointment);
		}
	}

	public Appointment createAppointment(Appointment newAppt) {
		return appointmentRepo.save(newAppt);
	}

	public boolean checkExistingAppointment(Patient patient, Doctor doctor , LocalDate selectedDate) {
		List<Appointment> appointments =  appointmentRepo.findByPatientAndDoctorAndAppointmentDate(patient,doctor,selectedDate);
		for(Appointment appt : appointments) {
			if(appt.getStatus().equals("Scheduled")) {
				return true;
			}
		}
		return false;
	}

	public List<Appointment> getAppointmentsByDoctorId(Integer doctorId) {
		List<Appointment> docappointments = appointmentRepo.findByDoctorDoctorId(doctorId);
		return docappointments;
	}

	public void updateStatus(Integer apptId) {
		Appointment appt = appointmentRepo.getById(apptId);
		appt.setStatus("Completed");
		appointmentRepo.save(appt);
		
	}

	public int getCountOfAppointments(Integer doctorId, LocalDate selectedDate) {	
		return appointmentRepo.getCountOfAppointments(doctorId,selectedDate);	
	}
	
	public Appointment getAppointmentById(Integer apptId) {
		return appointmentRepo.findById(apptId).get();
	}

}
