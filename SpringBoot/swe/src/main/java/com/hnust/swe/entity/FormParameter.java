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

    public static class ClassFunction {
        public static final String NAME = "className";
        public static final String DESC = "classDesc";
        public static final String CREATER = "createrId";
        // 添加其他注册参数...
    }

    public static class StudentFunction {
        public static final String NAME = "className";
        public static final String DESC = "classDesc";
        public static final String ID = "studentId";
        public static final String TOKEN="classToken";

        // 添加其他注册参数...
    }

    public static class TeacherFunction {
        public static final String CREATER = "createrId";
        // 添加其他注册参数...
    }
    
}
