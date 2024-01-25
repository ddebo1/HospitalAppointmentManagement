package com.care.hospital.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.care.hospital.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

	
}
