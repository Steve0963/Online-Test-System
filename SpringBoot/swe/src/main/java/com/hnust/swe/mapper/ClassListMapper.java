package com.hnust.swe.mapper;

import com.hnust.swe.entity.ClassListResult;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ClassListMapper {

    @Insert("SELECT teacher_account.account,teacher_account.role, teacher_info.name from teacher_account INNER JOIN teacher_info ON teacher_account.account = teacher_info.account WHERE teacher_account.account = #{account} and teacher_account.password = #{password}")
    public ClassListResult creatClass(String className, String classDesc,String classToken);

    @Select("SELECT stu_account.account, stu_account.role, stu_info.name from stu_account INNER JOIN stu_info ON stu_account.account = stu_info.account WHERE stu_account.account = #{account} and stu_account.password = #{password}")
    public ClassListResult loadClassList(String account);
}
