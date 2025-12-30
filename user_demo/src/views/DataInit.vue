<template>
  <div class="data-init">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>数据初始化管理</span>
        </div>
      </template>

      <!-- 数据摘要 -->
      <el-descriptions title="当前数据概况" :column="2" border>
        <el-descriptions-item label="学生数量">
          <el-tag type="primary" size="large">{{ dataSummary.studentCount }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="教师数量">
          <el-tag type="success" size="large">{{ dataSummary.teacherCount }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="课程数量">
          <el-tag type="warning" size="large">{{ dataSummary.courseCount }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="教学班数量">
          <el-tag type="danger" size="large">{{ dataSummary.teachingClassCount }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="成绩记录数">
          <el-tag type="info" size="large">{{ dataSummary.gradeCount }}</el-tag>
        </el-descriptions-item>
      </el-descriptions>

      <!-- 操作按钮 -->
      <el-divider />
      
      <el-space wrap :size="20">
        <el-button
          type="primary"
          size="large"
          :icon="Setting"
          @click="handleInit"
          :loading="loading"
        >
          生成初始化数据
        </el-button>

        <el-button
          type="danger"
          size="large"
          :icon="Delete"
          @click="handleReset"
          :loading="loading"
        >
          重置所有数据
        </el-button>

        <el-button
          type="info"
          size="large"
          :icon="Refresh"
          @click="handleRefresh"
          :loading="loading"
        >
          刷新数据摘要
        </el-button>
      </el-space>

      <!-- 说明信息 -->
      <el-divider />
      
      <el-alert
        title="操作说明"
        type="info"
        :closable="false"
        show-icon
      >
        <template #default>
          <div style="line-height: 1.8">
            <p><strong>1. 生成初始化数据：</strong>系统将自动生成以下数据</p>
            <ul style="margin-left: 20px;">
              <li>100名学生（学号格式：2023XXXXXX）</li>
              <li>10名教师（包括教授、副教授、讲师、助教）</li>
              <li>12门课程（计算机相关专业课程）</li>
              <li>每门课程2-3个教学班（不同教师授课）</li>
            </ul>
            <p style="margin-top: 10px;"><strong>2. 重置所有数据：</strong>清空系统中的所有数据，包括学生、教师、课程、教学班和成绩数据</p>
            <p><strong>3. 刷新数据摘要：</strong>重新加载并显示最新的数据统计信息</p>
          </div>
        </template>
      </el-alert>

      <!-- 数据生成规则 -->
      <el-divider />
      
      <el-descriptions title="数据生成规则" :column="1" border>
        <el-descriptions-item label="学生数量">不少于100名</el-descriptions-item>
        <el-descriptions-item label="教师数量">不少于6名</el-descriptions-item>
        <el-descriptions-item label="课程数量">不少于3门</el-descriptions-item>
        <el-descriptions-item label="教学班容量">20-40人/班</el-descriptions-item>
        <el-descriptions-item label="每门课教师数">至少2名教师</el-descriptions-item>
        <el-descriptions-item label="学生选课数">每人3-5门课程</el-descriptions-item>
      </el-descriptions>
    </el-card>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { Setting, Delete, Refresh } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useAppStore } from '@/stores/app'

const appStore = useAppStore()
const loading = ref(false)

const dataSummary = computed(() => appStore.dataSummary)

// 生成初始化数据
const handleInit = async () => {
  if (dataSummary.value.studentCount > 0) {
    try {
      await ElMessageBox.confirm(
        '系统中已有数据，是否重新生成？（将清除所有现有数据）',
        '确认操作',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }
      )
    } catch {
      return
    }
  }

  loading.value = true
  try {
    const success = await appStore.initData()
    if (success) {
      ElMessage.success('初始化数据生成成功！')
    } else {
      ElMessage.error('初始化数据生成失败！')
    }
  } finally {
    loading.value = false
  }
}

// 重置所有数据
const handleReset = async () => {
  if (dataSummary.value.studentCount === 0) {
    ElMessage.warning('系统中没有数据！')
    return
  }

  try {
    await ElMessageBox.confirm(
      '确定要清除所有数据吗？此操作不可恢复！',
      '警告',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }
    )
  } catch {
    return
  }

  loading.value = true
  try {
    const success = await appStore.resetData()
    if (success) {
      ElMessage.success('所有数据已清除！')
    } else {
      ElMessage.error('数据清除失败！')
    }
  } finally {
    loading.value = false
  }
}

// 刷新数据摘要
const handleRefresh = async () => {
  loading.value = true
  try {
    await appStore.fetchDataSummary()
    ElMessage.success('数据摘要已刷新！')
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  appStore.fetchDataSummary()
})
</script>

<style scoped>
.data-init {
  width: 100%;
}

.card-header {
  font-size: 16px;
  font-weight: bold;
}

.el-descriptions {
  margin-top: 20px;
}

.el-divider {
  margin: 30px 0;
}
</style>
