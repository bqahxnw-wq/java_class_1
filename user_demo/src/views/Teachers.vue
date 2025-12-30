<template>
  <div class="teachers">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>教师管理</span>
        </div>
      </template>

      <el-table :data="tableData" stripe border v-loading="loading" style="width: 100%">
        <el-table-column type="index" label="序号" width="60" />
        <el-table-column prop="teacherId" label="教师编号" width="120" sortable />
        <el-table-column prop="name" label="姓名" width="120" />
        <el-table-column prop="title" label="职称" width="120" />
      </el-table>

      <el-pagination
        v-model:current-page="pagination.page"
        v-model:page-size="pagination.pageSize"
        :total="pagination.total"
        :page-sizes="[10, 20, 50]"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="loadData"
        @current-change="loadData"
        style="margin-top: 20px; justify-content: flex-end"
      />
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { teacherAPI } from '@/api'

const loading = ref(false)
const tableData = ref([])

const pagination = reactive({
  page: 1,
  pageSize: 10,
  total: 0
})

const loadData = async () => {
  loading.value = true
  try {
    const res = await teacherAPI.getList({
      page: pagination.page,
      pageSize: pagination.pageSize
    })
    tableData.value = res.data.list
    pagination.total = res.data.total
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
