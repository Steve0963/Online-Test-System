package com.hnust.swe.service;
import com.hnust.swe.entity.ApiResult;
import com.hnust.swe.entity.ExamListResult;
import com.hnust.swe.entity.ScoreListResult;



public interface ExamService {

    public ApiResult <ExamListResult> examList(String studentId);
    public ApiResult <ScoreListResult> scoreList(String studentId);

    public ApiResult <ExamListResult> teacherExamList(String teacherId);

}
