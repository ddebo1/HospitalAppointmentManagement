package com.care.hospital.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.care.hospital.dao.DepartmentRepository;
import com.care.hospital.entity.Department;

@Service
public class DepartmentService {
	
	@Autowired
	DepartmentRepository deptRepo;
	
	public List<Department> getAllDepartments(){
		return deptRepo.findAll();
	}

}
