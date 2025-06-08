package com.example.demo.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.dto.ApplicationDto;
import com.example.demo.dto.AppliedExamDto;
import com.example.demo.dto.ExamInfo;
import com.example.demo.dto.StudentDto;
import com.example.demo.service.ApplicationManageLogic;
import com.example.demo.service.StudentPageService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/student")
public class StudentMenuController {
	
	private final StudentPageService studentPageService;
	private final ApplicationManageLogic applicationManageLogic;
	public StudentMenuController(StudentPageService studentPageService,
			ApplicationManageLogic applicationManageLogic) {
		this.studentPageService = studentPageService;
		this.applicationManageLogic = applicationManageLogic;
	}
	
	//メニュー画面
	@GetMapping("/studentMenu")
	public String showStudentmenu(HttpSession session) {
		
		//ページ遷移の場合はセッションを削除する
		session.removeAttribute("examInfo");		//対象の試験情報
		session.removeAttribute("selectedExam");	//申請対象の試験ID
		session.removeAttribute("application");		//申請情報全部

		return "student/studentMenu";
	}
	
	//試験選択画面
	@GetMapping("/selectExam")
	public String showSelectExam(Model model, HttpSession session) {
		
		//ページ遷移の場合はセッションを削除
		session.removeAttribute("application");
		
		StudentDto loginStudent = (StudentDto)session.getAttribute("loginStudent");
		String studentId = loginStudent.getStudentId();
		
		//未申請の試験リスト取得、セッションに保存
		List<ExamInfo> notYetAppliedExams = 
				studentPageService.getNotYetAppliedExams(studentId);
		
		session.setAttribute("examInfo", notYetAppliedExams);
		model.addAttribute("examInfo", notYetAppliedExams);
		
		return "student/selectExam";
	}
	
	//マイページ
	@GetMapping("/myPage")
	public String showMyPage(Model model, HttpSession session) {
		
		//ページ遷移の場合はセッションを削除する
		session.removeAttribute("examInfo");		//対象の試験情報
		session.removeAttribute("selectedExam");	//申請対象の試験ID
		session.removeAttribute("application");		//申請情報全部

		
		StudentDto loginStudent = (StudentDto)session.getAttribute("loginStudent");
		String studentId = loginStudent.getStudentId();
		
		//申請済み試験の情報
		List<AppliedExamDto> appliedExams = 
				studentPageService.getAppliedExamData(studentId);
		
		model.addAttribute("loginStudent", loginStudent);
		model.addAttribute("appliedExams", appliedExams);
;		
		return "student/myPage";
	}
	
	//申請内容詳細(モーダル用)
	@GetMapping("/applicationDetailFragment")
	public String showApplicationDetail(@RequestParam Long applicationId, Model model) {
		ApplicationDto appDto = 
				applicationManageLogic.getApplicationDetail(applicationId);
		model.addAttribute("appDetail", appDto);
		
		return "student/fragments/studentApplicationDetail :: detail";
	}
}
