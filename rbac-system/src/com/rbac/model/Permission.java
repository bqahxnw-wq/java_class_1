package com.rbac.model;

import java.time.LocalDateTime;
import java.util.*;

/**
 * 权限实体类
 */
public class Permission {
    private String permissionId;        // 权限ID
    private String permissionCode;      // 权限编码（唯一标识）
    private String permissionName;      // 权限名称
    private String description;         // 权限描述
    private PermissionType permissionType; // 权限类型
    private String resourcePattern;     // 资源模式（如：/user/*, /admin/*）
    private LocalDateTime createTime;   // 创建时间
    
    public Permission(String permissionId, String permissionCode, String permissionName, 
                     String description, PermissionType permissionType) {
        this.permissionId = permissionId;
        this.permissionCode = permissionCode;
        this.permissionName = permissionName;
        this.description = description;
        this.permissionType = permissionType;
        this.createTime = LocalDateTime.now();
    }
    
    // Getter和Setter方法
    public String getPermissionId() {
        return permissionId;
    }
    
    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }
    
    public String getPermissionCode() {
        return permissionCode;
    }
    
    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }
    
    public String getPermissionName() {
        return permissionName;
    }
    
    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public PermissionType getPermissionType() {
        return permissionType;
    }
    
    public void setPermissionType(PermissionType permissionType) {
        this.permissionType = permissionType;
    }
    
    public String getResourcePattern() {
        return resourcePattern;
    }
    
    public void setResourcePattern(String resourcePattern) {
        this.resourcePattern = resourcePattern;
    }
    
    public LocalDateTime getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permission that = (Permission) o;
        return Objects.equals(permissionId, that.permissionId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(permissionId);
    }
    
    @Override
    public String toString() {
        return String.format("权限ID: %s, 权限编码: %s, 权限名称: %s, 类型: %s", 
                permissionId, permissionCode, permissionName, permissionType.getDescription());
    }
}
