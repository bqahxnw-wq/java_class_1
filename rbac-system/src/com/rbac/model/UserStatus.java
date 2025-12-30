package com.rbac.model;

/**
 * 用户状态枚举
 */
public enum UserStatus {
    ACTIVE("正常", "用户账号正常可用"),
    LOCKED("锁定", "用户账号被锁定"),
    DISABLED("禁用", "用户账号被禁用"),
    EXPIRED("过期", "用户账号已过期");
    
    private final String description;
    private final String detail;
    
    UserStatus(String description, String detail) {
        this.description = description;
        this.detail = detail;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getDetail() {
        return detail;
    }
}
