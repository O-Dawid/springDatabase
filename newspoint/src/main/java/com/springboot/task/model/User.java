package com.springboot.task.model;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"phoneNumber"})})
public class User {
	@Column (name = "firstName")
	private String firstName;
	@Column (name = "lastName")
	String lastName;
	@Column (name = "birthDate")
	String birthDate;
	@Id
	@Column(name = "phoneNumber")
	int phoneNumber = 0;
	@Column(name = "age")
	int age;
	
	public int getAge() {
		return age;
	}

	public void setAge(String birthday) {	
		Period p = calculateAge(birthday);	
		age = p.getYears();		
	}

	private Period calculateAge(String birthday) {
		LocalDate dt = null;
		 List<String> formatStrings = Arrays.asList("yyyy.MM.dd", "yyyy.M.d", "yyyy.MM.d", "yyyy.M.dd");
		    for (String formatString : formatStrings)
		    {
		    	final DateTimeFormatter dtf = DateTimeFormatter.ofPattern(formatString);
		    	try {
		    		dt = LocalDate.parse(birthday, dtf);
				} catch (Exception e) {
					// TODO: handle exception
				}		       		        
		    }	
		LocalDate today = LocalDate.now();
		Period p = Period.between(dt, today);
		return p;
	}
		
	public User() {}
	
	public User(String firstname, String lastname, String birthday, int phonenumber) {
		firstName = firstname;
		lastName = lastname;
		birthDate = birthday;
		phoneNumber = phonenumber;
		// TODO Auto-generated constructor stub
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

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public int getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "User [firstName=" + firstName + ", lastName=" + lastName + ", birthDate=" + birthDate + ", phoneNumber="
				+ phoneNumber + ", age=" + age + "]";
	}



	

	

	

	
}
