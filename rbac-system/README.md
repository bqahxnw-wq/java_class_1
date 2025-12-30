# 基于命令行的权限管理系统 (RBAC System)

## 项目简介

本系统是一个完整的基于角色的访问控制(RBAC - Role-Based Access Control)系统，采用Java开发，使用MVC设计模式。

系统实现了"用户-角色-权限"三层映射关系，提供完整的权限管理功能，包括：
- 用户管理
- 角色管理
- 权限管理
- 权限校验
- 审计日志

## 系统特点

1. **MVC架构设计**：清晰的分层架构，代码结构合理
2. **单例模式**：DataStorage使用单例模式管理数据
3. **RBAC权限模型**：标准的基于角色的访问控制
4. **完整的审计功能**：记录所有权限相关操作
5. **友好的用户界面**：清晰的命令行菜单导航
6. **完善的数据管理**：支持数据初始化、查询、统计

## 项目结构

```
rbac-system/
├── src/
│   └── com/
│       └── rbac/
│           ├── Main.java                          # 主程序入口
│           ├── controller/                        # 控制器层
│           │   ├── SystemController.java          # 系统主控制器
│           │   ├── DataInitController.java        # 数据初始化控制器
│           │   ├── UserManagementController.java  # 用户管理控制器
│           │   ├── RoleManagementController.java  # 角色管理控制器
│           │   ├── PermissionManagementController.java  # 权限管理控制器
│           │   ├── AuthorizationController.java   # 权限校验控制器
│           │   └── AuditController.java           # 审计日志控制器
│           ├── model/                             # 模型层
│           │   ├── User.java                      # 用户实体
│           │   ├── UserStatus.java                # 用户状态枚举
│           │   ├── Role.java                      # 角色实体
│           │   ├── RoleType.java                  # 角色类型枚举
│           │   ├── Permission.java                # 权限实体
│           │   ├── PermissionType.java            # 权限类型枚举
│           │   ├── Resource.java                  # 资源实体
│           │   ├── ResourceType.java              # 资源类型枚举
│           │   └── AuditLog.java                  # 审计日志实体
│           ├── service/                           # 服务层
│           │   ├── DataGenerator.java             # 数据生成服务
│           │   ├── AuthorizationService.java      # 授权服务
│           │   └── AuditService.java              # 审计服务
│           ├── util/                              # 工具层
│           │   ├── Constants.java                 # 常量定义
│           │   ├── DataStorage.java               # 数据存储(单例)
│           │   └── InputValidator.java            # 输入验证工具
│           └── view/                              # 视图层
│               ├── MenuView.java                  # 菜单视图
│               └── DisplayView.java               # 显示视图
├── bin/                                           # 编译输出目录
└── README.md                                       # 说明文档
```

## 环境要求

- **JDK版本**: JDK 8 或更高版本
- **开发工具**: IntelliJ IDEA (推荐)
- **操作系统**: Windows / macOS / Linux

## 部署步骤

### 方法一：使用IntelliJ IDEA（推荐）

1. **打开IntelliJ IDEA**

2. **导入项目**
   - 点击 `File` -> `Open`
   - 选择 `rbac-system` 文件夹
   - 点击 `OK`

3. **配置JDK**
   - 点击 `File` -> `Project Structure`
   - 在 `Project` 选项卡中
   - 设置 `Project SDK` 为 JDK 8 或更高版本
   - 设置 `Project language level` 为 8 或更高
   - 点击 `OK`

4. **标记源代码目录**
   - 在项目结构中右键点击 `src` 文件夹
   - 选择 `Mark Directory as` -> `Sources Root`

5. **配置运行**
   - 点击 `Run` -> `Edit Configurations`
   - 点击左上角的 `+` 号
   - 选择 `Application`
   - 配置如下：
     - Name: `RBAC System`
     - Main class: `com.rbac.Main`
     - 点击 `OK`

6. **运行程序**
   - 点击工具栏的绿色运行按钮
   - 或按快捷键 `Shift + F10`

### 方法二：使用命令行

1. **编译项目**
   ```bash
   cd rbac-system
   mkdir -p bin
   javac -d bin -sourcepath src src/com/rbac/Main.java
   ```

2. **运行程序**
   ```bash
   java -cp bin com.rbac.Main
   ```

### 方法三：创建可执行JAR包

1. **创建manifest文件**
   ```bash
   cd rbac-system
   echo "Main-Class: com.rbac.Main" > manifest.txt
   ```

2. **编译并打包**
   ```bash
   mkdir -p bin
   javac -d bin -sourcepath src src/com/rbac/Main.java
   jar cvfm rbac-system.jar manifest.txt -C bin .
   ```

