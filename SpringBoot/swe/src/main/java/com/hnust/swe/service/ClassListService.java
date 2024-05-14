package com.hnust.swe.service;

import java.util.List;

import com.hnust.swe.entity.ApiResult;
import com.hnust.swe.entity.ClassListResult;
import com.hnust.swe.entity.StudentClassListResult;


public interface ClassListService {

    public ApiResult<List<ClassListResult>> creatClass(String className, String classDesc,String classToken,String createrID,String classId);
    public ApiResult <StudentClassListResult> joinClass(String token, String studentId);
    public ApiResult<List<ClassListResult>> loadClassList(String account);
    public List <StudentClassListResult > loadMyClassList(String account);
}
