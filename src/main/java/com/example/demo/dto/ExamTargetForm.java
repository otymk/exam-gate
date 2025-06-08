package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamTargetForm {

	@NotEmpty(message = "選択してください。")
	private List<String> studentIds = new ArrayList<>();
}
