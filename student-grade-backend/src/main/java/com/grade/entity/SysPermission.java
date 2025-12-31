package com.grade.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 权限实体
 * 
 * @author hx
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_permission")
public class SysPermission extends BaseEntity {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 权限编码
     */
    private String permissionCode;
    
    /**
     * 权限名称
     */
    private String permissionName;
    
    /**
     * 权限类型 (MENU-菜单, OPERATION-操作, RESOURCE-资源)
     */
    private String permissionType;
    
    /**
     * 描述
     */
    private String description;
}
