package com.hnust.swe.mapper;

import com.hnust.swe.entity.ClassListResult;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ClassListMapper {

    @Insert("INSERT INTO class (name, creater_id, class_token, class_desc) " +
            "VALUES (#{className}, #{createrID}, #{classToken}, #{classDesc})")
    public void creatClass(String className, String classDesc,String classToken,String createrID);

    @Select("SELECT `name`, grade, class_token, creat_time, class_id FROM class WHERE creater_id = #{account}")
    public List <ClassListResult> loadClassList(String account);
}
