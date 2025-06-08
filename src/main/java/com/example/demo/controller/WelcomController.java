package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WelcomController {		//ログイン画面表示
	
	@GetMapping("/")
	public String showLoginForm(
			@RequestParam(name = "error", required = false) String error,
            Model model) {		
		model.addAttribute("error", error);
		return "login";
	}
}
