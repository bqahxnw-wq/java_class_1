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
