package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
public class LogoutController {

	@GetMapping("/logout")
	public String logout(HttpSession session, RedirectAttributes ra) {
	    
		session.invalidate(); // セッションを破棄
	    ra.addFlashAttribute("logoutMessage", "ログアウトしました。");
		
	    return "redirect:/";
	}

}
