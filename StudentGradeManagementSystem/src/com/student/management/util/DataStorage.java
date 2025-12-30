package com.student.management.util;

import com.student.management.model.*;
import java.util.*;

/**
 * 数据存储类（单例模式）
 * 作为内存数据库，存储所有系统数据
 */
public class DataStorage {
    
    // 单例实例
    private static DataStorage instance;
    
    // 数据存储
    private List<Student> students;
    private List<Teacher> teachers;
    private List<Course> courses;
    private List<TeachingClass> teachingClasses;
    private Map<String, Grade> grades;  // key: studentId_classId
    
    // 索引（用于快速查询）
    private Map<String, Student> studentIndex;
    private Map<String, Teacher> teacherIndex;
    private Map<String, Course> courseIndex;
    private Map<String, TeachingClass> classIndex;
    
    /**
     * 私有构造函数
     */
    private DataStorage() {
        students = new ArrayList<>();
        teachers = new ArrayList<>();
        courses = new ArrayList<>();
        teachingClasses = new ArrayList<>();
        grades = new HashMap<>();
        
        studentIndex = new HashMap<>();
        teacherIndex = new HashMap<>();
        courseIndex = new HashMap<>();
        classIndex = new HashMap<>();
    }
    
    /**
     * 获取单例实例
     * @return DataStorage实例
     */
    public static synchronized DataStorage getInstance() {
        if (instance == null) {
            instance = new DataStorage();
        }
        return instance;
    }
    
    /**
     * 重置所有数据（用于重新初始化）
     */
    public void reset() {
        students.clear();
        teachers.clear();
        courses.clear();
        teachingClasses.clear();
        grades.clear();
        
        studentIndex.clear();
        teacherIndex.clear();
        courseIndex.clear();
        classIndex.clear();
    }
    
    // ==================== 学生相关方法 ====================
    
    public void addStudent(Student student) {
        students.add(student);
        studentIndex.put(student.getStudentId(), student);
    }
    
    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }
    
    public Student getStudentById(String studentId) {
        return studentIndex.get(studentId);
    }
    
    public Student getStudentByName(String name) {
        return students.stream()
                .filter(s -> s.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
    
    // ==================== 教师相关方法 ====================
    
    public void addTeacher(Teacher teacher) {
        teachers.add(teacher);
        teacherIndex.put(teacher.getTeacherId(), teacher);
    }
    
    public List<Teacher> getAllTeachers() {
        return new ArrayList<>(teachers);
    }
    
    public Teacher getTeacherById(String teacherId) {
        return teacherIndex.get(teacherId);
    }
    
    // ==================== 课程相关方法 ====================
    
    public void addCourse(Course course) {
        courses.add(course);
        courseIndex.put(course.getCourseId(), course);
    }
    
    public List<Course> getAllCourses() {
        return new ArrayList<>(courses);
    }
    
    public Course getCourseById(String courseId) {
        return courseIndex.get(courseId);
    }
    
    // ==================== 教学班相关方法 ====================
    
    public void addTeachingClass(TeachingClass teachingClass) {
        teachingClasses.add(teachingClass);
        classIndex.put(teachingClass.getClassId(), teachingClass);
    }
    
    public List<TeachingClass> getAllTeachingClasses() {
        return new ArrayList<>(teachingClasses);
    }
    
    public TeachingClass getTeachingClassById(String classId) {
        return classIndex.get(classId);
    }
    
    public List<TeachingClass> getTeachingClassesByCourse(Course course) {
        List<TeachingClass> result = new ArrayList<>();
        for (TeachingClass tc : teachingClasses) {
            if (tc.getCourse().equals(course)) {
                result.add(tc);
            }
        }
        return result;
    }
    
    // ==================== 成绩相关方法 ====================
    
    public void addGrade(Grade grade) {
        String key = generateGradeKey(
                grade.getStudent().getStudentId(),
                grade.getTeachingClass().getClassId()
        );
        grades.put(key, grade);
    }
    
    public Grade getGrade(String studentId, String classId) {
        String key = generateGradeKey(studentId, classId);
        return grades.get(key);
    }
    
    public List<Grade> getGradesByStudent(Student student) {
        List<Grade> result = new ArrayList<>();
        for (Grade grade : grades.values()) {
            if (grade.getStudent().equals(student)) {
                result.add(grade);
            }
        }
        return result;
    }
    
    public List<Grade> getGradesByClass(TeachingClass teachingClass) {
        List<Grade> result = new ArrayList<>();
        for (Grade grade : grades.values()) {
            if (grade.getTeachingClass().equals(teachingClass)) {
                result.add(grade);
            }
        }
        return result;
    }
    
    public Map<String, Grade> getAllGrades() {
        return new HashMap<>(grades);
    }
    
    /**
     * 生成成绩的唯一键
     */
    private String generateGradeKey(String studentId, String classId) {
        return studentId + "_" + classId;
    }
    
    // ==================== 统计方法 ====================
    
    public int getStudentCount() {
        return students.size();
    }
    
    public int getTeacherCount() {
        return teachers.size();
    }
    
    public int getCourseCount() {
        return courses.size();
    }
    
    public int getTeachingClassCount() {
        return teachingClasses.size();
    }
    
    public int getGradeCount() {
        return grades.size();
    }
}
