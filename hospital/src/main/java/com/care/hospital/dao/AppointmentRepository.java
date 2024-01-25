package com.care.hospital.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.care.hospital.entity.Appointment;
import com.care.hospital.entity.Doctor;
import com.care.hospital.entity.Patient;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer>{

	List<Appointment> findByPatientPatientId(Integer patientId);

	List<Appointment> findByPatientAndDoctorAndAppointmentDate(Patient patient, Doctor doctor, LocalDate selectedDate);

	List<Appointment> findByDoctorDoctorId(Integer doctorId);

	@Query("SELECT COUNT(a) FROM Appointment a WHERE a.doctor.id = :doctorId AND a.appointmentDate = :selectedDate AND a.status = 'Scheduled'")
	int getCountOfAppointments(Integer doctorId, LocalDate selectedDate);
	
	

}
