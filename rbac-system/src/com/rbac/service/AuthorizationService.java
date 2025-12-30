package com.rbac.service;

import com.rbac.model.*;
import com.rbac.util.DataStorage;

/**
 * 授权服务类
 */
public class AuthorizationService {
    
    private DataStorage storage = DataStorage.getInstance();
    private AuditService auditService = new AuditService();
    
    /**
     * 检查用户是否有权限执行操作
     */
    public boolean checkPermission(User user, String permissionCode) {
        if (user == null || permissionCode == null) {
            return false;
        }
        
        // 检查用户状态
        if (user.getStatus() != UserStatus.ACTIVE) {
            auditService.logPermissionCheck(user, permissionCode, false, "用户状态不可用");
            return false;
        }
        
        // 检查用户是否拥有该权限
        boolean hasPermission = user.hasPermission(permissionCode);
        
        // 记录权限校验日志
        auditService.logPermissionCheck(user, permissionCode, hasPermission, 
                hasPermission ? "权限校验通过" : "权限不足");
        
        return hasPermission;
    }
    
    /**
     * 检查用户是否有权限执行操作（带详细提示）
     */
    public boolean checkPermissionWithMessage(User user, String permissionCode, String operationDesc) {
        boolean hasPermission = checkPermission(user, permissionCode);
        
        if (!hasPermission) {
            System.out.println("\n[权限不足] 您没有权限执行此操作: " + operationDesc);
            System.out.println("需要权限: " + permissionCode);
        }
        
        return hasPermission;
    }
    
    /**
     * 检查用户是否拥有某个角色
     */
    public boolean checkRole(User user, String roleCode) {
        if (user == null || roleCode == null) {
            return false;
        }
        
        return user.hasRole(roleCode);
    }
    
    /**
     * 检查用户是否是管理员
     */
    public boolean isAdmin(User user) {
        return checkRole(user, com.rbac.util.Constants.ROLE_ADMIN);
    }
    
    /**
     * 检查用户是否可以访问资源
     */
    public boolean checkResourceAccess(User user, String resourceCode) {
        if (user == null || resourceCode == null) {
            return false;
        }
        
        Resource resource = storage.getResourceByCode(resourceCode);
        if (resource == null) {
            return false;
        }
        
        // 简化处理：根据资源类型判断需要的权限
        String requiredPermission = getRequiredPermissionForResource(resource);
        
        if (requiredPermission != null) {
            return checkPermission(user, requiredPermission);
        }
        
        return false;
    }
    
    /**
     * 根据资源类型获取所需权限
     */
    private String getRequiredPermissionForResource(Resource resource) {
        // 这里简化处理，实际应该有更复杂的映射关系
        switch (resource.getResourceType()) {
            case API:
            case PAGE:
                if (resource.getResourceCode().contains("USER")) {
                    return com.rbac.util.Constants.PERM_USER_READ;
                } else if (resource.getResourceCode().contains("ROLE")) {
                    return com.rbac.util.Constants.PERM_ROLE_READ;
                }
                break;
            case FILE:
                return com.rbac.util.Constants.PERM_SYSTEM_CONFIG;
            case MENU:
                return "MENU_" + resource.getResourceCode();
            default:
                break;
        }
        return null;
    }
    
    /**
     * 为用户分配角色（需要权限）
     */
    public boolean assignRoleToUser(User operator, User targetUser, Role role) {
        // 检查操作者权限
        if (!checkPermission(operator, com.rbac.util.Constants.PERM_USER_ASSIGN_ROLE)) {
            System.out.println("\n[权限不足] 您没有权限分配角色");
            return false;
        }
        
        // 执行分配
        targetUser.assignRole(role);
        
        // 记录审计日志
        auditService.logUserOperation(operator, "分配角色", 
                "用户[" + targetUser.getUsername() + "]", 
                "分配角色[" + role.getRoleName() + "]", true);
        
        return true;
    }
    
    /**
     * 为角色分配权限（需要权限）
     */
    public boolean assignPermissionToRole(User operator, Role role, Permission permission) {
        // 检查操作者权限
        if (!checkPermission(operator, com.rbac.util.Constants.PERM_ROLE_ASSIGN_PERM)) {
            System.out.println("\n[权限不足] 您没有权限分配权限");
            return false;
        }
        
        // 执行分配
        role.assignPermission(permission);
        
        // 记录审计日志
        auditService.logUserOperation(operator, "分配权限", 
                "角色[" + role.getRoleName() + "]", 
                "分配权限[" + permission.getPermissionName() + "]", true);
        
        return true;
    }
}
