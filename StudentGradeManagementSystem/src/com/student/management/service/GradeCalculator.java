package com.student.management.service;

import com.student.management.model.*;
import com.student.management.util.DataStorage;

import java.util.*;

/**
 * 成绩计算服务类
 * 提供成绩相关的计算功能
 */
public class GradeCalculator {
    
    private DataStorage storage = DataStorage.getInstance();
    private Random random = new Random();
    
    /**
     * 为教学班生成平时成绩
     * @param teachingClass 教学班
     */
    public void generateDailyScores(TeachingClass teachingClass) {
        System.out.println("\n为教学班 " + teachingClass.getClassId() + 
                " (" + teachingClass.getCourse().getCourseName() + ") 生成平时成绩...");
        
        int count = 0;
        for (Student student : teachingClass.getStudents()) {
            Grade grade = storage.getGrade(student.getStudentId(), teachingClass.getClassId());
            if (grade != null && grade.getScore(GradeComponent.DAILY) == null) {
                int score = generateScore(70, 100); // 平时成绩通常较高
                grade.setScore(GradeComponent.DAILY, score);
                count++;
            }
        }
        
        System.out.println("已为 " + count + " 名学生生成平时成绩");
    }
    
    /**
     * 为教学班生成期中成绩
     * @param teachingClass 教学班
     */
    public void generateMidtermScores(TeachingClass teachingClass) {
        System.out.println("\n为教学班 " + teachingClass.getClassId() + 
                " (" + teachingClass.getCourse().getCourseName() + ") 生成期中成绩...");
        
        int count = 0;
        for (Student student : teachingClass.getStudents()) {
            Grade grade = storage.getGrade(student.getStudentId(), teachingClass.getClassId());
            if (grade != null && grade.getScore(GradeComponent.MIDTERM) == null) {
                int score = generateScore(50, 95); // 期中成绩分布较广
                grade.setScore(GradeComponent.MIDTERM, score);
                count++;
            }
        }
        
        System.out.println("已为 " + count + " 名学生生成期中成绩");
    }
    
    /**
     * 为教学班生成实验成绩
     * @param teachingClass 教学班
     */
    public void generateExperimentScores(TeachingClass teachingClass) {
        System.out.println("\n为教学班 " + teachingClass.getClassId() + 
                " (" + teachingClass.getCourse().getCourseName() + ") 生成实验成绩...");
        
        int count = 0;
        for (Student student : teachingClass.getStudents()) {
            Grade grade = storage.getGrade(student.getStudentId(), teachingClass.getClassId());
            if (grade != null && grade.getScore(GradeComponent.EXPERIMENT) == null) {
                int score = generateScore(60, 98); // 实验成绩通常较好
                grade.setScore(GradeComponent.EXPERIMENT, score);
                count++;
            }
        }
        
        System.out.println("已为 " + count + " 名学生生成实验成绩");
    }
    
    /**
     * 为教学班生成期末成绩
     * @param teachingClass 教学班
     */
    public void generateFinalScores(TeachingClass teachingClass) {
        System.out.println("\n为教学班 " + teachingClass.getClassId() + 
                " (" + teachingClass.getCourse().getCourseName() + ") 生成期末成绩...");
        
        int count = 0;
        for (Student student : teachingClass.getStudents()) {
            Grade grade = storage.getGrade(student.getStudentId(), teachingClass.getClassId());
            if (grade != null && grade.getScore(GradeComponent.FINAL) == null) {
                int score = generateScore(45, 95); // 期末成绩分布最广
                grade.setScore(GradeComponent.FINAL, score);
                count++;
            }
        }
        
        System.out.println("已为 " + count + " 名学生生成期末成绩");
    }
    
    /**
     * 为所有教学班生成某种类型的成绩
     * @param component 成绩类型
     */
    public void generateScoresForAll(GradeComponent component) {
        List<TeachingClass> classes = storage.getAllTeachingClasses();
        
        System.out.println("\n=== 为所有教学班生成" + component.getName() + " ===");
        
        for (TeachingClass tc : classes) {
            switch (component) {
                case DAILY:
                    generateDailyScores(tc);
                    break;
                case MIDTERM:
                    generateMidtermScores(tc);
                    break;
                case EXPERIMENT:
                    generateExperimentScores(tc);
                    break;
                case FINAL:
                    generateFinalScores(tc);
                    break;
            }
        }
        
        System.out.println("\n所有" + component.getName() + "生成完成！");
    }
    
    /**
     * 计算所有完整成绩的综合成绩
     */
    public void calculateAllTotalScores() {
        System.out.println("\n=== 计算所有综合成绩 ===");
        
        int count = 0;
        Map<String, Grade> allGrades = storage.getAllGrades();
        
        for (Grade grade : allGrades.values()) {
            if (grade.isComplete() && grade.getTotalScore() == null) {
                grade.calculateTotalScore();
                count++;
            }
        }
        
        System.out.println("已计算 " + count + " 个综合成绩");
    }
    
    /**
     * 生成指定范围内的随机成绩
     * 使用正态分布，使成绩分布更合理
     * @param min 最小值
     * @param max 最大值
     * @return 成绩
     */
    private int generateScore(int min, int max) {
        // 使用正态分布生成成绩，均值为(min+max)/2，标准差为(max-min)/6
        double mean = (min + max) / 2.0;
        double stdDev = (max - min) / 6.0;
        
        int score;
        do {
            double gaussian = random.nextGaussian();
            score = (int) Math.round(mean + gaussian * stdDev);
        } while (score < min || score > max);
        
        return score;
    }
    
    /**
     * 检查教学班是否已生成某类成绩
     * @param teachingClass 教学班
     * @param component 成绩类型
     * @return 是否已生成
     */
    public boolean hasGeneratedScores(TeachingClass teachingClass, GradeComponent component) {
        List<Grade> grades = storage.getGradesByClass(teachingClass);
        if (grades.isEmpty()) {
            return false;
        }
        
        for (Grade grade : grades) {
            if (grade.getScore(component) == null) {
                return false;
            }
        }
        
        return true;
    }
}
