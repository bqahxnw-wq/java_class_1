# 学生成绩管理系统后端

## 项目简介

本项目是一个完整的学生成绩管理系统后端,使用SpringBoot框架开发,整合了前面三个实验的成果:
- **实验1**: 学生成绩管理系统的业务模型和功能
- **实验2**: RBAC权限管理系统
- **实验3**: Vue前端界面开发

## 技术栈

### 核心框架
- Spring Boot 2.7.18
- Spring Security + JWT
- MyBatis-Plus 3.5.3.1
- MySQL 8.0
- Redis

### 其他组件
- Druid数据库连接池
- Lombok
- Swagger (Springdoc OpenAPI)
- Hutool工具类
- FastJSON

## 系统特色

### 1. 完整的业务功能
- ✅ 学生管理
- ✅ 教师管理  
- ✅ 课程管理
- ✅ 教学班管理
- ✅ 选课管理
- ✅ 成绩管理(平时、期中、实验、期末、综合)
- ✅ 成绩查询与统计

### 2. RBAC权限管理
- ✅ 用户-角色-权限三层映射
- ✅ JWT Token认证
- ✅ 基于注解的权限控制
- ✅ 审计日志记录

### 3. 系统优化
- ✅ Redis缓存提升性能
- ✅ Druid连接池优化
- ✅ 全局异常处理
- ✅ 统一响应格式
- ✅ 参数验证
- ✅ SQL防注入

### 4. 并发控制
- ✅ 乐观锁(MyBatis-Plus版本号机制)
- ✅ 数据库事务管理
- ✅ 连接池配置优化

## 项目结构

```
student-grade-backend/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/grade/
│       │       ├── StudentGradeApplication.java  # 启动类
│       │       ├── common/                       # 通用类
│       │       │   ├── Result.java              # 统一响应
│       │       │   ├── ResultCode.java          # 响应码
│       │       │   ├── BusinessException.java   # 业务异常
│       │       │   └── GlobalExceptionHandler.java  # 全局异常处理
│       │       ├── entity/                      # 实体类
│       │       │   ├── BaseEntity.java          # 基础实体
│       │       │   ├── SysUser.java             # 用户
│       │       │   ├── SysRole.java             # 角色
│       │       │   ├── SysPermission.java       # 权限
│       │       │   ├── Student.java             # 学生
│       │       │   ├── Teacher.java             # 教师
│       │       │   ├── Course.java              # 课程
│       │       │   ├── TeachingClass.java       # 教学班
│       │       │   └── Grade.java               # 成绩
│       │       ├── dto/                         # 数据传输对象
│       │       │   ├── LoginDTO.java
│       │       │   └── TokenDTO.java
│       │       ├── dao/                         # 数据访问层
│       │       │   ├── SysUserMapper.java
│       │       │   ├── StudentMapper.java
│       │       │   ├── TeacherMapper.java
│       │       │   ├── CourseMapper.java
│       │       │   ├── TeachingClassMapper.java
│       │       │   └── GradeMapper.java
│       │       ├── service/                     # 业务逻辑层
│       │       ├── controller/                  # 控制器层
│       │       ├── config/                      # 配置类
│       │       ├── security/                    # 安全配置
│       │       └── util/                        # 工具类
│       │           └── JwtUtil.java
│       └── resources/
│           ├── application.yml                   # 配置文件
│           └── sql/
│               └── schema.sql                    # 数据库脚本
├── pom.xml                                       # Maven配置
└── README.md                                     # 说明文档
```

## 环境要求

- JDK 1.8+
- Maven 3.6+
- MySQL 8.0+
- Redis 6.0+ (可选)
- IntelliJ IDEA (推荐)

## 部署步骤

### 1. 数据库准备

```sql
-- 执行SQL脚本创建数据库和表
source src/main/resources/sql/schema.sql;
```

**注意**: 
- 数据库名: `student_grade`
- 默认管理员账号: `admin` / `admin123`
- 请根据实际情况修改`application.yml`中的数据库连接信息

### 2. Redis配置(可选)

如果没有Redis,请注释掉`pom.xml`中的Redis依赖和`application.yml`中的Redis配置。

### 3. 导入项目

#### 方法一: 使用IntelliJ IDEA (推荐)

1. 打开IDEA → File → Open
2. 选择项目根目录 `student-grade-backend`
3. 等待Maven下载依赖
4. 配置JDK 1.8
5. 修改`application.yml`中的数据库配置
6. 运行`StudentGradeApplication`主类

