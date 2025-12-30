package com.student.management.controller;

import com.student.management.model.*;
import com.student.management.util.Constants;
import com.student.management.util.DataStorage;
import com.student.management.util.InputValidator;
import com.student.management.view.DisplayView;
import com.student.management.view.MenuView;

import java.util.*;

/**
 * 选课控制器
 * 处理学生选课相关的操作
 */
public class CourseSelectionController {
    
    private DataStorage storage = DataStorage.getInstance();
    private MenuView menuView = new MenuView();
    private DisplayView displayView = new DisplayView();
    private Random random = new Random();
    
    /**
     * 运行选课管理模块
     */
    public void run() {
        boolean running = true;
        
        while (running) {
            menuView.showCourseSelectionMenu();
            int choice = InputValidator.getIntInput("请选择操作 (0-3): ", 0, 3);
            
            switch (choice) {
                case 1:
                    autoSelectCourses();
                    break;
                case 2:
                    viewClassStudents();
                    break;
                case 3:
                    viewStudentCourses();
                    break;
                case 0:
                    running = false;
                    break;
            }
        }
    }
    
    /**
     * 自动为所有学生选课
     */
    private void autoSelectCourses() {
        if (storage.getStudentCount() == 0 || storage.getTeachingClassCount() == 0) {
            menuView.showError("请先生成初始化数据！");
            InputValidator.pause();
            return;
        }
        
        // 检查是否已经选过课
        if (storage.getGradeCount() > 0) {
            boolean confirm = InputValidator.getConfirmation(
                    "学生已经选过课，是否重新选课？（将清除所有选课和成绩数据）");
            if (!confirm) {
                return;
            }
            // 清除成绩数据
            storage.getAllGrades().clear();
        }
        
        System.out.println("\n=== 开始自动选课 ===");
        
        List<Student> students = storage.getAllStudents();
        List<Course> courses = storage.getAllCourses();
        
        int successCount = 0;
        int totalEnrollments = 0;
        
        for (Student student : students) {
            // 清除学生的已选课程
            student.getEnrolledClasses().clear();
            
            // 随机选择课程
            List<Course> selectedCourses = selectRandomCourses(courses);
            
            // 为每门课程选择一个教学班
            for (Course course : selectedCourses) {
                TeachingClass selectedClass = selectTeachingClass(course);
                
                if (selectedClass != null && selectedClass.addStudent(student)) {
                    student.enrollClass(selectedClass);
                    
                    // 创建成绩记录
                    Grade grade = new Grade(student, selectedClass);
                    storage.addGrade(grade);
                    
                    totalEnrollments++;
                }
            }
            
            successCount++;
            
            if (successCount % 20 == 0) {
                System.out.println("已完成 " + successCount + " 名学生的选课...");
            }
        }
        
        System.out.println("\n=== 选课完成 ===");
        System.out.println("成功为 " + successCount + " 名学生完成选课");
        System.out.println("共生成 " + totalEnrollments + " 条选课记录");
        
        menuView.showSuccess("自动选课完成！");
        InputValidator.pause();
    }
    
    /**
     * 随机选择课程
     * @param allCourses 所有课程
     * @return 选中的课程列表
     */
    private List<Course> selectRandomCourses(List<Course> allCourses) {
        List<Course> availableCourses = new ArrayList<>(allCourses);
        Collections.shuffle(availableCourses);
        
        // 每个学生选择3-5门课程
        int courseCount = Constants.MIN_COURSES_PER_STUDENT + random.nextInt(3);
        courseCount = Math.min(courseCount, availableCourses.size());
        
        return availableCourses.subList(0, courseCount);
    }
    
    /**
     * 为课程选择一个教学班（负载均衡策略）
     * @param course 课程
     * @return 选中的教学班
     */
    private TeachingClass selectTeachingClass(Course course) {
        List<TeachingClass> classes = storage.getTeachingClassesByCourse(course);
        
        if (classes.isEmpty()) {
            return null;
        }
        
        // 筛选未满的教学班
        List<TeachingClass> availableClasses = classes.stream()
                .filter(tc -> !tc.isFull())
                .toList();
        
        if (availableClasses.isEmpty()) {
            return null;
        }
        
        // 选择学生数最少的教学班（负载均衡）
        return availableClasses.stream()
                .min(Comparator.comparingInt(TeachingClass::getCurrentSize))
                .orElse(null);
    }
    
    /**
     * 查看教学班学生名单
     */
    private void viewClassStudents() {
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
        } else if (teachingClass.getStudents().isEmpty()) {
            menuView.showWarning("该教学班暂无学生！");
        } else {
            displayView.displayClassStudents(teachingClass);
        }
        
        InputValidator.pause();
    }
    
    /**
     * 查看学生选课情况
     */
    private void viewStudentCourses() {
        if (storage.getStudentCount() == 0) {
            menuView.showError("系统中没有学生数据！");
            InputValidator.pause();
            return;
        }
        
        String studentId = InputValidator.getNonEmptyString("请输入学号: ");
        Student student = storage.getStudentById(studentId);
        
        if (student == null) {
            menuView.showError("未找到该学生！");
        } else if (student.getEnrolledClasses().isEmpty()) {
            menuView.showWarning("该学生尚未选课！");
        } else {
            displayView.displayStudentCourses(student);
        }
        
        InputValidator.pause();
    }
}
