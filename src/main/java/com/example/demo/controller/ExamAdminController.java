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
import com.example.demo.dao.ExamRepository;
import com.example.demo.dto.ExamDto;
import com.example.demo.service.ExamManageLogic;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class ExamAdminController {

	private final ExamManageLogic examManageLogic;
	private final ExamRepository examRepository;
	public ExamAdminController(
			ExamManageLogic examManageLogic,
			ExamRepository examRepository) {
		this.examManageLogic = examManageLogic;
		this.examRepository = examRepository;
	}
	
	//試験情報一覧を表示
	@GetMapping("/examList")
	public String showExamList(Model model, HttpSession session) {
		
		//ページ遷移の場合セッション削除
		session.removeAttribute("selectedExam");
		
		//試験情報を取得
		List<ExamDto> examList = 
				examManageLogic.getAllExams();
		model.addAttribute("examList", examList);
		
		return "admin/examList";
	}
	
	//試験情報の編集
	@PostMapping("/examBatchAction")
	public String handleExamAction(
			@RequestParam(name = "selectedIds", required = false) List<String> selectedIds,
			@RequestParam("action") String action,
			RedirectAttributes redirectAttributes) {
		
		//ボタンによって処理分岐
		switch (action) {
			case "delete":
				if (selectedIds == null || selectedIds.isEmpty()) {
					redirectAttributes.addFlashAttribute(
							"errorMessage", "削除する試験を選択してください。");
					return "redirect:/admin/examList";
				}
				selectedIds.forEach(examRepository::deleteById);
				redirectAttributes.addFlashAttribute(
						"successMessage", "選択された試験を削除しました。");
				break;
			
			case "add":
				return "redirect:/admin/addExam";

			/*	
			case "edit":
			*/	
		}
		return "redirect:/admin/examList";
	}
		
	//バインド用のインスタンスをセットしてリダイレクト
	@GetMapping("/addExam")
	public String showAddExam(Model model) {
		model.addAttribute("examDto", new ExamDto());
		return "admin/addExam";
	}
	
	//試験情報の登録→追加画面へリダイレクト
	@PostMapping("/addExam")
	public String addExam(@Valid @ModelAttribute ExamDto dto, 
			BindingResult bindingResult, Model model,
			@RequestParam("action") String action,
			RedirectAttributes redirectAttributes) {
		
		if ("back".equals(action)) {		//「戻る」をクリックした場合
			return "redirect:/admin/examList";
		}
		
		if (bindingResult.hasErrors()) {	//未入力の項目がある場合
			model.addAttribute("examDto", dto);
			return "admin/addExam";
		}
		
		examRepository.save(dto.toEntity());
		redirectAttributes.addFlashAttribute(
				"successMessage", "試験を登録しました。");
		return "redirect:/admin/addExam";		//再送信防止
	}
}
