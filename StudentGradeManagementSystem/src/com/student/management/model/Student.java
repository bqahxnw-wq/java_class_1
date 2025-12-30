package com.student.management.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 学生实体类
 * 包含学生的基本信息和选课信息
 */
public class Student {
    private String studentId;      // 学号
    private String name;           // 姓名
    private String gender;         // 性别
    private List<TeachingClass> enrolledClasses;  // 已选教学班列表
    
    public Student(String studentId, String name, String gender) {
        this.studentId = studentId;
        this.name = name;
        this.gender = gender;
        this.enrolledClasses = new ArrayList<>();
    }
    
    // Getter和Setter方法
    public String getStudentId() {
        return studentId;
    }
    
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getGender() {
        return gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public List<TeachingClass> getEnrolledClasses() {
        return enrolledClasses;
    }
    
    public void setEnrolledClasses(List<TeachingClass> enrolledClasses) {
        this.enrolledClasses = enrolledClasses;
    }
    
    /**
     * 添加选课
     * @param teachingClass 教学班
     */
    public void enrollClass(TeachingClass teachingClass) {
        if (!enrolledClasses.contains(teachingClass)) {
            enrolledClasses.add(teachingClass);
        }
    }
    
    /**
     * 检查是否已选某门课程
     * @param course 课程
     * @return 是否已选
     */
    public boolean hasEnrolledCourse(Course course) {
        return enrolledClasses.stream()
                .anyMatch(tc -> tc.getCourse().equals(course));
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(studentId, student.studentId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(studentId);
    }
    
    @Override
    public String toString() {
        return String.format("学号: %s, 姓名: %s, 性别: %s", 
                studentId, name, gender);
    }
}
