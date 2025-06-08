package com.example.demo.model;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="exam_targets")
@Getter
@NoArgsConstructor
public class ExamTarget implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long targetId;
	
	@ManyToOne(fetch = FetchType.LAZY)		//ExamTarget(多)－(一)Exam
	@JoinColumn(
			name = "exam_id",		//exam_targetsの外部キー
			referencedColumnName = "examId",	//examsの主キー
			nullable = false
			)
	private Exam exam;
	
	@ManyToOne(fetch = FetchType.LAZY)		//ExamTarget(多)－(一)Student
	@JoinColumn(
			name = "student_id",		//exam_targetsの外部キー
			referencedColumnName = "studentId",		//studentsの主キー
			nullable = false
			)
	private Student student;
	
	//コンストラクタ
	//targetIdは自動採番
	public ExamTarget(Exam exam, Student student) {
		this.exam = exam;
		this.student = student;
	}

	//targetIdはsetterなし
	public void setExam(Exam exam) {this.exam = exam;}
	public void setStudent(Student student) {this.student = student;}
	
}

