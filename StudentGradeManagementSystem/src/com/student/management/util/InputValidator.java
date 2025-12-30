package com.student.management.util;

import java.util.Scanner;

/**
 * 输入验证工具类
 * 提供各种输入验证和获取方法
 */
public class InputValidator {
    
    private static Scanner scanner = new Scanner(System.in);
    
    /**
     * 获取整数输入
     * @param prompt 提示信息
     * @param min 最小值
     * @param max 最大值
     * @return 输入的整数
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
     * @param prompt 提示信息
     * @return 输入的字符串
     */
    public static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    /**
     * 获取非空字符串输入
     * @param prompt 提示信息
     * @return 输入的字符串
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
     * @param prompt 提示信息
     * @return true表示确认，false表示取消
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
