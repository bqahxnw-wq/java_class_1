package com.grade.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.grade.entity.Student;
import org.apache.ibatis.annotations.Mapper;

/**
 * 学生Mapper
 * 
 * @author hx
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {
}
