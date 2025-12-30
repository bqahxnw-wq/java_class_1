package com.student.management.view;

import com.student.management.model.*;
import com.student.management.service.StatisticsService;

import java.util.List;
import java.util.Map;

/**
 * 显示视图类
 * 负责格式化显示各种数据
 */
public class DisplayView {
    
    private StatisticsService statisticsService = new StatisticsService();
    
    /**
     * 显示数据摘要
     */
    public void displayDataSummary(int studentCount, int teacherCount, 
                                   int courseCount, int classCount, int gradeCount) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                      数据摘要");
        System.out.println("=".repeat(60));
        System.out.println("  学生数量: " + studentCount);
        System.out.println("  教师数量: " + teacherCount);
        System.out.println("  课程数量: " + courseCount);
        System.out.println("  教学班数量: " + classCount);
        System.out.println("  成绩记录数: " + gradeCount);
        System.out.println("=".repeat(60));
    }
    
    /**
     * 显示课程列表
     */
    public void displayCourseList(List<Course> courses) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("                            课程列表");
        System.out.println("=".repeat(80));
        System.out.printf("%-10s %-25s %-8s %-20s\n", 
                "课程编号", "课程名称", "学分", "开课院系");
        System.out.println("-".repeat(80));
        
        for (Course course : courses) {
            System.out.printf("%-10s %-25s %-8d %-20s\n",
                    course.getCourseId(),
                    course.getCourseName(),
                    course.getCredit(),
                    course.getDepartment());
        }
        
        System.out.println("=".repeat(80));
        System.out.println("共 " + courses.size() + " 门课程");
    }
    
    /**
     * 显示教学班列表
     */
    public void displayTeachingClassList(List<TeachingClass> classes) {
        System.out.println("\n" + "=".repeat(100));
        System.out.println("                                教学班列表");
        System.out.println("=".repeat(100));
        System.out.printf("%-10s %-25s %-12s %-18s %-10s\n",
                "教学班号", "课程名称", "授课教师", "开课学期", "人数");
        System.out.println("-".repeat(100));
        
        for (TeachingClass tc : classes) {
            System.out.printf("%-10s %-25s %-12s %-18s %d/%d\n",
                    tc.getClassId(),
                    tc.getCourse().getCourseName(),
                    tc.getTeacher().getName(),
                    tc.getSemester(),
                    tc.getCurrentSize(),
                    tc.getCapacity());
        }
        
        System.out.println("=".repeat(100));
        System.out.println("共 " + classes.size() + " 个教学班");
    }
    
    /**
     * 显示教学班学生名单
     */
    public void displayClassStudents(TeachingClass teachingClass) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("                        教学班学生名单");
        System.out.println("=".repeat(80));
        System.out.println("教学班号: " + teachingClass.getClassId());
        System.out.println("课程名称: " + teachingClass.getCourse().getCourseName());
        System.out.println("授课教师: " + teachingClass.getTeacher().getName());
        System.out.println("开课学期: " + teachingClass.getSemester());
        System.out.println("-".repeat(80));
        System.out.printf("%-15s %-20s %-10s\n", "学号", "姓名", "性别");
        System.out.println("-".repeat(80));
        
        List<Student> students = teachingClass.getStudents();
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            System.out.printf("%-15s %-20s %-10s\n",
                    student.getStudentId(),
                    student.getName(),
                    student.getGender());
        }
        
        System.out.println("=".repeat(80));
        System.out.println("共 " + students.size() + " 名学生");
    }
    
    /**
     * 显示学生选课情况
     */
    public void displayStudentCourses(Student student) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("                        学生选课情况");
        System.out.println("=".repeat(80));
        System.out.println("学号: " + student.getStudentId());
        System.out.println("姓名: " + student.getName());
        System.out.println("性别: " + student.getGender());
        System.out.println("-".repeat(80));
        System.out.printf("%-10s %-25s %-12s %-18s\n",
                "教学班号", "课程名称", "授课教师", "开课学期");
        System.out.println("-".repeat(80));
        
        List<TeachingClass> classes = student.getEnrolledClasses();
        for (TeachingClass tc : classes) {
            System.out.printf("%-10s %-25s %-12s %-18s\n",
                    tc.getClassId(),
                    tc.getCourse().getCourseName(),
                    tc.getTeacher().getName(),
                    tc.getSemester());
        }
        
        System.out.println("=".repeat(80));
        System.out.println("共选 " + classes.size() + " 门课程");
    }
    
    /**
     * 显示教学班成绩单（按学号排序）
     */
    public void displayClassGrades(TeachingClass teachingClass, List<Grade> grades) {
        List<Grade> sortedGrades = statisticsService.sortByStudentId(grades);
        
        System.out.println("\n" + "=".repeat(120));
        System.out.println("                                    教学班成绩单");
        System.out.println("=".repeat(120));
        System.out.println("教学班号: " + teachingClass.getClassId());
        System.out.println("课程名称: " + teachingClass.getCourse().getCourseName());
        System.out.println("授课教师: " + teachingClass.getTeacher().getName());
        System.out.println("-".repeat(120));
        System.out.printf("%-15s %-12s %-10s %-10s %-10s %-10s %-10s\n",
                "学号", "姓名", "平时成绩", "期中成绩", "实验成绩", "期末成绩", "综合成绩");
        System.out.println("-".repeat(120));
        
        for (Grade grade : sortedGrades) {
            System.out.printf("%-15s %-12s %-10s %-10s %-10s %-10s %-10s\n",
                    grade.getStudent().getStudentId(),
                    grade.getStudent().getName(),
                    formatScore(grade.getScore(GradeComponent.DAILY)),
                    formatScore(grade.getScore(GradeComponent.MIDTERM)),
                    formatScore(grade.getScore(GradeComponent.EXPERIMENT)),
                    formatScore(grade.getScore(GradeComponent.FINAL)),
                    formatScore(grade.getTotalScore()));
        }
        
        System.out.println("=".repeat(120));
        System.out.println("共 " + grades.size() + " 名学生");
    }
    
    /**
     * 显示教学班成绩排名
     */
    public void displayClassRanking(TeachingClass teachingClass, List<Grade> grades) {
        List<Grade> rankedGrades = statisticsService.rankByScore(grades);
        
        System.out.println("\n" + "=".repeat(100));
        System.out.println("                            教学班成绩排名");
        System.out.println("=".repeat(100));
        System.out.println("教学班号: " + teachingClass.getClassId());
        System.out.println("课程名称: " + teachingClass.getCourse().getCourseName());
        System.out.println("-".repeat(100));
        System.out.printf("%-8s %-15s %-12s %-10s\n",
                "排名", "学号", "姓名", "综合成绩");
        System.out.println("-".repeat(100));
        
        int rank = 1;
        for (Grade grade : rankedGrades) {
            if (grade.getTotalScore() != null) {
                System.out.printf("%-8d %-15s %-12s %-10d\n",
                        rank++,
                        grade.getStudent().getStudentId(),
                        grade.getStudent().getName(),
                        grade.getTotalScore());
            }
        }
        
        System.out.println("=".repeat(100));
    }
    
    /**
     * 显示教学班成绩统计
     */
    public void displayClassStatistics(TeachingClass teachingClass, List<Grade> grades) {
        // 筛选有综合成绩的记录
        List<Grade> validGrades = grades.stream()
                .filter(g -> g.getTotalScore() != null)
                .toList();
        
        if (validGrades.isEmpty()) {
            System.out.println("\n该教学班暂无综合成绩数据！");
            return;
        }
        
        double average = statisticsService.calculateAverage(validGrades);
        int maxScore = statisticsService.calculateMaxScore(validGrades);
        int minScore = statisticsService.calculateMinScore(validGrades);
        double passRate = statisticsService.calculatePassRate(validGrades);
        Map<String, Integer> distribution = statisticsService.calculateScoreDistribution(validGrades);
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("                          教学班成绩统计");
        System.out.println("=".repeat(80));
        System.out.println("教学班号: " + teachingClass.getClassId());
        System.out.println("课程名称: " + teachingClass.getCourse().getCourseName());
        System.out.println("-".repeat(80));
        System.out.println("基本统计:");
        System.out.printf("  平均分: %.2f\n", average);
        System.out.printf("  最高分: %d\n", maxScore);
        System.out.printf("  最低分: %d\n", minScore);
        System.out.printf("  及格率: %.2f%%\n", passRate);
        System.out.println("\n分数段分布:");
        
        for (Map.Entry<String, Integer> entry : distribution.entrySet()) {
            int count = entry.getValue();
            double percentage = (double) count / validGrades.size() * 100;
            System.out.printf("  %s: %d人 (%.2f%%)\n", 
                    entry.getKey(), count, percentage);
        }
        
        System.out.println("=".repeat(80));
    }
    
    /**
     * 显示学生成绩详情
     */
    public void displayStudentGrades(Student student, List<Grade> grades) {
        System.out.println("\n" + "=".repeat(100));
        System.out.println("                              学生成绩详情");
        System.out.println("=".repeat(100));
        System.out.println("学号: " + student.getStudentId());
        System.out.println("姓名: " + student.getName());
        System.out.println("性别: " + student.getGender());
        System.out.println("-".repeat(100));
        System.out.printf("%-25s %-10s %-10s %-10s %-10s %-10s\n",
                "课程名称", "平时", "期中", "实验", "期末", "综合");
        System.out.println("-".repeat(100));
        
        int totalScore = 0;
        int courseCount = 0;
        
        for (Grade grade : grades) {
            System.out.printf("%-25s %-10s %-10s %-10s %-10s %-10s\n",
                    grade.getTeachingClass().getCourse().getCourseName(),
                    formatScore(grade.getScore(GradeComponent.DAILY)),
                    formatScore(grade.getScore(GradeComponent.MIDTERM)),
                    formatScore(grade.getScore(GradeComponent.EXPERIMENT)),
                    formatScore(grade.getScore(GradeComponent.FINAL)),
                    formatScore(grade.getTotalScore()));
            
            if (grade.getTotalScore() != null) {
                totalScore += grade.getTotalScore();
                courseCount++;
            }
        }
        
        System.out.println("=".repeat(100));
        System.out.println("总成绩: " + totalScore);
        if (courseCount > 0) {
            System.out.printf("平均成绩: %.2f\n", (double) totalScore / courseCount);
        }
    }
    
    /**
     * 显示全体学生总成绩排名
     */
    public void displayOverallRanking(List<Student> students, List<Grade> allGrades) {
        // 按学生分组计算总成绩
        Map<Student, Integer> studentTotalScores = new java.util.HashMap<>();
        
        for (Student student : students) {
            int total = allGrades.stream()
                    .filter(g -> g.getStudent().equals(student) && g.getTotalScore() != null)
                    .mapToInt(Grade::getTotalScore)
                    .sum();
            studentTotalScores.put(student, total);
        }
        
        // 排序
        List<Map.Entry<Student, Integer>> sortedList = new java.util.ArrayList<>(studentTotalScores.entrySet());
        sortedList.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("                        全体学生总成绩排名");
        System.out.println("=".repeat(80));
        System.out.printf("%-8s %-15s %-12s %-10s\n",
                "排名", "学号", "姓名", "总成绩");
        System.out.println("-".repeat(80));
        
        int rank = 1;
        for (Map.Entry<Student, Integer> entry : sortedList) {
            if (entry.getValue() > 0) {  // 只显示有成绩的学生
                Student student = entry.getKey();
                System.out.printf("%-8d %-15s %-12s %-10d\n",
                        rank++,
                        student.getStudentId(),
                        student.getName(),
                        entry.getValue());
            }
        }
        
        System.out.println("=".repeat(80));
    }
    
    /**
     * 格式化分数显示
     */
    private String formatScore(Integer score) {
        return score != null ? String.valueOf(score) : "未录入";
    }
}
