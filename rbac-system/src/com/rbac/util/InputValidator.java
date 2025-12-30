package com.rbac.util;

import java.util.Scanner;

/**
 * 输入验证工具类
 */
public class InputValidator {
    
    private static Scanner scanner = new Scanner(System.in);
    
    /**
     * 获取整数输入
     */
    public static int getIntInput(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                int value = Integer.parseInt(input);
                
                if (value < min || value > max) {
                    System.out.println("输入超出范围，请输入" + min + "到" + max + "之间的数字！");
                    continue;
                }
                
                return value;
            } catch (NumberFormatException e) {
                System.out.println("输入格式错误，请输入有效的数字！");
            }
        }
    }
    
    /**
     * 获取字符串输入
     */
    public static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    /**
     * 获取非空字符串输入
     */
    public static String getNonEmptyString(String prompt) {
        while (true) {
            String input = getStringInput(prompt);
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("输入不能为空，请重新输入！");
        }
    }
    
    /**
     * 获取确认输入（Y/N）
     */
    public static boolean getConfirmation(String prompt) {
        while (true) {
            String input = getStringInput(prompt + " (Y/N): ").toUpperCase();
            if (input.equals("Y") || input.equals("YES")) {
                return true;
            } else if (input.equals("N") || input.equals("NO")) {
                return false;
            }
            System.out.println("请输入Y或N！");
        }
    }
    
    /**
     * 验证用户名格式（3-20个字符，只能包含字母、数字、下划线）
     */
    public static boolean isValidUsername(String username) {
        if (username == null || username.length() < 3 || username.length() > 20) {
            return false;
        }
        return username.matches("^[a-zA-Z0-9_]+$");
    }
    
    /**
     * 验证密码格式（6-20个字符）
     */
    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6 && password.length() <= 20;
    }
    
    /**
     * 验证邮箱格式
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }
    
    /**
     * 验证手机号格式（11位数字）
     */
    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.isEmpty()) {
            return false;
        }
        return phone.matches("^1[3-9]\\d{9}$");
    }
    
    /**
     * 暂停，等待用户按回车继续
     */
    public static void pause() {
        System.out.print("\n按回车键继续...");
        scanner.nextLine();
    }
    
    /**
     * 关闭Scanner
     */
    public static void close() {
        scanner.close();
    }
}
