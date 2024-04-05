package com.hnust.swe.serviceimpl;
import com.hnust.swe.entity.ClassListResult;
import com.hnust.swe.mapper.ClassListMapper;
import com.hnust.swe.service.ClassListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ClassListServiceImpl implements ClassListService {

    @Autowired
    private ClassListMapper classListMapper;


    @Override
    public ClassListResult creatClass(String className, String classDesc,String classToken) {
        return classListMapper.creatClass(className,classDesc,classToken);
    }

    @Override
    public ClassListResult loadClassList(String account) {
    
        return classListMapper.loadClassList(account);
    }
}
