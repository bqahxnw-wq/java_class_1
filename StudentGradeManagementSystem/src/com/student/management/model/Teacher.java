package com.student.management.model;

import java.util.Objects;

/**
 * 教师实体类
 * 包含教师的基本信息
 */
public class Teacher {
    private String teacherId;    // 教师编号
    private String name;         // 教师姓名
    private String title;        // 职称（如：教授、副教授、讲师）
    
    public Teacher(String teacherId, String name, String title) {
        this.teacherId = teacherId;
        this.name = name;
        this.title = title;
    }
    
    // Getter和Setter方法
    public String getTeacherId() {
        return teacherId;
    }
    
    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(teacherId, teacher.teacherId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(teacherId);
    }
    
    @Override
    public String toString() {
        return String.format("教师编号: %s, 姓名: %s, 职称: %s", 
                teacherId, name, title);
    }
}
