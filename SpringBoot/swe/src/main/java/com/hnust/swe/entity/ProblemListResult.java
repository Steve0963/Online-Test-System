package com.hnust.swe.entity;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProblemListResult {

	private String id;
	private String desc;
	private String type;
	private String answer;
	private String explain;
	private Date update_time;
	private String creater_id;
	private Date create_time;
	private String options;


}
