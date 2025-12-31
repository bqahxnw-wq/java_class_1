package com.grade.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 教师实体
 * 
 * @author hx
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("teacher")
public class Teacher extends BaseEntity {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 关联用户ID
     */
    private Long userId;
    
    /**
     * 工号
     */
    private String teacherNo;
    
    /**
     * 姓名
     */
    private String name;
    
    /**
     * 性别 (0-未知, 1-男, 2-女)
     */
    private Integer gender;
    
    /**
     * 职称
     */
    private String title;
    
    /**
     * 所属部门
     */
    private String department;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 联系电话
     */
    private String phone;
    
    /**
     * 状态 (0-离职, 1-在职)
     */
    private Integer status;
}
