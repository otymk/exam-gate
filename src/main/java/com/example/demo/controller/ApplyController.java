package com.example.demo.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.dto.ApplicationData;
import com.example.demo.dto.ApplicationForm;
import com.example.demo.dto.ExamInfo;
import com.example.demo.dto.StudentDto;
import com.example.demo.service.ApplyLogic;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/student")
public class ApplyController {
	
	private final ApplyLogic applyLogic;
	public ApplyController(ApplyLogic applyLogic) { this.applyLogic = applyLogic; }
	
	//申請画面を表示
	@GetMapping("/apply/form")
	public String showApplyForm(
			@RequestParam(value = "examId", required = false) String examId, 
				HttpSession session, Model model) {
		
		//examIdがなければリダイレクト
		if (examId == null) {
			return "redirect:/student/selectExam";
		}
		
		//セッションからexamInfoを取得
		@SuppressWarnings("unchecked")		//キャストチェックなし
		List<ExamInfo> examInfo = (List<ExamInfo>)session.getAttribute("examInfo");
	
		if (examInfo == null) {
			return "redirect:/student/selectExam";
		}
		
		//リストとパラメータでexamIdが一致する1つを探してselectedExamに代入
		ExamInfo selectedExam = examInfo.stream()
				.filter(e -> e.getExamId().equals(examId))
				.findFirst()
				.orElse(null);
		
		if (selectedExam == null) {
			return "redirect:/student/selectExam";
		}

		session.setAttribute("selectedExam", selectedExam);  //ExamInfoDto型
		model.addAttribute("selectedExam", selectedExam);


		ApplicationData appData = (ApplicationData)session.getAttribute("application");
		
		if (appData != null) {		//修正ボタン→フォームに戻る
			model.addAttribute("applicationForm", appData.getForm());	//入力値を再表示
		} else {
			model.addAttribute("applicationForm", new ApplicationForm());	//初回表示
		}
		
		model.addAttribute("loginStudent", (StudentDto)session.getAttribute("loginStudent"));
		
		return "student/applyForm";
	}
	
	//確認画面を表示
	@PostMapping("/apply/confirm")
	public String showConfirmApplication(
			@ModelAttribute ApplicationForm form, HttpSession session, Model model) {
		//POSTされたフォームの内容をformにセット
		
		//セッション"loginStudent", "examInfo"をそれぞれ取得
		StudentDto student = (StudentDto)session.getAttribute("loginStudent");
		ExamInfo selectedExam = (ExamInfo)session.getAttribute("selectedExam");
		
		if (selectedExam == null) {
			return "redirect:/student/selectExam";
		}
		
		//確認画面の表示に使う情報
		model.addAttribute("selectedExam", selectedExam);
		model.addAttribute("loginStudent", student);
		model.addAttribute("form", form);
		
		//studentId, examId, form情報をappDataにまとめる
		ApplicationData appData = 
				new ApplicationData(form, student.getStudentId(), selectedExam.getExamId());
		
		//appDataをセッション"application"に保存
		session.setAttribute("application", appData);
		
		return "student/confirmApplication";	//確認画面へ
	}
	
	//申請処理
	@PostMapping("/apply/doApply")
	public String doApply(HttpSession session) {
		
		//セッションのnullチェック
		ApplicationData appData = 
				(ApplicationData)session.getAttribute("application");
		if (appData == null) {
			return "redirect:/student/selectExam";
		}
		
		//Logicの引数を準備
		ApplicationForm form = appData.getForm();
		String studentId = appData.getStudentId();
		String examId = appData.getExamId();
		
		applyLogic.doApply(form, studentId, examId);
		
		//保存完了後、セッションを削除
		session.removeAttribute("examInfo");		//対象の試験情報
		session.removeAttribute("selectedExam");	//申請対象の試験ID
		session.removeAttribute("application");		//申請情報全部
				
		return "redirect:/student/apply/complete";		//リダイレクト(再送信防止)
	}
	
	//申請完了画面を表示
	@GetMapping("/apply/complete")
	public String showApplyComplete() {
	    return "student/applyComplete";
	}
	
}
