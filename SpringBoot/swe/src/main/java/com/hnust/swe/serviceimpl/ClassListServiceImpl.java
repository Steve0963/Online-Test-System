package com.hnust.swe.serviceimpl;
import com.hnust.swe.entity.ClassListResult;
import com.hnust.swe.mapper.ClassListMapper;
import com.hnust.swe.service.ClassListService;

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

    @Override
    public List <ClassListResult> loadClassList(String account) {
    
        return classListMapper.loadClassList(account);
    }
}
