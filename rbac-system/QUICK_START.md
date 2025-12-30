# 快速开始指南

## 1. 在IntelliJ IDEA中运行（最简单）

### 第一步：打开项目
1. 解压 `rbac-system.zip` 到任意目录
2. 打开 IntelliJ IDEA
3. 点击 `File` -> `Open`
4. 选择解压后的 `rbac-system` 文件夹
5. 点击 `OK`

### 第二步：配置项目
1. 等待IDEA加载完项目
2. 如果提示配置SDK，选择JDK 8或更高版本
3. 在项目结构中，右键点击 `src` 文件夹
4. 选择 `Mark Directory as` -> `Sources Root`（如果已标记则跳过）

### 第三步：运行程序
1. 在项目结构中找到 `src/com/rbac/Main.java`
2. 右键点击 `Main.java`
3. 选择 `Run 'Main.main()'`
4. 程序将在下方的Run窗口中运行

### 第四步：使用系统
1. 按回车键进入主菜单
2. 选择 `1` 进入数据初始化
3. 选择 `1` 生成初始化数据
4. 返回主菜单后即可使用各项功能

## 2. 使用命令行运行

### Windows系统

1. 打开命令提示符(cmd)
2. 进入项目目录:
   ```cmd
   cd C:\path\to\rbac-system
   ```

3. 编译项目:
   ```cmd
   mkdir bin
   javac -d bin -encoding UTF-8 -sourcepath src src\com\rbac\Main.java
   ```

4. 运行程序:
   ```cmd
   java -cp bin com.rbac.Main
   ```

### macOS/Linux系统

1. 打开终端
2. 进入项目目录:
   ```bash
   cd /path/to/rbac-system
   ```

3. 编译项目:
   ```bash
   mkdir -p bin
   javac -d bin -encoding UTF-8 -sourcepath src src/com/rbac/Main.java
   ```

4. 运行程序:
   ```bash
   java -cp bin com.rbac.Main
   ```

## 3. 功能快速体验

### 初始化数据
```
主菜单 -> 1. 数据初始化 -> 1. 生成初始化数据
```
系统将自动生成20个用户、5个角色、17个权限和10个资源

### 查看用户列表
```
主菜单 -> 2. 用户管理 -> 1. 查看所有用户
```

### 查看用户权限
```
主菜单 -> 2. 用户管理 -> 7. 查看用户权限
输入用户名: admin
```
可以看到admin用户拥有所有权限

### 权限校验测试
```
主菜单 -> 5. 权限校验 -> 1. 检查用户权限
输入用户名: admin
输入权限编码: USER_CREATE
```
系统会显示admin是否拥有创建用户的权限

### 查看审计日志
```
主菜单 -> 6. 审计日志 -> 1. 查看所有审计日志
```
可以看到所有操作的记录

## 4. 默认账号信息

- **管理员账号**: admin / 123456（拥有所有权限）
- **普通用户**: user2-user20 / 123456（权限根据分配的角色而定）

## 5. 常用操作流程

### 创建新用户并分配角色
1. 进入用户管理 -> 创建新用户
2. 输入用户信息
3. 返回用户管理 -> 为用户分配角色
4. 输入刚创建的用户名
5. 查看角色列表，选择要分配的角色编码

### 创建自定义角色
1. 进入角色管理 -> 创建新角色
2. 输入角色信息
3. 返回角色管理 -> 为角色分配权限
4. 查看权限列表，为角色分配所需权限

### 查看审计记录
1. 进入审计日志
2. 选择查看方式：
   - 查看所有日志
   - 查看最近N条日志
   - 按用户查询
   - 按操作类型查询
   - 查看失败的操作

## 6. 故障排除

### 问题：编译时出现编码错误
**解决方案**: 在javac命令中添加 `-encoding UTF-8` 参数

### 问题：找不到javac命令
**解决方案**: 
1. 确认已安装JDK（不是JRE）
2. 配置JAVA_HOME环境变量
3. 将JDK的bin目录添加到PATH环境变量

### 问题：IDEA中无法运行
**解决方案**:
1. 检查是否配置了JDK
2. 确认src目录被标记为Sources Root
3. 尝试 Invalidate Caches and Restart

### 问题：中文显示乱码
**解决方案**:
- Windows: 在cmd中运行 `chcp 65001` 切换到UTF-8编码
- IDEA: File -> Settings -> Editor -> File Encodings -> 设置为UTF-8

## 7. 联系方式

如有问题，请参考 README.md 中的详细文档。
