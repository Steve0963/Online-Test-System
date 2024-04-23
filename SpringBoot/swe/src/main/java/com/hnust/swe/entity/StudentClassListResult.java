package com.hnust.swe.entity;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class StudentClassListResult {

    private String grade;
    private String name;
    private String teacher_name;
    private String class_id;
    private String creater_id;
    private String class_desc;
    private int class_num;
    private Date join_time;

}
