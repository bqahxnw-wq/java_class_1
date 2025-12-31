package com.grade.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 教学班实体
 * 
 * @author hx
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("teaching_class")
public class TeachingClass extends BaseEntity {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 教学班编号
     */
    private String classCode;
    
    /**
     * 教学班名称
     */
    private String className;
    
    /**
     * 课程ID
     */
    private Long courseId;
    
    /**
     * 教师ID
     */
    private Long teacherId;
    
    /**
     * 学期
     */
    private String semester;
    
    /**
     * 最大学生数
     */
    private Integer maxStudents;
    
    /**
     * 当前学生数
     */
    private Integer currentStudents;
    
    /**
     * 上课时间安排
     */
    private String schedule;
    
    /**
     * 教室
     */
    private String classroom;
    
    /**
     * 状态 (0-停课, 1-正常, 2-已结课)
     */
    private Integer status;
    
    /**
     * 课程名称(非数据库字段)
     */
    @TableField(exist = false)
    private String courseName;
    
    /**
     * 教师姓名(非数据库字段)
     */
    @TableField(exist = false)
    private String teacherName;
}
