package com.care.hospital.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.care.hospital.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
	
	Item findItemByItemName(String name);

}
