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
