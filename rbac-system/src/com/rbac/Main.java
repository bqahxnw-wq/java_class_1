package com.rbac;

import com.rbac.controller.SystemController;

/**
 * 权限管理系统 - 主程序入
 **/
public class Main {
    
    public static void main(String[] args) {
        try {
            SystemController systemController = new SystemController();
            systemController.start();
        } catch (Exception e) {
            System.err.println("\n系统运行异常: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
