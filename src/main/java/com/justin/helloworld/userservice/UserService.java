package com.justin.helloworld.userservice;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.justin.helloworld.shared.dto.UserDto;
import com.justin.helloworld.ui.model.request.UserDetailRequest;
import com.justin.helloworld.ui.model.response.UserRest;

public interface UserService extends UserDetailsService{
	UserDto createUser(UserDto user);
	UserDto getUser(String email);
	UserDto getUserByUserId(String userId);
	UserDto updateUser(String userId, UserDto userDto);
	void deleteUser(String userId);

}
