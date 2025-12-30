package com.student.management.util;

/**
 * 系统常量定义类
 * 存储所有系统级别的常量配置
 */
public class Constants {
    
    // 数据生成数量常量
    public static final int MIN_STUDENTS = 100;           // 最少学生数量
    public static final int MIN_COURSES = 3;              // 最少课程数量
    public static final int MIN_TEACHERS = 6;             // 最少教师数量
    public static final int MIN_STUDENTS_PER_CLASS = 20;  // 每个教学班最少学生数
    public static final int MIN_COURSES_PER_STUDENT = 3;  // 每个学生最少选课数
    public static final int MIN_TEACHERS_PER_COURSE = 2;  // 每门课最少教师数
    
    // 成绩相关常量
    public static final int MAX_SCORE = 100;              // 最高分数
    public static final int MIN_SCORE = 0;                // 最低分数
    
    // 成绩权重配置
    public static final double DAILY_WEIGHT = 0.2;        // 平时成绩权重 20%
    public static final double MIDTERM_WEIGHT = 0.2;      // 期中成绩权重 20%
    public static final double EXPERIMENT_WEIGHT = 0.2;   // 实验成绩权重 20%
    public static final double FINAL_WEIGHT = 0.4;        // 期末成绩权重 40%
    
    // 学期配置
    public static final String[] SEMESTERS = {
        "2024-2025(1)", "2024-2025(2)", 
        "2025-2026(1)", "2025-2026(2)"
    };
    
    // 性别选项
    public static final String[] GENDERS = {"男", "女"};
    
    // 课程名称示例
    public static final String[] COURSE_NAMES = {
        "Java企业级应用", "数据结构与算法", "数据库原理", 
        "计算机网络", "操作系统", "软件工程",
        "Web开发技术", "人工智能基础", "移动应用开发",
        "云计算技术", "大数据处理", "信息安全"
    };
    
    // 姓氏库（用于生成随机姓名）
    public static final String[] SURNAMES = {
        "王", "李", "张", "刘", "陈", "杨", "黄", "赵", "周", "吴",
        "徐", "孙", "朱", "马", "胡", "郭", "林", "何", "高", "梁"
    };
    
    // 名字库
    public static final String[] GIVEN_NAMES = {
        "伟", "芳", "娜", "秀英", "敏", "静", "丽", "强", "磊", "军",
        "洋", "勇", "艳", "杰", "涛", "明", "超", "秀兰", "霞", "平",
        "刚", "桂英", "建", "浩", "鹏", "俊", "慧", "婷", "峰", "辉"
    };
    
    // 分数段定义
    public static final int[] SCORE_RANGES = {60, 70, 80, 90, 100};
    public static final String[] SCORE_LABELS = {
        "不及格(0-59)", "及格(60-69)", "中等(70-79)", 
        "良好(80-89)", "优秀(90-100)"
    };
}
