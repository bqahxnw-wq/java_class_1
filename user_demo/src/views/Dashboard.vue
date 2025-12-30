<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <!-- 数据统计卡片 -->
      <el-col :xs="12" :sm="12" :md="6" :lg="6" v-for="item in statsCards" :key="item.title">
        <el-card class="stats-card" :body-style="{ padding: '20px' }">
          <div class="stats-content">
            <div class="stats-info">
              <div class="stats-title">{{ item.title }}</div>
              <div class="stats-value">{{ item.value }}</div>
            </div>
            <div class="stats-icon" :style="{ background: item.color }">
              <el-icon :size="32">
                <component :is="item.icon" />
              </el-icon>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 快捷操作 -->
    <el-card class="quick-actions" style="margin-top: 20px">
      <template #header>
        <div class="card-header">
          <span>快捷操作</span>
        </div>
      </template>
      
      <el-row :gutter="20">
        <el-col :xs="24" :sm="12" :md="8" :lg="6" v-for="action in quickActions" :key="action.title">
          <div class="action-item" @click="handleAction(action.path)">
            <el-icon :size="40" :color="action.color">
              <component :is="action.icon" />
            </el-icon>
            <div class="action-title">{{ action.title }}</div>
            <div class="action-desc">{{ action.desc }}</div>
          </div>
        </el-col>
      </el-row>
    </el-card>

    <!-- 系统说明 -->
    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="12">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>系统功能</span>
            </div>
          </template>
          <el-timeline>
            <el-timeline-item
              v-for="(item, index) in features"
              :key="index"
              :timestamp="item.module"
              placement="top"
            >
              <el-card>
                <h4>{{ item.title }}</h4>
                <p>{{ item.desc }}</p>
              </el-card>
            </el-timeline-item>
          </el-timeline>
        </el-card>
      </el-col>

      <el-col :span="12">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>系统信息</span>
            </div>
          </template>
          <el-descriptions :column="1" border>
            <el-descriptions-item label="系统名称">学生成绩管理系统</el-descriptions-item>
            <el-descriptions-item label="系统版本">v1.0</el-descriptions-item>
            <el-descriptions-item label="开发框架">Vue 3 + Element Plus</el-descriptions-item>
            <el-descriptions-item label="设计模式">MVC (Model-View-Controller)</el-descriptions-item>
            <el-descriptions-item label="数据存储">内存存储（Mock数据）</el-descriptions-item>
            <el-descriptions-item label="开发时间">2025年12月</el-descriptions-item>
          </el-descriptions>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useAppStore } from '@/stores/app'

const router = useRouter()
const appStore = useAppStore()

// 统计卡片数据
const statsCards = computed(() => [
  {
    title: '学生总数',
    value: appStore.dataSummary.studentCount,
    icon: 'User',
    color: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)'
  },
  {
    title: '教师总数',
    value: appStore.dataSummary.teacherCount,
    icon: 'UserFilled',
    color: 'linear-gradient(135deg, #f093fb 0%, #f5576c 100%)'
  },
  {
    title: '课程总数',
    value: appStore.dataSummary.courseCount,
    icon: 'Reading',
    color: 'linear-gradient(135deg, #4facfe 0%, #00f2fe 100%)'
  },
  {
    title: '教学班总数',
    value: appStore.dataSummary.teachingClassCount,
    icon: 'School',
    color: 'linear-gradient(135deg, #43e97b 0%, #38f9d7 100%)'
  }
])

// 快捷操作
const quickActions = [
  {
    title: '数据初始化',
    desc: '生成系统初始数据',
    icon: 'Setting',
    color: '#409eff',
    path: '/data-init'
  },
  {
    title: '选课管理',
    desc: '自动为学生选课',
    icon: 'Document',
    color: '#67c23a',
    path: '/course-selection'
  },
  {
    title: '成绩管理',
    desc: '生成和管理成绩',
    icon: 'Edit',
    color: '#e6a23c',
    path: '/grade-management'
  },
  {
    title: '查询统计',
    desc: '查询成绩和统计分析',
    icon: 'DataAnalysis',
    color: '#f56c6c',
    path: '/query-statistics'
  }
]

// 系统功能
const features = [
  {
    module: '模块一',
    title: '数据初始化',
    desc: '生成学生、教师、课程、教学班等基础数据'
  },
  {
    module: '模块二',
    title: '选课管理',
    desc: '自动为学生分配课程和教学班，支持负载均衡'
  },
  {
    module: '模块三',
    title: '成绩管理',
    desc: '生成平时、期中、实验、期末成绩，计算综合成绩'
  },
  {
    module: '模块四',
    title: '查询统计',
    desc: '支持多维度成绩查询、排名和统计分析'
  }
]

const handleAction = (path) => {
  router.push(path)
}

onMounted(() => {
  appStore.fetchDataSummary()
})
</script>

<style scoped>
.dashboard {
  width: 100%;
}

.stats-card {
  margin-bottom: 20px;
  cursor: pointer;
  transition: all 0.3s;
}

.stats-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

.stats-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.stats-info {
  flex: 1;
}

.stats-title {
  font-size: 14px;
  color: #909399;
  margin-bottom: 8px;
}

.stats-value {
  font-size: 28px;
  font-weight: bold;
  color: #303133;
}

.stats-icon {
  width: 60px;
  height: 60px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-weight: bold;
}

.quick-actions .action-item {
  text-align: center;
  padding: 30px 20px;
  cursor: pointer;
  border-radius: 8px;
  transition: all 0.3s;
  margin-bottom: 20px;
}

.quick-actions .action-item:hover {
  background: #f5f7fa;
  transform: translateY(-3px);
}

.action-title {
  margin-top: 12px;
  font-size: 16px;
  font-weight: bold;
  color: #303133;
}

.action-desc {
  margin-top: 8px;
  font-size: 13px;
  color: #909399;
}
</style>
