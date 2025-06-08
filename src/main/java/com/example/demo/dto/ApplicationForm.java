package com.example.demo.dto;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationForm implements Serializable {
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

}
