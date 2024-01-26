package com.luv2code.springmvc.demosecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController
{

	@GetMapping("/showMyLoginPage")
	public String showLoginPage()
	{
		return "ModelLogin";
	}
	@GetMapping("/access-denied")
	public String showAccessDeniedPage()
	{
		return "access-denied";
	}
}
