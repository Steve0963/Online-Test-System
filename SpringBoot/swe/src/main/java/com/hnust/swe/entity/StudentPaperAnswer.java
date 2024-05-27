package com.hnust.swe.entity;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentPaperAnswer {


	private Double score;
	private Integer id;
	private Integer stu_id;
	private Integer paper_problem_id;
	private Date create_time;
	private String answer;
	private Integer exam_id;

}
