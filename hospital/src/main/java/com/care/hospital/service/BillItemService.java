package com.care.hospital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.care.hospital.dao.BillItemRepository;
import com.care.hospital.entity.BillItem;

@Service
public class BillItemService {
	
	@Autowired
	BillItemRepository billItemRepo;
	
	public BillItem save(BillItem billItem) {
		return billItemRepo.save(billItem);
	}

}
