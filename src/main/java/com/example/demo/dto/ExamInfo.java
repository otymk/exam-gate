package com.example.demo.dto;

import java.io.Serializable;

public class ExamInfo implements Serializable {
	private String examId;
	private String examName;
	
	public ExamInfo(String examId, String examName) {
		this.examId = examId;
		this.examName = examName;
	}
	
	public String getExamId() { return examId; }
	public String getExamName() { return examName; }
}
