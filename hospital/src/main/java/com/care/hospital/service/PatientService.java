package com.care.hospital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.care.hospital.dao.PatientRepository;
import com.care.hospital.entity.Patient;

@Service
public class PatientService {
	
	@Autowired
	PatientRepository patientrepo;
	
	public Patient findPatientByEmailAndPassword(String email, String password) {
		return patientrepo.findByEmailAndPassword(email, password);
	}
	
	public Patient registerNewPatient(Patient patient) {
		
		Patient patientWithSameEmail = patientrepo.findByEmail(patient.getEmail());
		
		if(patientWithSameEmail == null)
			return patientrepo.save(patient);
		else
			return null;
	}

	public Patient findById(Integer patientId) {
		return patientrepo.findById(patientId).get();
	}

}
