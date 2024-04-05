package com.hnust.swe.entity;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ClassListResult {

    private String class_id;
    private String class_name;
    private String class_token;
    private Date creat_time;
    private int class_num;

}
