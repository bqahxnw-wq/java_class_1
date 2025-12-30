package com.rbac.controller;

import com.rbac.model.*;
import com.rbac.service.AuditService;
import com.rbac.util.DataStorage;
import com.rbac.util.InputValidator;
import com.rbac.view.DisplayView;
import com.rbac.view.MenuView;

import java.util.List;
import java.util.Map;

/**
 * 审计日志控制器
 */
public class AuditController {
    
    private DataStorage storage = DataStorage.getInstance();
    private MenuView menuView = new MenuView();
    private DisplayView displayView = new DisplayView();
    private AuditService auditService = new AuditService();
    
    /**
     * 运行审计日志模块
     */
    public void run() {
        boolean running = true;
        
        while (running) {
            menuView.showAuditMenu();
            int choice = InputValidator.getIntInput("请选择操作 (0-6): ", 0, 6);
            
            switch (choice) {
                case 1:
                    viewAllAuditLogs();
                    break;
                case 2:
                    viewRecentLogs();
                    break;
                case 3:
                    viewLogsByUser();
                    break;
                case 4:
                    viewLogsByOperation();
                    break;
                case 5:
                    viewFailedOperations();
                    break;
                case 6:
                    viewOperationStatistics();
                    break;
                case 0:
                    running = false;
                    break;
            }
        }
    }
    
    /**
     * 查看所有审计日志
     */
    private void viewAllAuditLogs() {
        List<AuditLog> logs = auditService.getAllAuditLogs();
        
        if (logs.isEmpty()) {
            menuView.showWarning("暂无审计日志！");
        } else {
            displayView.displayAuditLogs(logs);
        }
        
        InputValidator.pause();
    }
    
    /**
     * 查看最近的日志
     */
    private void viewRecentLogs() {
        int count = InputValidator.getIntInput("请输入要查看的日志数量 (1-100): ", 1, 100);
        
        List<AuditLog> logs = auditService.getRecentAuditLogs(count);
        
        if (logs.isEmpty()) {
            menuView.showWarning("暂无审计日志！");
        } else {
            displayView.displayAuditLogs(logs);
        }
        
        InputValidator.pause();
    }
    
    /**
     * 按用户查询日志
     */
    private void viewLogsByUser() {
        if (storage.getUserCount() == 0) {
            menuView.showError("系统中没有用户数据！");
            InputValidator.pause();
            return;
        }
        
        String username = InputValidator.getNonEmptyString("请输入用户名: ");
        User user = storage.getUserByUsername(username);
        
        if (user == null) {
            menuView.showError("未找到该用户！");
            InputValidator.pause();
            return;
        }
        
        List<AuditLog> logs = auditService.getAuditLogsByUser(user.getUserId());
        
        if (logs.isEmpty()) {
            menuView.showWarning("该用户暂无操作记录！");
        } else {
            displayView.displayAuditLogs(logs);
        }
        
        InputValidator.pause();
    }
    
    /**
     * 按操作类型查询日志
     */
    private void viewLogsByOperation() {
        String operation = InputValidator.getNonEmptyString("请输入操作类型 (如: 创建用户, 删除角色): ");
        
        List<AuditLog> logs = auditService.getAuditLogsByOperation(operation);
        
        if (logs.isEmpty()) {
            menuView.showWarning("未找到相关操作记录！");
        } else {
            displayView.displayAuditLogs(logs);
        }
        
        InputValidator.pause();
    }
    
    /**
     * 查看失败的操作
     */
    private void viewFailedOperations() {
        List<AuditLog> logs = auditService.getFailedOperations();
        
        if (logs.isEmpty()) {
            menuView.showInfo("暂无失败的操作记录！");
        } else {
            System.out.println("\n=== 失败的操作记录 ===");
            displayView.displayAuditLogs(logs);
        }
        
        InputValidator.pause();
    }
    
    /**
     * 查看操作统计
     */
    private void viewOperationStatistics() {
        Map<String, Long> statistics = auditService.getOperationStatistics();
        
        if (statistics.isEmpty()) {
            menuView.showWarning("暂无操作统计数据！");
        } else {
            displayView.displayOperationStatistics(statistics);
        }
        
        InputValidator.pause();
    }
}
