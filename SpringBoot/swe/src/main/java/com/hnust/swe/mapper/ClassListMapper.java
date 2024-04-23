package com.hnust.swe.mapper;

import com.hnust.swe.entity.ClassListResult;
import com.hnust.swe.entity.StudentClassListResult;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ClassListMapper {

    @Insert("INSERT INTO class (name, creater_id, class_token, class_desc) " +
            "VALUES (#{className}, #{createrID}, #{classToken}, #{classDesc})")
    public void creatClass(String className, String classDesc,String classToken,String createrID);


    @Select("SELECT class_id FROM class WHERE class_token = #{token}")
    public Integer getClassIdByToken(String token);

    @Insert("INSERT INTO student_class (stu_id, class_id) " +
    "VALUES (#{studentId}, #{classId})")
    public void joinClass(String studentId,Integer classId);

    @Select("SELECT `name`, grade, class_token, creat_time, class_id FROM class WHERE creater_id = #{account}")
    public List <ClassListResult> loadClassList(String account);

    @Select("SELECT\n" +
    "	class.`name`, \n" +
    "	class.grade, \n" +
    "	class.class_id, \n" +
    "	class.creater_id, \n" +
    "	class.class_desc, \n" +
    "	student_class.join_time, \n" +
    "	teacher_info.`name` AS teacher_name\n" +
    "FROM\n" +
    "	class\n" +
    "	INNER JOIN\n" +
    "	student_class\n" +
    "	INNER JOIN\n" +
    "	teacher_info\n" +
    "	ON \n" +
    "		class.creater_id = teacher_info.teacher_id\n" +
    "WHERE\n" +
    "	student_class.stu_id = #{account} AND\n" +
    "	student_class.class_id = class.class_id AND\n" +
    "	class.creater_id = teacher_info.teacher_id")
    public List <StudentClassListResult > loadMyClassList(String account);
}