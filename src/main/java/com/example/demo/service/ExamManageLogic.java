package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

import com.example.demo.dao.ApplicationRepository;
import com.example.demo.dao.ExamRepository;
import com.example.demo.dao.ExamTargetRepository;
import com.example.demo.dto.ExamDto;
import com.example.demo.dto.ExamInfo;
import com.example.demo.model.Application;
import com.example.demo.model.Exam;

@Service
public class ExamManageLogic {

	private final ExamRepository examRepository;
	private final ExamTargetRepository examTargetRepository;
	private final ApplicationRepository applicationRepository;
	public ExamManageLogic(ExamRepository examRepository, 
			ExamTargetRepository examTargetRepository,
			ApplicationRepository applicationRepository) {
		this.examRepository = examRepository;
		this.examTargetRepository = examTargetRepository;
		this.applicationRepository = applicationRepository;
	}
	
	public List<ExamDto> getAllExams() {
		//DBから試験情報を全件取得、examIdが小さい順
		List<Exam> examList = examRepository.findAllByOrderByExamIdAsc();
		
		//Dto型のListへ変換
		List<ExamDto> dtoList =
				examList.stream().map(exam -> new ExamDto(
					exam.getExamId(),
					exam.getExamName(),
					exam.getExamDate(),
					exam.getPerfForm(),
					exam.getApplicationDeadline()
					))
				.collect(Collectors.toList());
		
		return dtoList;
	
	}
	
}
