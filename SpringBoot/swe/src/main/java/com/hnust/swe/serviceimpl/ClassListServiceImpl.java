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

    @SuppressWarnings("unchecked")
    @Override
    public ApiResult<List<ClassListResult>> creatClass(String className, String classDesc, String classToken,
            String createrID, String classId) {
                System.out.println("班级id"+classId);
        try {
            if (classId == "") {
                classListMapper.creatClass(className, classDesc, classToken, createrID);
                return ApiResultHandler.buildApiResult(200, "创建成功！", null);
            }

            else {
                classListMapper.updateClassInfo(className, classId, classDesc);
                return ApiResultHandler.buildApiResult(200, "保存成功！", null);
            }

        } catch (Exception e) {
            // TODO: handle exception
            return ApiResultHandler.buildApiResult(401, "发生错误！", null);
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public ApiResult<StudentClassListResult> joinClass(String token, String studenId) {
        System.out.println("asdasdasdas");
        Integer classId = classListMapper.getClassIdByToken(token);
        if (classId != null) {
            System.out.println("error");
            classListMapper.joinClass(studenId, classId);

            ApiResultHandler.buildApiResult(200, "请求成功", null);

        }
        return ApiResultHandler.buildApiResult(401, "班级不存在！", null);

        // System.out.println("token"+classId);
        // classListMapper.joinClass(studenId);

    }

    @SuppressWarnings("unchecked")
    @Override
    public ApiResult<List<ClassListResult>> loadClassList(String account) {

        List<ClassListResult> classList = classListMapper.loadClassList(account);
        if (classList != null) {

            for (ClassListResult entity : classList) {
                int num = classListMapper.getStudentListByClassId(entity.getClass_id()).size();
                entity.setClass_num(num);
            }
            return ApiResultHandler.buildApiResult(200, "请求成功", classList);

        }
        return ApiResultHandler.buildApiResult(400, "没有班级", null);
    }

    @Override
    public List<StudentClassListResult> loadMyClassList(String account) {
        List<StudentClassListResult> result = classListMapper.loadMyClassList(account);

        for (StudentClassListResult studentClassListResult : result) {

            int num=classListMapper.getStudentListByClassId(studentClassListResult.getClass_id()).size();
            studentClassListResult.setClass_num(num);
            
        }
        
        System.out.println("加载班级list");

        return result;
    }
}
