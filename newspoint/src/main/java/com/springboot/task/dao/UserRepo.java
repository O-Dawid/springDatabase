package com.springboot.task.dao;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.springboot.task.model.User;


public interface UserRepo extends CrudRepository<User, Integer> {

	List<User> findByLastNameIgnoreCase(String lastName);
	
	List<User> findByOrderByAgeAsc();

}
