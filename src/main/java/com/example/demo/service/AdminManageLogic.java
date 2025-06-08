package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.example.demo.dao.AdminRepository;
import com.example.demo.dto.AdminDto;
import com.example.demo.dto.AdminLoginForm;
import com.example.demo.dto.AdminLoginResult;
import com.example.demo.dto.ExamDto;
import com.example.demo.model.Admin;
import com.example.demo.model.Exam;

@Service
public class AdminManageLogic {

	private final AdminRepository adminRepository;
	public AdminManageLogic(AdminRepository adminRepository) {
		this.adminRepository = adminRepository;
	}

	//管理者情報を全件取得
	public List<AdminDto> getAllAdmins() {
		List<Admin> adminList = adminRepository.findAll();
		List<AdminDto> dtoList =
				adminList.stream().map(exam -> new AdminDto(
					exam.getAdminId(),
					exam.getAdminName(),
					exam.getPass()
					))
				.collect(Collectors.toList());
		
		return dtoList;
	}
}