#### 方法二: 使用命令行

```bash
# 1. 编译项目
mvn clean package -DskipTests

# 2. 运行项目
java -jar target/student-grade-backend-1.0.0.jar
```

### 4. 访问接口

启动成功后,访问以下地址:

- **API文档**: http://localhost:8080/api/swagger-ui.html
- **Druid监控**: http://localhost:8080/api/druid/ (admin/admin)

## 核心API接口

### 认证接口
```
POST /api/auth/login          # 用户登录
POST /api/auth/logout         # 用户登出
GET  /api/auth/info           # 获取当前用户信息
```

### 学生管理
```
GET    /api/students          # 查询学生列表
GET    /api/students/{id}     # 查询学生详情
POST   /api/students          # 创建学生
PUT    /api/students/{id}     # 更新学生
DELETE /api/students/{id}     # 删除学生
```

### 成绩管理
```
GET    /api/grades                    # 查询成绩列表
GET    /api/grades/student/{id}       # 查询学生成绩
GET    /api/grades/class/{id}         # 查询教学班成绩
POST   /api/grades/regular            # 录入平时成绩
POST   /api/grades/midterm            # 录入期中成绩
POST   /api/grades/experiment         # 录入实验成绩
POST   /api/grades/final              # 录入期末成绩
POST   /api/grades/calculate          # 计算综合成绩
GET    /api/grades/statistics/{id}    # 成绩统计分析
```

### 教学班管理
```
GET    /api/classes                   # 查询教学班列表
POST   /api/classes                   # 创建教学班
POST   /api/classes/{id}/select       # 学生选课
GET    /api/classes/{id}/students     # 查询教学班学生
```

## 权限说明

### 默认角色

| 角色 | 角色编码 | 说明 | 主要权限 |
|------|---------|------|---------|
| 系统管理员 | ADMIN | 超级管理员 | 所有权限 |
| 教师 | TEACHER | 教师角色 | 课程管理、成绩管理 |
| 学生 | STUDENT | 学生角色 | 选课、查看自己成绩 |

### 权限编码示例

```
USER_CREATE       - 创建用户
STUDENT_READ      - 查看学生
GRADE_CREATE      - 录入成绩
GRADE_READ_ALL    - 查看所有成绩
GRADE_READ_OWN    - 查看自己成绩
COURSE_SELECT     - 选课
```

## 数据库设计

### 核心表结构

1. **sys_user** - 用户表
2. **sys_role** - 角色表  
3. **sys_permission** - 权限表
4. **sys_user_role** - 用户角色关联
5. **sys_role_permission** - 角色权限关联
6. **student** - 学生表
7. **teacher** - 教师表
8. **course** - 课程表
9. **teaching_class** - 教学班表
10. **grade** - 成绩表
11. **sys_audit_log** - 审计日志表

## 测试说明

### 使用JMeter测试

1. 下载并安装Apache JMeter
2. 创建测试计划
3. 添加HTTP请求
4. 配置并发数和循环次数
5. 运行测试并查看结果

### 测试场景示例

```
场景1: 并发选课测试
- 线程数: 100
- 循环次数: 10
- 测试接口: POST /api/classes/{id}/select

场景2: 成绩查询测试
- 线程数: 200
- 循环次数: 20  
- 测试接口: GET /api/grades/student/{id}
```

## 常见问题

### Q1: 启动时提示端口被占用?
A: 修改`application.yml`中的`server.port`配置

### Q2: 无法连接数据库?
A: 检查MySQL是否启动,配置信息是否正确

### Q3: Redis连接失败?
A: 如不需要Redis,可注释相关配置

### Q4: Swagger无法访问?
A: 检查URL是否正确,确保包含`/api`前缀

### Q5: JWT Token失效?
A: Token默认7天有效期,过期需重新登录

## 扩展功能建议

1. ✨ 成绩导入导出(Excel)
2. ✨ 成绩变动通知(邮件/短信)
3. ✨ 数据统计图表
4. ✨ 成绩分析报告
5. ✨ 教学评价系统
6. ✨ 课程推荐系统
7. ✨ 移动端适配

## 开发者信息

- 开发者: 张卉馨
- 学号: 20231145
- 版本: v1.0.0
- 开发时间: 2025年12月

## 许可证

本项目仅用于学习和教学目的。
