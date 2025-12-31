package com.grade;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 学生成绩管理系统启动类
 * 
 * @author hx
 * @version 1.0
 */
@SpringBootApplication
@MapperScan("com.grade.dao")
@EnableTransactionManagement
@EnableCaching
@EnableAsync
public class StudentGradeApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentGradeApplication.class, args);
        System.out.println("======================================");
        System.out.println("学生成绩管理系统启动成功!");
        System.out.println("Swagger文档地址: http://localhost:8080/api/swagger-ui.html");
        System.out.println("======================================");
    }
}
