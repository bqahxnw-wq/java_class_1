package com.grade.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 成绩实体
 * 
 * @author hx
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("grade")
public class Grade extends BaseEntity {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 学生ID
     */
    private Long studentId;
    
    /**
     * 教学班ID
     */
    private Long teachingClassId;
    
    /**
     * 平时成绩
     */
    private Integer regularScore;
    
    /**
     * 平时成绩录入时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regularScoreTime;
    
    /**
     * 期中成绩
     */
    private Integer midtermScore;
    
    /**
     * 期中成绩录入时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime midtermScoreTime;
    
    /**
     * 实验成绩
     */
    private Integer experimentScore;
    
    /**
     * 实验成绩录入时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime experimentScoreTime;
    
    /**
     * 期末成绩
     */
    private Integer finalScore;
    
    /**
     * 期末成绩录入时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime finalScoreTime;
    
    /**
     * 综合成绩
     */
    private Integer totalScore;
    
    /**
     * 综合成绩计算时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime totalScoreTime;
    
    /**
     * 等级 (A/B/C/D/F)
     */
    private String gradeLevel;
    
    /**
     * 是否及格 (0-不及格, 1-及格)
     */
    private Integer isPass;
    
    /**
     * 是否完成 (0-未完成, 1-已完成)
     */
    private Integer isCompleted;
    
    /**
     * 学生姓名(非数据库字段)
     */
    @TableField(exist = false)
    private String studentName;
    
    /**
     * 学号(非数据库字段)
     */
    @TableField(exist = false)
    private String studentNo;
    
    /**
     * 课程名称(非数据库字段)
     */
    @TableField(exist = false)
    private String courseName;
    
    /**
     * 教学班名称(非数据库字段)
     */
    @TableField(exist = false)
    private String className;
}
