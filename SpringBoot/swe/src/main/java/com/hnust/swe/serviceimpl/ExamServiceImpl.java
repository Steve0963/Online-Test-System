package com.hnust.swe.serviceimpl;

import com.hnust.swe.entity.ApiResult;
import com.hnust.swe.entity.ClassListResult;
import com.hnust.swe.entity.ExamListResult;
import com.hnust.swe.entity.PaperListResult;
import com.hnust.swe.entity.ProblemListResult;
import com.hnust.swe.entity.ScoreListResult;
import com.hnust.swe.entity.StudentListResult;
import com.hnust.swe.entity.FormParameter.Paper;
import com.hnust.swe.mapper.ExamMapper;

import com.hnust.swe.service.ExamService;
import com.hnust.swe.util.ApiResultHandler;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

@Service
public class ExamServiceImpl implements ExamService {

    @Autowired
    private ExamMapper examMapper;

    @SuppressWarnings("unchecked")
    @Override
    public ApiResult<ExamListResult> examList(String studenId) {
        List<ExamListResult> allExamList = new LinkedList<>();

        List<Integer> classIdList = examMapper.getClassIdByStuId(studenId);
        if (classIdList != null) {
            for (Integer classId : classIdList) {
                List<Integer> examIdList = examMapper.getExamIdByClassId(classId);
                if (examIdList != null) {
                    for (Integer examId : examIdList) {
                        ExamListResult exam = examMapper.getExamListByExamId(examId);
                        allExamList.add(exam);

                    }
                }
            }
            return ApiResultHandler.buildApiResult(200, "请求成功", allExamList);

        }

        return ApiResultHandler.buildApiResult(400, "没有加入班级！", null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ApiResult<ScoreListResult> scoreList(String studenId) {

        List<ScoreListResult> scoreList = examMapper.getScoreListByStuId(studenId);
        if (scoreList != null) {
            return ApiResultHandler.buildApiResult(200, "请求成功", scoreList);
        }

        return ApiResultHandler.buildApiResult(401, "暂无成绩！", null);

    }

    @SuppressWarnings("unchecked")
    @Override
    public ApiResult<ExamListResult> teacherExamList(String teacherId) {
        List<ExamListResult> examList = examMapper.getExamListByTeacherId(teacherId);
        for (ExamListResult exam : examList) {
            List<Integer> classIdList = examMapper.getClassListByExamId(exam.getId());
            exam.setExam_class(classIdList);
        }
        if (examList != null) {
            System.out.println("success");
            return ApiResultHandler.buildApiResult(200, "获取成功", examList);
        }
        return ApiResultHandler.buildApiResult(401, "暂时没有已发布的考试！", null);

    }

    @SuppressWarnings("unchecked")
    @Override
    public ApiResult<ExamListResult> saveExam(Object exam) {
        System.out.println(exam);
        ExamListResult examEntity = new ExamListResult();
        try {
            examEntity.setEnd_time(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
                    .parse(((Map<String, String>) exam).get("end_time")));
            examEntity.setStart_time(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
                    .parse(((Map<String, String>) exam).get("start_time")));
            // System.out.println(((Map<String,String>) exam).get("start_time"));
            examEntity.setExam_name(((Map<String, String>) exam).get("exam_name"));
            examEntity.setPaper_id(((Map<String, String>) exam).get("paper_id"));
            examEntity.setExam_place(((Map<String, String>) exam).get("exam_place"));
            examEntity.setExam_type(((Map<String, String>) exam).get("exam_type"));
            examEntity.setCreater_id(((Map<String, String>) exam).get("createrId"));
            examEntity.setId(((Map<String, String>) exam).get("id"));
            examEntity.setExam_class(((Map<String, List<Integer>>) exam).get("exam_class"));
            System.out.println(examEntity);
            List<Integer> classIdList = examEntity.getExam_class();

            if (examEntity.getId() == "") {
                examMapper.createExam(examEntity);
                System.out.println("error");

                for (int i = 0; i < classIdList.size(); i++) {
                    examMapper.createExamClass(examEntity.getId(), classIdList.get(i));
                }

                return ApiResultHandler.buildApiResult(200, "创建成功！", null);

            } else {

                System.out.println("保存");
                examMapper.updateExamInfo(examEntity);
                examMapper.deleteExamClassByExamId(examEntity.getId());
                for (int i = 0; i < classIdList.size(); i++) {
                    examMapper.addClassToExam(examEntity.getId(), classIdList.get(i));
                }

                return ApiResultHandler.buildApiResult(200, "保存成功！", null);

            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return ApiResultHandler.buildApiResult(401, "非法日期！", null);
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public ApiResult<PaperListResult> paperList(String createrid) {

        List<PaperListResult> paperList = examMapper.getPaperListById(createrid);
        if (paperList != null) {
            return ApiResultHandler.buildApiResult(200, "请求成功", paperList);
        }

        return ApiResultHandler.buildApiResult(400, "暂无试卷！", null);

    }

    @SuppressWarnings("unchecked")
    @Override
    public ApiResult<StudentListResult> studentList(String createrid) {

        List<Integer> classIds = examMapper.getClassIdByTeacherId(createrid);
        System.out.println(classIds);
        List<StudentListResult> studentList = new LinkedList<>();
        System.out.println(studentList == null);
        if (classIds != null) {

            for (Integer classId : classIds) {
                List<StudentListResult> students = examMapper.getStudentListByClassId(classId);
                for (StudentListResult student : students) {
                    System.out.println(student);
                    studentList.add(student);
                }
            }
            if (studentList.size() != 0)
                return ApiResultHandler.buildApiResult(200, "请求成功", studentList);
            else
                return ApiResultHandler.buildApiResult(401, "暂无学生加入班级", null);
        }
        return ApiResultHandler.buildApiResult(400, "没有创建班级！", null);

    }

    @SuppressWarnings("unchecked")
    @Override
    public ApiResult<ProblemListResult> problemList(String createrid) {

        List<ProblemListResult> problemList = examMapper.getProblemListById(createrid);
        if (problemList != null) {
            return ApiResultHandler.buildApiResult(200, "请求成功", problemList);
        }

        return ApiResultHandler.buildApiResult(400, "暂无题目！", null);

    }

    @SuppressWarnings("unchecked")
    @Override
    public ApiResult<ProblemListResult> paperProblemList(String paperid) {

        List<Integer> problemIdList = examMapper.getProblemIdByPaperId(paperid);
        List<ProblemListResult> problemList = new LinkedList<>();
        if (problemIdList != null)
            for (Integer problemId : problemIdList) {
                ProblemListResult problem = examMapper.getProblemById(problemId);
                problemList.add(problem);
            }
        else
            return ApiResultHandler.buildApiResult(400, "暂无题目！", null);

        if (problemList.size() != 0) {
            return ApiResultHandler.buildApiResult(200, "请求成功", problemList);
        }
        return ApiResultHandler.buildApiResult(400, "暂无题目！", null);

    }

    @SuppressWarnings("unchecked")
    @Override
    public ApiResult<PaperListResult> savePaperProblem(String createrId, String paperid, String paperName,
            List<Map<String, String>> problems) {

        if (paperid == "") {
            PaperListResult paper = new PaperListResult();
            paper.setPaper_name(paperName);
            paper.setCreater_id(createrId);
            examMapper.createPaper(paper);
            for (Map<String, String> problem : problems) {
                examMapper.addProblemToPaper(paper.getId(), problem.get("id"), createrId);
            }

        } else {
            PaperListResult paper = new PaperListResult();
            paper.setId(paperid);
            paper.setPaper_name(paperName);
            examMapper.deletePaperProblemByPaperId(paperid);
            examMapper.updatePaper(paper);

            for (Map<String, String> problem : problems) {
                examMapper.addProblemToPaper(paperid, problem.get("id"), createrId);
            }

        }

        return ApiResultHandler.buildApiResult(200, "保存成功！", null);

    }

    @SuppressWarnings("unchecked")
    @Override
    public ApiResult<StudentListResult> removeStudent(String classtId, String studentId) {

        try {
            examMapper.removeStudentFromClassById(classtId, studentId);
            return ApiResultHandler.buildApiResult(200, "移除成功", null);
        } catch (Exception e) {
            // TODO: handle exception
            return ApiResultHandler.buildApiResult(400, "移除失败，当前班级没有该学生！", null);

        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public ApiResult<ClassListResult> deleteClass(String classtId) {

        try {

            examMapper.deleteAllExamByClassId(classtId);
            examMapper.deleteAllStudentByClassId(classtId);
            examMapper.deleteClassById(classtId);
            return ApiResultHandler.buildApiResult(200, "删除成功", null);
        } catch (Exception e) {
            // TODO: handle exception
            return ApiResultHandler.buildApiResult(400, "删除失败，未知错误！", null);

        }

    }


    @SuppressWarnings("unchecked")
    @Override
    public ApiResult<ExamListResult> deleteExam(String exam) {

        try {

            examMapper.deleteExamClassByExamId(exam);
            examMapper.deleteExamByExamId(exam);
            return ApiResultHandler.buildApiResult(200, "删除成功", null);
        } catch (Exception e) {
            // TODO: handle exception
            return ApiResultHandler.buildApiResult(400, "删除失败，未知错误！", null);

        }

    }


    @SuppressWarnings("unchecked")
    @Override
    public ApiResult<PaperListResult> deletePaper(String paper) {

        try {

            examMapper.deletePaperProblemByPaperId(paper);
            examMapper.deletePaperById(paper);
            return ApiResultHandler.buildApiResult(200, "删除成功", null);
        } catch (Exception e) {
            // TODO: handle exception
            return ApiResultHandler.buildApiResult(400, "删除失败，未知错误！", null);

        }

    }

}
