package com.rbac.service;

import com.rbac.model.*;
import com.rbac.util.Constants;
import com.rbac.util.DataStorage;

import java.util.*;

/**
 * 数据生成服务类
 */
public class DataGenerator {
    
    private DataStorage storage = DataStorage.getInstance();
    private Random random = new Random();
    
    /**
     * 生成所有初始化数据
     */
    public void generateAllData() {
        System.out.println("\n=== 开始生成初始化数据 ===");
        
        generatePermissions();
        generateRoles();
        generateUsers();
        generateResources();
        
        System.out.println("\n=== 数据生成完成 ===");
        printDataSummary();
    }
    
    /**
     * 生成权限数据
     */
    private void generatePermissions() {
        System.out.print("正在生成权限数据...");
        
        // 用户管理权限
        addPermission("P001", Constants.PERM_USER_CREATE, "创建用户", "允许创建新用户", PermissionType.OPERATION);
        addPermission("P002", Constants.PERM_USER_READ, "查看用户", "允许查看用户信息", PermissionType.OPERATION);
        addPermission("P003", Constants.PERM_USER_UPDATE, "修改用户", "允许修改用户信息", PermissionType.OPERATION);
        addPermission("P004", Constants.PERM_USER_DELETE, "删除用户", "允许删除用户", PermissionType.OPERATION);
        addPermission("P005", Constants.PERM_USER_ASSIGN_ROLE, "分配角色", "允许为用户分配角色", PermissionType.OPERATION);
        
        // 角色管理权限
        addPermission("P006", Constants.PERM_ROLE_CREATE, "创建角色", "允许创建新角色", PermissionType.OPERATION);
        addPermission("P007", Constants.PERM_ROLE_READ, "查看角色", "允许查看角色信息", PermissionType.OPERATION);
        addPermission("P008", Constants.PERM_ROLE_UPDATE, "修改角色", "允许修改角色信息", PermissionType.OPERATION);
        addPermission("P009", Constants.PERM_ROLE_DELETE, "删除角色", "允许删除角色", PermissionType.OPERATION);
        addPermission("P010", Constants.PERM_ROLE_ASSIGN_PERM, "分配权限", "允许为角色分配权限", PermissionType.OPERATION);
        
        // 审计权限
        addPermission("P011", Constants.PERM_AUDIT_VIEW, "查看审计日志", "允许查看系统审计日志", PermissionType.OPERATION);
        
        // 系统配置权限
        addPermission("P012", Constants.PERM_SYSTEM_CONFIG, "系统配置", "允许修改系统配置", PermissionType.OPERATION);
        
        // 菜单权限
        addPermission("P013", "MENU_USER_MGMT", "用户管理菜单", "用户管理模块菜单访问权限", PermissionType.MENU);
        addPermission("P014", "MENU_ROLE_MGMT", "角色管理菜单", "角色管理模块菜单访问权限", PermissionType.MENU);
        addPermission("P015", "MENU_PERM_MGMT", "权限管理菜单", "权限管理模块菜单访问权限", PermissionType.MENU);
        addPermission("P016", "MENU_AUDIT", "审计日志菜单", "审计日志模块菜单访问权限", PermissionType.MENU);
        addPermission("P017", "MENU_SYSTEM", "系统设置菜单", "系统设置模块菜单访问权限", PermissionType.MENU);
        
        System.out.println(" 完成！共生成 " + storage.getPermissionCount() + " 个权限");
    }
    
    /**
     * 添加权限
     */
    private void addPermission(String id, String code, String name, String desc, PermissionType type) {
        Permission permission = new Permission(id, code, name, desc, type);
        storage.addPermission(permission);
    }
    
    /**
     * 生成角色数据
     */
    private void generateRoles() {
        System.out.print("正在生成角色数据...");
        
        // 创建系统预定义角色
        Role adminRole = new Role("R001", Constants.ROLE_ADMIN, "系统管理员", 
                "拥有所有权限的超级管理员", RoleType.SYSTEM);
        // 为管理员分配所有权限
        for (Permission permission : storage.getAllPermissions()) {
            adminRole.assignPermission(permission);
        }
        storage.addRole(adminRole);
        
        Role managerRole = new Role("R002", Constants.ROLE_MANAGER, "部门经理", 
                "部门管理人员，拥有用户管理权限", RoleType.SYSTEM);
        // 为经理分配用户管理和角色查看权限
        assignPermissionsByCode(managerRole, Constants.PERM_USER_READ, Constants.PERM_USER_UPDATE,
                Constants.PERM_USER_ASSIGN_ROLE, Constants.PERM_ROLE_READ, "MENU_USER_MGMT", "MENU_ROLE_MGMT");
        storage.addRole(managerRole);
        
        Role userRole = new Role("R003", Constants.ROLE_USER, "普通用户", 
                "普通系统用户，只能查看信息", RoleType.SYSTEM);
        // 为普通用户分配查看权限
        assignPermissionsByCode(userRole, Constants.PERM_USER_READ, Constants.PERM_ROLE_READ, "MENU_USER_MGMT");
        storage.addRole(userRole);
        
        Role guestRole = new Role("R004", Constants.ROLE_GUEST, "访客", 
                "访客用户，权限最少", RoleType.SYSTEM);
        // 访客只有基本查看权限
        assignPermissionsByCode(guestRole, Constants.PERM_USER_READ);
        storage.addRole(guestRole);
        
        // 创建自定义角色
        Role auditorRole = new Role("R005", "AUDITOR", "审计员", 
                "负责审计日志查看的角色", RoleType.CUSTOM);
        assignPermissionsByCode(auditorRole, Constants.PERM_AUDIT_VIEW, "MENU_AUDIT");
        storage.addRole(auditorRole);
        
        System.out.println(" 完成！共生成 " + storage.getRoleCount() + " 个角色");
    }
    
