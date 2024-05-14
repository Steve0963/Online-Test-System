package com.hnust.swe.controller;
import com.hnust.swe.entity.*;
import com.hnust.swe.serviceimpl.ClassListServiceImpl;
import com.hnust.swe.serviceimpl.ExamServiceImpl;
import com.hnust.swe.util.ApiResultHandler;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("rawtypes")
@CrossOrigin(origins = "*")
@RestController
public class StudentController {

    @Autowired
    private ClassListServiceImpl classListService;

    @PostMapping("/joinClass")
    public ApiResult createClass(@RequestBody Map<String, String> classEntity) {

  
        String token = classEntity.get(FormParameter.Student.TOKEN);
        String studentID=classEntity.get(FormParameter.Student.ID);
System.out.println(studentID);
System.out.println(token);
try {
    ApiResult result= classListService.joinClass(token,studentID);
    return result;

} catch (Exception e) {
    return ApiResultHandler.buildApiResult(400, "请求失败", null);
    
}

    
    }


    @PostMapping("/myClass")
    public ApiResult loadClass(@RequestBody Map<String, String> classEntity) {
        System.out.println(classEntity);

        String id = classEntity.get(FormParameter.Student.ID);


List <StudentClassListResult > classListRes = classListService.loadMyClassList(id);

        if (classListRes != null) {
            return ApiResultHandler.buildApiResult(200, "请求成功", classListRes);
        }

        return ApiResultHandler.buildApiResult(400, "请求失败", null);
    }

    @Autowired
    private ExamServiceImpl examService;

    @PostMapping("/myExam")
    public ApiResult loadExam(@RequestBody Map<String, String> classEntity) {
        System.out.println(classEntity);
        String id = classEntity.get(FormParameter.Student.ID);

return  examService.examList(id);

    }

    @PostMapping("/myScore")
    public ApiResult loadScore(@RequestBody Map<String, String> classEntity) {
        System.out.println(classEntity);
        String id = classEntity.get(FormParameter.Student.ID);

    return  examService.scoreList(id);

    }




}
