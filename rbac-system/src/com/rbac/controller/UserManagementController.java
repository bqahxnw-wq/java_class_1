package com.rbac.controller;

import com.rbac.model.*;
import com.rbac.service.AuditService;
import com.rbac.util.Constants;
import com.rbac.util.DataStorage;
import com.rbac.util.InputValidator;
import com.rbac.view.DisplayView;
import com.rbac.view.MenuView;

import java.util.List;

/**
 * 用户管理控制器
 */
public class UserManagementController {
    
    private DataStorage storage = DataStorage.getInstance();
    private MenuView menuView = new MenuView();
    private DisplayView displayView = new DisplayView();
    private AuditService auditService = new AuditService();
    
    /**
     * 运行用户管理模块
     */
    public void run() {
        boolean running = true;
        
        while (running) {
            menuView.showUserManagementMenu();
            int choice = InputValidator.getIntInput("请选择操作 (0-7): ", 0, 7);
            
            switch (choice) {
                case 1:
                    viewAllUsers();
                    break;
                case 2:
                    queryUserInfo();
                    break;
                case 3:
                    createUser();
                    break;
                case 4:
                    updateUser();
                    break;
                case 5:
                    deleteUser();
                    break;
                case 6:
                    assignRoleToUser();
                    break;
                case 7:
                    viewUserPermissions();
                    break;
                case 0:
                    running = false;
                    break;
            }
        }
    }
    
    /**
     * 查看所有用户
     */
    private void viewAllUsers() {
        if (storage.getUserCount() == 0) {
            menuView.showError("系统中没有用户数据！");
            InputValidator.pause();
            return;
        }
        
        displayView.displayUserList(storage.getAllUsers());
        InputValidator.pause();
    }
    
    /**
     * 查询用户信息
     */
    private void queryUserInfo() {
        if (storage.getUserCount() == 0) {
            menuView.showError("系统中没有用户数据！");
            InputValidator.pause();
            return;
        }
        
        String username = InputValidator.getNonEmptyString("请输入用户名: ");
        User user = storage.getUserByUsername(username);
        
        if (user == null) {
            menuView.showError("未找到该用户！");
        } else {
            displayView.displayUserDetail(user);
        }
        
        InputValidator.pause();
    }
    
    /**
     * 创建新用户
     */
    private void createUser() {
        System.out.println("\n=== 创建新用户 ===");
        
        String username = InputValidator.getNonEmptyString("用户名: ");
        if (storage.getUserByUsername(username) != null) {
            menuView.showError("用户名已存在！");
            InputValidator.pause();
            return;
        }
        
        String password = InputValidator.getNonEmptyString("密码: ");
        String realName = InputValidator.getNonEmptyString("真实姓名: ");
        String email = InputValidator.getStringInput("邮箱 (可选): ");
        String phone = InputValidator.getStringInput("电话 (可选): ");
        
        // 生成用户ID
        String userId = String.format("U%03d", storage.getUserCount() + 1);
        
        User user = new User(userId, username, password, realName);
        if (!email.isEmpty()) {
            user.setEmail(email);
        }
        if (!phone.isEmpty()) {
            user.setPhone(phone);
        }
        
        storage.addUser(user);
        
        // 记录审计日志
        auditService.logUserOperation(user, "创建用户", "用户[" + username + "]", 
                "创建新用户", true);
        
        menuView.showSuccess("用户创建成功！");
        InputValidator.pause();
    }
    
    /**
     * 修改用户信息
     */
    private void updateUser() {
        if (storage.getUserCount() == 0) {
            menuView.showError("系统中没有用户数据！");
            InputValidator.pause();
            return;
        }
        
        String username = InputValidator.getNonEmptyString("请输入要修改的用户名: ");
        User user = storage.getUserByUsername(username);
        
        if (user == null) {
            menuView.showError("未找到该用户！");
            InputValidator.pause();
            return;
        }
        
        System.out.println("\n当前用户信息:");
        displayView.displayUserDetail(user);
        
        System.out.println("\n请输入新信息（留空表示不修改）:");
        String realName = InputValidator.getStringInput("真实姓名: ");
        String email = InputValidator.getStringInput("邮箱: ");
        String phone = InputValidator.getStringInput("电话: ");
        
        if (!realName.isEmpty()) {
            user.setRealName(realName);
        }
        if (!email.isEmpty()) {
            user.setEmail(email);
        }
        if (!phone.isEmpty()) {
            user.setPhone(phone);
        }
        
        // 记录审计日志
        auditService.logUserOperation(user, "修改用户", "用户[" + username + "]", 
                "修改用户信息", true);
        
        menuView.showSuccess("用户信息修改成功！");
        InputValidator.pause();
    }
    
    /**
     * 删除用户
     */
    private void deleteUser() {
        if (storage.getUserCount() == 0) {
            menuView.showError("系统中没有用户数据！");
            InputValidator.pause();
            return;
        }
        
        String username = InputValidator.getNonEmptyString("请输入要删除的用户名: ");
        User user = storage.getUserByUsername(username);
        
        if (user == null) {
            menuView.showError("未找到该用户！");
            InputValidator.pause();
            return;
        }
        
        // 不能删除admin用户
        if (user.getUsername().equals("admin")) {
            menuView.showError("不能删除系统管理员账号！");
            InputValidator.pause();
            return;
        }
        
        boolean confirm = InputValidator.getConfirmation(
                "确定要删除用户 [" + username + "] 吗？");
        
        if (confirm) {
            storage.removeUser(user);
            
            // 记录审计日志
            auditService.logUserOperation(user, "删除用户", "用户[" + username + "]", 
                    "删除用户", true);
            
            menuView.showSuccess("用户删除成功！");
        } else {
            menuView.showInfo("操作已取消");
        }
        
        InputValidator.pause();
    }
    
    /**
     * 为用户分配角色
     */
    private void assignRoleToUser() {
        if (storage.getUserCount() == 0 || storage.getRoleCount() == 0) {
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
        
        // 显示可用角色
        System.out.println("\n可用角色列表:");
        displayView.displayRoleList(storage.getAllRoles());
        
        String roleCode = InputValidator.getNonEmptyString("\n请输入角色编码: ");
        Role role = storage.getRoleByCode(roleCode);
        
        if (role == null) {
            menuView.showError("未找到该角色！");
            InputValidator.pause();
            return;
        }
        
        if (user.getRoles().contains(role)) {
            menuView.showWarning("该用户已拥有此角色！");
            InputValidator.pause();
            return;
        }
        
        user.assignRole(role);
        
        // 记录审计日志（这里简化处理，实际应该有当前操作用户）
        auditService.logUserOperation(user, "分配角色", 
                "用户[" + username + "]", 
                "分配角色[" + role.getRoleName() + "]", true);
        
        menuView.showSuccess("角色分配成功！");
        InputValidator.pause();
    }
    
    /**
     * 查看用户权限
     */
    private void viewUserPermissions() {
        if (storage.getUserCount() == 0) {
            menuView.showError("系统中没有用户数据！");
            InputValidator.pause();
            return;
        }
        
        String username = InputValidator.getNonEmptyString("请输入用户名: ");
        User user = storage.getUserByUsername(username);
        
        if (user == null) {
            menuView.showError("未找到该用户！");
        } else {
            displayView.displayUserDetail(user);
        }
        
        InputValidator.pause();
    }
}
