-- 创建数据库
CREATE DATABASE IF NOT EXISTS student_grade DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE student_grade;

-- ===================== 权限管理相关表 =====================

-- 用户表
CREATE TABLE `sys_user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` VARCHAR(50) NOT NULL COMMENT '用户名',
  `password` VARCHAR(200) NOT NULL COMMENT '密码(加密)',
  `real_name` VARCHAR(50) COMMENT '真实姓名',
  `email` VARCHAR(100) COMMENT '邮箱',
  `phone` VARCHAR(20) COMMENT '手机号',
  `gender` TINYINT COMMENT '性别:0-未知,1-男,2-女',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态:0-禁用,1-正常',
  `user_type` VARCHAR(20) NOT NULL DEFAULT 'STUDENT' COMMENT '用户类型:ADMIN,TEACHER,STUDENT',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记:0-未删除,1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  KEY `idx_user_type` (`user_type`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 角色表
CREATE TABLE `sys_role` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_code` VARCHAR(50) NOT NULL COMMENT '角色编码',
  `role_name` VARCHAR(50) NOT NULL COMMENT '角色名称',
  `description` VARCHAR(200) COMMENT '描述',
  `is_system` TINYINT NOT NULL DEFAULT 0 COMMENT '是否系统角色:0-否,1-是',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态:0-禁用,1-正常',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_code` (`role_code`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色表';

-- 权限表
CREATE TABLE `sys_permission` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `permission_code` VARCHAR(50) NOT NULL COMMENT '权限编码',
  `permission_name` VARCHAR(50) NOT NULL COMMENT '权限名称',
  `permission_type` VARCHAR(20) NOT NULL COMMENT '权限类型:MENU,OPERATION,RESOURCE',
  `description` VARCHAR(200) COMMENT '描述',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_permission_code` (`permission_code`),
  KEY `idx_permission_type` (`permission_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='权限表';

-- 用户角色关联表
CREATE TABLE `sys_user_role` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `role_id` BIGINT NOT NULL COMMENT '角色ID',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_role` (`user_id`, `role_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户角色关联表';

-- 角色权限关联表
CREATE TABLE `sys_role_permission` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` BIGINT NOT NULL COMMENT '角色ID',
  `permission_id` BIGINT NOT NULL COMMENT '权限ID',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_permission` (`role_id`, `permission_id`),
  KEY `idx_role_id` (`role_id`),
  KEY `idx_permission_id` (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色权限关联表';

-- 审计日志表
CREATE TABLE `sys_audit_log` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `user_id` BIGINT COMMENT '操作用户ID',
  `username` VARCHAR(50) COMMENT '操作用户名',
  `operation` VARCHAR(50) NOT NULL COMMENT '操作类型',
  `target_type` VARCHAR(50) COMMENT '操作对象类型',
  `target_id` VARCHAR(50) COMMENT '操作对象ID',
  `result` TINYINT NOT NULL COMMENT '操作结果:0-失败,1-成功',
  `message` VARCHAR(500) COMMENT '操作消息',
  `ip_address` VARCHAR(50) COMMENT 'IP地址',
  `user_agent` VARCHAR(500) COMMENT '用户代理',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_operation` (`operation`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='审计日志表';

-- ===================== 业务相关表 =====================

-- 学生表
CREATE TABLE `student` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '学生ID',
  `user_id` BIGINT NOT NULL COMMENT '关联用户ID',
  `student_no` VARCHAR(20) NOT NULL COMMENT '学号',
  `name` VARCHAR(50) NOT NULL COMMENT '姓名',
  `gender` TINYINT COMMENT '性别:0-未知,1-男,2-女',
  `birth_date` DATE COMMENT '出生日期',
  `class_name` VARCHAR(50) COMMENT '班级名称',
  `major` VARCHAR(50) COMMENT '专业',
  `enrollment_year` INT COMMENT '入学年份',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态:0-休学,1-在读,2-毕业,3-退学',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_student_no` (`student_no`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_class_name` (`class_name`),
  KEY `idx_enrollment_year` (`enrollment_year`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='学生表';

-- 教师表
CREATE TABLE `teacher` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '教师ID',
  `user_id` BIGINT NOT NULL COMMENT '关联用户ID',
  `teacher_no` VARCHAR(20) NOT NULL COMMENT '工号',
  `name` VARCHAR(50) NOT NULL COMMENT '姓名',
  `gender` TINYINT COMMENT '性别:0-未知,1-男,2-女',
  `title` VARCHAR(50) COMMENT '职称',
  `department` VARCHAR(100) COMMENT '所属部门',
  `email` VARCHAR(100) COMMENT '邮箱',
  `phone` VARCHAR(20) COMMENT '联系电话',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态:0-离职,1-在职',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_teacher_no` (`teacher_no`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_department` (`department`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='教师表';

-- 课程表
CREATE TABLE `course` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '课程ID',
  `course_code` VARCHAR(20) NOT NULL COMMENT '课程编号',
  `course_name` VARCHAR(100) NOT NULL COMMENT '课程名称',
  `credits` DECIMAL(3,1) NOT NULL COMMENT '学分',
  `hours` INT NOT NULL COMMENT '学时',
  `course_type` VARCHAR(20) COMMENT '课程类型:必修,选修',
  `description` TEXT COMMENT '课程描述',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态:0-停用,1-正常',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_course_code` (`course_code`),
  KEY `idx_course_type` (`course_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='课程表';

-- 教学班表
CREATE TABLE `teaching_class` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '教学班ID',
  `class_code` VARCHAR(20) NOT NULL COMMENT '教学班编号',
  `class_name` VARCHAR(100) NOT NULL COMMENT '教学班名称',
  `course_id` BIGINT NOT NULL COMMENT '课程ID',
  `teacher_id` BIGINT NOT NULL COMMENT '教师ID',
  `semester` VARCHAR(20) NOT NULL COMMENT '学期(如2024-2025-1)',
  `max_students` INT NOT NULL DEFAULT 50 COMMENT '最大学生数',
  `current_students` INT NOT NULL DEFAULT 0 COMMENT '当前学生数',
  `schedule` VARCHAR(200) COMMENT '上课时间安排',
  `classroom` VARCHAR(50) COMMENT '教室',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态:0-停课,1-正常,2-已结课',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_class_code` (`class_code`),
  KEY `idx_course_id` (`course_id`),
  KEY `idx_teacher_id` (`teacher_id`),
  KEY `idx_semester` (`semester`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='教学班表';

-- 选课记录表
CREATE TABLE `course_selection` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '选课ID',
  `student_id` BIGINT NOT NULL COMMENT '学生ID',
  `teaching_class_id` BIGINT NOT NULL COMMENT '教学班ID',
  `selection_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '选课时间',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态:0-已退课,1-已选课',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_student_class` (`student_id`, `teaching_class_id`),
  KEY `idx_student_id` (`student_id`),
  KEY `idx_teaching_class_id` (`teaching_class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='选课记录表';

-- 成绩表
CREATE TABLE `grade` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '成绩ID',
  `student_id` BIGINT NOT NULL COMMENT '学生ID',
  `teaching_class_id` BIGINT NOT NULL COMMENT '教学班ID',
  `regular_score` INT COMMENT '平时成绩(0-100)',
  `regular_score_time` DATETIME COMMENT '平时成绩录入时间',
  `midterm_score` INT COMMENT '期中成绩(0-100)',
  `midterm_score_time` DATETIME COMMENT '期中成绩录入时间',
  `experiment_score` INT COMMENT '实验成绩(0-100)',
  `experiment_score_time` DATETIME COMMENT '实验成绩录入时间',
  `final_score` INT COMMENT '期末成绩(0-100)',
  `final_score_time` DATETIME COMMENT '期末成绩录入时间',
  `total_score` INT COMMENT '综合成绩(0-100)',
  `total_score_time` DATETIME COMMENT '综合成绩计算时间',
  `grade_level` VARCHAR(10) COMMENT '等级(A/B/C/D/F)',
  `is_pass` TINYINT COMMENT '是否及格:0-不及格,1-及格',
  `is_completed` TINYINT NOT NULL DEFAULT 0 COMMENT '是否完成:0-未完成,1-已完成',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_student_class` (`student_id`, `teaching_class_id`),
  KEY `idx_student_id` (`student_id`),
  KEY `idx_teaching_class_id` (`teaching_class_id`),
  KEY `idx_total_score` (`total_score`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='成绩表';

-- ===================== 初始化数据 =====================

-- 插入默认权限
INSERT INTO `sys_permission` (`permission_code`, `permission_name`, `permission_type`, `description`) VALUES
('USER_CREATE', '创建用户', 'OPERATION', '创建新用户的权限'),
('USER_READ', '查看用户', 'OPERATION', '查看用户信息的权限'),
('USER_UPDATE', '修改用户', 'OPERATION', '修改用户信息的权限'),
('USER_DELETE', '删除用户', 'OPERATION', '删除用户的权限'),
('STUDENT_CREATE', '创建学生', 'OPERATION', '创建学生信息的权限'),
('STUDENT_READ', '查看学生', 'OPERATION', '查看学生信息的权限'),
('STUDENT_UPDATE', '修改学生', 'OPERATION', '修改学生信息的权限'),
('STUDENT_DELETE', '删除学生', 'OPERATION', '删除学生的权限'),
('TEACHER_CREATE', '创建教师', 'OPERATION', '创建教师信息的权限'),
('TEACHER_READ', '查看教师', 'OPERATION', '查看教师信息的权限'),
('TEACHER_UPDATE', '修改教师', 'OPERATION', '修改教师信息的权限'),
('TEACHER_DELETE', '删除教师', 'OPERATION', '删除教师的权限'),
('COURSE_CREATE', '创建课程', 'OPERATION', '创建课程的权限'),
('COURSE_READ', '查看课程', 'OPERATION', '查看课程信息的权限'),
('COURSE_UPDATE', '修改课程', 'OPERATION', '修改课程信息的权限'),
('COURSE_DELETE', '删除课程', 'OPERATION', '删除课程的权限'),
('CLASS_CREATE', '创建教学班', 'OPERATION', '创建教学班的权限'),
('CLASS_READ', '查看教学班', 'OPERATION', '查看教学班信息的权限'),
('CLASS_UPDATE', '修改教学班', 'OPERATION', '修改教学班信息的权限'),
('CLASS_DELETE', '删除教学班', 'OPERATION', '删除教学班的权限'),
('GRADE_CREATE', '录入成绩', 'OPERATION', '录入学生成绩的权限'),
('GRADE_READ', '查看成绩', 'OPERATION', '查看成绩的权限'),
('GRADE_UPDATE', '修改成绩', 'OPERATION', '修改成绩的权限'),
('GRADE_DELETE', '删除成绩', 'OPERATION', '删除成绩的权限'),
('GRADE_READ_ALL', '查看所有成绩', 'OPERATION', '查看所有学生成绩的权限'),
('GRADE_READ_OWN', '查看自己成绩', 'OPERATION', '查看自己成绩的权限'),
('COURSE_SELECT', '选课', 'OPERATION', '学生选课的权限'),
('AUDIT_READ', '查看日志', 'OPERATION', '查看审计日志的权限'),
('SYSTEM_CONFIG', '系统配置', 'OPERATION', '系统配置的权限');

-- 插入默认角色
INSERT INTO `sys_role` (`role_code`, `role_name`, `description`, `is_system`) VALUES
('ADMIN', '系统管理员', '拥有系统所有权限', 1),
('TEACHER', '教师', '教师角色,可以管理课程和成绩', 1),
('STUDENT', '学生', '学生角色,可以查看自己的成绩和选课', 1);

-- 为管理员角色分配所有权限
INSERT INTO `sys_role_permission` (`role_id`, `permission_id`)
SELECT 1, id FROM `sys_permission`;

-- 为教师角色分配权限
INSERT INTO `sys_role_permission` (`role_id`, `permission_id`)
SELECT 2, id FROM `sys_permission` 
WHERE permission_code IN (
    'COURSE_READ', 'CLASS_READ', 'CLASS_CREATE', 'CLASS_UPDATE',
    'GRADE_CREATE', 'GRADE_READ', 'GRADE_UPDATE', 'GRADE_READ_ALL',
    'STUDENT_READ', 'TEACHER_READ'
);

-- 为学生角色分配权限
INSERT INTO `sys_role_permission` (`role_id`, `permission_id`)
SELECT 3, id FROM `sys_permission` 
WHERE permission_code IN (
    'GRADE_READ_OWN', 'COURSE_SELECT', 'COURSE_READ', 'CLASS_READ', 'TEACHER_READ'
);

-- 插入默认管理员用户 (密码: admin123)
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `email`, `gender`, `status`, `user_type`) 
VALUES ('admin', '$2a$10$Wx3qGPXKW.5WyVtJJF5fXeJxvDfQQn6xrJZYQqHQQqVJ5yG9JhFXO', '系统管理员', 'admin@example.com', 1, 1, 'ADMIN');

-- 为管理员分配角色
INSERT INTO `sys_user_role` (`user_id`, `role_id`) VALUES (1, 1);
