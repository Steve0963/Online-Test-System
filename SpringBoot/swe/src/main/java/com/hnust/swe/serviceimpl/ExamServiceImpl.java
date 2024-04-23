package com.hnust.swe.serviceimpl;
import com.hnust.swe.entity.ApiResult;
import com.hnust.swe.entity.ExamListResult;
import com.hnust.swe.entity.ScoreListResult;
import com.hnust.swe.mapper.ExamMapper;

import com.hnust.swe.service.ExamService;
import com.hnust.swe.util.ApiResultHandler;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ExamServiceImpl implements ExamService {

    @Autowired
    private ExamMapper examMapper;

    @SuppressWarnings("unchecked")
    @Override
    public ApiResult <ExamListResult> examList(String studenId){
           List <Integer> classIdList= examMapper.getClassIdByStuId(studenId);
           List<ExamListResult> allExamList=new LinkedList<>();
            if (classIdList!=null){
                System.out.println("error");
                for (Integer classId : classIdList) {
                    List<ExamListResult> examList=examMapper.getExamListByClassId(classId);
                    if(examList!=null){
                        for (ExamListResult exam : examList) {
                            allExamList.add(exam);
                        }
                    }
                }
                  return ApiResultHandler.buildApiResult(200, "请求成功", allExamList);
            }
            return ApiResultHandler.buildApiResult(401, "还没有加入班级！", null);
    
    }


    @SuppressWarnings("unchecked")
    @Override
    public ApiResult <ScoreListResult> scoreList(String studenId){
           
           List<ScoreListResult> scoreList=examMapper.getScoreListByStuId(studenId);
            if (scoreList!=null){
                return ApiResultHandler.buildApiResult(200, "请求成功", scoreList);
                }

                return ApiResultHandler.buildApiResult(401, "暂无成绩！", null);
                 
            }
    
    }


