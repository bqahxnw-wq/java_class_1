package com.grade.controller;

import com.grade.common.Result;
import com.grade.dto.LoginDTO;
import com.grade.dto.TokenDTO;
import com.grade.entity.SysUser;
import com.grade.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

/**
 * 认证控制器
 * 
 * @author hx
 */
@Tag(name = "认证管理")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;
    
    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result<TokenDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
        TokenDTO token = authService.login(loginDTO);
        return Result.success(token);
    }
    
    @Operation(summary = "获取当前用户信息")
    @GetMapping("/info")
    public Result<SysUser> getUserInfo(Principal principal) {
        SysUser user = authService.getUserInfo(principal.getName());
        return Result.success(user);
    }
    
    @Operation(summary = "用户登出")
    @PostMapping("/logout")
    public Result<Void> logout() {
        return Result.success("登出成功", null);
    }
}
