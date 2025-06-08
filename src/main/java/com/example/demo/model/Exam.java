package com.example.demo.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "exams")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Exam implements Serializable {
	
	@Id
	@Column(length = 20)
	private String examId;					//試験ID
	
	@Column(nullable = false, length = 100)
	private String examName;				//試験名
	
	@Column(nullable = false)
	private LocalDate examDate;				//試験日
	
	@Column(nullable = false, length = 50)
	private String perfForm;				//編成
	
	@Column(nullable = false)
	private LocalDate applicationDeadline;	//申請締切日

	
	//リレーション Exam(一)－(多)Application
    //Examに紐づいたApplication内容はapplicationsで一覧できる
    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Application> applications = new ArrayList<>();
    
    //リレーション Exam(一)－(多)ExamTarget
    //Examに紐づいたExamTargetの内容はexamTargetsで一覧できる
    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExamTarget> examTargets = new ArrayList<>();
}
