package com.yiting.toeflvoc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yiting.toeflvoc.models.User;
import com.yiting.toeflvoc.services.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

	@RequestMapping(path="/register", method = RequestMethod.POST)
	public String register(@RequestParam String email,
			@RequestParam String firstName,
			@RequestParam String lastName,
			@RequestParam String password,
			@RequestParam String retypePassword) throws Exception{

		User user = this.userService.registerUser(email, password, retypePassword, firstName, lastName);
		return "pages/login";
	}
}
