package com.kodnest.tunehub.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodnest.tunehub.entity.User;
import com.kodnest.tunehub.repository.UsersRepository;
import com.kodnest.tunehub.service.UsersService;
@Service
public class UsersServiceImpl implements UsersService{
	@Autowired
	UsersRepository usersRepository;
	public String addUser(User user) {
		usersRepository.save(user);
		return "User Added Successfully";
	}
	
	//To check the Duplicate entries 
	public boolean emailExists(String email) {
		if(usersRepository.findByEmail(email)!=null) {
			return true;
		}
		else {
			return false;
		}
	}
	
	//To check the DB for Entries for Login
	public boolean validateUser(String email, String password) {
		User user = usersRepository.findByEmail(email);
		String dbpwd = user.getPassword();
		if(password.equals(dbpwd)) {
			return true;
		}
		return false;
	}
	
	//To redirect the user to their role
	public String getRole(String email) {
		User user = usersRepository.findByEmail(email);
		return user.getRole();
	}

	@Override
	public User getUser(String email) {
		return usersRepository.findByEmail(email);
	}

	@Override
	public void updateUser(User user) {
		usersRepository.save(user);
	}
}
