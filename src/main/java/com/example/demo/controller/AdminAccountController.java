package com.example.demo.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.demo.dao.AdminRepository;
import com.example.demo.dto.AdminDto;
import com.example.demo.dto.AdminLoginResult;
import com.example.demo.dto.StudentDto;
import com.example.demo.service.AdminManageLogic;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminAccountController {

	private final AdminRepository adminRepository;
	private final AdminManageLogic adminManageLogic;
	public AdminAccountController(
			AdminRepository adminRepository,
			AdminManageLogic adminManageLogic) {
		this.adminRepository = adminRepository;
		this.adminManageLogic = adminManageLogic;
	}
	
	//管理者情報一覧を表示
	@GetMapping("/adminList")
	public String showadminList(Model model, HttpSession session) {
		
		List<AdminDto> adminList = 
				adminManageLogic.getAllAdmins();
		model.addAttribute("adminList", adminList);
		return "admin/adminList";
	}

	//管理者情報の編集
	@PostMapping("/adminBatchAction")
	public String handleAdminAction(
			@RequestParam(name = "selectedIds", required = false) List<String> selectedIds,
			@RequestParam("action") String action,
			RedirectAttributes redirectAttributes) {
		
		//ボタンによって処理分岐
		switch (action) {
			case "delete":
				if (selectedIds == null || selectedIds.isEmpty()) {
					redirectAttributes.addFlashAttribute(
							"errorMessage", "削除する管理者を選択してください。");
					return "redirect:/admin/adminList";
				}
				selectedIds.forEach(adminRepository::deleteById);
				redirectAttributes.addFlashAttribute(
						"successMessage", "選択された管理者の情報を削除しました。");
				break;
			
			case "add":
				return "redirect:/admin/addAdmin";

			/*	
			case "edit":
			*/	
		}
		return "redirect:/admin/adminList";
	}
	
	
	//管理者の登録画面を表示
	@GetMapping("/addAdmin")
	public String showAddAdmin(Model model) {
		model.addAttribute("adminDto", new AdminDto());
		return "admin/addAdmin";
	}
	
	//管理者を登録
	@PostMapping("/addAdmin")
	public String addAdmin(@Valid @ModelAttribute AdminDto dto, 
			BindingResult bindingResult, Model model,
			@RequestParam("action") String action,
			RedirectAttributes redirectAttributes) {
		
		if ("back".equals(action)) {		//「戻る」をクリックした場合
			return "redirect:/admin/adminList";
		}
		
		if (bindingResult.hasErrors()) {	//未入力の項目がある場合
			model.addAttribute("adminDto", dto);
			return "admin/addAdmin";
		}
		
		adminRepository.save(dto.toEntity());
		redirectAttributes.addFlashAttribute(
				"successMessage", "管理者を登録しました。");
		return "redirect:/admin/addAdmin";
	}
	
}
