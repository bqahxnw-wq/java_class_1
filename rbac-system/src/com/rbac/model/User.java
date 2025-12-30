package com.rbac.model;

import java.time.LocalDateTime;
import java.util.*;

/**
 * 用户实体类
 */
public class User {
    private String userId;              // 用户ID
    private String username;            // 用户名（登录名）
    private String password;            // 密码
    private String realName;            // 真实姓名
    private String email;               // 邮箱
    private String phone;               // 电话
    private UserStatus status;          // 用户状态
    private LocalDateTime createTime;   // 创建时间
    private LocalDateTime lastLoginTime; // 最后登录时间
    private Set<Role> roles;            // 用户拥有的角色集合
    
    public User(String userId, String username, String password, String realName) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.realName = realName;
        this.status = UserStatus.ACTIVE;
        this.createTime = LocalDateTime.now();
        this.roles = new HashSet<>();
    }
    
    // Getter和Setter方法
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getRealName() {
        return realName;
    }
    
    public void setRealName(String realName) {
        this.realName = realName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public UserStatus getStatus() {
        return status;
    }
    
    public void setStatus(UserStatus status) {
        this.status = status;
    }
    
    public LocalDateTime getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    
    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }
    
    public void setLastLoginTime(LocalDateTime lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
    
    public Set<Role> getRoles() {
        return roles;
    }
    
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    
    /**
     * 为用户分配角色
     */
    public void assignRole(Role role) {
        roles.add(role);
    }
    
    /**
     * 移除用户的角色
     */
    public void removeRole(Role role) {
        roles.remove(role);
    }
    
    /**
     * 检查用户是否拥有某个角色
     */
    public boolean hasRole(String roleCode) {
        return roles.stream().anyMatch(r -> r.getRoleCode().equals(roleCode));
    }
    
    /**
     * 获取用户的所有权限（通过角色）
     */
    public Set<Permission> getAllPermissions() {
        Set<Permission> permissions = new HashSet<>();
        for (Role role : roles) {
            permissions.addAll(role.getPermissions());
        }
        return permissions;
    }
    
    /**
     * 检查用户是否拥有某个权限
     */
    public boolean hasPermission(String permissionCode) {
        return getAllPermissions().stream()
                .anyMatch(p -> p.getPermissionCode().equals(permissionCode));
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
    
    @Override
    public String toString() {
        return String.format("用户ID: %s, 用户名: %s, 真实姓名: %s, 状态: %s", 
                userId, username, realName, status.getDescription());
    }
}
