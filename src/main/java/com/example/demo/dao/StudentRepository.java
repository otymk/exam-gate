//studentsテーブルを操作するDAO

package com.example.demo.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Student;

public interface StudentRepository extends JpaRepository<Student, String> {
	
	//ログイン確認用
	Optional<Student> findByStudentIdAndNameAndMail(
			String studentId, String name, String mail);

	//学生情報を全件取得(studentIdの小さい順)
	List<Student> findAllByOrderByStudentIdAsc();

}
