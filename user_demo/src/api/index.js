import mockAPI from '@/mock'

// 数据初始化API
export const dataAPI = {
  init: () => mockAPI.initData(),
  reset: () => mockAPI.resetData(),
  getSummary: () => mockAPI.getDataSummary()
}

// 学生API
export const studentAPI = {
  getList: (params) => mockAPI.getStudents(params),
  getById: (id) => mockAPI.getStudentById(id),
  getCourses: (id) => mockAPI.getStudentCourses(id),
  getGrades: (id) => mockAPI.getStudentGrades(id)
}

// 教师API
export const teacherAPI = {
  getList: (params) => mockAPI.getTeachers(params)
}

// 课程API
export const courseAPI = {
  getList: () => mockAPI.getCourses()
}

// 教学班API
export const teachingClassAPI = {
  getList: () => mockAPI.getTeachingClasses(),
  getById: (id) => mockAPI.getTeachingClassById(id),
  getStudents: (id) => mockAPI.getClassStudents(id),
  getGrades: (id) => mockAPI.getClassGrades(id),
  getStatistics: (id) => mockAPI.getClassStatistics(id)
}

// 选课API
export const courseSelectionAPI = {
  autoSelect: () => mockAPI.autoSelectCourse()
}

// 成绩API
export const gradeAPI = {
  generate: (type) => mockAPI.generateGrades(type),
  calculateTotal: () => mockAPI.calculateTotal(),
  generateAll: () => mockAPI.generateAllGrades(),
  getList: (params) => mockAPI.getGrades(params)
}

// 统计API
export const statisticsAPI = {
  getOverallRanking: () => mockAPI.getOverallRanking()
}
