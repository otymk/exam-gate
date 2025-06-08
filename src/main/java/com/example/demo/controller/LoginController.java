package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.demo.dto.AdminLoginForm;
import com.example.demo.dto.AdminLoginResult;
import com.example.demo.dto.Login;
import com.example.demo.dto.StudentDto;
import com.example.demo.exception.InvalidCredentialsException;
import com.example.demo.service.LoginLogic;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	
	private final LoginLogic loginLogic;
	public LoginController(LoginLogic loginLogic) { this.loginLogic = loginLogic; }
	
	//学生のログイン処理
	@PostMapping("/student/login")
	public String redirectStudentMenu(@ModelAttribute Login login,
			HttpSession session,RedirectAttributes ra) {
				
		try {
			StudentDto studentDto = loginLogic.loginStudent(login);
			session.setAttribute("loginStudent", studentDto);	//セッションに保存
			return "redirect:/student/studentMenu";		//試験選択画面へリダイレクト

		} catch (InvalidCredentialsException e) {
			//loginとBDの情報不一致でエラーが返ってきたとき
			
			ra.addFlashAttribute("errorMessage", "入力内容に誤りがあります");
			return "redirect:/";
		}
	}
	
	//管理者のログイン処理
	@PostMapping("/admin/adminLogin")
	public String redirectAdminMenu(@ModelAttribute AdminLoginForm form,
			HttpSession session, RedirectAttributes ra) {
		
		try {
			AdminLoginResult loginAdmin = loginLogic.loginAdmin(form);
			session.setAttribute("loginAdmin", loginAdmin);
			return "redirect:/admin/adminMenu";
			
		} catch (InvalidCredentialsException e) {
			ra.addFlashAttribute("errorMessage", "入力内容に誤りがあります");
			return "redirect:/";
		}
	}
	//管理者メニュー表示
		@GetMapping("/admin/adminMenu")
		public String showAdminMenu() { return "admin/adminMenu"; }
}

