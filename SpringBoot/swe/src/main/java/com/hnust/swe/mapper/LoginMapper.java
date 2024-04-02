package com.hnust.swe.mapper;
import com.hnust.swe.entity.LoginResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface LoginMapper {

    @Select("select role from admin_account where account = #{account} and password = #{password}")
    public LoginResult adminLogin(String account, String password);

    @Select("select role from teacher_account where account = #{account} and password = #{password}")
    public LoginResult teacherLogin(String account, String password);

    @Select("select role from stu_account where account = #{account} and password = #{password}")
    public LoginResult studentLogin(String account,String password);
}
