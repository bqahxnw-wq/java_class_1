<template>
  <div class="teaching-classes">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>教学班管理</span>
        </div>
      </template>

      <el-table :data="tableData" stripe border v-loading="loading" style="width: 100%">
        <el-table-column type="index" label="序号" width="60" />
        <el-table-column prop="classId" label="教学班号" width="120" sortable />
        <el-table-column prop="course.courseName" label="课程名称" width="150" />
        <el-table-column prop="teacher.name" label="授课教师" width="120" />
        <el-table-column prop="semester" label="学期" width="130" />
        <el-table-column label="人数" width="150">
          <template #default="{ row }">
            <el-progress
              :percentage="(row.currentSize / row.capacity) * 100"
              :color="getProgressColor(row.currentSize, row.capacity)"
            >
              <span>{{ row.currentSize }}/{{ row.capacity }}</span>
            </el-progress>
          </template>
        </el-table-column>
        <el-table-column label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.currentSize >= row.capacity ? 'danger' : 'success'">
              {{ row.currentSize >= row.capacity ? '已满' : '未满' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" fixed="right" width="150">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="handleViewStudents(row)">
              查看学生
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 学生详情对话框 -->
    <el-dialog v-model="dialog.visible" title="教学班学生名单" width="800px">
      <el-descriptions :column="2" border>
        <el-descriptions-item label="教学班号">{{ dialog.class?.classId }}</el-descriptions-item>
        <el-descriptions-item label="课程名称">{{ dialog.class?.course?.courseName }}</el-descriptions-item>
        <el-descriptions-item label="授课教师">{{ dialog.class?.teacher?.name }}</el-descriptions-item>
        <el-descriptions-item label="学期">{{ dialog.class?.semester }}</el-descriptions-item>
        <el-descriptions-item label="容量">{{ dialog.class?.capacity }}</el-descriptions-item>
        <el-descriptions-item label="当前人数">{{ dialog.students.length }}</el-descriptions-item>
      </el-descriptions>

      <el-table :data="dialog.students" stripe border style="margin-top: 20px" max-height="400">
        <el-table-column type="index" label="序号" width="60" />
        <el-table-column prop="studentId" label="学号" width="120" />
        <el-table-column prop="name" label="姓名" />
        <el-table-column prop="gender" label="性别" width="80" />
      </el-table>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { teachingClassAPI } from '@/api'

const loading = ref(false)
const tableData = ref([])

const dialog = reactive({
  visible: false,
  class: null,
  students: []
})

// 获取进度条颜色
const getProgressColor = (current, capacity) => {
  const ratio = current / capacity
  if (ratio >= 1) return '#f56c6c'
  if (ratio >= 0.8) return '#e6a23c'
  return '#67c23a'
}

// 查看学生
const handleViewStudents = async (row) => {
  try {
    const res = await teachingClassAPI.getStudents(row.classId)
    dialog.class = row
    dialog.students = res.data
    dialog.visible = true
  } catch (error) {
    console.error('获取学生名单失败:', error)
  }
}

// 加载数据
const loadData = async () => {
  loading.value = true
  try {
    const res = await teachingClassAPI.getList()
    tableData.value = res.data
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.card-header {
  font-size: 16px;
  font-weight: bold;
}
</style>
