package com.hnust.swe.entity;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaperProblem {


	private Double problem_score;
	private Integer id;
	private Integer paper_id;
	private Integer problem_id;
	private Date update_time;
	private Date create_time;
	private String updater_id;
	private String exam_type;
}
