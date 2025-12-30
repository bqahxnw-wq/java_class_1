package com.rbac.model;

/**
 * 角色类型枚举
 */
public enum RoleType {
    SYSTEM("系统角色", "系统预定义的角色，不可删除"),
    CUSTOM("自定义角色", "用户自定义创建的角色");
    
    private final String description;
    private final String detail;
    
    RoleType(String description, String detail) {
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
