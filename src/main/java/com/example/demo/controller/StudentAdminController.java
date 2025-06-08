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
import com.example.demo.dao.StudentRepository;
import com.example.demo.dto.StudentDto;
import com.example.demo.service.StudentManageLogic;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class StudentAdminController {

	private final StudentManageLogic studentManageLogic;
	private final StudentRepository studentRepository;
	public StudentAdminController(
			StudentManageLogic studentManageLogic,
			StudentRepository studentRepository) {
		this.studentManageLogic = studentManageLogic;
		this.studentRepository = studentRepository;
	}
	
	//学生情報一覧を表示
	@GetMapping("/studentList")
	public String showStudentList(Model model, HttpSession session) {
		List<StudentDto> studentList =
				studentManageLogic.getAllStudent();
		model.addAttribute("studentList", studentList);
		return "admin/studentList";
	}
	
	
	//学生情報の編集
	@PostMapping("/studentBatchAction")
	public String handleStudentAction(
			@RequestParam(name = "selectedIds", required = false) List<String> selectedIds,
			@RequestParam("action") String action, RedirectAttributes redirectAttributes) {
		
		//ボタンによって処理分岐
		switch (action) {
			case "delete":
				if (selectedIds == null || selectedIds.isEmpty()) {
					redirectAttributes.addFlashAttribute(
							"errorMessage", "削除する学生を選択してください。");
					return "redirect:/admin/studentList";
				}
				
				selectedIds.forEach(studentRepository::deleteById);
				redirectAttributes.addFlashAttribute(
						"successMessage", "選択された学生を削除しました。");
				break;
				
			case "add":
				return "redirect:/admin/addStudent";

			/*	
			case "edit":
			*/	
		}
		return "redirect:/admin/studentList";
	}
	
	//バインド用のインスタンスをセットしてリダイレクト
	@GetMapping("/addStudent")
	public String showAddStudent(Model model) {
		model.addAttribute("studentDto", new StudentDto());
		return "admin/addStudent";
	}
	
	//学生を登録→追加画面へリダイレクト
	@PostMapping("/addStudent")
	public String addStudent(@Valid @ModelAttribute StudentDto dto, 
			BindingResult bindingResult, Model model,
			@RequestParam("action") String action,
			RedirectAttributes redirectAttributes) {
		
		
		if ("back".equals(action)) {		//「戻る」をクリックした場合
			return "redirect:/admin/studentList";
		}
		
		if (bindingResult.hasErrors()) {	//未入力の項目がある場合
			model.addAttribute("studentDto", dto);
			return "admin/addStudent";
		}
		
		studentRepository.save(dto.toEntity());
		redirectAttributes.addFlashAttribute(
				"successMessage", "学生を登録しました。");
		return "redirect:/admin/addStudent";
		
	}
}
