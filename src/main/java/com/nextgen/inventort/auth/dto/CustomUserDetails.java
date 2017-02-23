package com.nextgen.inventort.auth.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.nextgen.inventory.entity.User;
import com.nextgen.inventory.enums.UserStatus;

public class CustomUserDetails extends User implements UserDetails {

	public CustomUserDetails(User user) {
		super(user);
	}

	private static final long serialVersionUID = 1L;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return getStatus() != UserStatus.ACCOUNT_EXPIRED;
	}

	@Override
	public boolean isAccountNonLocked() {
		return getStatus() != UserStatus.LOCKED;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return getStatus() != UserStatus.CREDS_EXPIRED;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return getStatus() == UserStatus.ACTIVE;
	}

}
