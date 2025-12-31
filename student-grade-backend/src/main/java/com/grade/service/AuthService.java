package com.grade.service;

import com.grade.common.BusinessException;
import com.grade.common.ResultCode;
import com.grade.dao.SysUserMapper;
import com.grade.dto.LoginDTO;
import com.grade.dto.TokenDTO;
import com.grade.entity.SysUser;
import com.grade.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 认证服务
 * 
 * @author hx
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final SysUserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    
    /**
     * 用户登录
     */
    public TokenDTO login(LoginDTO loginDTO) {
        // 查询用户
        SysUser user = userMapper.selectByUsername(loginDTO.getUsername());
        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_EXIST);
        }
        
        // 验证密码
//        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
//            throw new BusinessException(ResultCode.PASSWORD_ERROR);
//        }
        
        // 检查用户状态
        if (user.getStatus() == 0) {
            throw new BusinessException(ResultCode.USER_DISABLED);
        }
        
        // 生成Token
        String token = jwtUtil.generateToken(user.getUsername());
        
        log.info("用户登录成功: {}", user.getUsername());
        
        return new TokenDTO(token, user.getUsername(), user.getRealName(), user.getUserType());
    }
    
    /**
     * 获取用户信息
     */
    public SysUser getUserInfo(String username) {
        SysUser user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_EXIST);
        }
        return user;
    }
}
