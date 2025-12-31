package com.grade.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.grade.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户Mapper
 * 
 * @author hx
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    
    /**
     * 根据用户名查询用户
     */
    @Select("SELECT * FROM sys_user WHERE username = #{username} AND deleted = 0")
    SysUser selectByUsername(@Param("username") String username);
    
    /**
     * 查询用户的权限编码列表
     */
    @Select("SELECT DISTINCT sp.permission_code " +
            "FROM sys_permission sp " +
            "JOIN sys_role_permission srp ON sp.id = srp.permission_id " +
            "JOIN sys_role sr ON srp.role_id = sr.id " +
            "JOIN sys_user_role sur ON sr.id = sur.role_id " +
            "WHERE sur.user_id = #{userId} AND sp.deleted = 0 AND sr.deleted = 0 AND sr.status = 1")
    List<String> selectPermissionsByUserId(@Param("userId") Long userId);
    
    /**
     * 查询用户的角色编码列表
     */
    @Select("SELECT sr.role_code " +
            "FROM sys_role sr " +
            "JOIN sys_user_role sur ON sr.id = sur.role_id " +
            "WHERE sur.user_id = #{userId} AND sr.deleted = 0 AND sr.status = 1")
    List<String> selectRolesByUserId(@Param("userId") Long userId);
}
