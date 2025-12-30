package com.student.management.controller;

import com.student.management.util.DataStorage;
import com.student.management.util.InputValidator;
import com.student.management.view.DisplayView;
import com.student.management.view.MenuView;

/**
 * 系统主控制器
 * 协调各个子控制器，管理系统整体流程
 */
public class SystemController {
    
    private DataStorage storage = DataStorage.getInstance();
    private MenuView menuView = new MenuView();
    private DisplayView displayView = new DisplayView();
    
    // 子控制器
    private DataInitController dataInitController = new DataInitController();
    private CourseSelectionController courseSelectionController = new CourseSelectionController();
    private GradeController gradeController = new GradeController();
    private QueryController queryController = new QueryController();
    
    /**
     * 启动系统
     */
    public void start() {
        menuView.showWelcome();
        InputValidator.pause();
        
        boolean running = true;
        
        while (running) {
            menuView.showMainMenu();
            int choice = InputValidator.getIntInput("请选择操作 (0-5): ", 0, 5);
            
            switch (choice) {
                case 1:
                    dataInitController.run();
                    break;
                case 2:
                    courseSelectionController.run();
                    break;
                case 3:
                    gradeController.run();
                    break;
                case 4:
                    queryController.run();
                    break;
                case 5:
                    showSystemInfo();
                    break;
                case 0:
                    running = false;
                    break;
            }
        }
        
        exit();
    }
    
    /**
     * 显示系统信息
     */
    private void showSystemInfo() {
        displayView.displayDataSummary(
                storage.getStudentCount(),
                storage.getTeacherCount(),
                storage.getCourseCount(),
                storage.getTeachingClassCount(),
                storage.getGradeCount()
        );
        
        System.out.println("\n系统设计信息:");
        System.out.println("  设计模式: MVC (Model-View-Controller)");
        System.out.println("  开发语言: Java");
        System.out.println("  数据存储: 内存存储（单例模式）");
        System.out.println("\n系统功能模块:");
        System.out.println("  1. 数据初始化 - 生成学生、教师、课程、教学班数据");
        System.out.println("  2. 选课管理 - 自动选课、查看选课情况");
        System.out.println("  3. 成绩管理 - 生成各项成绩、计算综合成绩");
        System.out.println("  4. 查询统计 - 成绩查询、排名、统计分析");
        
        InputValidator.pause();
    }
    
    /**
     * 退出系统
     */
    private void exit() {
        menuView.showGoodbye();
        InputValidator.close();
    }
}
