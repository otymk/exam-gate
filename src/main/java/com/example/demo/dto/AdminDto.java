package com.example.demo.dto;

import java.io.Serializable;

import com.example.demo.model.Admin;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDto implements Serializable {

	@NotBlank(message = "管理者IDが未入力です")
	private String adminId;
	
	@NotBlank(message = "氏名が未入力です")
	private String adminName;
	
	@NotBlank(message = "パスワードが未入力です")
	private String pass;
	
	public Admin toEntity() {
		Admin admin = new Admin();
		admin.setAdminId(this.adminId);
		admin.setAdminName(this.adminName);
		admin.setPass(this.pass);
		return admin;
	}
}
