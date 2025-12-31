package com.grade.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 课程实体
 * 
 * @author hx
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("course")
public class Course extends BaseEntity {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 课程编号
     */
    private String courseCode;
    
    /**
     * 课程名称
     */
    private String courseName;
    
    /**
     * 学分
     */
    private BigDecimal credits;
    
    /**
     * 学时
     */
    private Integer hours;
    
    /**
     * 课程类型 (必修/选修)
     */
    private String courseType;
    
    /**
     * 课程描述
     */
    private String description;
    
    /**
     * 状态 (0-停用, 1-正常)
     */
    private Integer status;
}
