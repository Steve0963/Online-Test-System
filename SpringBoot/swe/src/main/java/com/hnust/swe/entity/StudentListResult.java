package com.hnust.swe.entity;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentListResult {


	private String stu_id; 
	private String stu_name;  
	private Date join_time; 
	private String class_name; 
	private String class_id; 


}
