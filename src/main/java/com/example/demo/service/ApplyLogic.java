package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.dao.ApplicationRepository;
import com.example.demo.dao.ExamRepository;
import com.example.demo.dao.StudentRepository;
import com.example.demo.dto.ApplicationData;
import com.example.demo.dto.ApplicationForm;
import com.example.demo.model.Application;
import com.example.demo.model.Exam;
import com.example.demo.model.Student;

import jakarta.servlet.http.HttpSession;

@Service
public class ApplyLogic {
	
	//applications, students, examsのDAO
	private final ApplicationRepository applicationRepository;
	private final StudentRepository studentRepository;
	private final ExamRepository examRepository;
	
	public ApplyLogic(
			ApplicationRepository applicationRepository,
			StudentRepository studentRepository,
			ExamRepository examRepository) {
		this.applicationRepository = applicationRepository;
		this.studentRepository = studentRepository;
		this.examRepository = examRepository;
	}
	
	public void doApply(ApplicationForm form, String studentId, String examId) {
		
		//studentId, examIdでEntityを取得(Applicationのフィールドに合わせる)
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new IllegalArgumentException("学生情報が登録されていません"));
		Exam exam = examRepository.findById(examId)
				.orElseThrow(() -> new IllegalArgumentException("試験情報が登録されていません"));
				
		//Applicationに情報をセット
		Application app = new Application(
				student,
				exam,
				form.getPerfForm(),
				form.getComposer(),
				form.getYears(),
				form.getTitle(),
				form.getMovement(),
				form.getDuration(),
				form.getMember(),
				form.getEquipment(),
				form.getMemo()
				);
		
		//DB:applicationsに保存
		applicationRepository.save(app);
	}
}