3. **运行JAR包**
   ```bash
   java -jar rbac-system.jar
   ```

## 使用说明

### 首次运行

1. 启动系统后，按回车键进入主菜单
2. 选择 `1. 数据初始化`
3. 选择 `1. 生成初始化数据`
4. 系统将自动生成：
   - 20个用户（包括admin管理员）
   - 5个角色（管理员、经理、用户、访客、审计员）
   - 17个权限
   - 10个资源

### 默认管理员账号

- 用户名: `admin`
- 密码: `123456`
- 拥有系统所有权限

### 主要功能模块

#### 1. 数据初始化
- 生成初始化数据
- 查看数据摘要
- 重置所有数据

#### 2. 用户管理
- 查看所有用户
- 查询用户信息
- 创建新用户
- 修改用户信息
- 删除用户
- 为用户分配角色
- 查看用户权限

#### 3. 角色管理
- 查看所有角色
- 查询角色信息
- 创建新角色
- 修改角色信息
- 删除角色
- 为角色分配权限
- 查看角色的用户

#### 4. 权限管理
- 查看所有权限
- 按类型查看权限（菜单/操作/资源/数据）
- 查看资源列表
- 查询权限详情

#### 5. 权限校验
- 检查用户权限
- 检查用户角色
- 检查资源访问权限
- 模拟操作权限校验

#### 6. 审计日志
- 查看所有审计日志
- 查看最近的日志
- 按用户查询日志
- 按操作类型查询日志
- 查看失败的操作
- 操作统计分析

### 权限编码说明

**用户管理权限**：
- `USER_CREATE`: 创建用户
- `USER_READ`: 查看用户
- `USER_UPDATE`: 修改用户
- `USER_DELETE`: 删除用户
- `USER_ASSIGN_ROLE`: 分配角色

**角色管理权限**：
- `ROLE_CREATE`: 创建角色
- `ROLE_READ`: 查看角色
- `ROLE_UPDATE`: 修改角色
- `ROLE_DELETE`: 删除角色
- `ROLE_ASSIGN_PERM`: 分配权限

**其他权限**：
- `AUDIT_VIEW`: 查看审计日志
- `SYSTEM_CONFIG`: 系统配置

## 设计模式

### 1. MVC模式
- **Model**: 实体类，包含业务数据和逻辑
- **View**: 视图类，负责界面显示
- **Controller**: 控制器类，协调Model和View

### 2. 单例模式
- `DataStorage`: 使用单例模式管理全局数据

### 3. 工厂模式思想
- 各种数据生成都在`DataGenerator`中统一管理

## 核心类说明

### User（用户）
包含用户基本信息和角色集合，提供权限检查方法。

### Role（角色）
包含角色信息和权限集合，连接用户和权限。

### Permission（权限）
定义系统中的各种权限，分为菜单、操作、资源、数据四种类型。

### Resource（资源）
表示系统中受保护的资源，如API、页面、文件等。

### AuditLog（审计日志）
记录所有权限相关操作，用于审计和追踪。

### DataStorage（数据存储）
单例类，作为内存数据库存储所有数据。

### AuthorizationService（授权服务）
提供权限校验功能，是权限管理的核心。

## 常见问题

### Q1: 如何添加新用户？
A: 进入"用户管理"模块，选择"创建新用户"，按提示输入信息。

### Q2: 如何给用户分配权限？
A: 权限通过角色分配。先为角色分配权限，再将角色分配给用户。

### Q3: 如何查看操作记录？
A: 进入"审计日志"模块，可查看所有操作记录和统计。

### Q4: 系统角色能删除吗？
A: 不能。系统预定义的角色（如ADMIN、MANAGER等）不能删除或修改。

### Q5: 如何重新初始化数据？
A: 进入"数据初始化"模块，选择"重置所有数据"，然后重新生成。

## 注意事项

1. **数据持久化**：本系统数据存储在内存中，程序关闭后数据会丢失
2. **密码安全**：示例中密码未加密，实际应用中应使用加密
3. **权限校验**：部分功能的权限校验已实现，可根据需要扩展
4. **线程安全**：当前实现未考虑并发，单线程使用

## 扩展建议

1. 添加数据库支持（如MySQL、SQLite）
2. 实现密码加密（如BCrypt）
3. 添加更详细的权限粒度控制
4. 实现权限的继承机制
5. 添加权限的时间限制功能
6. 实现基于属性的访问控制（ABAC）

## 作者信息

- 开发者: hx
- 版本: v1.0
- 开发时间: 2025

## 许可证

本项目仅用于学习和教学目的。
