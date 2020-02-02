package com.justin.helloworld.ui.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.justin.helloworld.exceptions.UserServiceException;
import com.justin.helloworld.shared.dto.UserDto;
import com.justin.helloworld.ui.model.request.UpdateUserDetailRequest;
import com.justin.helloworld.ui.model.request.UserDetailRequest;
import com.justin.helloworld.ui.model.response.ErrorMessages;
import com.justin.helloworld.ui.model.response.OperationStatusModel;
import com.justin.helloworld.ui.model.response.RequestOperationStatus;
import com.justin.helloworld.ui.model.response.UserRest;
import com.justin.helloworld.userservice.UserService;
import com.justin.helloworld.userservice.impl.UserServiceImpl;

@RestController
@RequestMapping("users")
public class UserController {
	
	Map<String, UserRest> users;
	
	@Autowired
	UserService userService;
	
	@GetMapping()
	public String getUsers(@RequestParam(value="page", defaultValue="1") int page, @RequestParam(value="limit", defaultValue="50") int limit)
	{
		return "get user method is called with pages of "  + page + " with limit of" + limit;
	}
	
	@GetMapping(path="/{userId}", 
			produces = {
					MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE
			})
	public UserRest getUser(@PathVariable String userId)
	{
		UserRest returnValue = new UserRest();
		
		UserDto userDto = userService.getUserByUserId(userId);
		BeanUtils.copyProperties(userDto, returnValue);
		
		return returnValue;
		
//		if(users.containsKey(userId))
//		{
//			return new ResponseEntity<>(users.get(userId), HttpStatus.OK);
//		} else {
//			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//		}
		
		
	}
	
	@PostMapping(
				consumes = {
						MediaType.APPLICATION_XML_VALUE,
						MediaType.APPLICATION_JSON_VALUE
				},
				produces = {
						MediaType.APPLICATION_XML_VALUE,
						MediaType.APPLICATION_JSON_VALUE
				}				
			)
	public UserRest createUser(@RequestBody UserDetailRequest userDetail) throws Exception
	{
		UserRest returnValue = new UserRest();
		
		if(userDetail.getFirstName().isEmpty()) throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		
		UserDto userDto  = new UserDto();
		BeanUtils.copyProperties(userDetail, userDto);
		
		UserDto createUser = userService.createUser(userDto);
		BeanUtils.copyProperties(createUser, returnValue);
		
		return returnValue;
	}
	
//	public ResponseEntity<UserRest> createUser(@Valid @RequestBody UserDetailRequest userDetail)
//	{
//		UserRest returnValue = userService.createUser(userDetail);
//		
//		return new ResponseEntity<UserRest>(returnValue, HttpStatus.OK);
//	}
	
	
	@PutMapping(path="/{userId}",
			consumes = {
					MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE
			},
			produces = {
					MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE
			})
	public UserRest updateUser(@PathVariable String userId, @Valid @RequestBody UpdateUserDetailRequest userDetail)
	{
		UserRest returnValue = new UserRest();
		
		if(userDetail.getFirstName().isEmpty()) throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		
		UserDto userDto  = new UserDto();
		BeanUtils.copyProperties(userDetail, userDto);
		
		UserDto updateUser = userService.updateUser(userId, userDto);
		BeanUtils.copyProperties(updateUser, returnValue);
		
		return returnValue;
	}
	
	@DeleteMapping(path="/{Id}", 
			produces = {
					MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE
			})
	public OperationStatusModel deleteUser(@PathVariable String Id)	
	{
//		users.remove(Id);
//		return ResponseEntity.noContent().build();
		
		OperationStatusModel returnValue = new OperationStatusModel();
		returnValue.setOperationName(RequestOperationName.DELETE.name());
		
		userService.deleteUser(Id);
		
		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		
		return returnValue;
	}
}
