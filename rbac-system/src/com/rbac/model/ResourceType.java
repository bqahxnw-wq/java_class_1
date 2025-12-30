package com.rbac.model;

/**
 * 资源类型枚举
 */
public enum ResourceType {
    FILE("文件", "文件资源"),
    API("API接口", "API接口资源"),
    PAGE("页面", "页面资源"),
    MENU("菜单", "菜单资源"),
    BUTTON("按钮", "按钮资源"),
    DATA("数据", "数据资源");
    
    private final String description;
    private final String detail;
    
    ResourceType(String description, String detail) {
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
