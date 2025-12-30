<template>
  <div class="students">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>学生管理</span>
        </div>
      </template>

      <!-- 搜索栏 -->
      <el-form inline>
        <el-form-item>
          <el-input
            v-model="keyword"
            placeholder="搜索学号或姓名"
            clearable
            style="width: 300px"
            @keyup.enter="handleSearch"
          >
            <template #prefix>
              <el-icon><Search /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :icon="Search" @click="handleSearch">搜索</el-button>
        </el-form-item>
      </el-form>

      <!-- 数据表格 -->
      <el-table
        :data="tableData"
        stripe
        border
        v-loading="loading"
        style="width: 100%; margin-top: 20px"
      >
        <el-table-column type="index" label="序号" width="60" />
        <el-table-column prop="studentId" label="学号" width="150" sortable />
        <el-table-column prop="name" label="姓名" width="120" />
        <el-table-column prop="gender" label="性别" width="80" />
        <el-table-column label="已选课程数" width="120">
          <template #default="{ row }">
            <el-tag type="info">{{ row.enrolledClasses.length }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" fixed="right" width="200">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="handleViewCourses(row)">
              查看选课
            </el-button>
            <el-button type="success" size="small" @click="handleViewGrades(row)">
              查看成绩
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <el-pagination
        v-model:current-page="pagination.page"
        v-model:page-size="pagination.pageSize"
        :total="pagination.total"
        :page-sizes="[10, 20, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSearch"
        @current-change="handleSearch"
        style="margin-top: 20px; justify-content: flex-end"
      />
    </el-card>

    <!-- 选课详情对话框 -->
    <el-dialog v-model="courseDialog.visible" title="学生选课详情" width="800px">
      <el-descriptions :column="2" border>
        <el-descriptions-item label="学号">{{ courseDialog.student?.studentId }}</el-descriptions-item>
        <el-descriptions-item label="姓名">{{ courseDialog.student?.name }}</el-descriptions-item>
        <el-descriptions-item label="性别">{{ courseDialog.student?.gender }}</el-descriptions-item>
        <el-descriptions-item label="选课数量">{{ courseDialog.courses.length }}</el-descriptions-item>
      </el-descriptions>

      <el-table :data="courseDialog.courses" stripe border style="margin-top: 20px">
        <el-table-column type="index" label="序号" width="60" />
        <el-table-column prop="classId" label="教学班号" width="100" />
        <el-table-column prop="course.courseName" label="课程名称" />
        <el-table-column prop="teacher.name" label="授课教师" width="100" />
        <el-table-column prop="semester" label="学期" width="120" />
      </el-table>
    </el-dialog>

    <!-- 成绩详情对话框 -->
    <el-dialog v-model="gradeDialog.visible" title="学生成绩详情" width="1000px">
      <el-descriptions :column="2" border>
        <el-descriptions-item label="学号">{{ gradeDialog.student?.studentId }}</el-descriptions-item>
        <el-descriptions-item label="姓名">{{ gradeDialog.student?.name }}</el-descriptions-item>
      </el-descriptions>

      <el-table :data="gradeDialog.grades" stripe border style="margin-top: 20px">
        <el-table-column prop="teachingClass.course.courseName" label="课程名称" width="150" />
        <el-table-column label="平时" width="80">
          <template #default="{ row }">{{ row.scores.daily || '-' }}</template>
        </el-table-column>
        <el-table-column label="期中" width="80">
          <template #default="{ row }">{{ row.scores.midterm || '-' }}</template>
        </el-table-column>
        <el-table-column label="实验" width="80">
          <template #default="{ row }">{{ row.scores.experiment || '-' }}</template>
        </el-table-column>
        <el-table-column label="期末" width="80">
          <template #default="{ row }">{{ row.scores.final || '-' }}</template>
        </el-table-column>
        <el-table-column label="综合成绩" width="100">
          <template #default="{ row }">
            <el-tag v-if="row.totalScore" :type="getScoreType(row.totalScore)">
              {{ row.totalScore }}
            </el-tag>
            <span v-else>-</span>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { Search } from '@element-plus/icons-vue'
import { studentAPI } from '@/api'

const loading = ref(false)
const keyword = ref('')
const tableData = ref([])

const pagination = reactive({
  page: 1,
  pageSize: 10,
  total: 0
})

const courseDialog = reactive({
  visible: false,
  student: null,
  courses: []
})

const gradeDialog = reactive({
  visible: false,
  student: null,
  grades: []
})

// 搜索
const handleSearch = async () => {
  loading.value = true
  try {
    const res = await studentAPI.getList({
      page: pagination.page,
      pageSize: pagination.pageSize,
      keyword: keyword.value
    })
    tableData.value = res.data.list
    pagination.total = res.data.total
  } finally {
    loading.value = false
  }
}

// 查看选课
const handleViewCourses = async (row) => {
  try {
    const res = await studentAPI.getCourses(row.studentId)
    courseDialog.student = row
    courseDialog.courses = res.data
    courseDialog.visible = true
  } catch (error) {
    console.error('获取选课信息失败:', error)
  }
}

// 查看成绩
const handleViewGrades = async (row) => {
  try {
    const res = await studentAPI.getGrades(row.studentId)
    gradeDialog.student = row
    gradeDialog.grades = res.data
    gradeDialog.visible = true
  } catch (error) {
    console.error('获取成绩信息失败:', error)
  }
}

// 获取成绩类型
const getScoreType = (score) => {
  if (score >= 90) return 'success'
  if (score >= 80) return 'primary'
  if (score >= 70) return 'warning'
  if (score >= 60) return 'info'
  return 'danger'
}

onMounted(() => {
  handleSearch()
})
</script>

<style scoped>
.card-header {
  font-size: 16px;
  font-weight: bold;
}
</style>
