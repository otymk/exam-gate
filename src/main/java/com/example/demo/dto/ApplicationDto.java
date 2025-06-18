package com.example.demo.dto;

import java.io.Serializable;
import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApplicationDto implements Serializable {

	    private Long applicationId;
	    private String studentId;
	    private String name;
	    private String grade;
	    private String instrument;
	    private String perfForm;
	    private String composer;
	    private String years;
	    private String title;
	    private String movement;
	    private String duration;
	    private String member;
	    private String equipment;
	    private String memo;
	    private LocalDate applicationDate;
		
	    public ApplicationDto(
	    	    Long applicationId,
	    	    String studentId, String name, String grade, String instrument,
	    	    String perfForm, String composer, String years,
	    	    String title, String movement, String duration,
	    	    String member, String equipment, String memo,
	    	    LocalDate applicationDate) {

	    	    this.applicationId = applicationId;
	    	    this.studentId = studentId;
	    	    this.name = name;
	    	    this.grade = grade;
	    	    this.instrument = instrument;
	    	    this.perfForm = perfForm;
	    	    this.composer = composer;
	    	    this.years = years;
	    	    this.title = title;
	    	    this.movement = movement;
	    	    this.duration = duration;
	    	    this.member = member;
	    	    this.equipment = equipment;
	    	    this.memo = memo;
	    	    this.applicationDate = applicationDate;
	    	}

}
