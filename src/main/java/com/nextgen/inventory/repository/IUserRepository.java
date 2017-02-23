package com.nextgen.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nextgen.inventory.entity.User;

public interface IUserRepository extends JpaRepository<User, Integer> {

	User findUserByUsername(String username);

	User findUserByUserId(Integer userId);

}
