package com.example.demo.dto;

import java.io.Serializable;
import com.example.demo.model.Student;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto implements Serializable {
	
	@NotBlank(message = "学生番号が未入力です")
	private String studentId;
	
	@NotBlank(message = "氏名が未入力です")
	private String name;
	
	@NotBlank(message = "学年が未入力です")
	private String grade;
	
	@NotBlank(message = "楽器が未入力です")
	private String instrument;
	
	@NotBlank(message = "メールアドレスが未入力です")
	private String mail;
	
	public Student toEntity() {
		Student student = new Student();
		student.setStudentId(this.studentId);
		student.setName(this.name);
		student.setGrade(this.grade);
		student.setInstrument(this.instrument);
		student.setMail(this.mail);
		return student;
	}
}
