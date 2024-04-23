package com.hnust.swe.serviceimpl;
import com.hnust.swe.entity.ApiResult;
import com.hnust.swe.entity.ClassListResult;
import com.hnust.swe.entity.StudentClassListResult;
import com.hnust.swe.mapper.ClassListMapper;
import com.hnust.swe.service.ClassListService;
import com.hnust.swe.util.ApiResultHandler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ClassListServiceImpl implements ClassListService {

    @Autowired
    private ClassListMapper classListMapper;


    @Override
    public void creatClass(String className, String classDesc,String classToken,String createrID) {
    classListMapper.creatClass(className,classDesc,classToken,createrID);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ApiResult <StudentClassListResult> joinClass(String token,String studenId){
        System.out.println("asdasdasdas");
           Integer classId= classListMapper.getClassIdByToken(token);
            if (classId!=null){
                System.out.println("error");
                classListMapper.joinClass(studenId,classId);

                 ApiResultHandler.buildApiResult(200, "请求成功", null);

            }
            return ApiResultHandler.buildApiResult(401, "班级不存在！", null);
          
            //System.out.println("token"+classId);
            //classListMapper.joinClass(studenId);
    
    }


    @Override
    public List <ClassListResult> loadClassList(String account) {
    
        return classListMapper.loadClassList(account);
    }

    @Override
    public List <StudentClassListResult> loadMyClassList(String account) {
        List <StudentClassListResult> result=classListMapper.loadMyClassList(account);
        System.out.println("加载班级list");
        
        return result;
    }
}
