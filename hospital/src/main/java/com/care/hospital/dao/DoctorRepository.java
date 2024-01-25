package com.care.hospital.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.care.hospital.entity.Doctor;


@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

	List<Doctor> findByDepartmentDepartmentName(String deptName);

	Doctor findByEmailAndPassword(String email, String password);
}
