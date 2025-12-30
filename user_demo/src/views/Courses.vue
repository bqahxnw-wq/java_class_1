<template>
  <div class="courses">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>课程管理</span>
        </div>
      </template>

      <el-table :data="tableData" stripe border v-loading="loading" style="width: 100%">
        <el-table-column type="index" label="序号" width="60" />
        <el-table-column prop="courseId" label="课程编号" width="120" sortable />
        <el-table-column prop="courseName" label="课程名称" width="200" />
        <el-table-column prop="credit" label="学分" width="100" sortable />
        <el-table-column prop="department" label="开课院系" />
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { courseAPI } from '@/api'

const loading = ref(false)
const tableData = ref([])

const loadData = async () => {
  loading.value = true
  try {
    const res = await courseAPI.getList()
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
