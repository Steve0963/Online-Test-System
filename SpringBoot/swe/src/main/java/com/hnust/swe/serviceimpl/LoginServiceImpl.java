package com.hnust.swe.serviceimpl;
import com.hnust.swe.entity.LoginResult;
import com.hnust.swe.mapper.LoginMapper;
import com.hnust.swe.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginMapper loginMapper;

    @Override
    public LoginResult adminLogin(String account, String password) {
        return loginMapper.adminLogin(account,password);
    }

    @Override
    public LoginResult teacherLogin(String account, String password) {
        return loginMapper.teacherLogin(account,password);
    }

    @Override
    public LoginResult studentLogin(String account, String password) {
    
        return loginMapper.studentLogin(account,password);
    }
}
