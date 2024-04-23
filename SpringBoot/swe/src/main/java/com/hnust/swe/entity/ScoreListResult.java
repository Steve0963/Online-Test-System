package com.hnust.swe.entity;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreListResult {


	private String exam_score;
	private String id;
	private String exam_name;
	private Date start_time;
	private String exam_type;
}
