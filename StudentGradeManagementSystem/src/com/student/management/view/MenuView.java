package com.student.management.view;

/**
 * 菜单视图类
 * 负责显示各种菜单界面
 */
public class MenuView {
    
    /**
     * 显示主菜单
     */
    public void showMainMenu() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                学生成绩管理系统 - 主菜单");
        System.out.println("=".repeat(60));
        System.out.println("  1. 数据初始化");
        System.out.println("  2. 学生选课管理");
        System.out.println("  3. 成绩管理");
        System.out.println("  4. 成绩查询与统计");
        System.out.println("  5. 系统信息");
        System.out.println("  0. 退出系统");
        System.out.println("=".repeat(60));
    }
    
    /**
     * 显示数据初始化菜单
     */
    public void showInitMenu() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                    数据初始化菜单");
        System.out.println("=".repeat(60));
        System.out.println("  1. 生成初始化数据（教师、学生、课程、教学班）");
        System.out.println("  2. 查看数据摘要");
        System.out.println("  3. 重置所有数据");
        System.out.println("  0. 返回主菜单");
        System.out.println("=".repeat(60));
    }
    
    /**
     * 显示选课管理菜单
     */
    public void showCourseSelectionMenu() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                    学生选课管理");
        System.out.println("=".repeat(60));
        System.out.println("  1. 自动为所有学生选课");
        System.out.println("  2. 查看教学班学生名单");
        System.out.println("  3. 查看学生选课情况");
        System.out.println("  0. 返回主菜单");
        System.out.println("=".repeat(60));
    }
    
    /**
     * 显示成绩管理菜单
     */
    public void showGradeManagementMenu() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                      成绩管理");
        System.out.println("=".repeat(60));
        System.out.println("  1. 生成平时成绩");
        System.out.println("  2. 生成期中成绩");
        System.out.println("  3. 生成实验成绩");
        System.out.println("  4. 生成期末成绩");
        System.out.println("  5. 计算综合成绩");
        System.out.println("  6. 一键生成所有成绩");
        System.out.println("  0. 返回主菜单");
        System.out.println("=".repeat(60));
    }
    
    /**
     * 显示查询统计菜单
     */
    public void showQueryMenu() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                  成绩查询与统计");
        System.out.println("=".repeat(60));
        System.out.println("  1. 查询学生成绩（按学号）");
        System.out.println("  2. 查询学生成绩（按姓名）");
        System.out.println("  3. 显示教学班成绩单");
        System.out.println("  4. 显示教学班成绩排名");
        System.out.println("  5. 显示教学班成绩统计");
        System.out.println("  6. 显示全体学生总成绩排名");
        System.out.println("  7. 显示课程列表");
        System.out.println("  8. 显示教学班列表");
        System.out.println("  0. 返回主菜单");
        System.out.println("=".repeat(60));
    }
    
    /**
     * 显示欢迎信息
     */
    public void showWelcome() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("          欢迎使用学生成绩管理系统 v1.0");
        System.out.println("=".repeat(60));
        System.out.println("  本系统采用MVC设计模式开发");
        System.out.println("  功能：数据管理、成绩录入、查询统计");
        System.out.println("=".repeat(60));
    }
    
    /**
     * 显示退出信息
     */
    public void showGoodbye() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("          感谢使用学生成绩管理系统！");
        System.out.println("                  再见！");
        System.out.println("=".repeat(60));
    }
    
    /**
     * 显示错误提示
     * @param message 错误信息
     */
    public void showError(String message) {
        System.out.println("\n[错误] " + message);
    }
    
    /**
     * 显示成功提示
     * @param message 成功信息
     */
    public void showSuccess(String message) {
        System.out.println("\n[成功] " + message);
    }
    
    /**
     * 显示警告提示
     * @param message 警告信息
     */
    public void showWarning(String message) {
        System.out.println("\n[警告] " + message);
    }
    
    /**
     * 显示信息提示
     * @param message 信息内容
     */
    public void showInfo(String message) {
        System.out.println("\n[信息] " + message);
    }
    
    /**
     * 清屏（模拟效果）
     */
    public void clearScreen() {
        for (int i = 0; i < 2; i++) {
            System.out.println();
        }
    }
}
