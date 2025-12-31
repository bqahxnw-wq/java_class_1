package com.grade.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户实体
 * 
 * @author hx
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
public class SysUser extends BaseEntity {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 密码(加密)
     */
    @JsonIgnore
    private String password;
    
    /**
     * 真实姓名
     */
    private String realName;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 手机号
     */
    private String phone;
    
    /**
     * 性别 (0-未知, 1-男, 2-女)
     */
    private Integer gender;
    
    /**
     * 状态 (0-禁用, 1-正常)
     */
    private Integer status;
    
    /**
     * 用户类型 (ADMIN-管理员, TEACHER-教师, STUDENT-学生)
     */
    private String userType;
    
    /**
     * 角色ID列表(非数据库字段)
     */
    @TableField(exist = false)
    private java.util.List<Long> roleIds;
}
