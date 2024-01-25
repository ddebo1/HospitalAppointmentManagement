package com.care.hospital.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.care.hospital.entity.Bill;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {
	
	Bill findByAppointmentAppointmentId(Integer appointmentId);

}
