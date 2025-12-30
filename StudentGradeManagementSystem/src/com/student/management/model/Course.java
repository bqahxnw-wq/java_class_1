package com.student.management.model;

import java.util.Objects;

/**
 * 课程实体类
 * 包含课程的基本信息
 */
public class Course {
    private String courseId;      // 课程编号
    private String courseName;    // 课程名称
    private int credit;           // 学分
    private String department;    // 开课院系
    
    public Course(String courseId, String courseName, int credit, String department) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.credit = credit;
        this.department = department;
    }
    
    // Getter和Setter方法
    public String getCourseId() {
        return courseId;
    }
    
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
    
    public String getCourseName() {
        return courseName;
    }
    
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    
    public int getCredit() {
        return credit;
    }
    
    public void setCredit(int credit) {
        this.credit = credit;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(courseId, course.courseId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(courseId);
    }
    
    @Override
    public String toString() {
        return String.format("课程编号: %s, 课程名称: %s, 学分: %d, 院系: %s", 
                courseId, courseName, credit, department);
    }
}
