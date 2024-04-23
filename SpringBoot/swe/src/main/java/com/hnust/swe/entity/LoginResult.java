package com.hnust.swe.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class LoginResult {

    private int role;
    private String account;
    private String name;
    private String id;

}
