package com.student.management.service;

import com.student.management.model.*;
import com.student.management.util.Constants;

import java.util.*;

/**
 * 统计服务类
 * 提供各种统计分析功能
 */
public class StatisticsService {
    
    /**
     * 统计分数段分布
     * @param grades 成绩列表
     * @return 分数段统计结果
     */
    public Map<String, Integer> calculateScoreDistribution(List<Grade> grades) {
        Map<String, Integer> distribution = new LinkedHashMap<>();
        
        // 初始化各分数段计数
        for (String label : Constants.SCORE_LABELS) {
            distribution.put(label, 0);
        }
        
        // 统计每个成绩所属的分数段
        for (Grade grade : grades) {
            if (grade.getTotalScore() != null) {
                int score = grade.getTotalScore();
                String label = getScoreLabel(score);
                distribution.put(label, distribution.get(label) + 1);
            }
        }
        
        return distribution;
    }
    
    /**
     * 根据分数获取分数段标签
     * @param score 分数
     * @return 分数段标签
     */
    private String getScoreLabel(int score) {
        if (score < 60) return Constants.SCORE_LABELS[0];
        if (score < 70) return Constants.SCORE_LABELS[1];
        if (score < 80) return Constants.SCORE_LABELS[2];
        if (score < 90) return Constants.SCORE_LABELS[3];
        return Constants.SCORE_LABELS[4];
    }
    
    /**
     * 计算平均分
     * @param grades 成绩列表
     * @return 平均分
     */
    public double calculateAverage(List<Grade> grades) {
        if (grades.isEmpty()) {
            return 0.0;
        }
        
        int sum = 0;
        int count = 0;
        
        for (Grade grade : grades) {
            if (grade.getTotalScore() != null) {
                sum += grade.getTotalScore();
                count++;
            }
        }
        
        return count > 0 ? (double) sum / count : 0.0;
    }
    
    /**
     * 计算最高分
     * @param grades 成绩列表
     * @return 最高分
     */
    public int calculateMaxScore(List<Grade> grades) {
        return grades.stream()
                .filter(g -> g.getTotalScore() != null)
                .mapToInt(Grade::getTotalScore)
                .max()
                .orElse(0);
    }
    
    /**
     * 计算最低分
     * @param grades 成绩列表
     * @return 最低分
     */
    public int calculateMinScore(List<Grade> grades) {
        return grades.stream()
                .filter(g -> g.getTotalScore() != null)
                .mapToInt(Grade::getTotalScore)
                .min()
                .orElse(0);
    }
    
    /**
     * 计算及格率
     * @param grades 成绩列表
     * @return 及格率（百分比）
     */
    public double calculatePassRate(List<Grade> grades) {
        long total = grades.stream()
                .filter(g -> g.getTotalScore() != null)
                .count();
        
        if (total == 0) {
            return 0.0;
        }
        
        long passCount = grades.stream()
                .filter(g -> g.getTotalScore() != null && g.getTotalScore() >= 60)
                .count();
        
        return (double) passCount / total * 100;
    }
    
    /**
     * 对学生成绩进行排名
     * @param grades 成绩列表
     * @return 排序后的成绩列表
     */
    public List<Grade> rankByScore(List<Grade> grades) {
        List<Grade> sortedGrades = new ArrayList<>(grades);
        sortedGrades.sort((g1, g2) -> {
            Integer score1 = g1.getTotalScore();
            Integer score2 = g2.getTotalScore();
            
            if (score1 == null && score2 == null) return 0;
            if (score1 == null) return 1;
            if (score2 == null) return -1;
            
            return score2.compareTo(score1); // 降序排列
        });
        
        return sortedGrades;
    }
    
    /**
     * 按学号排序
     * @param grades 成绩列表
     * @return 排序后的成绩列表
     */
    public List<Grade> sortByStudentId(List<Grade> grades) {
        List<Grade> sortedGrades = new ArrayList<>(grades);
        sortedGrades.sort(Comparator.comparing(g -> g.getStudent().getStudentId()));
        return sortedGrades;
    }
    
    /**
     * 计算学生的总成绩（所有课程综合成绩之和）
     * @param studentGrades 学生的所有成绩
     * @return 总成绩
     */
    public int calculateStudentTotalScore(List<Grade> studentGrades) {
        return studentGrades.stream()
                .filter(g -> g.getTotalScore() != null)
                .mapToInt(Grade::getTotalScore)
                .sum();
    }
    
    /**
     * 计算学生的平均成绩
     * @param studentGrades 学生的所有成绩
     * @return 平均成绩
     */
    public double calculateStudentAverage(List<Grade> studentGrades) {
        long count = studentGrades.stream()
                .filter(g -> g.getTotalScore() != null)
                .count();
        
        if (count == 0) {
            return 0.0;
        }
        
        int sum = calculateStudentTotalScore(studentGrades);
        return (double) sum / count;
    }
}
