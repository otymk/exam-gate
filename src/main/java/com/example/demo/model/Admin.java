package com.example.demo.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "admins")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin implements Serializable {	//Serializable不要か？？
	
	@Id
	@Column(length = 10)
	private String adminId;			//管理者ID
	
	@Column(nullable = false, length = 50)
	private String adminName;		//氏名
	
	@Column(nullable = false, length = 20)
	private String pass;			//パスワード
		
}
