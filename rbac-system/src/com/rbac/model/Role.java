package com.rbac.model;

import java.time.LocalDateTime;
import java.util.*;

/**
 * 角色实体类
 */
public class Role {
    private String roleId;              // 角色ID
    private String roleCode;            // 角色编码（唯一标识）
    private String roleName;            // 角色名称
    private String description;         // 角色描述
    private RoleType roleType;          // 角色类型
    private LocalDateTime createTime;   // 创建时间
    private Set<Permission> permissions; // 角色拥有的权限集合
    
    public Role(String roleId, String roleCode, String roleName, String description) {
        this.roleId = roleId;
        this.roleCode = roleCode;
        this.roleName = roleName;
        this.description = description;
        this.roleType = RoleType.CUSTOM;
        this.createTime = LocalDateTime.now();
        this.permissions = new HashSet<>();
    }
    
    public Role(String roleId, String roleCode, String roleName, String description, RoleType roleType) {
        this.roleId = roleId;
        this.roleCode = roleCode;
        this.roleName = roleName;
        this.description = description;
        this.roleType = roleType;
        this.createTime = LocalDateTime.now();
        this.permissions = new HashSet<>();
    }
    
    // Getter和Setter方法
    public String getRoleId() {
        return roleId;
    }
    
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
    
    public String getRoleCode() {
        return roleCode;
    }
    
    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
    
    public String getRoleName() {
        return roleName;
    }
    
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public RoleType getRoleType() {
        return roleType;
    }
    
    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }
    
    public LocalDateTime getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    
    public Set<Permission> getPermissions() {
        return permissions;
    }
    
    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }
    
    /**
     * 为角色分配权限
     */
    public void assignPermission(Permission permission) {
        permissions.add(permission);
    }
    
    /**
     * 移除角色的权限
     */
    public void removePermission(Permission permission) {
        permissions.remove(permission);
    }
    
    /**
     * 检查角色是否拥有某个权限
     */
    public boolean hasPermission(String permissionCode) {
        return permissions.stream()
                .anyMatch(p -> p.getPermissionCode().equals(permissionCode));
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(roleId, role.roleId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(roleId);
    }
    
    @Override
    public String toString() {
        return String.format("角色ID: %s, 角色编码: %s, 角色名称: %s, 类型: %s", 
                roleId, roleCode, roleName, roleType.getDescription());
    }
}
