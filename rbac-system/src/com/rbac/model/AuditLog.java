package com.rbac.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 审计日志实体类
 */
public class AuditLog {
    private String logId;               // 日志ID
    private String userId;              // 操作用户ID
    private String username;            // 操作用户名
    private String operation;           // 操作类型
    private String target;              // 操作目标
    private String detail;              // 操作详情
    private boolean success;            // 操作是否成功
    private String errorMessage;        // 错误信息（如果失败）
    private LocalDateTime operationTime; // 操作时间
    
    public AuditLog(String logId, String userId, String username, String operation, 
                   String target, String detail, boolean success) {
        this.logId = logId;
        this.userId = userId;
        this.username = username;
        this.operation = operation;
        this.target = target;
        this.detail = detail;
        this.success = success;
        this.operationTime = LocalDateTime.now();
    }
    
    // Getter和Setter方法
    public String getLogId() {
        return logId;
    }
    
    public void setLogId(String logId) {
        this.logId = logId;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getOperation() {
        return operation;
    }
    
    public void setOperation(String operation) {
        this.operation = operation;
    }
    
    public String getTarget() {
        return target;
    }
    
    public void setTarget(String target) {
        this.target = target;
    }
    
    public String getDetail() {
        return detail;
    }
    
    public void setDetail(String detail) {
        this.detail = detail;
    }
    
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    public LocalDateTime getOperationTime() {
        return operationTime;
    }
    
    public void setOperationTime(LocalDateTime operationTime) {
        this.operationTime = operationTime;
    }
    
    /**
     * 格式化操作时间
     */
    public String getFormattedTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return operationTime.format(formatter);
    }
    
    @Override
    public String toString() {
        return String.format("[%s] 用户:%s 操作:%s 目标:%s 结果:%s", 
                getFormattedTime(), username, operation, target, success ? "成功" : "失败");
    }
}
