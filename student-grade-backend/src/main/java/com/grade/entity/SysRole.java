package com.grade.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色实体
 * 
 * @author hx
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_role")
public class SysRole extends BaseEntity {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 角色编码
     */
    private String roleCode;
    
    /**
     * 角色名称
     */
    private String roleName;
    
    /**
     * 描述
     */
    private String description;
    
    /**
     * 是否系统角色 (0-否, 1-是)
     */
    private Integer isSystem;
    
    /**
     * 状态 (0-禁用, 1-正常)
     */
    private Integer status;
}
