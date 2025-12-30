package com.rbac.controller;

import com.rbac.model.*;
import com.rbac.service.AuditService;
import com.rbac.util.DataStorage;
import com.rbac.util.InputValidator;
import com.rbac.view.DisplayView;
import com.rbac.view.MenuView;

import java.util.List;

/**
 * 角色管理控制器
 */
public class RoleManagementController {
    
    private DataStorage storage = DataStorage.getInstance();
    private MenuView menuView = new MenuView();
    private DisplayView displayView = new DisplayView();
    private AuditService auditService = new AuditService();
    
    /**
     * 运行角色管理模块
     */
    public void run() {
        boolean running = true;
        
        while (running) {
            menuView.showRoleManagementMenu();
            int choice = InputValidator.getIntInput("请选择操作 (0-7): ", 0, 7);
            
            switch (choice) {
                case 1:
                    viewAllRoles();
                    break;
                case 2:
                    queryRoleInfo();
                    break;
                case 3:
                    createRole();
                    break;
                case 4:
                    updateRole();
                    break;
                case 5:
                    deleteRole();
                    break;
                case 6:
                    assignPermissionToRole();
                    break;
                case 7:
                    viewRoleUsers();
                    break;
                case 0:
                    running = false;
                    break;
            }
        }
    }
    
    /**
     * 查看所有角色
     */
    private void viewAllRoles() {
        if (storage.getRoleCount() == 0) {
            menuView.showError("系统中没有角色数据！");
            InputValidator.pause();
            return;
        }
        
        displayView.displayRoleList(storage.getAllRoles());
        InputValidator.pause();
    }
    
    /**
     * 查询角色信息
     */
    private void queryRoleInfo() {
        if (storage.getRoleCount() == 0) {
            menuView.showError("系统中没有角色数据！");
            InputValidator.pause();
            return;
        }
        
        String roleCode = InputValidator.getNonEmptyString("请输入角色编码: ");
        Role role = storage.getRoleByCode(roleCode);
        
        if (role == null) {
            menuView.showError("未找到该角色！");
        } else {
            displayView.displayRoleDetail(role);
        }
        
        InputValidator.pause();
    }
    
    /**
     * 创建新角色
     */
    private void createRole() {
        System.out.println("\n=== 创建新角色 ===");
        
        String roleCode = InputValidator.getNonEmptyString("角色编码: ");
        if (storage.getRoleByCode(roleCode) != null) {
            menuView.showError("角色编码已存在！");
            InputValidator.pause();
            return;
        }
        
        String roleName = InputValidator.getNonEmptyString("角色名称: ");
        String description = InputValidator.getStringInput("角色描述: ");
        
        // 生成角色ID
        String roleId = String.format("R%03d", storage.getRoleCount() + 1);
        
        Role role = new Role(roleId, roleCode, roleName, description, RoleType.CUSTOM);
        storage.addRole(role);
        
        // 记录审计日志（这里简化处理）
        User admin = storage.getUserByUsername("admin");
        if (admin != null) {
            auditService.logUserOperation(admin, "创建角色", "角色[" + roleCode + "]", 
                    "创建新角色", true);
        }
        
        menuView.showSuccess("角色创建成功！");
        InputValidator.pause();
    }
    
    /**
     * 修改角色信息
     */
    private void updateRole() {
        if (storage.getRoleCount() == 0) {
            menuView.showError("系统中没有角色数据！");
            InputValidator.pause();
            return;
        }
        
        String roleCode = InputValidator.getNonEmptyString("请输入要修改的角色编码: ");
        Role role = storage.getRoleByCode(roleCode);
        
        if (role == null) {
            menuView.showError("未找到该角色！");
            InputValidator.pause();
            return;
        }
        
        // 系统角色不能修改
        if (role.getRoleType() == RoleType.SYSTEM) {
            menuView.showError("系统角色不能修改！");
            InputValidator.pause();
            return;
        }
        
        System.out.println("\n当前角色信息:");
        displayView.displayRoleDetail(role);
        
        System.out.println("\n请输入新信息（留空表示不修改）:");
        String roleName = InputValidator.getStringInput("角色名称: ");
        String description = InputValidator.getStringInput("角色描述: ");
        
        if (!roleName.isEmpty()) {
            role.setRoleName(roleName);
        }
        if (!description.isEmpty()) {
            role.setDescription(description);
        }
        
        // 记录审计日志
        User admin = storage.getUserByUsername("admin");
        if (admin != null) {
            auditService.logUserOperation(admin, "修改角色", "角色[" + roleCode + "]", 
                    "修改角色信息", true);
        }
        
        menuView.showSuccess("角色信息修改成功！");
        InputValidator.pause();
    }
    
