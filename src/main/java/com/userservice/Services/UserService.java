package com.userservice.Services;

import java.util.List;

import com.userservice.Entities.Users;


public interface UserService {

	
//	create 
	Users saveUser (Users user);
	
//	Get All Users
	List<Users> getAllUsers();
	
//	Get Single User
	Users getUser(String userId);
	
	
}
