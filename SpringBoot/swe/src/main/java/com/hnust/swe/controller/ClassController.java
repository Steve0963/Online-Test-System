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

    @PostMapping("/creatClass")
    public ApiResult createClass(@RequestBody Map<String, String> classEntity) {

        String name = classEntity.get(FormParameter.ClassFunction.NAME);
        String desc = classEntity.get(FormParameter.ClassFunction.DESC);
        String createrID=classEntity.get(FormParameter.ClassFunction.CREATER);
        String token= TokenGenerator.generateToken();
System.out.println(name);
System.out.println(desc);
System.out.println(token);
System.out.println(createrID);
try {
    classListService.creatClass(name,desc,token,createrID);
    return ApiResultHandler.buildApiResult(200, "请求成功",null);

} catch (Exception e) {
    return ApiResultHandler.buildApiResult(400, "请求失败", null);
    
}

    
    }


    @PostMapping("/loadClass")
    public ApiResult loadClass(@RequestBody Map<String, String> classEntity) {
        System.out.println(classEntity);

        String creater = classEntity.get(FormParameter.ClassFunction.CREATER);

System.out.println(creater);



List <ClassListResult> classListRes = classListService.loadClassList(creater);
System.out.println(classListRes);

        if (classListRes != null) {
            return ApiResultHandler.buildApiResult(200, "请求成功", classListRes);
        }

        return ApiResultHandler.buildApiResult(400, "请求失败", null);
    }




}
