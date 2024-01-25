package com.care.hospital.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.care.hospital.dao.BillRepository;
import com.care.hospital.entity.Bill;

@Service
public class BillService {
	
	@Autowired
	BillRepository billRepo;
	
	public Bill getBillByAppointmentId(Integer id){
		Bill bill = billRepo.findByAppointmentAppointmentId(id);
		return bill;
	}

	public Bill save(Bill bill) {
		return billRepo.save(bill);
	}

	public Bill getBillById(Integer billId) {
		Optional<Bill> bill =  billRepo.findById(billId);
		if(bill.isPresent()) {
			return bill.get();
		}
		else {
			return null;
		}				
	}

}
