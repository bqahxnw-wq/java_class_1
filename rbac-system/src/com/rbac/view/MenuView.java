package com.rbac.view;

/**
 * 菜单视图类
 */
public class MenuView {
    
    /**
     * 显示主菜单
     */
    public void showMainMenu() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                权限管理系统 - 主菜单");
        System.out.println("=".repeat(60));
        System.out.println("  1. 数据初始化");
        System.out.println("  2. 用户管理");
        System.out.println("  3. 角色管理");
        System.out.println("  4. 权限管理");
        System.out.println("  5. 权限校验");
        System.out.println("  6. 审计日志");
        System.out.println("  7. 系统信息");
        System.out.println("  0. 退出系统");
        System.out.println("=".repeat(60));
    }
    
    /**
     * 显示数据初始化菜单
     */
    public void showInitMenu() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                    数据初始化菜单");
        System.out.println("=".repeat(60));
        System.out.println("  1. 生成初始化数据（用户、角色、权限、资源）");
        System.out.println("  2. 查看数据摘要");
        System.out.println("  3. 重置所有数据");
        System.out.println("  0. 返回主菜单");
        System.out.println("=".repeat(60));
    }
    
    /**
     * 显示用户管理菜单
     */
    public void showUserManagementMenu() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                      用户管理");
        System.out.println("=".repeat(60));
        System.out.println("  1. 查看所有用户");
        System.out.println("  2. 查询用户信息");
        System.out.println("  3. 创建新用户");
        System.out.println("  4. 修改用户信息");
        System.out.println("  5. 删除用户");
        System.out.println("  6. 为用户分配角色");
        System.out.println("  7. 查看用户权限");
        System.out.println("  0. 返回主菜单");
        System.out.println("=".repeat(60));
    }
    
    /**
     * 显示角色管理菜单
     */
    public void showRoleManagementMenu() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                      角色管理");
        System.out.println("=".repeat(60));
        System.out.println("  1. 查看所有角色");
        System.out.println("  2. 查询角色信息");
        System.out.println("  3. 创建新角色");
        System.out.println("  4. 修改角色信息");
        System.out.println("  5. 删除角色");
        System.out.println("  6. 为角色分配权限");
        System.out.println("  7. 查看角色的用户");
        System.out.println("  0. 返回主菜单");
        System.out.println("=".repeat(60));
    }
    
    /**
     * 显示权限管理菜单
     */
    public void showPermissionManagementMenu() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                      权限管理");
        System.out.println("=".repeat(60));
        System.out.println("  1. 查看所有权限");
        System.out.println("  2. 按类型查看权限");
        System.out.println("  3. 查看资源列表");
        System.out.println("  4. 查询权限详情");
        System.out.println("  0. 返回主菜单");
        System.out.println("=".repeat(60));
    }
    
    /**
     * 显示权限校验菜单
     */
    public void showAuthorizationMenu() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                      权限校验");
        System.out.println("=".repeat(60));
        System.out.println("  1. 检查用户权限");
        System.out.println("  2. 检查用户角色");
        System.out.println("  3. 检查资源访问权限");
        System.out.println("  4. 模拟操作权限校验");
        System.out.println("  0. 返回主菜单");
        System.out.println("=".repeat(60));
    }
    
    /**
     * 显示审计日志菜单
     */
    public void showAuditMenu() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                      审计日志");
        System.out.println("=".repeat(60));
        System.out.println("  1. 查看所有审计日志");
        System.out.println("  2. 查看最近的日志");
        System.out.println("  3. 按用户查询日志");
        System.out.println("  4. 按操作类型查询日志");
        System.out.println("  5. 查看失败的操作");
        System.out.println("  6. 操作统计分析");
        System.out.println("  0. 返回主菜单");
        System.out.println("=".repeat(60));
    }
    
    /**
     * 显示欢迎信息
     */
    public void showWelcome() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("          欢迎使用权限管理系统 v1.0");
        System.out.println("=".repeat(60));
        System.out.println("=".repeat(60));
    }
    
    /**
     * 显示退出信息
     */
    public void showGoodbye() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("          感谢使用权限管理系统！");
        System.out.println("                  再见！");
        System.out.println("=".repeat(60));
    }
    
    /**
     * 显示错误提示
     * @param message 错误信息
     */
    public void showError(String message) {
        System.out.println("\n[错误] " + message);
    }
    
    /**
     * 显示成功提示
     * @param message 成功信息
     */
    public void showSuccess(String message) {
        System.out.println("\n[成功] " + message);
    }
    
    /**
     * 显示警告提示
     * @param message 警告信息
     */
    public void showWarning(String message) {
        System.out.println("\n[警告] " + message);
    }
    
    /**
     * 显示信息提示
     * @param message 信息内容
     */
    public void showInfo(String message) {
        System.out.println("\n[信息] " + message);
    }
    
    /**
     * 清屏（模拟效果）
     */
    public void clearScreen() {
        for (int i = 0; i < 2; i++) {
            System.out.println();
        }
    }
}
