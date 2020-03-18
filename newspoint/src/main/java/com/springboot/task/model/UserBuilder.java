package com.springboot.task.model;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class UserBuilder {
	String firstName;
	String lastName;
	String birthDate;
	String phoneNumber;
    int age;

    public UserBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserBuilder birthDate(String birthDate) {
        age = calculateAge(birthDate).getYears();
        this.birthDate = birthDate;
        return this;
    }

    public UserBuilder phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public User build(){
        return new User(this);

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
}