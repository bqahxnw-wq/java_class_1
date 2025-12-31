package com.grade.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Token响应DTO
 * 
 * @author hx
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO {
    
    /**
     * 访问令牌
     */
    private String token;
    
    /**
     * 令牌类型
     */
    private String tokenType = "Bearer";
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 真实姓名
     */
    private String realName;
    
    /**
     * 用户类型
     */
    private String userType;
    
    public TokenDTO(String token, String username, String realName, String userType) {
        this.token = token;
        this.username = username;
        this.realName = realName;
        this.userType = userType;
    }
}
