package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Exam;

public interface ExamRepository extends JpaRepository<Exam, String> {

	//examIdの昇順で全件取得
	List<Exam> findAllByOrderByExamIdAsc();
}
