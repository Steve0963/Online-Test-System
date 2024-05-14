package com.hnust.swe.entity;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaperListResult {


	private String id; 
	private String paper_name;  
	private Date update_time; 
	private Date create_time; 
	private String creater_id;
	private String total_score;

}
