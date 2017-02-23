package com.nextgen.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import com.nextgen.inventort.auth.RESTAuthenticationFailureHandler;
import com.nextgen.inventort.auth.RESTAuthenticationSuccessHandler;
import com.nextgen.inventort.auth.RestAuthenticationEntryPoint;
import com.nextgen.inventory.repository.IUserRepository;
import com.nextgen.inventory.service.IUserService;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfigurer extends WebSecurityConfigurerAdapter {

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	IUserService userService;

	@Autowired
	private RestAuthenticationEntryPoint authEntryPoint;

	@Autowired
	private RESTAuthenticationFailureHandler authenticationFailureHandler;

	@Autowired
	private RESTAuthenticationSuccessHandler authenticationSuccessHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/api/**").authenticated();
		http.csrf().disable();
		http.exceptionHandling().authenticationEntryPoint(authEntryPoint);
		http.formLogin().successHandler(authenticationSuccessHandler);
		http.formLogin().failureHandler(authenticationFailureHandler);
		http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.ACCEPTED));
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
	}

	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		// auth.userDetailsService(userService).passwordEncoder(passwordencoder());
		auth.userDetailsService(userService);
	}

}
