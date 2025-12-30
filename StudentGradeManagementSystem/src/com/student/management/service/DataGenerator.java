package com.student.management.service;

import com.student.management.model.*;
import com.student.management.util.Constants;
import com.student.management.util.DataStorage;

import java.util.*;

/**
 * 数据生成服务类
 * 负责生成初始化数据
 */
public class DataGenerator {
    
    private DataStorage storage = DataStorage.getInstance();
    private Random random = new Random();
    
    /**
     * 生成所有初始化数据
     */
    public void generateAllData() {
        System.out.println("\n=== 开始生成初始化数据 ===");
        
        generateTeachers();
        generateCourses();
        generateStudents();
        generateTeachingClasses();
        
        System.out.println("\n=== 数据生成完成 ===");
        printDataSummary();
    }
    
    /**
     * 生成教师数据
     */
    private void generateTeachers() {
        System.out.print("正在生成教师数据...");
        
        String[] titles = {"教授", "副教授", "讲师", "助教"};
        
        for (int i = 1; i <= Constants.MIN_TEACHERS + 4; i++) {
            String teacherId = String.format("T%04d", i);
            String name = generateRandomName();
            String title = titles[random.nextInt(titles.length)];
            
            Teacher teacher = new Teacher(teacherId, name, title);
            storage.addTeacher(teacher);
        }
        
        System.out.println(" 完成！共生成 " + storage.getTeacherCount() + " 名教师");
    }
    
    /**
     * 生成课程数据
     */
    private void generateCourses() {
        System.out.print("正在生成课程数据...");
        
        String[] departments = {"计算机学院", "软件学院", "信息学院"};
        
        for (int i = 0; i < Constants.COURSE_NAMES.length; i++) {
            String courseId = String.format("C%03d", i + 1);
            String courseName = Constants.COURSE_NAMES[i];
            int credit = 2 + random.nextInt(3); // 2-4学分
            String department = departments[random.nextInt(departments.length)];
            
            Course course = new Course(courseId, courseName, credit, department);
            storage.addCourse(course);
        }
        
        System.out.println(" 完成！共生成 " + storage.getCourseCount() + " 门课程");
    }
    
    /**
     * 生成学生数据
     */
    private void generateStudents() {
        System.out.print("正在生成学生数据...");
        
        int year = 2023;
        for (int i = 1; i <= Constants.MIN_STUDENTS; i++) {
            String studentId = String.format("%d%06d", year, i);
            String name = generateRandomName();
            String gender = Constants.GENDERS[random.nextInt(Constants.GENDERS.length)];
            
            Student student = new Student(studentId, name, gender);
            storage.addStudent(student);
        }
        
        System.out.println(" 完成！共生成 " + storage.getStudentCount() + " 名学生");
    }
    
    /**
     * 生成教学班数据
     * 确保每门课至少有2个教师授课
     */
    private void generateTeachingClasses() {
        System.out.print("正在生成教学班数据...");
        
        List<Course> courses = storage.getAllCourses();
        List<Teacher> teachers = storage.getAllTeachers();
        
        int classCounter = 1;
        
        for (Course course : courses) {
            // 为每门课程创建至少2个教学班（由不同教师授课）
            int classesPerCourse = Constants.MIN_TEACHERS_PER_COURSE + 
                    random.nextInt(2); // 2-3个教学班
            
            // 为这门课程随机选择教师
            List<Teacher> availableTeachers = new ArrayList<>(teachers);
            Collections.shuffle(availableTeachers);
            
            for (int i = 0; i < classesPerCourse && i < availableTeachers.size(); i++) {
                String classId = String.format("TC%04d", classCounter++);
                Teacher teacher = availableTeachers.get(i);
                String semester = Constants.SEMESTERS[random.nextInt(Constants.SEMESTERS.length)];
                int capacity = Constants.MIN_STUDENTS_PER_CLASS + random.nextInt(21); // 20-40人
                
                TeachingClass teachingClass = new TeachingClass(
                        classId, course, teacher, semester, capacity);
                storage.addTeachingClass(teachingClass);
            }
        }
        
        System.out.println(" 完成！共生成 " + storage.getTeachingClassCount() + " 个教学班");
    }
    
    /**
     * 生成随机姓名
     */
    private String generateRandomName() {
        String surname = Constants.SURNAMES[random.nextInt(Constants.SURNAMES.length)];
        String givenName = Constants.GIVEN_NAMES[random.nextInt(Constants.GIVEN_NAMES.length)];
        
        // 有30%概率生成单字名
        if (random.nextDouble() < 0.3) {
            return surname + givenName.charAt(0);
        }
        
        return surname + givenName;
    }
    
    /**
     * 打印数据摘要
     */
    private void printDataSummary() {
        System.out.println("\n--- 数据生成摘要 ---");
        System.out.println("学生数量: " + storage.getStudentCount());
        System.out.println("教师数量: " + storage.getTeacherCount());
        System.out.println("课程数量: " + storage.getCourseCount());
        System.out.println("教学班数量: " + storage.getTeachingClassCount());
        System.out.println("-------------------");
    }
}
