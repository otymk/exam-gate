package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.example.demo.dao.ExamRepository;
import com.example.demo.dao.ExamTargetRepository;
import com.example.demo.dao.StudentRepository;
import com.example.demo.dto.StudentDto;
import com.example.demo.model.Exam;
import com.example.demo.model.ExamTarget;
import com.example.demo.model.Student;
import jakarta.transaction.Transactional;

@Service
public class ExamTargetManageLogic {

	private final ExamTargetRepository examTargetRepository;
	private final ExamRepository examRepository;
	private final StudentManageLogic studentManageLogic;
	private final StudentRepository studentRepository;
	public ExamTargetManageLogic(ExamTargetRepository examTargetRepository,
			ExamRepository examRepository,StudentManageLogic studentManageLogic,
			StudentRepository studentRepository) {
		this.examTargetRepository = examTargetRepository;
		this.examRepository = examRepository;
		this.studentManageLogic = studentManageLogic;
		this.studentRepository = studentRepository;
	}

	//試験対象者をDBに保存
	@Transactional
	public void addTargets(String examId, List<String> studentIds) {
		Exam exam = examRepository.findById(examId)
				.orElseThrow(() -> new IllegalArgumentException("試験が見つかりません。"));
		
		for (String studentId : studentIds) {
			Student student = studentRepository.findById(studentId)
					.orElseThrow(() -> new IllegalArgumentException("学生が見つかりません。"));
			ExamTarget target = new ExamTarget(exam, student);
			examTargetRepository.save(target);
		}
	}
	
	
	//試験対象者になっていない学生リスト
	public List<StudentDto> getSelectableStudents(String examId) {
		//学生情報を全件取得
		List<StudentDto> studentList = studentManageLogic.getAllStudent();
		//試験対象者の学生IDを全件取得
		List<String> targetIds = 
				examTargetRepository.findStudent_StudentIdByExam_ExamId(examId);
		
		//フィルターして未登録の学生のリストをつくる
		List<StudentDto> notYetStudents = studentList.stream()
				.filter(student -> !targetIds.contains(student.getStudentId()))
				.collect(Collectors.toList());
		return notYetStudents;
	}
}
