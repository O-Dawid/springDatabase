package com.springboot.task.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.springboot.task.validator.UniquePhone;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long id;
	@NotEmpty
	@Column (name = "firstName")
	String firstName;
	@Column (name = "lastName")
	String lastName;
	@NotEmpty
	@Column (name = "birthDate")
	String birthDate;
	@UniquePhone
	@Pattern(regexp = "[0-9]{9}")
	String phoneNumber;
	@Column(name = "age")
	int age;

	public User() {}
	
	public User(UserBuilder userBuilder){
		firstName = userBuilder.firstName;
		lastName = userBuilder.lastName;
		birthDate = userBuilder.birthDate;
		phoneNumber = userBuilder.phoneNumber;
		age = userBuilder.age;
	}	
	
	public int getAge() {
		return age;
	}
		
	public String getFirstName() {
		return firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public String getBirthDate() {
		return birthDate;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	@Override
	public String toString() {
		return "User [firstName=" + firstName + ", lastName=" + lastName + ", birthDate=" + birthDate + ", phoneNumber="
				+ phoneNumber + ", age=" + age + "]";
	}



	

	

	

	
}
