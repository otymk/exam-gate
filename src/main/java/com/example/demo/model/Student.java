package com.example.demo.model;

import java.io.Serializable;
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
@Table(name = "students")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable {	//Serializable不要か？？
	
	@Id
	@Column(length = 20)
	private String studentId;		//学生番号
	
	@Column(nullable = false, length = 50)
	private String name;			//氏名
	
	@Column(nullable = false, length = 20)
    private String grade;			//学年
	
	@Column(nullable = false, length = 20)
    private String instrument;		//楽器
	
	@Column(nullable = false, length = 255)
    private String mail;			//メールアドレス
    
	
    //リレーション定義 Student(一)－(多)Application
    //Studentに紐づいたApplication内容はapplicationsで一覧できる
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Application> applications = new ArrayList<>();
    
    //リレーション Student(一)－(多)ExamTarget
    //Studentに紐づいたExamTargetの内容はexamTargetsで一覧できる
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExamTarget> examTargets = new ArrayList<>();
}
