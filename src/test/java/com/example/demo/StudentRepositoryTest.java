package com.example.demo;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.dao.StudentRepository;
import com.example.demo.model.*;

@SpringBootTest
public class StudentRepositoryTest {
	@Autowired
	private StudentRepository studentRepository;
	
	@Test
	void testFindBy() {
		Optional<Student> studentOpt =
			studentRepository.findByStudentIdAndNameAndMail(
					"S001", "田中花子", "hanako@example.com");
		if (studentOpt.isPresent()) {
			System.out.println("findBy成功");
		} else {
			System.out.println("findBy失敗");
		}
	}
}
