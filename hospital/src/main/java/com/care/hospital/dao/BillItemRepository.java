package com.care.hospital.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.care.hospital.entity.BillItem;

@Repository
public interface BillItemRepository extends JpaRepository<BillItem, Integer> {

}
