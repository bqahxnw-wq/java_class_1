package com.student.management.controller;

import com.student.management.service.DataGenerator;
import com.student.management.util.DataStorage;
import com.student.management.util.InputValidator;
import com.student.management.view.DisplayView;
import com.student.management.view.MenuView;

/**
 * 数据初始化控制器
 * 处理数据初始化相关的操作
 */
public class DataInitController {
    
    private DataStorage storage = DataStorage.getInstance();
    private DataGenerator dataGenerator = new DataGenerator();
    private MenuView menuView = new MenuView();
    private DisplayView displayView = new DisplayView();
    
    /**
     * 运行数据初始化模块
     */
    public void run() {
        boolean running = true;
        
        while (running) {
            menuView.showInitMenu();
            int choice = InputValidator.getIntInput("请选择操作 (0-3): ", 0, 3);
            
            switch (choice) {
                case 1:
                    generateData();
                    break;
                case 2:
                    showDataSummary();
                    break;
                case 3:
                    resetData();
                    break;
                case 0:
                    running = false;
                    break;
            }
        }
    }
    
    /**
     * 生成初始化数据
     */
    private void generateData() {
        // 检查是否已有数据
        if (storage.getStudentCount() > 0) {
            boolean confirm = InputValidator.getConfirmation(
                    "系统中已有数据，是否重新生成？（将清除所有现有数据）");
            if (!confirm) {
                return;
            }
            storage.reset();
        }
        
        dataGenerator.generateAllData();
        menuView.showSuccess("初始化数据生成成功！");
        InputValidator.pause();
    }
    
    /**
     * 显示数据摘要
     */
    private void showDataSummary() {
        displayView.displayDataSummary(
                storage.getStudentCount(),
                storage.getTeacherCount(),
                storage.getCourseCount(),
                storage.getTeachingClassCount(),
                storage.getGradeCount()
        );
        InputValidator.pause();
    }
    
    /**
     * 重置所有数据
     */
    private void resetData() {
        if (storage.getStudentCount() == 0) {
            menuView.showWarning("系统中没有数据！");
            InputValidator.pause();
            return;
        }
        
        boolean confirm = InputValidator.getConfirmation(
                "确定要清除所有数据吗？此操作不可恢复！");
        
        if (confirm) {
            storage.reset();
            menuView.showSuccess("所有数据已清除！");
        } else {
            menuView.showInfo("操作已取消");
        }
        
        InputValidator.pause();
    }
}
