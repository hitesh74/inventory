package com.nextgen.inventory.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nextgen.inventort.exception.InternalServerExcepton;
import com.nextgen.inventory.dto.UserDto;
import com.nextgen.inventory.entity.User;
import com.nextgen.inventory.service.IUserService;

@RestController
@RequestMapping("/api/user")
public class UserController extends AbstractController {

	@Autowired
	IUserService userService;

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public UserDto getUser() {

		Integer userId = getLoggedInUser().getUserId();
		User user = userService.getUser(userId);

		UserDto userDto = new UserDto();
		String[] ignoreProps = new String[] { "userId", "username", "password" };
		BeanUtils.copyProperties(user, userDto, ignoreProps);

		return userDto;
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public UserDto updateUser(@RequestBody UserDto userDto, @RequestParam boolean updatePicture) throws InternalServerExcepton {

		Integer userId = getLoggedInUser().getUserId();
		userDto.setUserId(userId);

		User user = userService.updateUser(userDto, updatePicture);

		if (user != null) {
			userDto = new UserDto();
			String[] ignoreProps = new String[] { "userId", "username", "password" };
			BeanUtils.copyProperties(user, userDto, ignoreProps);

			// userDto.setFirstName(user.getFirstName());
			// userDto.setLastName(user.getLastName());
			// userDto.setEmail(user.getEmail());
			// userDto.setMobile(user.getMobile());
			// userDto.setCompanyName(user.getCompanyName());
			// userDto.setCompanyAddress(user.getCompanyAddress());
			// userDto.setCompanyCity(user.getCompanyCity());
			// userDto.setCompanyCountry(user.getCompanyCountry());
			// userDto.setCompanyPin(user.getCompanyPin());
			return userDto;
		}

		throw new InternalServerExcepton();
	}
}
