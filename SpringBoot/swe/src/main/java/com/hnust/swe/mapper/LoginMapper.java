package com.hnust.swe.mapper;

import com.hnust.swe.entity.LoginResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LoginMapper {

    @Select("select role from admin_account where account = #{account} and password = #{password}")
    public LoginResult adminLogin(String account, String password);

    @Select("SELECT teacher_account.account,teacher_account.role, teacher_info.name ,teacher_info.teacher_id from teacher_account INNER JOIN teacher_info ON teacher_account.account = teacher_info.account WHERE teacher_account.account = #{account} and teacher_account.password = #{password}")
    public LoginResult teacherLogin(String account, String password);

    @Select("SELECT stu_account.account, stu_account.role, stu_info.name from stu_account INNER JOIN stu_info ON stu_account.account = stu_info.account WHERE stu_account.account = #{account} and stu_account.password = #{password}")
    public LoginResult studentLogin(String account, String password);
}
