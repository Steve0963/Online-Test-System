package com.hnust.swe.controller;
import com.hnust.swe.entity.*;
import com.hnust.swe.serviceimpl.ClassListServiceImpl;
import com.hnust.swe.util.ApiResultHandler;
import com.hnust.swe.util.TokenGenerator;

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

    @PostMapping("/creatClass")
    public ApiResult login(@RequestBody Map<String, String> login) {

        String name = login.get(FormParameter.ClassFunction.NAME);
        String desc = login.get(FormParameter.ClassFunction.DESC);
        String token= TokenGenerator.generateToken();
System.out.println(name);
System.out.println(desc);
System.out.println(token);



        ClassListResult classListRes = classListService.creatClass(name,desc,token);
        if (classListRes != null) {
            return ApiResultHandler.buildApiResult(200, "请求成功", classListRes);
        }

        return ApiResultHandler.buildApiResult(400, "请求失败", null);
    }
}
