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
import com.example.demo.dto.ApplicationDto;
import com.example.demo.dto.ExamInfo;
import com.example.demo.dto.ExamTargetForm;
import com.example.demo.dto.ExamTargetStatusDto;
import com.example.demo.dto.StudentDto;
import com.example.demo.model.Exam;
import com.example.demo.service.ApplicationManageLogic;
import com.example.demo.service.ExamTargetManageLogic;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class ApplicationAdminController {

	private final ApplicationManageLogic applicationManageLogic;
	private final ExamRepository examRepository;
	private final ExamTargetManageLogic examTargetManageLogic;
	public ApplicationAdminController(
			ApplicationManageLogic applicationManageLogic,
			ExamRepository examRepository,
			ExamTargetManageLogic examTargetManageLogic) {
		this.applicationManageLogic = applicationManageLogic;
		this.examRepository = examRepository;
		this.examTargetManageLogic = examTargetManageLogic;
	}
	
	//申請状況一覧を表示
	@GetMapping("/applicationStatusList")
	public String showApplicationStatusList(
			@RequestParam String examId, HttpSession session, Model model) {

		//試験IDから試験情報を取得してセッションに保存
		Exam exam = examRepository.findById(examId).orElseThrow();
		ExamInfo examInfo = 
				new ExamInfo(exam.getExamId(), exam.getExamName());
		session.setAttribute("selectedExam", examInfo);
		model.addAttribute("selectedExam", examInfo);
		
		//試験対象者の情報を取得
		List<ExamTargetStatusDto> targetStatusList =
				applicationManageLogic.getTargetStatusList(examId);
		model.addAttribute("targetStatusList", targetStatusList);

		return "admin/applicationStatusList";		
	}
	
	//申請内容詳細を表示(モーダル用)
	@GetMapping("/applicationDetailFragment")
	public String getApplicationDetailFragment(@RequestParam Long applicationId, Model model) {
	    ApplicationDto application = 
	    		applicationManageLogic.getApplicationDetail(applicationId);
	    
	    model.addAttribute("applicationDto", application);
	    return "admin/fragments/applicationDetail :: detail";		//フラグメントを返す
	}

	
	//試験対象者の登録ページを表示
	@GetMapping("/addExamTarget")
	public String showAddExamTarget(Model model, HttpSession session) {
		
		ExamInfo selectedExam = (ExamInfo)session.getAttribute("selectedExam");
		model.addAttribute("examInfo", selectedExam);
			
		List<StudentDto> notYetStudents = 
				examTargetManageLogic.getSelectableStudents(selectedExam.getExamId());
		model.addAttribute("nyStudents", notYetStudents);
		
		//バインド用のインスタンスをセット
		model.addAttribute("form", new ExamTargetForm());
		
		return "admin/addExamTarget";
		
	}
	
	//試験対象者を登録
	@PostMapping("/addExamTarget")
	public String addExamTarget(@Valid @ModelAttribute ExamTargetForm form, 
			BindingResult bindingResult, Model model,@RequestParam("action") String action,
			HttpSession session, RedirectAttributes redirectAttributes) {
		
		ExamInfo selectedExam = (ExamInfo)session.getAttribute("selectedExam");
		String examId = selectedExam.getExamId();
		
		if ("back".equals(action)) {	//「戻る」:パラメータexamId
			return "redirect:/admin/applicationStatusList?examId=" + examId;
		}
		
		//バリデーションエラー:学生一覧をセットして戻る
		if (bindingResult.hasErrors()) {
			model.addAttribute("nyStudents",
					examTargetManageLogic.getSelectableStudents(examId));
			return "redirect:/admin/addExamTarget";
		}
		
		//登録
		examTargetManageLogic.addTargets(examId, form.getStudentIds());
		redirectAttributes.addFlashAttribute("successMessage", "登録が完了しました。");
		
		return "redirect:/admin/addExamTarget";
	}
	
}