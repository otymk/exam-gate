package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.dto.ExamInfo;
import com.example.demo.model.ExamTarget;

public interface ExamTargetRepository extends JpaRepository<ExamTarget, Integer> {
	@Query("SELECT new com.example.demo.dto.ExamInfo(e.exam.examId, e.exam.examName) " +
		       "FROM ExamTarget e WHERE e.student.studentId = :studentId")
		List<ExamInfo> findExamInfosByStudentId(@Param("studentId") String studentId);

	@Query("SELECT t FROM ExamTarget t JOIN FETCH t.student")
		List<ExamTarget> findAllWithStudent();

	List<ExamTarget> findByExam_ExamId(String examId);

	@Query("""
		select et.student.studentId
		from ExamTarget et
		where et.exam.examId = :examId
	""")
	List<String> findStudent_StudentIdByExam_ExamId(String examId);

}

/*
① @Query(...)
	これは Spring Data JPA のカスタムJPQLクエリです。

② SELECT new your.package.ExamInfoDto(...)
	これは DTOコンストラクタ式（コンストラクタ投影）。
	ExamInfoDto というDTOクラスの コンストラクタを使ってオブジェクトを作る という意味。
	new ExamInfoDto(e.exam.examId, e.exam.examName) によって
		examId と examName を渡して DTO を1つ作ります。
	これをレコードの数だけ繰り返して List<ExamInfoDto> を返します。
	🔸 注意：この書き方を使うには、ExamInfoDto に該当の引数を受け取るコンストラクタが必要です！

③ FROM ExamTarget e
	これは ExamTarget エンティティ（DBテーブルの対応クラス）を eという別名で使う という宣言。
	e は ExamTarget の1行（1オブジェクト）です。

④ e.exam.examId, e.exam.examName
	これは ExamTarget の中の exam フィールド（@ManyToOneで Exam に紐づいてる）を通して、
		Exam の各カラムにアクセスしています。
	なので e.exam.examId は実質：
		ExamTarget.getExam().getExamId()
	と同じ意味。

⑤ WHERE e.student.studentId = :studentId
	:studentId は パラメータプレースホルダ（あとで実際の値が入る）
	e.student.studentId は、ExamTarget → Student へのリレーションを使って、
		studentId で絞り込みをしています

⑥ @Param("studentId") String studentId
	上の :studentId プレースホルダに対応させるパラメータ定義
	メソッド引数の studentId をクエリに渡すためのもの
*/