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
public class TeacherController {


    @Autowired
    private ExamServiceImpl examService;

    @PostMapping("/teacher/examList")
    public ApiResult loadExam(@RequestBody Map<String, String> classEntity) {
        System.out.println(classEntity);
        String id = classEntity.get(FormParameter.TeacherFunction.CREATER);
return  examService.teacherExamList(id);

    }

    @PostMapping("/teacher/publicExam")
    public ApiResult loadScore(@RequestBody Map<String, String> classEntity) {
        System.out.println(classEntity);
        String id = classEntity.get(FormParameter.StudentFunction.ID);

    return  examService.scoreList(id);

    }




}
