package com.rbac.controller;

import com.rbac.util.DataStorage;
import com.rbac.util.InputValidator;
import com.rbac.view.DisplayView;
import com.rbac.view.MenuView;

/**
 * 系统主控制器
 */
public class SystemController {
    
    private DataStorage storage = DataStorage.getInstance();
    private MenuView menuView = new MenuView();
    private DisplayView displayView = new DisplayView();
    
    // 子控制器
    private DataInitController dataInitController = new DataInitController();
    private UserManagementController userManagementController = new UserManagementController();
    private RoleManagementController roleManagementController = new RoleManagementController();
    private PermissionManagementController permissionManagementController = new PermissionManagementController();
    private AuthorizationController authorizationController = new AuthorizationController();
    private AuditController auditController = new AuditController();
    
    /**
     * 启动系统
     */
    public void start() {
        menuView.showWelcome();
        InputValidator.pause();
        
        boolean running = true;
        
        while (running) {
            menuView.showMainMenu();
            int choice = InputValidator.getIntInput("请选择操作 (0-7): ", 0, 7);
            
            switch (choice) {
                case 1:
                    dataInitController.run();
                    break;
                case 2:
                    userManagementController.run();
                    break;
                case 3:
                    roleManagementController.run();
                    break;
                case 4:
                    permissionManagementController.run();
                    break;
                case 5:
                    authorizationController.run();
                    break;
                case 6:
                    auditController.run();
                    break;
                case 7:
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
                storage.getUserCount(),
                storage.getRoleCount(),
                storage.getPermissionCount(),
                storage.getResourceCount(),
                storage.getAuditLogCount()
        );
        
        System.out.println("\n系统设计信息:");
        System.out.println("  设计模式: MVC (Model-View-Controller)");
        System.out.println("  权限模型: RBAC (Role-Based Access Control)");
        System.out.println("  开发语言: Java");
        System.out.println("  数据存储: 内存存储（单例模式）");
        System.out.println("\n系统功能模块:");
        System.out.println("  1. 数据初始化 - 生成用户、角色、权限、资源数据");
        System.out.println("  2. 用户管理 - 创建、查询、修改、删除用户");
        System.out.println("  3. 角色管理 - 创建、查询、修改、删除角色");
        System.out.println("  4. 权限管理 - 查看权限、资源列表");
        System.out.println("  5. 权限校验 - 校验用户权限和角色");
        System.out.println("  6. 审计日志 - 记录和查询所有操作");
        
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
