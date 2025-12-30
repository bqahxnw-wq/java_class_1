<template>
  <div class="course-selection">
    <el-row :gutter="20">
      <!-- 自动选课 -->
      <el-col :span="24">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>自动选课</span>
            </div>
          </template>

          <el-alert
            title="选课规则说明"
            type="info"
            :closable="false"
            show-icon
          >
            <div style="line-height: 1.8">
              <li>每个学生随机选择3-5门课程</li>
              <li>每门课程从可选教学班中选择（负载均衡策略）</li>
              <li>优先选择学生数较少的教学班</li>
              <li>教学班满员后不再分配学生</li>
            </div>
          </el-alert>

          <div style="margin-top: 20px">
            <el-button
              type="primary"
              size="large"
              :icon="Document"
              @click="handleAutoSelect"
              :loading="loading"
            >
              开始自动选课
            </el-button>
          </div>
        </el-card>
      </el-col>

      <!-- 查询教学班学生 -->
      <el-col :span="12">
        <el-card style="margin-top: 20px">
          <template #header>
            <div class="card-header">
              <span>查看教学班学生名单</span>
            </div>
          </template>

          <el-form @submit.prevent="handleQueryClassStudents">
            <el-form-item label="选择教学班">
              <el-select
                v-model="selectedClassId"
                placeholder="请选择教学班"
                filterable
                style="width: 100%"
              >
                <el-option
                  v-for="tc in teachingClasses"
                  :key="tc.classId"
                  :label="`${tc.classId} - ${tc.course.courseName} - ${tc.teacher.name}`"
                  :value="tc.classId"
                />
              </el-select>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" @click="handleQueryClassStudents" :loading="loading">
                查询
              </el-button>
            </el-form-item>
          </el-form>

          <el-divider v-if="classStudents.length > 0" />

          <div v-if="classStudents.length > 0">
            <el-descriptions :column="2" border>
              <el-descriptions-item label="教学班">{{ currentClass?.classId }}</el-descriptions-item>
              <el-descriptions-item label="课程">{{ currentClass?.course?.courseName }}</el-descriptions-item>
              <el-descriptions-item label="教师">{{ currentClass?.teacher?.name }}</el-descriptions-item>
              <el-descriptions-item label="学期">{{ currentClass?.semester }}</el-descriptions-item>
              <el-descriptions-item label="容量">{{ currentClass?.capacity }}</el-descriptions-item>
              <el-descriptions-item label="当前人数">{{ classStudents.length }}</el-descriptions-item>
            </el-descriptions>

            <el-table
              :data="classStudents"
              stripe
              style="width: 100%; margin-top: 20px"
              max-height="400"
            >
              <el-table-column type="index" label="序号" width="60" />
              <el-table-column prop="studentId" label="学号" width="120" />
              <el-table-column prop="name" label="姓名" />
              <el-table-column prop="gender" label="性别" width="80" />
            </el-table>
          </div>

          <el-empty v-else description="暂无数据" />
        </el-card>
      </el-col>

      <!-- 查询学生选课 -->
      <el-col :span="12">
        <el-card style="margin-top: 20px">
          <template #header>
            <div class="card-header">
              <span>查看学生选课情况</span>
            </div>
          </template>

          <el-form @submit.prevent="handleQueryStudentCourses">
            <el-form-item label="学号">
              <el-input
                v-model="studentId"
                placeholder="请输入学号"
                clearable
              />
            </el-form-item>

            <el-form-item>
              <el-button type="primary" @click="handleQueryStudentCourses" :loading="loading">
                查询
              </el-button>
            </el-form-item>
          </el-form>

          <el-divider v-if="studentCourses.length > 0" />

          <div v-if="studentCourses.length > 0">
            <el-descriptions :column="2" border>
              <el-descriptions-item label="学号">{{ currentStudent?.studentId }}</el-descriptions-item>
              <el-descriptions-item label="姓名">{{ currentStudent?.name }}</el-descriptions-item>
              <el-descriptions-item label="性别">{{ currentStudent?.gender }}</el-descriptions-item>
              <el-descriptions-item label="选课数量">{{ studentCourses.length }}</el-descriptions-item>
            </el-descriptions>

            <el-table
              :data="studentCourses"
              stripe
              style="width: 100%; margin-top: 20px"
              max-height="400"
            >
              <el-table-column type="index" label="序号" width="60" />
              <el-table-column prop="classId" label="教学班号" width="100" />
              <el-table-column prop="course.courseName" label="课程名称" />
              <el-table-column prop="teacher.name" label="授课教师" width="100" />
              <el-table-column prop="semester" label="学期" width="120" />
            </el-table>
          </div>

          <el-empty v-else description="暂无数据" />
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { Document } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { courseSelectionAPI, teachingClassAPI, studentAPI } from '@/api'
import { useAppStore } from '@/stores/app'

const appStore = useAppStore()
const loading = ref(false)

const teachingClasses = ref([])
const selectedClassId = ref('')
const classStudents = ref([])
const currentClass = ref(null)

const studentId = ref('')
const studentCourses = ref([])
const currentStudent = ref(null)

// 自动选课
const handleAutoSelect = async () => {
  if (appStore.dataSummary.studentCount === 0 || appStore.dataSummary.teachingClassCount === 0) {
    ElMessage.error('请先生成初始化数据！')
    return
  }

  if (appStore.dataSummary.gradeCount > 0) {
    try {
      await ElMessageBox.confirm(
        '学生已经选过课，是否重新选课？（将清除所有选课和成绩数据）',
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
    const res = await courseSelectionAPI.autoSelect()
    if (res.code === 200) {
      ElMessage.success(res.message)
      await appStore.fetchDataSummary()
    }
  } finally {
    loading.value = false
  }
}

// 查询教学班学生
const handleQueryClassStudents = async () => {
  if (!selectedClassId.value) {
    ElMessage.warning('请选择教学班！')
    return
  }

  loading.value = true
  try {
    const [classRes, studentsRes] = await Promise.all([
      teachingClassAPI.getById(selectedClassId.value),
      teachingClassAPI.getStudents(selectedClassId.value)
    ])
    
    currentClass.value = classRes.data
    classStudents.value = studentsRes.data
    
    if (classStudents.value.length === 0) {
      ElMessage.warning('该教学班暂无学生！')
    }
  } finally {
    loading.value = false
  }
}

// 查询学生选课
const handleQueryStudentCourses = async () => {
  if (!studentId.value) {
    ElMessage.warning('请输入学号！')
    return
  }

  loading.value = true
  try {
    const [studentRes, coursesRes] = await Promise.all([
      studentAPI.getById(studentId.value),
      studentAPI.getCourses(studentId.value)
    ])
    
    if (!studentRes.data) {
      ElMessage.error('未找到该学生！')
      currentStudent.value = null
      studentCourses.value = []
      return
    }

    currentStudent.value = studentRes.data
    studentCourses.value = coursesRes.data
    
    if (studentCourses.value.length === 0) {
      ElMessage.warning('该学生尚未选课！')
    }
  } finally {
    loading.value = false
  }
}

// 加载教学班列表
const loadTeachingClasses = async () => {
  try {
    const res = await teachingClassAPI.getList()
    teachingClasses.value = res.data
  } catch (error) {
    console.error('加载教学班列表失败:', error)
  }
}

onMounted(() => {
  loadTeachingClasses()
})
</script>

<style scoped>
.card-header {
  font-size: 16px;
  font-weight: bold;
}
</style>
