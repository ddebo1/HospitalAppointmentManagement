package com.care.hospital.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.care.hospital.dao.DoctorRepository;
import com.care.hospital.entity.Doctor;

@Service
public class DoctorService {
	
	@Autowired
	DoctorRepository doctRepo;
	
	public List<Doctor> getDoctorsByDeptName(String deptName){
		return doctRepo.findByDepartmentDepartmentName(deptName);
	}

	public Doctor getDoctorById(Integer doctorId) {
		return doctRepo.findById(doctorId).get();
	}

	public Doctor findDoctorByEmailAndPassword(String email, String password) {
		return doctRepo.findByEmailAndPassword(email,password);
	}
}
