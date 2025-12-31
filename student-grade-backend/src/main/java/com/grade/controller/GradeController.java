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
