package com.hnust.swe.service;

import java.util.List;

import com.hnust.swe.entity.ClassListResult;


public interface ClassListService {

    public void creatClass(String className, String classDesc,String classToken,String createrID);
    public List <ClassListResult> loadClassList(String account);
}
