package com.nextgen.inventory.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.nextgen.inventory.dto.UserDto;
import com.nextgen.inventory.entity.User;

public interface IUserService extends UserDetailsService {

	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

	User getUser(Integer userId);

	User updateUser(UserDto userDto, boolean updatePicture);

}
