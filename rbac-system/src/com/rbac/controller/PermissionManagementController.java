package com.rbac.controller;

import com.rbac.model.*;
import com.rbac.util.DataStorage;
import com.rbac.util.InputValidator;
import com.rbac.view.DisplayView;
import com.rbac.view.MenuView;

import java.util.List;

/**
 * 权限管理控制器
 */
public class PermissionManagementController {
    
    private DataStorage storage = DataStorage.getInstance();
    private MenuView menuView = new MenuView();
    private DisplayView displayView = new DisplayView();
    
    /**
     * 运行权限管理模块
     */
    public void run() {
        boolean running = true;
        
        while (running) {
            menuView.showPermissionManagementMenu();
            int choice = InputValidator.getIntInput("请选择操作 (0-4): ", 0, 4);
            
            switch (choice) {
                case 1:
                    viewAllPermissions();
                    break;
                case 2:
                    viewPermissionsByType();
                    break;
                case 3:
                    viewAllResources();
                    break;
                case 4:
                    queryPermissionDetail();
                    break;
                case 0:
                    running = false;
                    break;
            }
        }
    }
    
    /**
     * 查看所有权限
     */
    private void viewAllPermissions() {
        if (storage.getPermissionCount() == 0) {
            menuView.showError("系统中没有权限数据！");
            InputValidator.pause();
            return;
        }
        
        displayView.displayPermissionList(storage.getAllPermissions());
        InputValidator.pause();
    }
    
    /**
     * 按类型查看权限
     */
    private void viewPermissionsByType() {
        if (storage.getPermissionCount() == 0) {
            menuView.showError("系统中没有权限数据！");
            InputValidator.pause();
            return;
        }
        
        System.out.println("\n权限类型:");
        System.out.println("1. 菜单权限");
        System.out.println("2. 操作权限");
        System.out.println("3. 资源权限");
        System.out.println("4. 数据权限");
        
        int choice = InputValidator.getIntInput("请选择权限类型 (1-4): ", 1, 4);
        
        PermissionType type = null;
        switch (choice) {
            case 1:
                type = PermissionType.MENU;
                break;
            case 2:
                type = PermissionType.OPERATION;
                break;
            case 3:
                type = PermissionType.RESOURCE;
                break;
            case 4:
                type = PermissionType.DATA;
                break;
        }
        
        List<Permission> permissions = storage.getPermissionsByType(type);
        
        System.out.println("\n" + type.getDescription() + " 列表:");
        if (permissions.isEmpty()) {
            System.out.println("暂无此类型权限");
        } else {
            displayView.displayPermissionList(permissions);
        }
        
        InputValidator.pause();
    }
    
    /**
     * 查看所有资源
     */
    private void viewAllResources() {
        if (storage.getResourceCount() == 0) {
            menuView.showError("系统中没有资源数据！");
            InputValidator.pause();
            return;
        }
        
        displayView.displayResourceList(storage.getAllResources());
        InputValidator.pause();
    }
    
    /**
     * 查询权限详情
     */
    private void queryPermissionDetail() {
        if (storage.getPermissionCount() == 0) {
            menuView.showError("系统中没有权限数据！");
            InputValidator.pause();
            return;
        }
        
        String permCode = InputValidator.getNonEmptyString("请输入权限编码: ");
        Permission permission = storage.getPermissionByCode(permCode);
        
        if (permission == null) {
            menuView.showError("未找到该权限！");
        } else {
            System.out.println("\n" + "=".repeat(60));
            System.out.println("权限详细信息:");
            System.out.println("=".repeat(60));
            System.out.println("权限ID: " + permission.getPermissionId());
            System.out.println("权限编码: " + permission.getPermissionCode());
            System.out.println("权限名称: " + permission.getPermissionName());
            System.out.println("权限类型: " + permission.getPermissionType().getDescription());
            System.out.println("描述: " + (permission.getDescription() != null ? permission.getDescription() : "-"));
            System.out.println("=".repeat(60));
        }
        
        InputValidator.pause();
    }
}
