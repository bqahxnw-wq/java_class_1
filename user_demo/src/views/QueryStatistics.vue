<template>
  <div class="query-statistics">
    <el-tabs v-model="activeTab" type="border-card">
      <!-- 学生成绩查询 -->
      <el-tab-pane label="学生成绩查询" name="student">
        <el-form inline @submit.prevent="handleQueryStudent">
          <el-form-item label="学号">
            <el-input v-model="studentQuery.studentId" placeholder="请输入学号" clearable style="width: 200px" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleQueryStudent" :loading="loading">查询</el-button>
          </el-form-item>
        </el-form>

        <div v-if="studentGrades.length > 0">
          <el-descriptions :column="3" border style="margin-bottom: 20px">
            <el-descriptions-item label="学号">{{ currentStudent?.studentId }}</el-descriptions-item>
            <el-descriptions-item label="姓名">{{ currentStudent?.name }}</el-descriptions-item>
            <el-descriptions-item label="性别">{{ currentStudent?.gender }}</el-descriptions-item>
          </el-descriptions>

          <el-table :data="studentGrades" stripe border>
            <el-table-column prop="teachingClass.course.courseName" label="课程名称" width="150" />
            <el-table-column prop="teachingClass.teacher.name" label="授课教师" width="100" />
            <el-table-column label="平时成绩" width="100">
              <template #default="{ row }">
                {{ row.scores.daily || '-' }}
              </template>
            </el-table-column>
            <el-table-column label="期中成绩" width="100">
              <template #default="{ row }">
                {{ row.scores.midterm || '-' }}
              </template>
            </el-table-column>
            <el-table-column label="实验成绩" width="100">
              <template #default="{ row }">
                {{ row.scores.experiment || '-' }}
              </template>
            </el-table-column>
            <el-table-column label="期末成绩" width="100">
              <template #default="{ row }">
                {{ row.scores.final || '-' }}
              </template>
            </el-table-column>
            <el-table-column label="综合成绩" width="100">
              <template #default="{ row }">
                <el-tag :type="getScoreType(row.totalScore)">
                  {{ row.totalScore || '-' }}
                </el-tag>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <el-empty v-else description="暂无数据" />
      </el-tab-pane>

      <!-- 教学班成绩 -->
      <el-tab-pane label="教学班成绩" name="class">
        <el-form inline @submit.prevent="handleQueryClass">
          <el-form-item label="教学班">
            <el-select v-model="classQuery.classId" placeholder="请选择教学班" filterable style="width: 300px">
              <el-option
                v-for="tc in teachingClasses"
                :key="tc.classId"
                :label="`${tc.classId} - ${tc.course.courseName} - ${tc.teacher.name}`"
                :value="tc.classId"
              />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleQueryClass" :loading="loading">查询</el-button>
          </el-form-item>
        </el-form>

        <div v-if="classGrades.length > 0">
          <el-descriptions :column="3" border style="margin-bottom: 20px">
            <el-descriptions-item label="教学班号">{{ currentClass?.classId }}</el-descriptions-item>
            <el-descriptions-item label="课程名称">{{ currentClass?.course?.courseName }}</el-descriptions-item>
            <el-descriptions-item label="授课教师">{{ currentClass?.teacher?.name }}</el-descriptions-item>
            <el-descriptions-item label="学期">{{ currentClass?.semester }}</el-descriptions-item>
            <el-descriptions-item label="学生人数">{{ classGrades.length }}</el-descriptions-item>
          </el-descriptions>

          <el-table :data="classGrades" stripe border max-height="500">
            <el-table-column type="index" label="序号" width="60" />
            <el-table-column prop="student.studentId" label="学号" width="120" />
            <el-table-column prop="student.name" label="姓名" width="100" />
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
                <el-tag :type="getScoreType(row.totalScore)">
                  {{ row.totalScore || '-' }}
                </el-tag>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <el-empty v-else description="暂无数据" />
      </el-tab-pane>

      <!-- 教学班排名 -->
      <el-tab-pane label="教学班排名" name="classRank">
        <el-form inline @submit.prevent="handleQueryClassRank">
          <el-form-item label="教学班">
            <el-select v-model="classRankQuery.classId" placeholder="请选择教学班" filterable style="width: 300px">
              <el-option
                v-for="tc in teachingClasses"
                :key="tc.classId"
                :label="`${tc.classId} - ${tc.course.courseName} - ${tc.teacher.name}`"
                :value="tc.classId"
              />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleQueryClassRank" :loading="loading">查询</el-button>
          </el-form-item>
        </el-form>

        <div v-if="classRanking.length > 0">
          <el-table :data="classRanking" stripe border max-height="600">
            <el-table-column label="排名" width="80">
              <template #default="{ $index }">
                <el-tag v-if="$index < 3" :type="['danger', 'warning', 'success'][$index]" effect="dark">
                  第{{ $index + 1 }}名
                </el-tag>
                <span v-else>第{{ $index + 1 }}名</span>
              </template>
            </el-table-column>
            <el-table-column prop="student.studentId" label="学号" width="120" />
            <el-table-column prop="student.name" label="姓名" width="100" />
            <el-table-column label="综合成绩" width="100">
              <template #default="{ row }">
                <el-tag :type="getScoreType(row.totalScore)" size="large">
                  {{ row.totalScore }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="各项成绩">
              <template #default="{ row }">
                <el-space>
                  <span>平时: {{ row.scores.daily }}</span>
                  <el-divider direction="vertical" />
                  <span>期中: {{ row.scores.midterm }}</span>
                  <el-divider direction="vertical" />
                  <span>实验: {{ row.scores.experiment }}</span>
                  <el-divider direction="vertical" />
                  <span>期末: {{ row.scores.final }}</span>
                </el-space>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <el-empty v-else description="暂无数据" />
      </el-tab-pane>

      <!-- 教学班统计 -->
      <el-tab-pane label="教学班统计" name="classStats">
        <el-form inline @submit.prevent="handleQueryClassStats">
          <el-form-item label="教学班">
            <el-select v-model="classStatsQuery.classId" placeholder="请选择教学班" filterable style="width: 300px">
              <el-option
                v-for="tc in teachingClasses"
                :key="tc.classId"
                :label="`${tc.classId} - ${tc.course.courseName} - ${tc.teacher.name}`"
                :value="tc.classId"
              />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleQueryClassStats" :loading="loading">查询</el-button>
          </el-form-item>
        </el-form>

        <div v-if="classStats.count > 0">
          <el-row :gutter="20">
            <el-col :span="8">
              <el-statistic title="平均分" :value="classStats.average" :precision="2" />
            </el-col>
            <el-col :span="8">
              <el-statistic title="最高分" :value="classStats.max" />
            </el-col>
            <el-col :span="8">
              <el-statistic title="最低分" :value="classStats.min" />
            </el-col>
          </el-row>

          <el-divider />

          <el-row :gutter="20">
            <el-col :span="8">
              <el-statistic title="及格率" :value="classStats.passRate" suffix="%" :precision="2" />
            </el-col>
            <el-col :span="8">
              <el-statistic title="学生人数" :value="classStats.count" />
            </el-col>
          </el-row>

          <el-divider />

          <h4>分数段分布</h4>
          <el-table :data="distributionData" stripe border style="margin-top: 10px">
            <el-table-column prop="label" label="分数段" />
            <el-table-column prop="count" label="人数" />
            <el-table-column label="占比">
              <template #default="{ row }">
                {{ ((row.count / classStats.count) * 100).toFixed(2) }}%
              </template>
            </el-table-column>
          </el-table>
        </div>
        <el-empty v-else description="暂无数据" />
      </el-tab-pane>

      <!-- 全体排名 -->
      <el-tab-pane label="全体学生排名" name="overallRank">
        <el-button type="primary" @click="handleQueryOverallRank" :loading="loading" style="margin-bottom: 20px">
          查询全体排名
        </el-button>

        <div v-if="overallRanking.length > 0">
          <el-table :data="overallRanking" stripe border max-height="600">
            <el-table-column label="排名" width="80">
              <template #default="{ row }">
                <el-tag v-if="row.rank <= 3" :type="['danger', 'warning', 'success'][row.rank - 1]" effect="dark">
                  第{{ row.rank }}名
                </el-tag>
                <span v-else>第{{ row.rank }}名</span>
              </template>
            </el-table-column>
            <el-table-column prop="student.studentId" label="学号" width="120" />
            <el-table-column prop="student.name" label="姓名" width="100" />
            <el-table-column prop="courseCount" label="选课数" width="100" />
            <el-table-column label="总成绩" width="120">
              <template #default="{ row }">
                <el-tag type="danger" size="large">{{ row.totalScore }}</el-tag>
              </template>
            </el-table-column>
            <el-table-column label="平均成绩" width="120">
              <template #default="{ row }">
                <el-tag :type="getScoreType(row.avgScore)" size="large">{{ row.avgScore }}</el-tag>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <el-empty v-else description="暂无数据" />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { studentAPI, teachingClassAPI, statisticsAPI } from '@/api'

const activeTab = ref('student')
const loading = ref(false)

const teachingClasses = ref([])

// 学生查询
const studentQuery = ref({ studentId: '' })
const currentStudent = ref(null)
const studentGrades = ref([])

// 教学班查询
const classQuery = ref({ classId: '' })
const currentClass = ref(null)
const classGrades = ref([])

// 教学班排名
const classRankQuery = ref({ classId: '' })
const classRanking = ref([])

// 教学班统计
const classStatsQuery = ref({ classId: '' })
const classStats = ref({
  count: 0,
  average: 0,
  max: 0,
  min: 0,
  passRate: 0,
  distribution: {}
})

// 全体排名
const overallRanking = ref([])

// 分数段分布数据
const distributionData = computed(() => {
  return [
    { label: '不及格 (0-59)', count: classStats.value.distribution[0] || 0 },
    { label: '及格 (60-69)', count: classStats.value.distribution[60] || 0 },
    { label: '中等 (70-79)', count: classStats.value.distribution[70] || 0 },
    { label: '良好 (80-89)', count: classStats.value.distribution[80] || 0 },
    { label: '优秀 (90-100)', count: classStats.value.distribution[90] || 0 }
  ]
})

// 获取成绩标签类型
const getScoreType = (score) => {
  if (!score) return 'info'
  if (score >= 90) return 'success'
  if (score >= 80) return 'primary'
  if (score >= 70) return 'warning'
  if (score >= 60) return 'info'
  return 'danger'
}

// 查询学生成绩
const handleQueryStudent = async () => {
  if (!studentQuery.value.studentId) {
    ElMessage.warning('请输入学号！')
    return
  }

  loading.value = true
  try {
    const [studentRes, gradesRes] = await Promise.all([
      studentAPI.getById(studentQuery.value.studentId),
      studentAPI.getGrades(studentQuery.value.studentId)
    ])

    if (!studentRes.data) {
      ElMessage.error('未找到该学生！')
      currentStudent.value = null
      studentGrades.value = []
      return
    }

    currentStudent.value = studentRes.data
    studentGrades.value = gradesRes.data

    if (studentGrades.value.length === 0) {
      ElMessage.warning('该学生暂无成绩记录！')
    }
  } finally {
    loading.value = false
  }
}

// 查询教学班成绩
const handleQueryClass = async () => {
  if (!classQuery.value.classId) {
    ElMessage.warning('请选择教学班！')
    return
  }

  loading.value = true
  try {
    const [classRes, gradesRes] = await Promise.all([
      teachingClassAPI.getById(classQuery.value.classId),
      teachingClassAPI.getGrades(classQuery.value.classId)
    ])

    currentClass.value = classRes.data
    classGrades.value = gradesRes.data

    if (classGrades.value.length === 0) {
      ElMessage.warning('该教学班暂无成绩记录！')
    }
  } finally {
    loading.value = false
  }
}

// 查询教学班排名
const handleQueryClassRank = async () => {
  if (!classRankQuery.value.classId) {
    ElMessage.warning('请选择教学班！')
    return
  }

  loading.value = true
  try {
    const res = await teachingClassAPI.getGrades(classRankQuery.value.classId)
    const validGrades = res.data.filter(g => g.totalScore !== null)

    if (validGrades.length === 0) {
      ElMessage.warning('该教学班暂无综合成绩数据！')
      classRanking.value = []
      return
    }

    classRanking.value = validGrades.sort((a, b) => b.totalScore - a.totalScore)
  } finally {
    loading.value = false
  }
}

// 查询教学班统计
const handleQueryClassStats = async () => {
  if (!classStatsQuery.value.classId) {
    ElMessage.warning('请选择教学班！')
    return
  }

  loading.value = true
  try {
    const res = await teachingClassAPI.getStatistics(classStatsQuery.value.classId)
    classStats.value = res.data
  } finally {
    loading.value = false
  }
}

// 查询全体排名
const handleQueryOverallRank = async () => {
  loading.value = true
  try {
    const res = await statisticsAPI.getOverallRanking()
    overallRanking.value = res.data

    if (overallRanking.value.length === 0) {
      ElMessage.warning('暂无成绩数据！')
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
.query-statistics {
  width: 100%;
}
</style>
