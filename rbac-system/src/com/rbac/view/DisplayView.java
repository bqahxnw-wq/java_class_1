package com.rbac.view;

import com.rbac.model.*;

import java.util.*;

/**
 * 显示视图类
 */
public class DisplayView {
    
    /**
     * 显示数据摘要
     */
    public void displayDataSummary(int userCount, int roleCount, int permissionCount, 
                                   int resourceCount, int auditLogCount) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                      数据摘要");
        System.out.println("=".repeat(60));
        System.out.println("  用户数量: " + userCount);
        System.out.println("  角色数量: " + roleCount);
        System.out.println("  权限数量: " + permissionCount);
        System.out.println("  资源数量: " + resourceCount);
        System.out.println("  审计日志数: " + auditLogCount);
        System.out.println("=".repeat(60));
    }
    
    /**
     * 显示用户列表
     */
    public void displayUserList(List<User> users) {
        System.out.println("\n" + "=".repeat(100));
        System.out.println("                                  用户列表");
        System.out.println("=".repeat(100));
        System.out.printf("%-10s %-15s %-12s %-20s %-10s %-5s\n",
                "用户ID", "用户名", "真实姓名", "邮箱", "状态", "角色数");
        System.out.println("-".repeat(100));
        
        for (User user : users) {
            System.out.printf("%-10s %-15s %-12s %-20s %-10s %-5d\n",
                    user.getUserId(),
                    user.getUsername(),
                    user.getRealName(),
                    user.getEmail() != null ? user.getEmail() : "-",
                    user.getStatus().getDescription(),
                    user.getRoles().size());
        }
        
        System.out.println("=".repeat(100));
        System.out.println("共 " + users.size() + " 个用户");
    }
    
    /**
     * 显示用户详情
     */
    public void displayUserDetail(User user) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("                          用户详细信息");
        System.out.println("=".repeat(80));
        System.out.println("用户ID: " + user.getUserId());
        System.out.println("用户名: " + user.getUsername());
        System.out.println("真实姓名: " + user.getRealName());
        System.out.println("邮箱: " + (user.getEmail() != null ? user.getEmail() : "-"));
        System.out.println("电话: " + (user.getPhone() != null ? user.getPhone() : "-"));
        System.out.println("状态: " + user.getStatus().getDescription());
        System.out.println("创建时间: " + formatDateTime(user.getCreateTime()));
        System.out.println("最后登录: " + formatDateTime(user.getLastLoginTime()));
        
        System.out.println("\n拥有的角色 (" + user.getRoles().size() + "):");
        if (user.getRoles().isEmpty()) {
            System.out.println("  无");
        } else {
            for (Role role : user.getRoles()) {
                System.out.println("  - " + role.getRoleName() + " (" + role.getRoleCode() + ")");
            }
        }
        
        System.out.println("\n拥有的权限 (" + user.getAllPermissions().size() + "):");
        if (user.getAllPermissions().isEmpty()) {
            System.out.println("  无");
        } else {
            Map<PermissionType, List<Permission>> permissionsByType = new HashMap<>();
            for (Permission perm : user.getAllPermissions()) {
                permissionsByType.computeIfAbsent(perm.getPermissionType(), k -> new ArrayList<>()).add(perm);
            }
            
            for (Map.Entry<PermissionType, List<Permission>> entry : permissionsByType.entrySet()) {
                System.out.println("  " + entry.getKey().getDescription() + ":");
                for (Permission perm : entry.getValue()) {
                    System.out.println("    - " + perm.getPermissionName() + " (" + perm.getPermissionCode() + ")");
                }
            }
        }
        System.out.println("=".repeat(80));
    }
    
    /**
     * 显示角色列表
     */
    public void displayRoleList(List<Role> roles) {
        System.out.println("\n" + "=".repeat(90));
        System.out.println("                              角色列表");
        System.out.println("=".repeat(90));
        System.out.printf("%-10s %-15s %-20s %-15s %-10s\n",
                "角色ID", "角色编码", "角色名称", "角色类型", "权限数");
        System.out.println("-".repeat(90));
        
        for (Role role : roles) {
            System.out.printf("%-10s %-15s %-20s %-15s %-10d\n",
                    role.getRoleId(),
                    role.getRoleCode(),
                    role.getRoleName(),
                    role.getRoleType().getDescription(),
                    role.getPermissions().size());
        }
        
        System.out.println("=".repeat(90));
        System.out.println("共 " + roles.size() + " 个角色");
    }
    
    /**
     * 显示角色详情
     */
    public void displayRoleDetail(Role role) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("                          角色详细信息");
        System.out.println("=".repeat(80));
        System.out.println("角色ID: " + role.getRoleId());
        System.out.println("角色编码: " + role.getRoleCode());
        System.out.println("角色名称: " + role.getRoleName());
        System.out.println("角色类型: " + role.getRoleType().getDescription());
        System.out.println("描述: " + (role.getDescription() != null ? role.getDescription() : "-"));
        System.out.println("创建时间: " + formatDateTime(role.getCreateTime()));
        
        System.out.println("\n拥有的权限 (" + role.getPermissions().size() + "):");
        if (role.getPermissions().isEmpty()) {
            System.out.println("  无");
        } else {
            Map<PermissionType, List<Permission>> permissionsByType = new HashMap<>();
            for (Permission perm : role.getPermissions()) {
                permissionsByType.computeIfAbsent(perm.getPermissionType(), k -> new ArrayList<>()).add(perm);
            }
            
            for (Map.Entry<PermissionType, List<Permission>> entry : permissionsByType.entrySet()) {
                System.out.println("  " + entry.getKey().getDescription() + ":");
                for (Permission perm : entry.getValue()) {
                    System.out.println("    - " + perm.getPermissionName() + " (" + perm.getPermissionCode() + ")");
                }
            }
        }
        System.out.println("=".repeat(80));
    }
    
    /**
     * 显示权限列表
     */
    public void displayPermissionList(List<Permission> permissions) {
        System.out.println("\n" + "=".repeat(100));
        System.out.println("                                  权限列表");
        System.out.println("=".repeat(100));
        System.out.printf("%-10s %-20s %-25s %-15s\n",
                "权限ID", "权限编码", "权限名称", "权限类型");
        System.out.println("-".repeat(100));
        
        for (Permission permission : permissions) {
            System.out.printf("%-10s %-20s %-25s %-15s\n",
                    permission.getPermissionId(),
                    permission.getPermissionCode(),
                    permission.getPermissionName(),
                    permission.getPermissionType().getDescription());
        }
        
        System.out.println("=".repeat(100));
        System.out.println("共 " + permissions.size() + " 个权限");
    }
    
    /**
     * 显示资源列表
     */
    public void displayResourceList(List<Resource> resources) {
        System.out.println("\n" + "=".repeat(100));
        System.out.println("                                  资源列表");
        System.out.println("=".repeat(100));
        System.out.printf("%-10s %-20s %-25s %-30s %-10s\n",
                "资源ID", "资源编码", "资源名称", "资源路径", "资源类型");
        System.out.println("-".repeat(100));
        
        for (Resource resource : resources) {
            System.out.printf("%-10s %-20s %-25s %-30s %-10s\n",
                    resource.getResourceId(),
                    resource.getResourceCode(),
                    resource.getResourceName(),
                    resource.getResourcePath(),
                    resource.getResourceType().getDescription());
        }
        
        System.out.println("=".repeat(100));
        System.out.println("共 " + resources.size() + " 个资源");
    }
    
    /**
     * 显示审计日志列表
     */
    public void displayAuditLogs(List<AuditLog> logs) {
        System.out.println("\n" + "=".repeat(120));
        System.out.println("                                      审计日志");
        System.out.println("=".repeat(120));
        System.out.printf("%-10s %-20s %-15s %-20s %-30s %-10s\n",
                "日志ID", "操作时间", "用户名", "操作类型", "操作目标", "结果");
        System.out.println("-".repeat(120));
        
        for (AuditLog log : logs) {
            System.out.printf("%-10s %-20s %-15s %-20s %-30s %-10s\n",
                    log.getLogId(),
                    log.getFormattedTime(),
                    log.getUsername(),
                    log.getOperation(),
                    log.getTarget(),
                    log.isSuccess() ? "成功" : "失败");
        }
        
        System.out.println("=".repeat(120));
        System.out.println("共 " + logs.size() + " 条日志");
    }
    
    /**
     * 显示操作统计
     */
    public void displayOperationStatistics(Map<String, Long> statistics) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                  操作统计分析");
        System.out.println("=".repeat(60));
        System.out.printf("%-30s %-10s\n", "操作类型", "次数");
        System.out.println("-".repeat(60));
        
        // 按次数降序排列
        statistics.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .forEach(entry -> {
                    System.out.printf("%-30s %-10d\n", entry.getKey(), entry.getValue());
                });
        
        System.out.println("=".repeat(60));
        long total = statistics.values().stream().mapToLong(Long::longValue).sum();
        System.out.println("总操作次数: " + total);
    }
    
    /**
     * 格式化日期时间
     */
    private String formatDateTime(java.time.LocalDateTime dateTime) {
        if (dateTime == null) {
            return "-";
        }
        java.time.format.DateTimeFormatter formatter = 
                java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }
}
