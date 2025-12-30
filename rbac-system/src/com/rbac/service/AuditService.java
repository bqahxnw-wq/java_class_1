package com.rbac.service;

import com.rbac.model.*;
import com.rbac.util.DataStorage;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 审计服务类
 */
public class AuditService {
    
    private DataStorage storage = DataStorage.getInstance();
    private static int logIdCounter = 1;
    
    /**
     * 记录用户操作
     */
    public void logUserOperation(User user, String operation, String target, 
                                 String detail, boolean success) {
        String logId = String.format("LOG%06d", logIdCounter++);
        AuditLog log = new AuditLog(logId, user.getUserId(), user.getUsername(),
                operation, target, detail, success);
        storage.addAuditLog(log);
    }
    
    /**
     * 记录用户操作（带错误信息）
     */
    public void logUserOperation(User user, String operation, String target, 
                                 String detail, boolean success, String errorMessage) {
        String logId = String.format("LOG%06d", logIdCounter++);
        AuditLog log = new AuditLog(logId, user.getUserId(), user.getUsername(),
                operation, target, detail, success);
        if (!success && errorMessage != null) {
            log.setErrorMessage(errorMessage);
        }
        storage.addAuditLog(log);
    }
    
    /**
     * 记录权限校验
     */
    public void logPermissionCheck(User user, String permissionCode, boolean success, String detail) {
        logUserOperation(user, "权限校验", "权限[" + permissionCode + "]", detail, success);
    }
    
    /**
     * 记录登录操作
     */
    public void logLogin(String username, boolean success, String detail) {
        String logId = String.format("LOG%06d", logIdCounter++);
        AuditLog log = new AuditLog(logId, "", username, "用户登录", "系统", detail, success);
        storage.addAuditLog(log);
    }
    
    /**
     * 获取所有审计日志
     */
    public List<AuditLog> getAllAuditLogs() {
        return storage.getAllAuditLogs();
    }
    
    /**
     * 获取指定用户的审计日志
     */
    public List<AuditLog> getAuditLogsByUser(String userId) {
        return storage.getAuditLogsByUser(userId);
    }
    
    /**
     * 获取最近的审计日志
     */
    public List<AuditLog> getRecentAuditLogs(int count) {
        List<AuditLog> allLogs = storage.getAllAuditLogs();
        int size = allLogs.size();
        int start = Math.max(0, size - count);
        return allLogs.subList(start, size);
    }
    
    /**
     * 按操作类型筛选审计日志
     */
    public List<AuditLog> getAuditLogsByOperation(String operation) {
        return storage.getAllAuditLogs().stream()
                .filter(log -> log.getOperation().equals(operation))
                .collect(Collectors.toList());
    }
    
    /**
     * 获取失败的操作日志
     */
    public List<AuditLog> getFailedOperations() {
        return storage.getAllAuditLogs().stream()
                .filter(log -> !log.isSuccess())
                .collect(Collectors.toList());
    }
    
    /**
     * 统计用户操作次数
     */
    public int getUserOperationCount(String userId) {
        return (int) storage.getAllAuditLogs().stream()
                .filter(log -> log.getUserId().equals(userId))
                .count();
    }
    
    /**
     * 统计操作类型分布
     */
    public Map<String, Long> getOperationStatistics() {
        return storage.getAllAuditLogs().stream()
                .collect(Collectors.groupingBy(AuditLog::getOperation, Collectors.counting()));
    }
    
    /**
     * 清空审计日志
     */
    public void clearAuditLogs() {
        storage.getAllAuditLogs().clear();
        logIdCounter = 1;
    }
}
