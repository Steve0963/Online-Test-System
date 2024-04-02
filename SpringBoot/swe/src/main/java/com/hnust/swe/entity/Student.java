package com.hnust.swe.entity;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Student {

    private Integer id;

    private String account;

    private String password;

    private Date reg_time;

    private int role;


}