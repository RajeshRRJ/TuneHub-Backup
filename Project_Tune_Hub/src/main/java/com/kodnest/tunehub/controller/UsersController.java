package com.kodnest.tunehub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kodnest.tunehub.entity.Song;
import com.kodnest.tunehub.entity.User;
import com.kodnest.tunehub.service.SongService;
import com.kodnest.tunehub.serviceimpl.UsersServiceImpl;

import jakarta.servlet.http.HttpSession;

@Controller
public class UsersController {
	//For Registration Form
	@Autowired
	UsersServiceImpl userServiceImpl;
	
	@Autowired
	SongService songService;
	
	@PostMapping("/register")
	public String addUser(@ModelAttribute User user) {
		
		//Email taken from registration form
		String email = user.getEmail();
		//checking if the entered email in registration form is present in DB or not
		boolean status = userServiceImpl.emailExists(email);
		
		if(status == false) {
			userServiceImpl.addUser(user);
			System.out.println("User Added");
		}
		else {
			System.out.println("User Already Exists");
		}
		
		return "login";
	}
	
	//For User to Login as their Role
	@PostMapping("/validate")
	public String validate(@RequestParam("email") String email,
			@RequestParam("password") String password, HttpSession session, Model model) {
		if(userServiceImpl.validateUser(email, password) == true) {
			String role = userServiceImpl.getRole(email);
			
			session.setAttribute("email", email);
			
			if(role.equals("admin")) {
				return "adminhome";
			}
			else {
				User user = userServiceImpl.getUser(email);
				boolean userstatus = user.isIspremium();
				model.addAttribute("ispremium",userstatus);
				
				List<Song> songList = songService.fetchAllSongs();
				model.addAttribute("songs", songList);
				return "customerhome";
			}
		}
		else {
			return "login";
		}
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "login";
	}
	
}
