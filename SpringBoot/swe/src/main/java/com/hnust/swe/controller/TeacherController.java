package com.hnust.swe.controller;
import com.hnust.swe.entity.*;
import com.hnust.swe.serviceimpl.ExamServiceImpl;

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
        String id = classEntity.get(FormParameter.Teacher.ID);
return  examService.teacherExamList(id);

    }

    @PostMapping("/teacher/publicExam")
    public ApiResult loadScore(@RequestBody Map<String, String> classEntity) {
        System.out.println(classEntity);
        String id = classEntity.get(FormParameter.Teacher.ID);

    return  examService.scoreList(id);

    }

    @PostMapping("/teacher/paperList")
    public ApiResult loadPaper(@RequestBody Map<String, String> classEntity) {
        System.out.println(classEntity);
        String id = classEntity.get(FormParameter.Teacher.ID);

    return  examService.paperList(id);

    }

    @PostMapping("/teacher/problemList")
    public ApiResult loadProblem(@RequestBody Map<String, String> classEntity) {
        System.out.println(classEntity);
        String id = classEntity.get(FormParameter.Teacher.ID);

    return  examService.problemList(id);

    }


    @PostMapping("/teacher/paperProblem")
    public ApiResult loadPaperProblem(@RequestBody Map<String, String> classEntity) {
        System.out.println(classEntity);
        String id = classEntity.get(FormParameter.Paper.ID);

    return  examService.paperProblemList(id);

    }

    @SuppressWarnings("unchecked")
    @PostMapping("/teacher/savePaperProblem")
    public ApiResult savePaperProblem(@RequestBody Map<Object, Object> entity) {
        String createrId=entity.get(FormParameter.Teacher.ID).toString();
        String paperId=entity.get(FormParameter.Paper.ID).toString();
        String paperName=entity.get(FormParameter.Paper.NAME).toString();
        //@SuppressWarnings("unchecked")
        List<Map<String, String>> problem=(List) entity.get(FormParameter.Problem.FORM);
        System.out.println(paperId=="");
        System.out.println(paperName);
        System.out.println(problem.get(0).get("desc"));

        return  examService.savePaperProblem(createrId,paperId,paperName,problem);

    }


    @PostMapping("/teacher/studentList")
    public ApiResult loadStudent(@RequestBody Map<String, String> classEntity) {
        System.out.println(classEntity);
        String id = classEntity.get(FormParameter.Teacher.ID);

    return  examService.studentList(id);

    }


    @PostMapping("/teacher/removeStudent")
    public ApiResult removeStudent(@RequestBody Map<String, String> classEntity) {
        System.out.println(classEntity);
        String classId = classEntity.get(FormParameter.Class.ID);
        String studentId=classEntity.get(FormParameter.Student.ID);

    return  examService.removeStudent(classId,studentId);

    }

    @PostMapping("/teacher/deleteClass")
    public ApiResult deleteClass(@RequestBody Map<String, String> classEntity) {
        System.out.println(classEntity);
        String classId = classEntity.get(FormParameter.Class.ID);

    return  examService.deleteClass(classId);

    }

    @PostMapping("/teacher/saveExam")
    public ApiResult saveExam(@RequestBody Map<String, Object> classEntity) {
        System.out.println(classEntity);
        Object exam = classEntity.get(FormParameter.Exam.FORM);
        //System.out.println(exam);

    return  examService.saveExam(exam);

    }

    @PostMapping("/teacher/deleteExam")
    public ApiResult deleteExam(@RequestBody Map<String, String> classEntity) {
        System.out.println(classEntity);
        String exam = classEntity.get(FormParameter.Exam.ID);
        //System.out.println(exam);

    return  examService.deleteExam(exam);

    }


    @PostMapping("/teacher/deletePaper")
    public ApiResult deletePaper(@RequestBody Map<String, String> classEntity) {
        System.out.println(classEntity);
        String paper = classEntity.get(FormParameter.Paper.ID);
        //System.out.println(exam);

    return  examService.deletePaper(paper);

    }






}
