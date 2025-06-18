package com.example.demo.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import com.example.demo.dao.AdminRepository;
import com.example.demo.dao.StudentRepository;
import com.example.demo.dto.AdminLoginForm;
import com.example.demo.dto.AdminLoginResult;
import com.example.demo.dto.Login;
import com.example.demo.dto.StudentDto;
import com.example.demo.exception.InvalidCredentialsException;
import com.example.demo.model.Admin;
import com.example.demo.model.Student;

@Service
public class LoginLogic {
	
	private final StudentRepository studentRepository;
	private final AdminRepository adminRepository;
	//コンストラクタ
	public LoginLogic(
			StudentRepository studentRepository,
			AdminRepository adminRepository) {
		this.studentRepository = studentRepository;
		this.adminRepository = adminRepository;
	}	
	
	//学生ログイン処理
	public StudentDto loginStudent(Login login) {
		Optional<Student> sOpt = studentRepository.findByStudentIdAndNameAndMail(
						login.getStudentId(),
						login.getName(),
						login.getMail());
				
		Student student = sOpt.orElseThrow(InvalidCredentialsException::new);
		
		return new StudentDto(
						student.getStudentId(),
						student.getName(),
						student.getGrade(),
						student.getInstrument(),
						student.getMail()
						);
	}
	
	//管理者ログイン処理
	public AdminLoginResult loginAdmin(AdminLoginForm loginDto) {
		Optional<Admin> aOpt = 
				adminRepository.findByAdminIdAndPass(
				loginDto.getAdminId(), loginDto.getPass());
		
		Admin admin = aOpt.orElseThrow(InvalidCredentialsException::new);
		
		return new AdminLoginResult(admin.getAdminId());
	}

}
