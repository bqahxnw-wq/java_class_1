import { defineStore } from 'pinia'
import { ref } from 'vue'
import { dataAPI } from '@/api'

export const useAppStore = defineStore('app', () => {
  // 数据摘要
  const dataSummary = ref({
    studentCount: 0,
    teacherCount: 0,
    courseCount: 0,
    teachingClassCount: 0,
    gradeCount: 0
  })

  // 加载状态
  const loading = ref(false)

  // 获取数据摘要
  async function fetchDataSummary() {
    try {
      loading.value = true
      const res = await dataAPI.getSummary()
      if (res.code === 200) {
        dataSummary.value = res.data
      }
    } finally {
      loading.value = false
    }
  }

  // 初始化数据
  async function initData() {
    try {
      loading.value = true
      await dataAPI.init()
      await fetchDataSummary()
      return true
    } catch (error) {
      console.error('初始化数据失败:', error)
      return false
    } finally {
      loading.value = false
    }
  }

  // 重置数据
  async function resetData() {
    try {
      loading.value = true
      await dataAPI.reset()
      await fetchDataSummary()
      return true
    } catch (error) {
      console.error('重置数据失败:', error)
      return false
    } finally {
      loading.value = false
    }
  }

  return {
    dataSummary,
    loading,
    fetchDataSummary,
    initData,
    resetData
  }
})
