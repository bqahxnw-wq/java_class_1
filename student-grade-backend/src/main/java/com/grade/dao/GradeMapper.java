package com.grade.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.grade.entity.Grade;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 成绩Mapper
 * 
 * @author hx
 */
@Mapper
public interface GradeMapper extends BaseMapper<Grade> {
    
    /**
     * 查询学生的成绩列表(包含课程和教学班信息)
     */
    @Select("SELECT g.*, s.name as studentName, s.student_no as studentNo, " +
            "c.course_name as courseName, tc.class_name as className " +
            "FROM grade g " +
            "JOIN student s ON g.student_id = s.id " +
            "JOIN teaching_class tc ON g.teaching_class_id = tc.id " +
            "JOIN course c ON tc.course_id = c.id " +
            "WHERE g.student_id = #{studentId} AND g.deleted = 0")
    List<Grade> selectByStudentIdWithDetail(@Param("studentId") Long studentId);
    
    /**
     * 查询教学班的成绩列表(包含学生信息)
     */
    @Select("SELECT g.*, s.name as studentName, s.student_no as studentNo, " +
            "c.course_name as courseName, tc.class_name as className " +
            "FROM grade g " +
            "JOIN student s ON g.student_id = s.id " +
            "JOIN teaching_class tc ON g.teaching_class_id = tc.id " +
            "JOIN course c ON tc.course_id = c.id " +
            "WHERE g.teaching_class_id = #{teachingClassId} AND g.deleted = 0 " +
            "ORDER BY g.total_score DESC")
    List<Grade> selectByTeachingClassIdWithDetail(@Param("teachingClassId") Long teachingClassId);
    
    /**
     * 统计成绩分布
     */
    @Select("SELECT " +
            "COUNT(CASE WHEN total_score >= 90 THEN 1 END) as excellent, " +
            "COUNT(CASE WHEN total_score >= 80 AND total_score < 90 THEN 1 END) as good, " +
            "COUNT(CASE WHEN total_score >= 70 AND total_score < 80 THEN 1 END) as medium, " +
            "COUNT(CASE WHEN total_score >= 60 AND total_score < 70 THEN 1 END) as pass, " +
            "COUNT(CASE WHEN total_score < 60 THEN 1 END) as fail " +
            "FROM grade " +
            "WHERE teaching_class_id = #{teachingClassId} AND is_completed = 1 AND deleted = 0")
    Map<String, Object> selectGradeDistribution(@Param("teachingClassId") Long teachingClassId);
}
