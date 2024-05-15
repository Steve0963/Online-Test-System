package com.hnust.swe.mapper;

import com.hnust.swe.entity.ExamListResult;
import com.hnust.swe.entity.PaperListResult;
import com.hnust.swe.entity.ProblemListResult;
import com.hnust.swe.entity.ScoreListResult;
import com.hnust.swe.entity.StudentListResult;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
            "	exam_id\n" +
            "FROM\n" +
            "	exam_class\n" +
            "WHERE\n" +
            "	exam_class.class_id = #{classId}")
    public List<Integer> getExamIdByClassId(Integer classId);

    @Select("SELECT\n" +
            "	exam.*\n" +
            "FROM\n" +
            "	exam\n" +
            "WHERE\n" +
            "	exam.id = #{examId}")
    public ExamListResult getExamListByExamId(Integer examId);

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

    @Select("SELECT\n" +
            "	exam.*\n" +
            "FROM\n" +
            "	exam\n" +
            "WHERE\n" +
            "	exam.creater_id = #{teacherId}")
    public List<ExamListResult> getExamListByTeacherId(String teacherId);

    @Select("SELECT\n" +
            "	exam_class.class_id\n" +
            "FROM\n" +
            "	exam_class\n" +
            "WHERE\n" +
            "	exam_class.exam_id = #{examId}")
    public List<Integer> getClassListByExamId(String examId);

    @Select("SELECT\n" +
            "	papers.*\n" +
            "FROM\n" +
            "	papers\n" +
            "WHERE\n" +
            "	papers.creater_id = #{teacherId}")
    public List<PaperListResult> getPaperListById(String teacherId);

    @Select("SELECT\n" +
            "	problems.*\n" +
            "FROM\n" +
            "	problems\n" +
            "WHERE\n" +
            "	problems.creater_id = #{teacherId}")
    public List<ProblemListResult> getProblemListById(String teacherId);

    @Select("SELECT\n" +
            "	problem_id\n" +
            "FROM\n" +
            "	paper_problems\n" +
            "WHERE\n" +
            "	paper_id = #{paperId}")
    public List<Integer> getProblemIdByPaperId(String paperId);

    @Select("SELECT\n" +
            "	class_id\n" +
            "FROM\n" +
            "	class\n" +
            "WHERE\n" +
            "	creater_id = #{createrId}")
    public List<Integer> getClassIdByTeacherId(String createrId);

    @Select("SELECT\n" +
            "	problems.*\n" +
            "FROM\n" +
            "	problems\n")
    public List<ProblemListResult> getAllProblemList(String teacherId);

    @Select("SELECT\n" +
            "	problems.*\n" +
            "FROM\n" +
            "	problems\n" +
            "WHERE\n" +
            "	id = #{problemId}")
    public ProblemListResult getProblemById(Integer problemId);

    @Insert("INSERT INTO papers (paper_name, creater_id) " +
            "VALUES (#{paper_name}, #{creater_id})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    public void createPaper(PaperListResult paper);

    @Insert("INSERT INTO exam (creater_id, exam_name, exam_type, start_time, paper_id, end_time, exam_place) " +
            "VALUES (#{creater_id}, #{exam_name}, #{exam_type}, #{start_time}, #{paper_id}, #{end_time}, #{exam_place})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    public void createExam(ExamListResult exam);

    @Update("update exam set creater_id=#{creater_id}, exam_name=#{exam_name}, exam_type=#{exam_type}, start_time=#{start_time}, paper_id=#{paper_id}, end_time=#{end_time}, exam_place=#{exam_place}"+ "where id = #{id}")
    public void updateExamInfo(ExamListResult exam);

    @Insert("INSERT INTO exam_class (exam_id, class_id) " +
            "VALUES (#{examId},#{classId})")
    public void createExamClass(String examId, Integer classId);

    @Insert("INSERT INTO exam_class (exam_id, class_id) " +
            "VALUES (#{examId}, #{classId})")
    public void addClassToExam(String examId, Integer classId);

    @Delete("Delete from exam_class where " + "exam_id = #{examId}")
    public void deleteExamClassByExamId(String examId);

    @Update("update papers set paper_name = #{paper_name} " + "where id = #{id}")
    public void updatePaper(PaperListResult paper);

    @Insert("INSERT INTO paper_problems (paper_id, problem_id,updater_id) " +
            "VALUES (#{paperId}, #{problemId}, #{updaterId})")
    public void addProblemToPaper(String paperId, String problemId, String updaterId);

    @Delete("Delete from paper_problems where " + "paper_id = #{paperId}")
    public void deletePaperProblemByPaperId(String paperId);


    @Delete("Delete from exam where " + "id = #{examId}")
    public void deleteExamByExamId(String examId);


    @Select("SELECT\n" +
            "	class.`name` AS class_name, \n" +
            "	class.`class_id`, \n" +
            "	student_class.join_time, \n" +
            "	stu_info.stu_id, \n" +
            "	stu_info.`name` AS stu_name\n" +
            "FROM\n" +
            "	class\n" +
            "	INNER JOIN\n" +
            "	student_class\n" +
            "	ON \n" +
            "		class.class_id = student_class.class_id\n" +
            "	INNER JOIN\n" +
            "	stu_info\n" +
            "	ON \n" +
            "		student_class.stu_id = stu_info.stu_id\n" +
            "WHERE\n" +
            "	class.class_id = #{classId}")
    public List<StudentListResult> getStudentListByClassId(Integer classId);

    @Delete("Delete from student_class where class_id = #{classId} and stu_id = #{studentId}")
    public void removeStudentFromClassById(String classId, String studentId);

    @Delete("Delete from student_class where class_id = #{classId}")
    public void deleteAllStudentByClassId(String classId);

    @Delete("Delete from exam_class where class_id = #{classId}")
    public void deleteAllExamByClassId(String classId);

    @Delete("Delete from class where class_id = #{classId}")
    public void deleteClassById(String classId);

}