package com.student.management.controller;

import com.student.management.model.*;
import com.student.management.util.DataStorage;
import com.student.management.util.InputValidator;
import com.student.management.view.DisplayView;
import com.student.management.view.MenuView;

import java.util.List;

/**
 * 查询控制器
 * 处理成绩查询和统计相关的操作
 */
public class QueryController {
    
    private DataStorage storage = DataStorage.getInstance();
    private MenuView menuView = new MenuView();
    private DisplayView displayView = new DisplayView();
    
    /**
     * 运行查询统计模块
     */
    public void run() {
        boolean running = true;
        
        while (running) {
            menuView.showQueryMenu();
            int choice = InputValidator.getIntInput("请选择操作 (0-8): ", 0, 8);
            
            switch (choice) {
                case 1:
                    queryByStudentId();
                    break;
                case 2:
                    queryByStudentName();
                    break;
                case 3:
                    displayClassGrades();
                    break;
                case 4:
                    displayClassRanking();
                    break;
                case 5:
                    displayClassStatistics();
                    break;
                case 6:
                    displayOverallRanking();
                    break;
                case 7:
                    displayCourseList();
                    break;
                case 8:
                    displayClassList();
                    break;
                case 0:
                    running = false;
                    break;
            }
        }
    }
    
    /**
     * 按学号查询学生成绩
     */
    private void queryByStudentId() {
        if (storage.getStudentCount() == 0) {
            menuView.showError("系统中没有学生数据！");
            InputValidator.pause();
            return;
        }
        
        String studentId = InputValidator.getNonEmptyString("请输入学号: ");
        Student student = storage.getStudentById(studentId);
        
        if (student == null) {
            menuView.showError("未找到该学生！");
        } else {
            List<Grade> grades = storage.getGradesByStudent(student);
            if (grades.isEmpty()) {
                menuView.showWarning("该学生暂无成绩记录！");
            } else {
                displayView.displayStudentGrades(student, grades);
            }
        }
        
        InputValidator.pause();
    }
    
    /**
     * 按姓名查询学生成绩
     */
    private void queryByStudentName() {
        if (storage.getStudentCount() == 0) {
            menuView.showError("系统中没有学生数据！");
            InputValidator.pause();
            return;
        }
        
        String name = InputValidator.getNonEmptyString("请输入姓名: ");
        Student student = storage.getStudentByName(name);
        
        if (student == null) {
            menuView.showError("未找到该学生！");
        } else {
            List<Grade> grades = storage.getGradesByStudent(student);
            if (grades.isEmpty()) {
                menuView.showWarning("该学生暂无成绩记录！");
            } else {
                displayView.displayStudentGrades(student, grades);
            }
        }
        
        InputValidator.pause();
    }
    
    /**
     * 显示教学班成绩单
     */
    private void displayClassGrades() {
        if (storage.getTeachingClassCount() == 0) {
            menuView.showError("系统中没有教学班数据！");
            InputValidator.pause();
            return;
        }
        
        // 显示教学班列表
        displayView.displayTeachingClassList(storage.getAllTeachingClasses());
        
        String classId = InputValidator.getNonEmptyString("\n请输入教学班号: ");
        TeachingClass teachingClass = storage.getTeachingClassById(classId);
        
        if (teachingClass == null) {
            menuView.showError("未找到该教学班！");
        } else {
            List<Grade> grades = storage.getGradesByClass(teachingClass);
            if (grades.isEmpty()) {
                menuView.showWarning("该教学班暂无成绩记录！");
            } else {
                displayView.displayClassGrades(teachingClass, grades);
            }
        }
        
        InputValidator.pause();
    }
    
    /**
     * 显示教学班成绩排名
     */
    private void displayClassRanking() {
        if (storage.getTeachingClassCount() == 0) {
            menuView.showError("系统中没有教学班数据！");
            InputValidator.pause();
            return;
        }
        
        // 显示教学班列表
        displayView.displayTeachingClassList(storage.getAllTeachingClasses());
        
        String classId = InputValidator.getNonEmptyString("\n请输入教学班号: ");
        TeachingClass teachingClass = storage.getTeachingClassById(classId);
        
        if (teachingClass == null) {
            menuView.showError("未找到该教学班！");
        } else {
            List<Grade> grades = storage.getGradesByClass(teachingClass);
            
            // 筛选有综合成绩的记录
            List<Grade> validGrades = grades.stream()
                    .filter(g -> g.getTotalScore() != null)
                    .toList();
            
            if (validGrades.isEmpty()) {
                menuView.showWarning("该教学班暂无综合成绩数据！");
            } else {
                displayView.displayClassRanking(teachingClass, validGrades);
            }
        }
        
        InputValidator.pause();
    }
    
    /**
     * 显示教学班成绩统计
     */
    private void displayClassStatistics() {
        if (storage.getTeachingClassCount() == 0) {
            menuView.showError("系统中没有教学班数据！");
            InputValidator.pause();
            return;
        }
        
        // 显示教学班列表
        displayView.displayTeachingClassList(storage.getAllTeachingClasses());
        
        String classId = InputValidator.getNonEmptyString("\n请输入教学班号: ");
        TeachingClass teachingClass = storage.getTeachingClassById(classId);
        
        if (teachingClass == null) {
            menuView.showError("未找到该教学班！");
        } else {
            List<Grade> grades = storage.getGradesByClass(teachingClass);
            displayView.displayClassStatistics(teachingClass, grades);
        }
        
        InputValidator.pause();
    }
    
    /**
     * 显示全体学生总成绩排名
     */
    private void displayOverallRanking() {
        if (storage.getStudentCount() == 0) {
            menuView.showError("系统中没有学生数据！");
            InputValidator.pause();
            return;
        }
        
        if (storage.getGradeCount() == 0) {
            menuView.showError("系统中没有成绩数据！");
            InputValidator.pause();
            return;
        }
        
        List<Student> students = storage.getAllStudents();
        List<Grade> allGrades = storage.getAllGrades().values().stream().toList();
        
        displayView.displayOverallRanking(students, allGrades);
        InputValidator.pause();
    }
    
    /**
     * 显示课程列表
     */
    private void displayCourseList() {
        if (storage.getCourseCount() == 0) {
            menuView.showError("系统中没有课程数据！");
            InputValidator.pause();
            return;
        }
        
        displayView.displayCourseList(storage.getAllCourses());
        InputValidator.pause();
    }
    
    /**
     * 显示教学班列表
     */
    private void displayClassList() {
        if (storage.getTeachingClassCount() == 0) {
            menuView.showError("系统中没有教学班数据！");
            InputValidator.pause();
            return;
        }
        
        displayView.displayTeachingClassList(storage.getAllTeachingClasses());
        InputValidator.pause();
    }
}
