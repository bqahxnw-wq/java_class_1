package com.student.management.controller;

import com.student.management.model.GradeComponent;
import com.student.management.service.GradeCalculator;
import com.student.management.util.DataStorage;
import com.student.management.util.InputValidator;
import com.student.management.view.MenuView;

/**
 * 成绩控制器
 * 处理成绩管理相关的操作
 */
public class GradeController {
    
    private DataStorage storage = DataStorage.getInstance();
    private GradeCalculator gradeCalculator = new GradeCalculator();
    private MenuView menuView = new MenuView();
    
    /**
     * 运行成绩管理模块
     */
    public void run() {
        boolean running = true;
        
        while (running) {
            menuView.showGradeManagementMenu();
            int choice = InputValidator.getIntInput("请选择操作 (0-6): ", 0, 6);
            
            switch (choice) {
                case 1:
                    generateDailyScores();
                    break;
                case 2:
                    generateMidtermScores();
                    break;
                case 3:
                    generateExperimentScores();
                    break;
                case 4:
                    generateFinalScores();
                    break;
                case 5:
                    calculateTotalScores();
                    break;
                case 6:
                    generateAllScores();
                    break;
                case 0:
                    running = false;
                    break;
            }
        }
    }
    
    /**
     * 生成平时成绩
     */
    private void generateDailyScores() {
        if (!checkPrerequisites()) {
            return;
        }
        
        gradeCalculator.generateScoresForAll(GradeComponent.DAILY);
        menuView.showSuccess("平时成绩生成完成！");
        InputValidator.pause();
    }
    
    /**
     * 生成期中成绩
     */
    private void generateMidtermScores() {
        if (!checkPrerequisites()) {
            return;
        }
        
        gradeCalculator.generateScoresForAll(GradeComponent.MIDTERM);
        menuView.showSuccess("期中成绩生成完成！");
        InputValidator.pause();
    }
    
    /**
     * 生成实验成绩
     */
    private void generateExperimentScores() {
        if (!checkPrerequisites()) {
            return;
        }
        
        gradeCalculator.generateScoresForAll(GradeComponent.EXPERIMENT);
        menuView.showSuccess("实验成绩生成完成！");
        InputValidator.pause();
    }
    
    /**
     * 生成期末成绩
     */
    private void generateFinalScores() {
        if (!checkPrerequisites()) {
            return;
        }
        
        gradeCalculator.generateScoresForAll(GradeComponent.FINAL);
        menuView.showSuccess("期末成绩生成完成！");
        InputValidator.pause();
    }
    
    /**
     * 计算综合成绩
     */
    private void calculateTotalScores() {
        if (!checkPrerequisites()) {
            return;
        }
        
        gradeCalculator.calculateAllTotalScores();
        menuView.showSuccess("综合成绩计算完成！");
        InputValidator.pause();
    }
    
    /**
     * 一键生成所有成绩
     */
    private void generateAllScores() {
        if (!checkPrerequisites()) {
            return;
        }
        
        boolean confirm = InputValidator.getConfirmation(
                "确定要生成所有成绩吗？（包括平时、期中、实验、期末和综合成绩）");
        
        if (!confirm) {
            menuView.showInfo("操作已取消");
            InputValidator.pause();
            return;
        }
        
        System.out.println("\n=== 开始生成所有成绩 ===");
        
        // 按顺序生成各项成绩
        gradeCalculator.generateScoresForAll(GradeComponent.DAILY);
        gradeCalculator.generateScoresForAll(GradeComponent.MIDTERM);
        gradeCalculator.generateScoresForAll(GradeComponent.EXPERIMENT);
        gradeCalculator.generateScoresForAll(GradeComponent.FINAL);
        gradeCalculator.calculateAllTotalScores();
        
        System.out.println("\n=== 所有成绩生成完成 ===");
        menuView.showSuccess("所有成绩已生成！");
        InputValidator.pause();
    }
    
    /**
     * 检查前置条件
     * @return 是否满足条件
     */
    private boolean checkPrerequisites() {
        if (storage.getGradeCount() == 0) {
            menuView.showError("请先完成学生选课！");
            InputValidator.pause();
            return false;
        }
        return true;
    }
}
