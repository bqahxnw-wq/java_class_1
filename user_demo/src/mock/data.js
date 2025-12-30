// Mock数据存储
export const mockData = {
  students: [],
  teachers: [],
  courses: [],
  teachingClasses: [],
  grades: []
}

// 姓氏和名字库
const surnames = ['王', '李', '张', '刘', '陈', '杨', '黄', '赵', '周', '吴', '徐', '孙', '朱', '马', '胡', '郭', '林', '何', '高', '梁']
const givenNames = ['伟', '芳', '娜', '秀英', '敏', '静', '丽', '强', '磊', '军', '洋', '勇', '艳', '杰', '涛', '明', '超', '秀兰', '霞', '平']
const genders = ['男', '女']

// 课程名称
const courseNames = [
  'Java企业级应用', '数据结构与算法', '数据库原理',
  '计算机网络', '操作系统', '软件工程',
  'Web开发技术', '人工智能基础', '移动应用开发',
  '云计算技术', '大数据处理', '信息安全'
]

// 职称
const titles = ['教授', '副教授', '讲师', '助教']

// 学期
const semesters = ['2024-2025(1)', '2024-2025(2)', '2025-2026(1)', '2025-2026(2)']

// 生成随机姓名
function generateName() {
  const surname = surnames[Math.floor(Math.random() * surnames.length)]
  const givenName = givenNames[Math.floor(Math.random() * givenNames.length)]
  return Math.random() < 0.3 ? surname + givenName[0] : surname + givenName
}

// 生成随机整数
function randomInt(min, max) {
  return Math.floor(Math.random() * (max - min + 1)) + min
}

// 生成学生数据
export function generateStudents(count = 100) {
  mockData.students = []
  for (let i = 1; i <= count; i++) {
    mockData.students.push({
      studentId: `2023${String(i).padStart(6, '0')}`,
      name: generateName(),
      gender: genders[Math.floor(Math.random() * genders.length)],
      enrolledClasses: []
    })
  }
}

// 生成教师数据
export function generateTeachers(count = 10) {
  mockData.teachers = []
  for (let i = 1; i <= count; i++) {
    mockData.teachers.push({
      teacherId: `T${String(i).padStart(4, '0')}`,
      name: generateName(),
      title: titles[Math.floor(Math.random() * titles.length)]
    })
  }
}

// 生成课程数据
export function generateCourses() {
  mockData.courses = []
  courseNames.forEach((name, index) => {
    mockData.courses.push({
      courseId: `C${String(index + 1).padStart(3, '0')}`,
      courseName: name,
      credit: randomInt(2, 4),
      department: ['计算机学院', '软件学院', '信息学院'][Math.floor(Math.random() * 3)]
    })
  })
}

// 生成教学班数据
export function generateTeachingClasses() {
  mockData.teachingClasses = []
  let classCounter = 1
  
  mockData.courses.forEach(course => {
    const classCount = randomInt(2, 3)
    const availableTeachers = [...mockData.teachers].sort(() => Math.random() - 0.5)
    
    for (let i = 0; i < classCount && i < availableTeachers.length; i++) {
      mockData.teachingClasses.push({
        classId: `TC${String(classCounter++).padStart(4, '0')}`,
        course: course,
        teacher: availableTeachers[i],
        semester: semesters[Math.floor(Math.random() * semesters.length)],
        capacity: randomInt(20, 40),
        students: [],
        currentSize: 0
      })
    }
  })
}

// 自动选课
export function autoSelectCourses() {
  // 清空之前的选课数据
  mockData.students.forEach(student => {
    student.enrolledClasses = []
  })
  mockData.teachingClasses.forEach(tc => {
    tc.students = []
    tc.currentSize = 0
  })
  mockData.grades = []
  
  mockData.students.forEach(student => {
    // 随机选择3-5门课程
    const courseCount = randomInt(3, 5)
    const availableCourses = [...mockData.courses].sort(() => Math.random() - 0.5).slice(0, courseCount)
    
    availableCourses.forEach(course => {
      // 为该课程选择一个教学班
      const classes = mockData.teachingClasses
        .filter(tc => tc.course.courseId === course.courseId && tc.currentSize < tc.capacity)
        .sort((a, b) => a.currentSize - b.currentSize)
      
      if (classes.length > 0) {
        const selectedClass = classes[0]
        selectedClass.students.push(student)
        selectedClass.currentSize++
        student.enrolledClasses.push(selectedClass)
        
        // 创建成绩记录
        mockData.grades.push({
          gradeId: `G${student.studentId}_${selectedClass.classId}`,
          student: student,
          teachingClass: selectedClass,
          scores: {
            daily: null,
            midterm: null,
            experiment: null,
            final: null
          },
          totalScore: null
        })
      }
    })
  })
}

// 生成成绩（使用正态分布）
function generateScore(min, max) {
  const mean = (min + max) / 2
  const stdDev = (max - min) / 6
  let score
  
  do {
    const u1 = Math.random()
    const u2 = Math.random()
    const z = Math.sqrt(-2 * Math.log(u1)) * Math.cos(2 * Math.PI * u2)
    score = Math.round(mean + z * stdDev)
  } while (score < min || score > max)
  
  return score
}

// 生成各类成绩
export function generateScores(type) {
  const ranges = {
    daily: [70, 100],
    midterm: [50, 95],
    experiment: [60, 98],
    final: [45, 95]
  }
  
  const [min, max] = ranges[type]
  
  mockData.grades.forEach(grade => {
    if (!grade.scores[type]) {
      grade.scores[type] = generateScore(min, max)
    }
  })
}

// 计算综合成绩
export function calculateTotalScores() {
  const weights = {
    daily: 0.2,
    midterm: 0.2,
    experiment: 0.2,
    final: 0.4
  }
  
  mockData.grades.forEach(grade => {
    if (grade.scores.daily && grade.scores.midterm && 
        grade.scores.experiment && grade.scores.final) {
      grade.totalScore = Math.round(
        grade.scores.daily * weights.daily +
        grade.scores.midterm * weights.midterm +
        grade.scores.experiment * weights.experiment +
        grade.scores.final * weights.final
      )
    }
  })
}

// 初始化所有数据
export function initializeAllData() {
  generateTeachers()
  generateCourses()
  generateStudents()
  generateTeachingClasses()
}

// 重置所有数据
export function resetAllData() {
  mockData.students = []
  mockData.teachers = []
  mockData.courses = []
  mockData.teachingClasses = []
  mockData.grades = []
}