    /**
     * 根据权限编码为角色分配权限
     */
    private void assignPermissionsByCode(Role role, String... permissionCodes) {
        for (String code : permissionCodes) {
            Permission permission = storage.getPermissionByCode(code);
            if (permission != null) {
                role.assignPermission(permission);
            }
        }
    }
    
    /**
     * 生成用户数据
     */
    private void generateUsers() {
        System.out.print("正在生成用户数据...");
        
        // 创建管理员用户
        User admin = new User("U001", "admin", Constants.DEFAULT_PASSWORD, "系统管理员");
        admin.setEmail("admin@company.com");
        admin.setPhone("13800138000");
        admin.assignRole(storage.getRoleByCode(Constants.ROLE_ADMIN));
        storage.addUser(admin);
        
        // 创建其他用户
        for (int i = 2; i <= Constants.MIN_USERS; i++) {
            String userId = String.format("U%03d", i);
            String username = "user" + i;
            String realName = generateRandomName();
            
            User user = new User(userId, username, Constants.DEFAULT_PASSWORD, realName);
            user.setEmail(username + getRandomEmailDomain());
            user.setPhone(generateRandomPhone());
            
            // 随机分配1-2个角色
            int roleCount = 1 + random.nextInt(2);
            List<Role> allRoles = storage.getAllRoles();
            Collections.shuffle(allRoles);
            
            for (int j = 0; j < roleCount && j < allRoles.size(); j++) {
                user.assignRole(allRoles.get(j));
            }
            
            storage.addUser(user);
        }
        
        System.out.println(" 完成！共生成 " + storage.getUserCount() + " 名用户");
    }
    
    /**
     * 生成资源数据
     */
    private void generateResources() {
        System.out.print("正在生成资源数据...");
        
        // API资源
        addResource("RES001", "API_USER_LIST", "用户列表API", "/api/users", ResourceType.API);
        addResource("RES002", "API_USER_CREATE", "创建用户API", "/api/users/create", ResourceType.API);
        addResource("RES003", "API_ROLE_LIST", "角色列表API", "/api/roles", ResourceType.API);
        
        // 页面资源
        addResource("RES004", "PAGE_USER_MGMT", "用户管理页面", "/pages/user/manage", ResourceType.PAGE);
        addResource("RES005", "PAGE_ROLE_MGMT", "角色管理页面", "/pages/role/manage", ResourceType.PAGE);
        
        // 菜单资源
        addResource("RES006", "MENU_DASHBOARD", "仪表板菜单", "/menu/dashboard", ResourceType.MENU);
        addResource("RES007", "MENU_SYSTEM", "系统设置菜单", "/menu/system", ResourceType.MENU);
        
        // 文件资源
        addResource("RES008", "FILE_USER_DATA", "用户数据文件", "/files/user_data.xlsx", ResourceType.FILE);
        addResource("RES009", "FILE_AUDIT_LOG", "审计日志文件", "/files/audit.log", ResourceType.FILE);
        addResource("RES010", "FILE_CONFIG", "系统配置文件", "/files/system_config.xml", ResourceType.FILE);
        
        System.out.println(" 完成！共生成 " + storage.getResourceCount() + " 个资源");
    }
    
    /**
     * 添加资源
     */
    private void addResource(String id, String code, String name, String path, ResourceType type) {
        Resource resource = new Resource(id, code, name, path, type);
        storage.addResource(resource);
    }
    
    /**
     * 生成随机姓名
     */
    private String generateRandomName() {
        String surname = Constants.SURNAMES[random.nextInt(Constants.SURNAMES.length)];
        String givenName = Constants.GIVEN_NAMES[random.nextInt(Constants.GIVEN_NAMES.length)];
        
        // 有30%概率生成单字名
        if (random.nextDouble() < 0.3) {
            return surname + givenName.charAt(0);
        }
        
        return surname + givenName;
    }
    
    /**
     * 生成随机手机号
     */
    private String generateRandomPhone() {
        return String.format("1%d%08d", 
                3 + random.nextInt(7), 
                random.nextInt(100000000));
    }
    
    /**
     * 获取随机邮箱域名
     */
    private String getRandomEmailDomain() {
        return Constants.EMAIL_DOMAINS[random.nextInt(Constants.EMAIL_DOMAINS.length)];
    }
    
    /**
     * 打印数据摘要
     */
    private void printDataSummary() {
        System.out.println("\n--- 数据生成摘要 ---");
        System.out.println("用户数量: " + storage.getUserCount());
        System.out.println("角色数量: " + storage.getRoleCount());
        System.out.println("权限数量: " + storage.getPermissionCount());
        System.out.println("资源数量: " + storage.getResourceCount());
        System.out.println("-------------------");
    }
}
