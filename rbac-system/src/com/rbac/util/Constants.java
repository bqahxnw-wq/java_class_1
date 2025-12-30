package com.rbac.util;

/**
 * 系统常量定义类
 */
public class Constants {
    
    // 数据生成数量常量
    public static final int MIN_USERS = 20;              // 最少用户数量
    public static final int MIN_ROLES = 5;               // 最少角色数量
    public static final int MIN_PERMISSIONS = 15;        // 最少权限数量
    public static final int MIN_RESOURCES = 10;          // 最少资源数量
    
    // 系统预定义角色编码
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_MANAGER = "MANAGER";
    public static final String ROLE_USER = "USER";
    public static final String ROLE_GUEST = "GUEST";
    
    // 系统预定义权限编码前缀
    public static final String PERM_USER_PREFIX = "USER_";
    public static final String PERM_ROLE_PREFIX = "ROLE_";
    public static final String PERM_PERMISSION_PREFIX = "PERM_";
    public static final String PERM_RESOURCE_PREFIX = "RES_";
    
    // 操作权限
    public static final String PERM_USER_CREATE = "USER_CREATE";
    public static final String PERM_USER_READ = "USER_READ";
    public static final String PERM_USER_UPDATE = "USER_UPDATE";
    public static final String PERM_USER_DELETE = "USER_DELETE";
    public static final String PERM_USER_ASSIGN_ROLE = "USER_ASSIGN_ROLE";
    
    public static final String PERM_ROLE_CREATE = "ROLE_CREATE";
    public static final String PERM_ROLE_READ = "ROLE_READ";
    public static final String PERM_ROLE_UPDATE = "ROLE_UPDATE";
    public static final String PERM_ROLE_DELETE = "ROLE_DELETE";
    public static final String PERM_ROLE_ASSIGN_PERM = "ROLE_ASSIGN_PERM";
    
    public static final String PERM_AUDIT_VIEW = "AUDIT_VIEW";
    public static final String PERM_SYSTEM_CONFIG = "SYSTEM_CONFIG";
    
    // 默认密码
    public static final String DEFAULT_PASSWORD = "123456";
    
    // 姓氏库（用于生成随机姓名）
    public static final String[] SURNAMES = {
        "王", "李", "张", "刘", "陈", "杨", "黄", "赵", "周", "吴",
        "徐", "孙", "朱", "马", "胡", "郭", "林", "何", "高", "梁"
    };
    
    // 名字库
    public static final String[] GIVEN_NAMES = {
        "伟", "芳", "娜", "秀英", "敏", "静", "丽", "强", "磊", "军",
        "洋", "勇", "艳", "杰", "涛", "明", "超", "秀兰", "霞", "平",
        "刚", "桂英", "建", "浩", "鹏", "俊", "慧", "婷", "峰", "辉"
    };
    
    // 部门名称
    public static final String[] DEPARTMENTS = {
        "技术部", "产品部", "运营部", "市场部", "人力资源部",
        "财务部", "行政部", "研发部", "客服部", "销售部"
    };
    
    // 邮箱域名
    public static final String[] EMAIL_DOMAINS = {
        "@company.com", "@corp.com", "@enterprise.com"
    };
}
