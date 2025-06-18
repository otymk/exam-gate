package com.example.demo.model;

import java.io.Serializable;
import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "applications")
@Getter
@NoArgsConstructor
public class Application implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)		//DB側で自動採番
	private Long applicationId;				//申請ID(自動採番)
	
	@ManyToOne(fetch = FetchType.LAZY)		//Application(多)－(一)Student
	@JoinColumn(
			name = "student_id",		//applicationsの外部キー
			referencedColumnName = "studentId",	//studentsの主キー
			nullable = false
			)
	private Student student;
	
	@ManyToOne(fetch = FetchType.LAZY)		//Application(多)－(一)Exam
	@JoinColumn(
			name = "exam_id",		//applicationsの外部キー
			referencedColumnName = "examId",	//examsの主キー
			nullable = false
			)
	private Exam exam;
	
	@Column(nullable = false, length = 50)
	private String perfForm;				//編成
	
	@Column(nullable = false)
	private String composer;				//作曲者
	
	@Column(nullable = false, length = 50)
	private String years;					//作曲者の生没年
	
	@Column(nullable = false)
	private String title;					//作品名
	
	private String movement;				//楽章(任意)
	
	@Column(nullable = false, length = 20)
	private String duration;
	
	private String member;
	private String equipment;
	private String memo;
	private LocalDate applicationDate = LocalDate.now();	//申請日(自動取得)
	 
	
	//コンストラクタ
	//applicationIdは自動採番, applicationdateは自動取得
	public Application(
			Student student, Exam exam, String perfForm, String composer,
			String years, String title, String movement, String duration,
			String member,String equipment, String memo) {
		this.student = student;
		this.exam = exam;
		this.perfForm = perfForm;
		this.composer = composer;
		this.years = years;
		this.title = title;
		this.movement = movement;
		this.duration = duration;
		this.member = member;
		this.equipment = equipment;
		this.memo = memo;
	}
	
	//applicationId, applicationdateはsetterなし
	public void setStudent(Student student) {this.student = student;}
	public void setExam(Exam exam) {this.exam = exam;}
	public void setPerfForm(String perfForm) {this.perfForm = perfForm;}
	public void setComposer(String composer) {this.composer = composer;}
	public void setYears(String years) {this.years = years;}
	public void setTitle(String title) {this.title = title;}
	public void setMovement(String movement) {this.movement = movement;}
	public void setDuration(String duration) {this.duration = duration;}
	public void setMember(String member) {this.member = member;}
	public void setEquipment(String equipment) {this.equipment = equipment;}
	public void setMemo(String memo) {this.memo = memo;}

}
