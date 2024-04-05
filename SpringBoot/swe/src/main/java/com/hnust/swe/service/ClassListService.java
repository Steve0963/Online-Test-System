package com.hnust.swe.service;

import com.hnust.swe.entity.ClassListResult;


public interface ClassListService {

    public ClassListResult creatClass(String className, String classDesc,String classToken);
    public ClassListResult loadClassList(String account);
}