    /**
     * 删除角色
     */
    private void deleteRole() {
        if (storage.getRoleCount() == 0) {
            menuView.showError("系统中没有角色数据！");
            InputValidator.pause();
            return;
        }
        
        String roleCode = InputValidator.getNonEmptyString("请输入要删除的角色编码: ");
        Role role = storage.getRoleByCode(roleCode);
        
        if (role == null) {
            menuView.showError("未找到该角色！");
            InputValidator.pause();
            return;
        }
        
        // 系统角色不能删除
        if (role.getRoleType() == RoleType.SYSTEM) {
            menuView.showError("系统角色不能删除！");
            InputValidator.pause();
            return;
        }
        
        boolean confirm = InputValidator.getConfirmation(
                "确定要删除角色 [" + role.getRoleName() + "] 吗？");
        
        if (confirm) {
            // 从所有用户中移除该角色
            for (User user : storage.getAllUsers()) {
                user.removeRole(role);
            }
            
            storage.removeRole(role);
            
            // 记录审计日志
            User admin = storage.getUserByUsername("admin");
            if (admin != null) {
                auditService.logUserOperation(admin, "删除角色", "角色[" + roleCode + "]", 
                        "删除角色", true);
            }
            
            menuView.showSuccess("角色删除成功！");
        } else {
            menuView.showInfo("操作已取消");
        }
        
        InputValidator.pause();
    }
    
    /**
     * 为角色分配权限
     */
    private void assignPermissionToRole() {
        if (storage.getRoleCount() == 0 || storage.getPermissionCount() == 0) {
            menuView.showError("请先生成初始化数据！");
            InputValidator.pause();
            return;
        }
        
        String roleCode = InputValidator.getNonEmptyString("请输入角色编码: ");
        Role role = storage.getRoleByCode(roleCode);
        
        if (role == null) {
            menuView.showError("未找到该角色！");
            InputValidator.pause();
            return;
        }
        
        // 显示可用权限
        System.out.println("\n可用权限列表:");
        displayView.displayPermissionList(storage.getAllPermissions());
        
        String permCode = InputValidator.getNonEmptyString("\n请输入权限编码: ");
        Permission permission = storage.getPermissionByCode(permCode);
        
        if (permission == null) {
            menuView.showError("未找到该权限！");
            InputValidator.pause();
            return;
        }
        
        if (role.getPermissions().contains(permission)) {
            menuView.showWarning("该角色已拥有此权限！");
            InputValidator.pause();
            return;
        }
        
        role.assignPermission(permission);
        
        // 记录审计日志
        User admin = storage.getUserByUsername("admin");
        if (admin != null) {
            auditService.logUserOperation(admin, "分配权限", 
                    "角色[" + role.getRoleName() + "]", 
                    "分配权限[" + permission.getPermissionName() + "]", true);
        }
        
        menuView.showSuccess("权限分配成功！");
        InputValidator.pause();
    }
    
    /**
     * 查看角色的用户
     */
    private void viewRoleUsers() {
        if (storage.getRoleCount() == 0) {
            menuView.showError("系统中没有角色数据！");
            InputValidator.pause();
            return;
        }
        
        String roleCode = InputValidator.getNonEmptyString("请输入角色编码: ");
        Role role = storage.getRoleByCode(roleCode);
        
        if (role == null) {
            menuView.showError("未找到该角色！");
            InputValidator.pause();
            return;
        }
        
        List<User> users = storage.getUsersByRole(role);
        
        System.out.println("\n角色 [" + role.getRoleName() + "] 的用户列表:");
        if (users.isEmpty()) {
            System.out.println("暂无用户");
        } else {
            displayView.displayUserList(users);
        }
        
        InputValidator.pause();
    }
}
