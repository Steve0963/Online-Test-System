package com.hnust.swe.entity;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ClassListResult {

    private Integer class_id;
    private String name;
    private String class_token;
    private Date creat_time;
    private int class_num;
    private String class_desc;

}
