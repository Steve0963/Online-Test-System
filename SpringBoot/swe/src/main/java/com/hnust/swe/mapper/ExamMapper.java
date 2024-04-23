package com.hnust.swe.mapper;

import com.hnust.swe.entity.ExamListResult;
import com.hnust.swe.entity.ScoreListResult;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ExamMapper {


    @Select("SELECT\n" +
    "	class_id\n" +
    "FROM\n" +
    "	student_class\n" +
    "WHERE\n" +
    "	stu_id = #{studentId}")
    public List<Integer> getClassIdByStuId(String studentId);

    @Select("SELECT\n" +
    "	exam.*\n" +
    "FROM\n" +
    "	exam\n" +
    "WHERE\n" +
    "	exam.class_id = #{classId}")
    public List<ExamListResult> getExamListByClassId(Integer classId);

    @Select("SELECT\n" +
    "	score.exam_score, \n" +
    "	exam.id, \n" +
    "	exam.exam_name, \n" +
    "	exam.start_time, \n" +
    "	exam.exam_type\n" +
    "FROM\n" +
    "	score\n" +
    "	INNER JOIN\n" +
    "	exam\n" +
    "	ON \n" +
    "		score.exam_id = exam.id\n" +
    "WHERE\n" +
    "	score.exam_id = exam.id AND\n" +
    "	score.student_id = #{studentId}")
    public List<ScoreListResult> getScoreListByStuId(String studentId);





}