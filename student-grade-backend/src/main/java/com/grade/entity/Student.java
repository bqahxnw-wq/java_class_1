package com.grade.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 学生实体
 * 
 * @author hx
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("student")
public class Student extends BaseEntity {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 关联用户ID
     */
    private Long userId;
    
    /**
     * 学号
     */
    private String studentNo;
    
    /**
     * 姓名
     */
    private String name;
    
    /**
     * 性别 (0-未知, 1-男, 2-女)
     */
    private Integer gender;
    
    /**
     * 出生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    
    /**
     * 班级名称
     */
    private String className;
    
    /**
     * 专业
     */
    private String major;
    
    /**
     * 入学年份
     */
    private Integer enrollmentYear;
    
    /**
     * 状态 (0-休学, 1-在读, 2-毕业, 3-退学)
     */
    private Integer status;
}
