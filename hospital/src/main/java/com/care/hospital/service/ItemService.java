package com.care.hospital.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.care.hospital.dao.ItemRepository;
import com.care.hospital.entity.Item;

@Service
public class ItemService {
	
	@Autowired
	ItemRepository itemRepo;
	
	public List<Item> getAllItems(){
		return itemRepo.findAll();
	}

	public Item getItemById(Integer itemId) {
		return itemRepo.findById(itemId).get();
	}

	public Item getItemByName(String itemName) {
		return itemRepo.findItemByItemName(itemName);
	}

}
