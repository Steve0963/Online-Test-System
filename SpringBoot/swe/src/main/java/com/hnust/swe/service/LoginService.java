package com.hnust.swe.service;

import com.hnust.swe.entity.LoginResult;


public interface LoginService {

    public LoginResult adminLogin(String username, String password);

    public LoginResult teacherLogin(String username, String password);

    public LoginResult studentLogin(String username, String password);
}
