package com.schemax.foodforward.repository;

import com.schemax.foodforward.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    //Create User

}
