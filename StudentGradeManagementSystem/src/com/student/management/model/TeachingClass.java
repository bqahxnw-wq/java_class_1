package com.student.management.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 教学班实体类
 * 一个教学班对应一个教师上一门课程
 */
public class TeachingClass {
    private String classId;           // 教学班号
    private Course course;            // 课程
    private Teacher teacher;          // 授课教师
    private String semester;          // 开课学期
    private int capacity;             // 容量
    private List<Student> students;   // 学生列表
    
    public TeachingClass(String classId, Course course, Teacher teacher, 
                        String semester, int capacity) {
        this.classId = classId;
        this.course = course;
        this.teacher = teacher;
        this.semester = semester;
        this.capacity = capacity;
        this.students = new ArrayList<>();
    }
    
    // Getter和Setter方法
    public String getClassId() {
        return classId;
    }
    
    public void setClassId(String classId) {
        this.classId = classId;
    }
    
    public Course getCourse() {
        return course;
    }
    
    public void setCourse(Course course) {
        this.course = course;
    }
    
    public Teacher getTeacher() {
        return teacher;
    }
    
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    
    public String getSemester() {
        return semester;
    }
    
    public void setSemester(String semester) {
        this.semester = semester;
    }
    
    public int getCapacity() {
        return capacity;
    }
    
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    
    public List<Student> getStudents() {
        return students;
    }
    
    public void setStudents(List<Student> students) {
        this.students = students;
    }
    
    /**
     * 添加学生到教学班
     * @param student 学生
     * @return 是否添加成功
     */
    public boolean addStudent(Student student) {
        if (students.size() >= capacity) {
            return false;
        }
        if (!students.contains(student)) {
            students.add(student);
            return true;
        }
        return false;
    }
    
    /**
     * 获取当前学生数量
     * @return 学生数量
     */
    public int getCurrentSize() {
        return students.size();
    }
    
    /**
     * 检查是否已满
     * @return 是否已满
     */
    public boolean isFull() {
        return students.size() >= capacity;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeachingClass that = (TeachingClass) o;
        return Objects.equals(classId, that.classId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(classId);
    }
    
    @Override
    public String toString() {
        return String.format("教学班号: %s, 课程: %s, 教师: %s, 学期: %s, 人数: %d/%d",
                classId, course.getCourseName(), teacher.getName(), 
                semester, students.size(), capacity);
    }
}
