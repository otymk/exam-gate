package com.example.demo.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.example.demo.model.Exam;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamDto implements Serializable {

	@NotBlank(message = "試験IDが未入力です")
	private String examId;
	
	@NotBlank(message = "試験名が未入力です")
	private String examName;
	
	@NotNull(message = "試験日が未入力です")
	private LocalDate examDate;
	
	@NotBlank(message = "編成が未入力です")
	private String perfForm;
	
	@NotNull(message = "申請期限が未入力です")
	private LocalDate applicationDeadline;
	
	public Exam toEntity() {
		Exam exam = new Exam();
		exam.setExamId(this.examId);
		exam.setExamName(this.examName);
		exam.setExamDate(this.examDate);
		exam.setPerfForm(this.perfForm);
		exam.setApplicationDeadline(this.applicationDeadline);
		return exam;
	}
}
