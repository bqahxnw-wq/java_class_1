#!/bin/bash

# 创建AuthService
cat > src/main/java/com/grade/service/AuthService.java << 'EOF'
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
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new BusinessException(ResultCode.PASSWORD_ERROR);
        }
        
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
EOF

# 创建GradeService
cat > src/main/java/com/grade/service/GradeService.java << 'EOF'
package com.grade.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.grade.common.BusinessException;
import com.grade.common.ResultCode;
import com.grade.dao.GradeMapper;
import com.grade.entity.Grade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 成绩服务
 * 
 * @author hx
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GradeService {
    
    private final GradeMapper gradeMapper;
    
    /**
     * 查询学生成绩
     */
    public List<Grade> getStudentGrades(Long studentId) {
        return gradeMapper.selectByStudentIdWithDetail(studentId);
    }
    
    /**
     * 查询教学班成绩
     */
    public List<Grade> getClassGrades(Long teachingClassId) {
        return gradeMapper.selectByTeachingClassIdWithDetail(teachingClassId);
    }
    
    /**
     * 录入平时成绩
     */
    @Transactional(rollbackFor = Exception.class)
    public void inputRegularScore(Long gradeId, Integer score) {
        Grade grade = gradeMapper.selectById(gradeId);
        if (grade == null) {
            throw new BusinessException(ResultCode.GRADE_NOT_EXIST);
        }
        
        grade.setRegularScore(score);
        grade.setRegularScoreTime(LocalDateTime.now());
        gradeMapper.updateById(grade);
        
        log.info("录入平时成绩: gradeId={}, score={}", gradeId, score);
    }
    
    /**
     * 计算综合成绩
     * 综合成绩 = 平时20% + 期中20% + 实验20% + 期末40%
     */
    @Transactional(rollbackFor = Exception.class)
    public void calculateTotalScore(Long gradeId) {
        Grade grade = gradeMapper.selectById(gradeId);
        if (grade == null) {
            throw new BusinessException(ResultCode.GRADE_NOT_EXIST);
        }
        
        // 检查是否所有成绩都已录入
        if (grade.getRegularScore() == null || grade.getMidtermScore() == null ||
            grade.getExperimentScore() == null || grade.getFinalScore() == null) {
            throw new BusinessException(ResultCode.GRADE_NOT_COMPLETE);
        }
        
        // 计算综合成绩
        double total = grade.getRegularScore() * 0.2 +
                      grade.getMidtermScore() * 0.2 +
                      grade.getExperimentScore() * 0.2 +
                      grade.getFinalScore() * 0.4;
        
        grade.setTotalScore((int) Math.round(total));
        grade.setTotalScoreTime(LocalDateTime.now());
        
        // 设置等级和及格状态
        int totalScore = grade.getTotalScore();
        if (totalScore >= 90) {
            grade.setGradeLevel("A");
        } else if (totalScore >= 80) {
            grade.setGradeLevel("B");
        } else if (totalScore >= 70) {
            grade.setGradeLevel("C");
        } else if (totalScore >= 60) {
            grade.setGradeLevel("D");
        } else {
            grade.setGradeLevel("F");
        }
        
        grade.setIsPass(totalScore >= 60 ? 1 : 0);
        grade.setIsCompleted(1);
        
        gradeMapper.updateById(grade);
        
        log.info("计算综合成绩: gradeId={}, totalScore={}", gradeId, grade.getTotalScore());
    }
    
    /**
     * 成绩统计
     */
    public Map<String, Object> getGradeStatistics(Long teachingClassId) {
        return gradeMapper.selectGradeDistribution(teachingClassId);
    }
}
EOF

# 创建AuthController
cat > src/main/java/com/grade/controller/AuthController.java << 'EOF'
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
EOF

# 创建GradeController
cat > src/main/java/com/grade/controller/GradeController.java << 'EOF'
package com.grade.controller;

import com.grade.common.Result;
import com.grade.entity.Grade;
import com.grade.service.GradeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 成绩控制器
 * 
 * @author hx
 */
@Tag(name = "成绩管理")
@RestController
@RequestMapping("/grades")
@RequiredArgsConstructor
public class GradeController {
    
    private final GradeService gradeService;
    
    @Operation(summary = "查询学生成绩")
    @GetMapping("/student/{studentId}")
    public Result<List<Grade>> getStudentGrades(@PathVariable Long studentId) {
        List<Grade> grades = gradeService.getStudentGrades(studentId);
        return Result.success(grades);
    }
    
    @Operation(summary = "查询教学班成绩")
    @GetMapping("/class/{teachingClassId}")
    public Result<List<Grade>> getClassGrades(@PathVariable Long teachingClassId) {
        List<Grade> grades = gradeService.getClassGrades(teachingClassId);
        return Result.success(grades);
    }
    
    @Operation(summary = "录入平时成绩")
    @PostMapping("/regular")
    public Result<Void> inputRegularScore(@RequestParam Long gradeId, @RequestParam Integer score) {
        gradeService.inputRegularScore(gradeId, score);
        return Result.success("录入成功", null);
    }
    
    @Operation(summary = "计算综合成绩")
    @PostMapping("/calculate/{gradeId}")
    public Result<Void> calculateTotalScore(@PathVariable Long gradeId) {
        gradeService.calculateTotalScore(gradeId);
        return Result.success("计算成功", null);
    }
    
    @Operation(summary = "成绩统计")
    @GetMapping("/statistics/{teachingClassId}")
    public Result<Map<String, Object>> getStatistics(@PathVariable Long teachingClassId) {
        Map<String, Object> statistics = gradeService.getGradeStatistics(teachingClassId);
        return Result.success(statistics);
    }
}
EOF

# 创建SecurityConfig
cat > src/main/java/com/grade/config/SecurityConfig.java << 'EOF'
package com.grade.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security配置
 * 
 * @author hx
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/auth/login", "/swagger-ui/**", "/v3/api-docs/**", "/druid/**").permitAll()
            .anyRequest().authenticated();
        
        return http.build();
    }
}
EOF

# 创建CorsConfig
cat > src/main/java/com/grade/config/CorsConfig.java << 'EOF'
package com.grade.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域配置
 * 
 * @author hx
 */
@Configuration
public class CorsConfig {
    
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOriginPattern("*");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        
        return new CorsFilter(source);
    }
}
EOF

# 创建MyBatisPlusConfig
cat > src/main/java/com/grade/config/MyBatisPlusConfig.java << 'EOF'
package com.grade.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis-Plus配置
 * 
 * @author hx
 */
@Configuration
public class MyBatisPlusConfig {
    
    /**
     * 分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
EOF

# 创建SwaggerConfig
cat > src/main/java/com/grade/config/SwaggerConfig.java << 'EOF'
package com.grade.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger配置
 * 
 * @author hx
 */
@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("学生成绩管理系统API")
                        .version("1.0.0")
                        .description("学生成绩管理系统后端接口文档")
                        .contact(new Contact()
                                .name("张卉馨")
                                .email("example@example.com")));
    }
}
EOF

echo "核心代码创建完成!"
