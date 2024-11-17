package com.schemax.foodforward.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.schemax.foodforward.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

	List<User> findAllByLatitudeIsNullAndLongitudeIsNull();

}
