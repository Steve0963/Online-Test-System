package com.hnust.swe.service;
import java.util.List;
import java.util.Map;

import com.hnust.swe.entity.ApiResult;
import com.hnust.swe.entity.ClassListResult;
import com.hnust.swe.entity.ExamListResult;
import com.hnust.swe.entity.PaperListResult;
import com.hnust.swe.entity.ProblemListResult;
import com.hnust.swe.entity.ScoreListResult;
import com.hnust.swe.entity.StudentListResult;



public interface ExamService {

    public ApiResult <ExamListResult> examList(String studentId);
    public ApiResult <PaperListResult> paperList(String createrid);
    public ApiResult <ProblemListResult> problemList(String createrid);

    public ApiResult <StudentListResult> studentList(String createrid);
    public ApiResult <ProblemListResult> paperProblemList(String paperid);

    public ApiResult <ScoreListResult> scoreList(String studentId);

    public ApiResult <ExamListResult> teacherExamList(String teacherId);

    public ApiResult <PaperListResult> savePaperProblem(String createrId,String paperid ,String paperName,List <Map<String,String>>problem);

    public ApiResult <StudentListResult> removeStudent(String classId,String studentId);

    public ApiResult <ClassListResult> deleteClass(String classId);

    public ApiResult <ExamListResult> saveExam(Object exam);


}
