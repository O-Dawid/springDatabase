package com.springboot.task.dao;

import java.util.List;
import java.util.Optional;

import com.springboot.task.model.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<User, Integer> {

	List<User> findByLastNameIgnoreCase(String lastName);
	
	List<User> findByOrderByAgeAsc();

	Optional<User> findByPhoneNumber(String phoneNumber);

}
