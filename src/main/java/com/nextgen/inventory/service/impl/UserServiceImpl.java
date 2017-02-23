package com.nextgen.inventory.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nextgen.inventort.auth.dto.CustomUserDetails;
import com.nextgen.inventory.dto.UserDto;
import com.nextgen.inventory.entity.User;
import com.nextgen.inventory.repository.IUserRepository;
import com.nextgen.inventory.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	IUserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		System.out.println("Here" + username);

		User user = userRepo.findUserByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid Credentials");
		}

		return new CustomUserDetails(user);
	}

	@Override
	public User getUser(Integer userId) {
		return userRepo.findUserByUserId(userId);
	}

	@Override
	public User updateUser(UserDto userDto, boolean updatePicture) {
		User user = userRepo.findUserByUserId(userDto.getUserId());
		String[] ignoreProps = new String[] { "userId", "username", "password", "status", "picture" };

		BeanUtils.copyProperties(userDto, user, ignoreProps);

		if (updatePicture) {
			user.setPicture(userDto.getPicture());
		}
		return userRepo.save(user);
	}
}
