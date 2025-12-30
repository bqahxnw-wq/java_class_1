<template>
  <div class="grade-management">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>成绩管理</span>
        </div>
      </template>

      <el-alert
        title="操作说明"
        type="info"
        :closable="false"
        show-icon
        style="margin-bottom: 20px"
      >
        <div style="line-height: 1.8">
          <li>成绩组成：平时成绩(20%) + 期中成绩(20%) + 实验成绩(20%) + 期末成绩(40%)</li>
          <li>成绩生成使用正态分布，确保数据分布合理</li>
          <li>请按顺序生成各项成绩，最后计算综合成绩</li>
          <li>也可以使用"一键生成所有成绩"功能自动完成全部操作</li>
        </div>
      </el-alert>

      <!-- 单项成绩生成 -->
      <el-row :gutter="20">
        <el-col :span="6" v-for="item in gradeTypes" :key="item.type">
          <el-card shadow="hover" class="grade-card">
            <div class="grade-card-content">
              <el-icon :size="40" :color="item.color">
                <component :is="item.icon" />
              </el-icon>
              <div class="grade-info">
                <div class="grade-title">{{ item.title }}</div>
                <div class="grade-weight">权重: {{ item.weight }}</div>
                <div class="grade-range">范围: {{ item.range }}</div>
              </div>
            </div>
            <el-button
              type="primary"
              :loading="loading"
              @click="handleGenerateScore(item.type)"
              style="width: 100%; margin-top: 15px"
            >
              生成{{ item.title }}
            </el-button>
          </el-card>
        </el-col>
      </el-row>

      <el-divider />

      <!-- 综合成绩计算 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-card shadow="hover">
            <div class="action-card">
              <el-icon :size="50" color="#67c23a">
                <DataAnalysis />
              </el-icon>
              <div>
                <div class="action-title">计算综合成绩</div>
                <div class="action-desc">根据各项成绩和权重计算综合成绩</div>
              </div>
            </div>
            <el-button
              type="success"
              size="large"
              :loading="loading"
              @click="handleCalculateTotal"
              style="width: 100%; margin-top: 15px"
            >
              计算综合成绩
            </el-button>
          </el-card>
        </el-col>

        <el-col :span="12">
          <el-card shadow="hover">
            <div class="action-card">
              <el-icon :size="50" color="#409eff">
                <MagicStick />
              </el-icon>
              <div>
                <div class="action-title">一键生成所有成绩</div>
                <div class="action-desc">自动生成所有成绩并计算综合成绩</div>
              </div>
            </div>
            <el-button
              type="primary"
              size="large"
              :loading="loading"
              @click="handleGenerateAll"
              style="width: 100%; margin-top: 15px"
            >
              一键生成所有成绩
            </el-button>
          </el-card>
        </el-col>
      </el-row>

      <el-divider />

      <!-- 成绩生成进度 -->
      <el-descriptions title="成绩生成状态" :column="2" border>
        <el-descriptions-item label="平时成绩">
          <el-tag :type="progress.daily ? 'success' : 'info'">
            {{ progress.daily ? '已生成' : '未生成' }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="期中成绩">
          <el-tag :type="progress.midterm ? 'success' : 'info'">
            {{ progress.midterm ? '已生成' : '未生成' }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="实验成绩">
          <el-tag :type="progress.experiment ? 'success' : 'info'">
            {{ progress.experiment ? '已生成' : '未生成' }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="期末成绩">
          <el-tag :type="progress.final ? 'success' : 'info'">
            {{ progress.final ? '已生成' : '未生成' }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="综合成绩">
          <el-tag :type="progress.total ? 'success' : 'info'">
            {{ progress.total ? '已计算' : '未计算' }}
          </el-tag>
        </el-descriptions-item>
      </el-descriptions>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { Edit, DataAnalysis, MagicStick } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { gradeAPI } from '@/api'
import { useAppStore } from '@/stores/app'

const appStore = useAppStore()
const loading = ref(false)

const gradeTypes = [
  {
    type: 'daily',
    title: '平时成绩',
    weight: '20%',
    range: '70-100',
    color: '#409eff',
    icon: 'Edit'
  },
  {
    type: 'midterm',
    title: '期中成绩',
    weight: '20%',
    range: '50-95',
    color: '#67c23a',
    icon: 'Edit'
  },
  {
    type: 'experiment',
    title: '实验成绩',
    weight: '20%',
    range: '60-98',
    color: '#e6a23c',
    icon: 'Edit'
  },
  {
    type: 'final',
    title: '期末成绩',
    weight: '40%',
    range: '45-95',
    color: '#f56c6c',
    icon: 'Edit'
  }
]

const progress = reactive({
  daily: false,
  midterm: false,
  experiment: false,
  final: false,
  total: false
})

// 生成单项成绩
const handleGenerateScore = async (type) => {
  if (appStore.dataSummary.gradeCount === 0) {
    ElMessage.error('请先完成学生选课！')
    return
  }

  loading.value = true
  try {
    const res = await gradeAPI.generate(type)
    if (res.code === 200) {
      ElMessage.success(res.message)
      progress[type] = true
      checkTotalProgress()
    }
  } finally {
    loading.value = false
  }
}

// 计算综合成绩
const handleCalculateTotal = async () => {
  if (appStore.dataSummary.gradeCount === 0) {
    ElMessage.error('请先完成学生选课！')
    return
  }

  if (!progress.daily || !progress.midterm || !progress.experiment || !progress.final) {
    ElMessage.warning('请先生成所有单项成绩！')
    return
  }

  loading.value = true
  try {
    const res = await gradeAPI.calculateTotal()
    if (res.code === 200) {
      ElMessage.success(res.message)
      progress.total = true
    }
  } finally {
    loading.value = false
  }
}

// 一键生成所有成绩
const handleGenerateAll = async () => {
  if (appStore.dataSummary.gradeCount === 0) {
    ElMessage.error('请先完成学生选课！')
    return
  }

  try {
    await ElMessageBox.confirm(
      '确定要生成所有成绩吗？（包括平时、期中、实验、期末和综合成绩）',
      '确认操作',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'info'
      }
    )
  } catch {
    return
  }

  loading.value = true
  try {
    const res = await gradeAPI.generateAll()
    if (res.code === 200) {
      ElMessage.success(res.message)
      progress.daily = true
      progress.midterm = true
      progress.experiment = true
      progress.final = true
      progress.total = true
    }
  } finally {
    loading.value = false
  }
}

// 检查是否可以计算综合成绩
const checkTotalProgress = () => {
  if (progress.daily && progress.midterm && progress.experiment && progress.final) {
    ElMessage.info('所有单项成绩已生成，可以计算综合成绩了')
  }
}

onMounted(() => {
  // 可以在这里检查当前成绩生成状态
})
</script>

<style scoped>
.card-header {
  font-size: 16px;
  font-weight: bold;
}

.grade-card {
  height: 100%;
}

.grade-card-content {
  display: flex;
  align-items: center;
  gap: 15px;
}

.grade-info {
  flex: 1;
}

.grade-title {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 5px;
}

.grade-weight,
.grade-range {
  font-size: 13px;
  color: #909399;
  margin-top: 3px;
}

.action-card {
  display: flex;
  align-items: center;
  gap: 20px;
  padding: 10px 0;
}

.action-title {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 8px;
}

.action-desc {
  font-size: 14px;
  color: #909399;
}
</style>
