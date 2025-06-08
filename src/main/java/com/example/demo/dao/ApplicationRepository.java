package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Application;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
	//studentIdから情報を取得
	List<Application> findByStudent_StudentId(String studentId);

	//studentIdとexamIdが一致するApplicationを取得
	Optional<Application> findByStudent_StudentIdAndExam_ExamId(
			String studentId, String examId);

}
