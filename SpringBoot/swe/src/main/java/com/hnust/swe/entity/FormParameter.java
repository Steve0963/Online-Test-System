package com.hnust.swe.entity;
public class FormParameter {

    public static class Login {
        public static final String ACCOUNT = "account";
        public static final String PASSWORD = "password";
    }

    public static class Registr {
        public static final String ACCOUNT = "account";
        public static final String PASSWORD = "password";
        // 添加其他注册参数...
    }

    public static class Class {
        public static final String NAME = "className";
        public static final String DESC = "classDesc";
        public static final String CREATER = "createrId";
        public static final String ID = "classId";
        // 添加其他注册参数...
    }

    public static class Student {
        public static final String NAME = "className";
        public static final String DESC = "classDesc";
        public static final String ID = "studentId";
        public static final String TOKEN="classToken";

        // 添加其他注册参数...
    }

    public static class Teacher {
        public static final String ID = "createrId";
        // 添加其他注册参数...
    }

    public static class Paper {
        
        public static final String ID = "paperId";
        public static final String NAME = "paperName";
        public static final String PAPER = "paperForm";

        // 添加其他注册参数...
    }


    public static class Exam {
        
        public static final String ID = "examId";
        public static final String NAME = "examName";
        public static final String FORM = "examForm";

        // 添加其他注册参数...
    }
    public static class Problem {
        
        public static final String ID = "problemId";
        public static final String NAME = "problemName";
        public static final String FORM = "problemForm";
        

        // 添加其他注册参数...
    }
    
}
