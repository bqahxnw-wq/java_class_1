package com.rbac.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 资源实体类
 */
public class Resource {
    private String resourceId;          // 资源ID
    private String resourceCode;        // 资源编码
    private String resourceName;        // 资源名称
    private String resourcePath;        // 资源路径
    private ResourceType resourceType;  // 资源类型
    private String description;         // 资源描述
    private LocalDateTime createTime;   // 创建时间
    
    public Resource(String resourceId, String resourceCode, String resourceName, 
                   String resourcePath, ResourceType resourceType) {
        this.resourceId = resourceId;
        this.resourceCode = resourceCode;
        this.resourceName = resourceName;
        this.resourcePath = resourcePath;
        this.resourceType = resourceType;
        this.createTime = LocalDateTime.now();
    }
    
    // Getter和Setter方法
    public String getResourceId() {
        return resourceId;
    }
    
    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }
    
    public String getResourceCode() {
        return resourceCode;
    }
    
    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }
    
    public String getResourceName() {
        return resourceName;
    }
    
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }
    
    public String getResourcePath() {
        return resourcePath;
    }
    
    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }
    
    public ResourceType getResourceType() {
        return resourceType;
    }
    
    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
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
        Resource resource = (Resource) o;
        return Objects.equals(resourceId, resource.resourceId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(resourceId);
    }
    
    @Override
    public String toString() {
        return String.format("资源ID: %s, 资源编码: %s, 资源名称: %s, 类型: %s", 
                resourceId, resourceCode, resourceName, resourceType.getDescription());
    }
}
