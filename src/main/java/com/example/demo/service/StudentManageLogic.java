package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.example.demo.dao.StudentRepository;
import com.example.demo.dto.StudentDto;
import com.example.demo.model.Student;

@Service
public class StudentManageLogic {
	
	private final StudentRepository studentRepository;
	public StudentManageLogic(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}
	
	public List<StudentDto> getAllStudent() {
		//学生情報を全件取得
		List<Student> studentList = studentRepository.findAllByOrderByStudentIdAsc();
		
		//Dto型のListへ変換
		List<StudentDto> studentDtoList = 
				studentList.stream().map(student -> new StudentDto(
						student.getStudentId(),
						student.getName(),
						student.getGrade(),
						student.getInstrument(),
						student.getMail()
				))
				.collect(Collectors.toList());
		
		return studentDtoList;
	}
}
