package com.rbac.util;

import com.rbac.model.*;
import java.util.*;

/**
 * 数据存储类（单例模式）
 */
public class DataStorage {
    
    // 单例实例
    private static DataStorage instance;
    
    // 数据存储
    private List<User> users;
    private List<Role> roles;
    private List<Permission> permissions;
    private List<Resource> resources;
    private List<AuditLog> auditLogs;
    
    // 索引（用于快速查询）
    private Map<String, User> userIdIndex;
    private Map<String, User> usernameIndex;
    private Map<String, Role> roleIdIndex;
    private Map<String, Role> roleCodeIndex;
    private Map<String, Permission> permissionIdIndex;
    private Map<String, Permission> permissionCodeIndex;
    private Map<String, Resource> resourceIdIndex;
    private Map<String, Resource> resourceCodeIndex;
    
    /**
     * 私有构造函数
     */
    private DataStorage() {
        users = new ArrayList<>();
        roles = new ArrayList<>();
        permissions = new ArrayList<>();
        resources = new ArrayList<>();
        auditLogs = new ArrayList<>();
        
        userIdIndex = new HashMap<>();
        usernameIndex = new HashMap<>();
        roleIdIndex = new HashMap<>();
        roleCodeIndex = new HashMap<>();
        permissionIdIndex = new HashMap<>();
        permissionCodeIndex = new HashMap<>();
        resourceIdIndex = new HashMap<>();
        resourceCodeIndex = new HashMap<>();
    }
    
    /**
     * 获取单例实例
     */
    public static synchronized DataStorage getInstance() {
        if (instance == null) {
            instance = new DataStorage();
        }
        return instance;
    }
    
    /**
     * 重置所有数据（用于重新初始化）
     */
    public void reset() {
        users.clear();
        roles.clear();
        permissions.clear();
        resources.clear();
        auditLogs.clear();
        
        userIdIndex.clear();
        usernameIndex.clear();
        roleIdIndex.clear();
        roleCodeIndex.clear();
        permissionIdIndex.clear();
        permissionCodeIndex.clear();
        resourceIdIndex.clear();
        resourceCodeIndex.clear();
    }
    
    // ==================== 用户相关方法 ====================
    
    public void addUser(User user) {
        users.add(user);
        userIdIndex.put(user.getUserId(), user);
        usernameIndex.put(user.getUsername(), user);
    }
    
    public void removeUser(User user) {
        users.remove(user);
        userIdIndex.remove(user.getUserId());
        usernameIndex.remove(user.getUsername());
    }
    
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }
    
    public User getUserById(String userId) {
        return userIdIndex.get(userId);
    }
    
    public User getUserByUsername(String username) {
        return usernameIndex.get(username);
    }
    
    public List<User> getUsersByRole(Role role) {
        List<User> result = new ArrayList<>();
        for (User user : users) {
            if (user.getRoles().contains(role)) {
                result.add(user);
            }
        }
        return result;
    }
    
    // ==================== 角色相关方法 ====================
    
    public void addRole(Role role) {
        roles.add(role);
        roleIdIndex.put(role.getRoleId(), role);
        roleCodeIndex.put(role.getRoleCode(), role);
    }
    
    public void removeRole(Role role) {
        roles.remove(role);
        roleIdIndex.remove(role.getRoleId());
        roleCodeIndex.remove(role.getRoleCode());
    }
    
    public List<Role> getAllRoles() {
        return new ArrayList<>(roles);
    }
    
    public Role getRoleById(String roleId) {
        return roleIdIndex.get(roleId);
    }
    
    public Role getRoleByCode(String roleCode) {
        return roleCodeIndex.get(roleCode);
    }
    
    // ==================== 权限相关方法 ====================
    
    public void addPermission(Permission permission) {
        permissions.add(permission);
        permissionIdIndex.put(permission.getPermissionId(), permission);
        permissionCodeIndex.put(permission.getPermissionCode(), permission);
    }
    
    public void removePermission(Permission permission) {
        permissions.remove(permission);
        permissionIdIndex.remove(permission.getPermissionId());
        permissionCodeIndex.remove(permission.getPermissionCode());
    }
    
    public List<Permission> getAllPermissions() {
        return new ArrayList<>(permissions);
    }
    
    public Permission getPermissionById(String permissionId) {
        return permissionIdIndex.get(permissionId);
    }
    
    public Permission getPermissionByCode(String permissionCode) {
        return permissionCodeIndex.get(permissionCode);
    }
    
    public List<Permission> getPermissionsByType(PermissionType type) {
        List<Permission> result = new ArrayList<>();
        for (Permission permission : permissions) {
            if (permission.getPermissionType() == type) {
                result.add(permission);
            }
        }
        return result;
    }
    
    // ==================== 资源相关方法 ====================
    
    public void addResource(Resource resource) {
        resources.add(resource);
        resourceIdIndex.put(resource.getResourceId(), resource);
        resourceCodeIndex.put(resource.getResourceCode(), resource);
    }
    
    public void removeResource(Resource resource) {
        resources.remove(resource);
        resourceIdIndex.remove(resource.getResourceId());
        resourceCodeIndex.remove(resource.getResourceCode());
    }
    
    public List<Resource> getAllResources() {
        return new ArrayList<>(resources);
    }
    
    public Resource getResourceById(String resourceId) {
        return resourceIdIndex.get(resourceId);
    }
    
    public Resource getResourceByCode(String resourceCode) {
        return resourceCodeIndex.get(resourceCode);
    }
    
    // ==================== 审计日志相关方法 ====================
    
    public void addAuditLog(AuditLog log) {
        auditLogs.add(log);
    }
    
    public List<AuditLog> getAllAuditLogs() {
        return new ArrayList<>(auditLogs);
    }
    
    public List<AuditLog> getAuditLogsByUser(String userId) {
        List<AuditLog> result = new ArrayList<>();
        for (AuditLog log : auditLogs) {
            if (log.getUserId().equals(userId)) {
                result.add(log);
            }
        }
        return result;
    }
    
    // ==================== 统计方法 ====================
    
    public int getUserCount() {
        return users.size();
    }
    
    public int getRoleCount() {
        return roles.size();
    }
    
    public int getPermissionCount() {
        return permissions.size();
    }
    
    public int getResourceCount() {
        return resources.size();
    }
    
    public int getAuditLogCount() {
        return auditLogs.size();
    }
}
