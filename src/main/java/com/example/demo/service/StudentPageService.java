package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.example.demo.dao.ApplicationRepository;
import com.example.demo.dao.ExamTargetRepository;
import com.example.demo.dto.AppliedExamDto;
import com.example.demo.dto.ExamInfo;
import com.example.demo.model.Application;

@Service
public class StudentPageService {

	private final ExamTargetRepository examTargetRepository;
	private final ApplicationRepository applicationRepository;
	public StudentPageService(ExamTargetRepository examTargetRepository,
			ApplicationRepository applicationRepository) {
		this.examTargetRepository = examTargetRepository;
		this.applicationRepository = applicationRepository;
	}

	//studentIdから未申請の試験を検索→examIdのリストにする
	public List<ExamInfo> getNotYetAppliedExams(String studentId) {
		
		//studentIdで対象の試験情報を取得
		List<ExamInfo> examInfo = 
				examTargetRepository.findExamInfosByStudentId(studentId);
		
		//DBから申請済み試験の試験ID一覧を取得
		List<Application> apps = 
				applicationRepository.findByStudent_StudentId(studentId);
		List<String> alreadyAppliedExamIds = apps.stream()
		    .map(app -> app.getExam().getExamId())
		    .collect(Collectors.toList());
		
		//フィルターして未申請の試験一覧つくる
		List<ExamInfo> notYetAppliedExams = examInfo.stream()
				.filter(info -> !alreadyAppliedExamIds.contains(info.getExamId()))
				.collect(Collectors.toList());
		
		return notYetAppliedExams;
	}
	
	//申請済み試験の情報をリストにする
	public List<AppliedExamDto> getAppliedExamData(String studentId) {
		
		//studentIdが一致する申請
		List<Application> apps = 
				applicationRepository.findByStudent_StudentId(studentId);
		//examId, examName, applicationIdをまとめる
		List<AppliedExamDto> appliedExams = apps.stream()
				.map(app -> new AppliedExamDto(
					app.getExam().getExamId(),
					app.getExam().getExamName(),
					app.getApplicationId()
				))
				.collect(Collectors.toList());
		
		return appliedExams;
	}
}
