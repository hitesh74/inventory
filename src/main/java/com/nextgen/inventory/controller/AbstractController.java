package com.nextgen.inventory.controller;

import org.springframework.security.core.context.SecurityContextHolder;

import com.nextgen.inventort.auth.dto.CustomUserDetails;

public class AbstractController {

	public CustomUserDetails getLoggedInUser() {
		return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

}
