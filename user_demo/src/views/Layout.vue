<template>
  <el-container class="layout-container">
    <!-- 侧边栏 -->
    <el-aside :width="isCollapse ? '64px' : '200px'" class="aside">
      <div class="logo">
        <el-icon v-if="!isCollapse" :size="28">
          <Reading />
        </el-icon>
        <span v-if="!isCollapse" class="logo-text">成绩管理系统</span>
      </div>
      
      <el-menu
        :default-active="activeMenu"
        :collapse="isCollapse"
        :collapse-transition="false"
        router
        class="menu"
      >
        <el-menu-item index="/dashboard">
          <el-icon><HomeFilled /></el-icon>
          <template #title>首页</template>
        </el-menu-item>
        
        <el-sub-menu index="1">
          <template #title>
            <el-icon><Setting /></el-icon>
            <span>数据管理</span>
          </template>
          <el-menu-item index="/data-init">数据初始化</el-menu-item>
          <el-menu-item index="/students">学生管理</el-menu-item>
          <el-menu-item index="/teachers">教师管理</el-menu-item>
          <el-menu-item index="/courses">课程管理</el-menu-item>
          <el-menu-item index="/teaching-classes">教学班管理</el-menu-item>
        </el-sub-menu>
        
        <el-menu-item index="/course-selection">
          <el-icon><Document /></el-icon>
          <template #title>选课管理</template>
        </el-menu-item>
        
        <el-menu-item index="/grade-management">
          <el-icon><Edit /></el-icon>
          <template #title>成绩管理</template>
        </el-menu-item>
        
        <el-menu-item index="/query-statistics">
          <el-icon><DataAnalysis /></el-icon>
          <template #title>查询统计</template>
        </el-menu-item>
      </el-menu>
    </el-aside>

    <!-- 主体内容 -->
    <el-container>
      <!-- 顶部栏 -->
      <el-header class="header">
        <div class="header-left">
          <el-icon class="collapse-icon" @click="toggleCollapse" :size="20">
            <Fold v-if="!isCollapse" />
            <Expand v-else />
          </el-icon>
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item v-if="currentRouteMeta.title">
              {{ currentRouteMeta.title }}
            </el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        
        <div class="header-right">
          <el-tag type="success" size="large">学生成绩管理系统 v1.0</el-tag>
        </div>
      </el-header>

      <!-- 内容区 -->
      <el-main class="main">
        <router-view v-slot="{ Component }">
          <transition name="fade" mode="out-in">
            <component :is="Component" />
          </transition>
        </router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const isCollapse = ref(false)

const activeMenu = computed(() => route.path)
const currentRouteMeta = computed(() => route.meta || {})

const toggleCollapse = () => {
  isCollapse.value = !isCollapse.value
}
</script>

<style scoped>
.layout-container {
  height: 100vh;
}

.aside {
  background: #001529;
  transition: width 0.3s;
}

.logo {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  font-size: 18px;
  font-weight: bold;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
  gap: 8px;
}

.logo-text {
  white-space: nowrap;
}

.menu {
  border-right: none;
  background: #001529;
}

/* 菜单项样式 */
.menu :deep(.el-menu-item) {
  color: rgba(255, 255, 255, 0.85);  /* 未选中时的文字颜色 */
}

.menu :deep(.el-menu-item:hover) {
  background-color: rgba(255, 255, 255, 0.08) !important;
  color: #fff !important;  /* 悬停时文字颜色 */
}

.menu :deep(.el-menu-item.is-active) {
  background-color: #1890ff !important;
  color: #fff !important;  /* 选中时文字颜色 */
}

/* 子菜单标题样式 */
.menu :deep(.el-sub-menu__title) {
  color: rgba(255, 255, 255, 0.85) !important;
}

.menu :deep(.el-sub-menu__title:hover) {
  background-color: rgba(255, 255, 255, 0.08) !important;
  color: #fff !important;
}

/* 子菜单展开后的背景（更深的颜色） */
.menu :deep(.el-menu--inline) {
  background-color: #000c17 !important;
}

/* 二级菜单项的文字颜色 */
.menu :deep(.el-menu--inline .el-menu-item) {
  background-color: #000c17 !important;
  color: rgba(255, 255, 255, 0.85) !important;  
}

/* 二级菜单的悬停和选中效果 */
.menu :deep(.el-menu--inline .el-menu-item:hover) {
  background-color: rgba(255, 255, 255, 0.08) !important;
  color: #fff !important;
}

.menu :deep(.el-menu--inline .el-menu-item.is-active) {
  background-color: #1890ff !important;  
  color: #fff !important;
}

/* 图标颜色 */
.menu :deep(.el-icon) {
  color: rgba(255, 255, 255, 0.85);
}

.header {
  background: #fff;
  border-bottom: 1px solid #e8e8e8;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
}

.header-left {
  display: flex;
  align-items: center;
  gap: 16px;
}

.collapse-icon {
  cursor: pointer;
  transition: all 0.3s;
}

.collapse-icon:hover {
  color: #409eff;
}

.main {
  background: #f0f2f5;
  padding: 20px;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
