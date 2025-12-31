package com.grade.common;

import lombok.Getter;

/**
 * 响应码枚举
 * 
 * @author hx
 */
@Getter
public enum ResultCode {
    
    // 成功
    SUCCESS(200, "操作成功"),
    
    // 客户端错误 (4xx)
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未授权,请先登录"),
    FORBIDDEN(403, "没有权限访问"),
    NOT_FOUND(404, "请求的资源不存在"),
    METHOD_NOT_ALLOWED(405, "请求方法不支持"),
    
    // 服务端错误 (5xx)
    ERROR(500, "系统内部错误"),
    SERVICE_UNAVAILABLE(503, "服务暂时不可用"),
    
    // 业务错误 (1xxx)
    BUSINESS_ERROR(1000, "业务处理失败"),
    USERNAME_EXIST(1001, "用户名已存在"),
    USER_NOT_EXIST(1002, "用户不存在"),
    PASSWORD_ERROR(1003, "密码错误"),
    USER_DISABLED(1004, "用户已被禁用"),
    TOKEN_EXPIRED(1005, "登录已过期,请重新登录"),
    TOKEN_INVALID(1006, "无效的token"),
    
    // 数据验证错误 (2xxx)
    VALIDATION_ERROR(2000, "数据验证失败"),
    STUDENT_NOT_EXIST(2001, "学生不存在"),
    TEACHER_NOT_EXIST(2002, "教师不存在"),
    COURSE_NOT_EXIST(2003, "课程不存在"),
    CLASS_NOT_EXIST(2004, "教学班不存在"),
    GRADE_NOT_EXIST(2005, "成绩不存在"),
    CLASS_FULL(2006, "教学班人数已满"),
    ALREADY_SELECTED(2007, "已经选过该课程"),
    GRADE_NOT_COMPLETE(2008, "成绩不完整,无法计算综合成绩"),
    
    // 权限错误 (3xxx)
    PERMISSION_DENIED(3000, "没有权限执行该操作"),
    ROLE_NOT_EXIST(3001, "角色不存在"),
    PERMISSION_NOT_EXIST(3002, "权限不存在");
    
    private final Integer code;
    private final String message;
    
    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
