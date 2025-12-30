package com.student.management;

import com.student.management.controller.SystemController;

/**
 * 学生成绩管理系统 - 主程序入口
 * 
 * 系统功能：
 * 1. 数据初始化：生成学生、教师、课程、教学班数据
 * 2. 选课管理：自动选课、查看教学班和学生选课情况
 * 3. 成绩管理：生成平时、期中、实验、期末成绩，计算综合成绩
 * 4. 查询统计：查询学生成绩、教学班成绩排名、成绩统计分析
 *
 * @author hx
 **/
public class Main {
    
    public static void main(String[] args) {
        try {
            SystemController systemController = new SystemController();
            systemController.start();
        } catch (Exception e) {
            System.err.println("\n系统运行异常: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
