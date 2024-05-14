package com.hnust.swe.entity;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamListResult {


    private List<Integer> exam_class; 
	private String id; 
	private String creater_id; 
	private String exam_name; 
	private String exam_type; 
	private Date start_time; 
	private Date create_time; 
	private String paper_id; 
	private Date end_time; 
	private String exam_place;

}
