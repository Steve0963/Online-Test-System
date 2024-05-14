package com.hnust.swe.controller;

import com.hnust.swe.entity.*;
import com.hnust.swe.serviceimpl.ClassListServiceImpl;
import com.hnust.swe.util.ApiResultHandler;
import com.hnust.swe.util.TokenGenerator;

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
public class ClassController {

    @Autowired
    private ClassListServiceImpl classListService;

    @PostMapping("/teacher/creatClass")
    public ApiResult createClass(@RequestBody Map<String, String> classEntity) {

        String name = classEntity.get(FormParameter.Class.NAME);
        String desc = classEntity.get(FormParameter.Class.DESC);
        String createrID = classEntity.get(FormParameter.Class.CREATER);
        String classId = classEntity.get(FormParameter.Class.ID);
        String token = TokenGenerator.generateToken();
        return classListService.creatClass(name, desc, token, createrID,classId);
           


    }

    @PostMapping("/teacher/loadClass")
    public ApiResult loadClass(@RequestBody Map<String, String> classEntity) {
        System.out.println(classEntity);

        String creater = classEntity.get(FormParameter.Teacher.ID);
        return classListService.loadClassList(creater);

    }

}
