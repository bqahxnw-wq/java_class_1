package com.grade.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.grade.entity.Course;
import org.apache.ibatis.annotations.Mapper;

/**
 * 课程Mapper
 * 
 * @author hx
 */
@Mapper
public interface CourseMapper extends BaseMapper<Course> {
}
