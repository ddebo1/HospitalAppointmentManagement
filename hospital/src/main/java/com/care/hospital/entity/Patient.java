package com.care.hospital.entity;
import java.util.List;

import jakarta.validation.constraints.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name ="patient")
public class Patient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "patient_id")
	private Integer patientId;
	
	
	@NotNull(message = "required")
	@Size(min = 1, message = "required")
	@Pattern(regexp="^[a-zA-Z]+$", message =  "Enter a valid name")
	@Column(name = "first_name")
	private String firstName;
	
	@NotNull(message = "required")
	@Size(min = 1, message = "required")
	@Pattern(regexp="^[a-zA-Z]+$", message =  "Enter a valid name")
	@Column(name = "last_name")
	private String lastName;
	
	
	@NotNull(message = "required")
	@Column(name = "gender")
	private Character gender;
	
	
	@Max(value = 100, message = "Enter a valid age")
	@Column(name = "age")
	private Integer age;
	
	
	@NotNull(message = "required")
	@Column(name = "email")
	private String email;
	
	
	@NotNull(message = "required")
	@Size(min = 8, message = "Password should be atleast 8 characters")
	@Column(name = "password")
	private String password;
	
	@OneToMany(mappedBy = "patient", cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
	private List<Appointment> patientAppointments;
	
	@OneToMany(mappedBy = "patient", cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
	private List<Bill> bills;


	public Integer getPatientId() {
		return patientId;
	}


	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public Character getGender() {
		return gender;
	}


	public void setGender(Character gender) {
		this.gender = gender;
	}


	public Integer getAge() {
		return age;
	}


	public void setAge(Integer age) {
		this.age = age;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public List<Appointment> getPatientAppointments() {
		return patientAppointments;
	}


	public void setPatientAppointments(List<Appointment> patientAppointments) {
		this.patientAppointments = patientAppointments;
	}


	public List<Bill> getBills() {
		return bills;
	}


	public void setBills(List<Bill> bills) {
		this.bills = bills;
	}
	
}
