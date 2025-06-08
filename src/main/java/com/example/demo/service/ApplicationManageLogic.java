package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.example.demo.dao.ApplicationRepository;
import com.example.demo.dao.ExamTargetRepository;
import com.example.demo.dto.ApplicationDto;
import com.example.demo.dto.ExamTargetForm;
import com.example.demo.dto.ExamTargetStatusDto;
import com.example.demo.model.Application;
import com.example.demo.model.ExamTarget;
import com.example.demo.model.Student;

@Service
public class ApplicationManageLogic {

	private final ExamTargetRepository examTargetRepository;
	private final ApplicationRepository applicationRepository;
	public ApplicationManageLogic(
			ExamTargetRepository examTargetRepository,
			ApplicationRepository applicationRepository) {
		this.examTargetRepository = examTargetRepository;
		this.applicationRepository = applicationRepository;
	}

	//試験対象者の情報を取得する
	public List<ExamTargetStatusDto> getTargetStatusList(String examId) {
		
		//試験の対象者を取得
		List<ExamTarget> targets = 
			examTargetRepository.findByExam_ExamId(examId);
		
		//対象者情報をまとめるリスト
		List<ExamTargetStatusDto> dtoList = new ArrayList<>();
		
		//対象者の情報を取得してリストに加える
		for (ExamTarget target : targets) {
			Student student = target.getStudent();
			Optional<Application> appOpt = 
					applicationRepository.findByStudent_StudentIdAndExam_ExamId(
							student.getStudentId(), examId);
			
			//対象者の情報(1人分)をセット
			ExamTargetStatusDto dto = new ExamTargetStatusDto();
			dto.setStudentId(student.getStudentId());
			dto.setName(student.getName());
			dto.setGrade(student.getGrade());
			dto.setInstrument(student.getInstrument());
		
			//申請情報の有無をセット
			if (appOpt.isPresent()) {
				dto.setApplied(true);
				dto.setApplicationId(appOpt.get().getApplicationId());
			} else {
				dto.setApplied(false);
			}
			
			//対象者情報のリストに追加
			dtoList.add(dto);
					
		}
		//対象者情報のリストを返す
		return dtoList;
	}

	//選択された対象者の申請内容を取得する
	public ApplicationDto getApplicationDetail(Long applicationId) {
		//DBから情報取得
		Optional<Application> appOpt = 
				applicationRepository.findById(applicationId);
		 
		if (appOpt.isEmpty()) {
		        System.out.println("Application not found for ID: " + applicationId);
		        return null;
		    }

	    Application app = appOpt.get();
	    Student s = app.getStudent();
	   
	    return new ApplicationDto(
	        app.getApplicationId(),
	        s.getStudentId(), s.getName(), s.getGrade(), s.getInstrument(),
	        app.getPerfForm(), app.getComposer(), app.getYears(),
	        app.getTitle(), app.getMovement(), app.getDuration(),
	        app.getMember(), app.getEquipment(), app.getMemo(),
	        app.getApplicationDate()
	    );
	}
	
}
