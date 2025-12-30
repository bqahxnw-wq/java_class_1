package com.rbac.model;

/**
 * 权限类型枚举
 */
public enum PermissionType {
    MENU("菜单权限", "控制菜单的访问"),
    OPERATION("操作权限", "控制具体操作的执行"),
    RESOURCE("资源权限", "控制资源的访问"),
    DATA("数据权限", "控制数据范围的访问");
    
    private final String description;
    private final String detail;
    
    PermissionType(String description, String detail) {
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
