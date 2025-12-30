package com.rbac.controller;

import com.rbac.model.*;
import com.rbac.service.AuthorizationService;
import com.rbac.util.DataStorage;
import com.rbac.util.InputValidator;
import com.rbac.view.MenuView;

/**
 * 权限校验控制器
 */
public class AuthorizationController {
    
    private DataStorage storage = DataStorage.getInstance();
    private MenuView menuView = new MenuView();
    private AuthorizationService authService = new AuthorizationService();
    
    /**
     * 运行权限校验模块
     */
    public void run() {
        boolean running = true;
        
        while (running) {
            menuView.showAuthorizationMenu();
            int choice = InputValidator.getIntInput("请选择操作 (0-4): ", 0, 4);
            
            switch (choice) {
                case 1:
                    checkUserPermission();
                    break;
                case 2:
                    checkUserRole();
                    break;
                case 3:
                    checkResourceAccess();
                    break;
                case 4:
                    simulateOperationCheck();
                    break;
                case 0:
                    running = false;
                    break;
            }
        }
    }
    
    /**
     * 检查用户权限
     */
    private void checkUserPermission() {
        if (storage.getUserCount() == 0) {
            menuView.showError("请先生成初始化数据！");
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
        
        String permCode = InputValidator.getNonEmptyString("请输入权限编码: ");
        
        boolean hasPermission = authService.checkPermission(user, permCode);
        
        System.out.println("\n=== 权限校验结果 ===");
        System.out.println("用户: " + user.getUsername());
        System.out.println("权限: " + permCode);
        System.out.println("结果: " + (hasPermission ? "✓ 拥有权限" : "✗ 无权限"));
        System.out.println("=".repeat(40));
        
        InputValidator.pause();
    }
    
    /**
     * 检查用户角色
     */
    private void checkUserRole() {
        if (storage.getUserCount() == 0) {
            menuView.showError("请先生成初始化数据！");
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
        
        String roleCode = InputValidator.getNonEmptyString("请输入角色编码: ");
        
        boolean hasRole = authService.checkRole(user, roleCode);
        
        System.out.println("\n=== 角色校验结果 ===");
        System.out.println("用户: " + user.getUsername());
        System.out.println("角色: " + roleCode);
        System.out.println("结果: " + (hasRole ? "✓ 拥有角色" : "✗ 无此角色"));
        System.out.println("=".repeat(40));
        
        InputValidator.pause();
    }
    
    /**
     * 检查资源访问权限
     */
    private void checkResourceAccess() {
        if (storage.getUserCount() == 0 || storage.getResourceCount() == 0) {
            menuView.showError("请先生成初始化数据！");
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
        
        String resourceCode = InputValidator.getNonEmptyString("请输入资源编码: ");
        
        boolean canAccess = authService.checkResourceAccess(user, resourceCode);
        
        System.out.println("\n=== 资源访问权限校验结果 ===");
        System.out.println("用户: " + user.getUsername());
        System.out.println("资源: " + resourceCode);
        System.out.println("结果: " + (canAccess ? "✓ 可以访问" : "✗ 无权访问"));
        System.out.println("=".repeat(40));
        
        InputValidator.pause();
    }
    
    /**
     * 模拟操作权限校验
     */
    private void simulateOperationCheck() {
        if (storage.getUserCount() == 0) {
            menuView.showError("请先生成初始化数据！");
            InputValidator.pause();
            return;
        }
        
        System.out.println("\n=== 模拟操作权限校验 ===");
        System.out.println("可选操作:");
        System.out.println("1. 创建用户 (需要 USER_CREATE 权限)");
        System.out.println("2. 删除用户 (需要 USER_DELETE 权限)");
        System.out.println("3. 创建角色 (需要 ROLE_CREATE 权限)");
        System.out.println("4. 查看审计日志 (需要 AUDIT_VIEW 权限)");
        
        int opChoice = InputValidator.getIntInput("请选择操作 (1-4): ", 1, 4);
        
        String permCode = "";
        String opDesc = "";
        
        switch (opChoice) {
            case 1:
                permCode = com.rbac.util.Constants.PERM_USER_CREATE;
                opDesc = "创建用户";
                break;
            case 2:
                permCode = com.rbac.util.Constants.PERM_USER_DELETE;
                opDesc = "删除用户";
                break;
            case 3:
                permCode = com.rbac.util.Constants.PERM_ROLE_CREATE;
                opDesc = "创建角色";
                break;
            case 4:
                permCode = com.rbac.util.Constants.PERM_AUDIT_VIEW;
                opDesc = "查看审计日志";
                break;
        }
        
        String username = InputValidator.getNonEmptyString("\n请输入用户名: ");
        User user = storage.getUserByUsername(username);
        
        if (user == null) {
            menuView.showError("未找到该用户！");
            InputValidator.pause();
            return;
        }
        
        boolean hasPermission = authService.checkPermissionWithMessage(user, permCode, opDesc);
        
        if (hasPermission) {
            System.out.println("\n✓ 权限校验通过！用户可以执行该操作。");
        } else {
            System.out.println("\n✗ 权限校验失败！用户无法执行该操作。");
        }
        
        InputValidator.pause();
    }
}
