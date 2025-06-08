package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Login {
	
	@NotBlank(message = "管理者IDが未入力です")
	private String studentId;
	
	@NotBlank(message = "氏名が未入力です")
	private String name;
	
	@NotBlank(message = "パスワードが未入力です")
	private String mail;

}
