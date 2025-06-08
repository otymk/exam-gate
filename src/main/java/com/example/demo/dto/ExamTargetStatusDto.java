package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamTargetStatusDto {

	private String studentId;
	private String name;
	private String grade;
	private String instrument;
	private boolean applied;
	private Long applicationId;
	
}
