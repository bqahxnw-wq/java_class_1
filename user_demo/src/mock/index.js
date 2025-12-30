import { mockData, initializeAllData, resetAllData, autoSelectCourses, generateScores, calculateTotalScores } from './data'

// 模拟API延迟
const delay = (ms = 300) => new Promise(resolve => setTimeout(resolve, ms))

// Mock API
class MockAPI {
  // 数据初始化相关
  async initData() {
    await delay()
    initializeAllData()
    return { code: 200, message: '数据初始化成功', data: null }
  }

  async resetData() {
    await delay()
    resetAllData()
    return { code: 200, message: '数据重置成功', data: null }
  }

  async getDataSummary() {
    await delay()
    return {
      code: 200,
      data: {
        studentCount: mockData.students.length,
        teacherCount: mockData.teachers.length,
        courseCount: mockData.courses.length,
        teachingClassCount: mockData.teachingClasses.length,
        gradeCount: mockData.grades.length
      }
    }
  }

  // 学生相关
  async getStudents(params = {}) {
    await delay()
    const { page = 1, pageSize = 10, keyword = '' } = params
    let list = mockData.students

    if (keyword) {
      list = list.filter(s => 
        s.name.includes(keyword) || s.studentId.includes(keyword)
      )
    }

    const total = list.length
    const start = (page - 1) * pageSize
    const data = list.slice(start, start + pageSize)

    return { code: 200, data: { list: data, total } }
  }

  async getStudentById(id) {
    await delay()
    const student = mockData.students.find(s => s.studentId === id)
    return { code: 200, data: student }
  }

  // 教师相关
  async getTeachers(params = {}) {
    await delay()
    const { page = 1, pageSize = 10 } = params
    const total = mockData.teachers.length
    const start = (page - 1) * pageSize
    const data = mockData.teachers.slice(start, start + pageSize)
    return { code: 200, data: { list: data, total } }
  }

  // 课程相关
  async getCourses() {
    await delay()
    return { code: 200, data: mockData.courses }
  }

  // 教学班相关
  async getTeachingClasses() {
    await delay()
    return { code: 200, data: mockData.teachingClasses }
  }

  async getTeachingClassById(id) {
    await delay()
    const tc = mockData.teachingClasses.find(c => c.classId === id)
    return { code: 200, data: tc }
  }

  // 选课相关
  async autoSelectCourse() {
    await delay(500)
    autoSelectCourses()
    return { code: 200, message: '自动选课完成', data: null }
  }

  async getClassStudents(classId) {
    await delay()
    const tc = mockData.teachingClasses.find(c => c.classId === classId)
    return { code: 200, data: tc ? tc.students : [] }
  }

  async getStudentCourses(studentId) {
    await delay()
    const student = mockData.students.find(s => s.studentId === studentId)
    return { code: 200, data: student ? student.enrolledClasses : [] }
  }

  // 成绩相关
  async generateGrades(type) {
    await delay(500)
    generateScores(type)
    return { code: 200, message: `${type}成绩生成完成`, data: null }
  }

  async calculateTotal() {
    await delay(500)
    calculateTotalScores()
    return { code: 200, message: '综合成绩计算完成', data: null }
  }

  async generateAllGrades() {
    await delay(1000)
    generateScores('daily')
    generateScores('midterm')
    generateScores('experiment')
    generateScores('final')
    calculateTotalScores()
    return { code: 200, message: '所有成绩生成完成', data: null }
  }

  async getGrades(params = {}) {
    await delay()
    const { studentId, classId } = params
    let grades = mockData.grades

    if (studentId) {
      grades = grades.filter(g => g.student.studentId === studentId)
    }

    if (classId) {
      grades = grades.filter(g => g.teachingClass.classId === classId)
    }

    return { code: 200, data: grades }
  }

  async getStudentGrades(studentId) {
    await delay()
    const grades = mockData.grades.filter(g => g.student.studentId === studentId)
    return { code: 200, data: grades }
  }

  async getClassGrades(classId) {
    await delay()
    const grades = mockData.grades.filter(g => g.teachingClass.classId === classId)
    return { code: 200, data: grades }
  }

  // 统计相关
  async getClassStatistics(classId) {
    await delay()
    const grades = mockData.grades.filter(
      g => g.teachingClass.classId === classId && g.totalScore !== null
    )

    if (grades.length === 0) {
      return {
        code: 200,
        data: {
          count: 0,
          average: 0,
          max: 0,
          min: 0,
          passRate: 0,
          distribution: { 0: 0, 60: 0, 70: 0, 80: 0, 90: 0 }
        }
      }
    }

    const scores = grades.map(g => g.totalScore)
    const sum = scores.reduce((a, b) => a + b, 0)
    const passCount = scores.filter(s => s >= 60).length
    
    const distribution = {
      0: scores.filter(s => s < 60).length,
      60: scores.filter(s => s >= 60 && s < 70).length,
      70: scores.filter(s => s >= 70 && s < 80).length,
      80: scores.filter(s => s >= 80 && s < 90).length,
      90: scores.filter(s => s >= 90).length
    }

    return {
      code: 200,
      data: {
        count: grades.length,
        average: (sum / grades.length).toFixed(2),
        max: Math.max(...scores),
        min: Math.min(...scores),
        passRate: ((passCount / grades.length) * 100).toFixed(2),
        distribution
      }
    }
  }

  async getOverallRanking() {
    await delay()
    const studentScores = {}

    mockData.students.forEach(student => {
      const grades = mockData.grades.filter(
        g => g.student.studentId === student.studentId && g.totalScore !== null
      )
      
      if (grades.length > 0) {
        const totalScore = grades.reduce((sum, g) => sum + g.totalScore, 0)
        const avgScore = (totalScore / grades.length).toFixed(2)
        
        studentScores[student.studentId] = {
          student,
          courseCount: grades.length,
          totalScore,
          avgScore: parseFloat(avgScore)
        }
      }
    })

    const ranking = Object.values(studentScores)
      .sort((a, b) => b.totalScore - a.totalScore)
      .map((item, index) => ({
        ...item,
        rank: index + 1
      }))

    return { code: 200, data: ranking }
  }
}

export default new MockAPI()
