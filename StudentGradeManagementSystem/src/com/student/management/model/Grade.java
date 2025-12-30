package com.student.management.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 成绩实体类
 * 记录学生在某个教学班的所有成绩信息
 */
public class Grade {
    private Student student;                           // 学生
    private TeachingClass teachingClass;              // 教学班
    private Map<GradeComponent, Integer> scores;      // 各部分成绩
    private Map<GradeComponent, LocalDateTime> scoreTimes;  // 成绩录入时间
    private Integer totalScore;                        // 综合成绩
    private LocalDateTime totalScoreTime;             // 综合成绩计算时间
    
    public Grade(Student student, TeachingClass teachingClass) {
        this.student = student;
        this.teachingClass = teachingClass;
        this.scores = new HashMap<>();
        this.scoreTimes = new HashMap<>();
    }
    
    // Getter和Setter方法
    public Student getStudent() {
        return student;
    }
    
    public void setStudent(Student student) {
        this.student = student;
    }
    
    public TeachingClass getTeachingClass() {
        return teachingClass;
    }
    
    public void setTeachingClass(TeachingClass teachingClass) {
        this.teachingClass = teachingClass;
    }
    
    public Map<GradeComponent, Integer> getScores() {
        return scores;
    }
    
    public Integer getTotalScore() {
        return totalScore;
    }
    
    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
        this.totalScoreTime = LocalDateTime.now();
    }
    
    public LocalDateTime getTotalScoreTime() {
        return totalScoreTime;
    }
    
    /**
     * 设置某个成绩组成部分的分数
     * @param component 成绩组成部分
     * @param score 分数
     */
    public void setScore(GradeComponent component, int score) {
        scores.put(component, score);
        scoreTimes.put(component, LocalDateTime.now());
    }
    
    /**
     * 获取某个成绩组成部分的分数
     * @param component 成绩组成部分
     * @return 分数，如果未录入则返回null
     */
    public Integer getScore(GradeComponent component) {
        return scores.get(component);
    }
    
    /**
     * 获取某个成绩的录入时间
     * @param component 成绩组成部分
     * @return 录入时间
     */
    public LocalDateTime getScoreTime(GradeComponent component) {
        return scoreTimes.get(component);
    }
    
    /**
     * 检查是否所有成绩都已录入
     * @return 是否完整
     */
    public boolean isComplete() {
        return scores.size() == GradeComponent.values().length;
    }
    
    /**
     * 计算综合成绩
     * @return 综合成绩
     */
    public int calculateTotalScore() {
        if (!isComplete()) {
            throw new IllegalStateException("成绩不完整，无法计算综合成绩");
        }
        
        double total = 0.0;
        for (GradeComponent component : GradeComponent.values()) {
            total += scores.get(component) * component.getWeight();
        }
        
        this.totalScore = (int) Math.round(total);
        this.totalScoreTime = LocalDateTime.now();
        return this.totalScore;
    }
    
    /**
     * 格式化时间显示
     * @param time 时间
     * @return 格式化后的字符串
     */
    private String formatTime(LocalDateTime time) {
        if (time == null) return "未录入";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return time.format(formatter);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("学生: %s, 课程: %s\n", 
                student.getName(), teachingClass.getCourse().getCourseName()));
        
        for (GradeComponent component : GradeComponent.values()) {
            Integer score = scores.get(component);
            LocalDateTime time = scoreTimes.get(component);
            sb.append(String.format("  %s: %s (录入时间: %s)\n",
                    component.getName(),
                    score != null ? score : "未录入",
                    formatTime(time)));
        }
        
        sb.append(String.format("  综合成绩: %s (计算时间: %s)",
                totalScore != null ? totalScore : "未计算",
                formatTime(totalScoreTime)));
        
        return sb.toString();
    }
}
