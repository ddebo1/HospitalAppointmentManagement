package com.care.hospital;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.care.hospital.dao.AppointmentRepository;
import com.care.hospital.dao.BillRepository;
import com.care.hospital.dao.ItemRepository;
import com.care.hospital.dao.PatientRepository;
import com.care.hospital.entity.Bill;

@SpringBootApplication
public class HospitalApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospitalApplication.class, args);
	}
	

}
