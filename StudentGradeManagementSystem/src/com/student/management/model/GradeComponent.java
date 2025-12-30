package com.student.management.model;

/**
 * 成绩组成部分枚举
 * 定义成绩的四个组成部分及其权重
 */
public enum GradeComponent {
    DAILY("平时成绩", 0.2),
    MIDTERM("期中成绩", 0.2),
    EXPERIMENT("实验成绩", 0.2),
    FINAL("期末成绩", 0.4);
    
    private final String name;
    private final double weight;
    
    GradeComponent(String name, double weight) {
        this.name = name;
        this.weight = weight;
    }
    
    public String getName() {
        return name;
    }
    
    public double getWeight() {
        return weight;
    }
}
