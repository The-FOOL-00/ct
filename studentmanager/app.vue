<script setup>
import { ref, onMounted } from 'vue'

const STUDENT_API = 'http://localhost:8080/api/students'
const COURSE_API = 'http://localhost:8080/api/courses'

const students = ref([])
const courses = ref([])
const message = ref('')
const error = ref('')

// Student form
const student = ref({
  id: null,
  name: '',
  department: '',
  year: ''
})

// Course form
const course = ref({
  id: null,
  courseName: '',
  credits: ''
})


// ================= STUDENT CRUD =================

// READ ALL STUDENTS
async function getStudents() {
  try {
    const res = await fetch(STUDENT_API)
    if (!res.ok) throw new Error()

    students.value = await res.json()
    error.value = ''
  } catch {
    error.value = 'Failed to fetch students!'
  }
}

// CREATE STUDENT
async function addStudent() {
  try {
    const res = await fetch(STUDENT_API, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        name: student.value.name,
        department: student.value.department,
        year: Number(student.value.year)
      })
    })

    if (!res.ok) throw new Error()

    message.value = 'Student added!'
    resetStudent()
    await getStudents()
  } catch {
    error.value = 'Failed to add student!'
  }
}

// SELECT STUDENT FOR EDITING
function editStudent(s) {
  student.value = { ...s }
  message.value = 'Editing student...'
}

// UPDATE STUDENT
async function updateStudent() {
  try {
    const res = await fetch(
      `${STUDENT_API}/${student.value.id}`,
      {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          name: student.value.name,
          department: student.value.department,
          year: Number(student.value.year)
        })
      }
    )

    if (!res.ok) throw new Error()

    message.value = 'Student updated!'
    resetStudent()
    await getStudents()
  } catch {
    error.value = 'Failed to update student!'
  }
}

// DELETE STUDENT
async function deleteStudent(s) {
  if (!confirm('Delete ' + s.name + '?')) return

  try {
    const res = await fetch(
      `${STUDENT_API}/${s.id}`,
      { method: 'DELETE' }
    )

    if (!res.ok) throw new Error()

    message.value = 'Student deleted!'
    error.value = ''
    await getStudents()

    if (student.value.id === s.id) resetStudent()
  } catch {
    error.value = 'Failed to delete student!'
  }
}

// RESET STUDENT FORM
function resetStudent() {
  student.value = {
    id: null,
    name: '',
    department: '',
    year: ''
  }
}


// ================= COURSE CRUD =================

// READ ALL COURSES
async function getCourses() {
  try {
    const res = await fetch(COURSE_API)
    if (!res.ok) throw new Error()

    courses.value = await res.json()
    error.value = ''
  } catch {
    error.value = 'Failed to fetch courses!'
  }
}

// CREATE COURSE
async function addCourse() {
  try {
    const res = await fetch(COURSE_API, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        courseName: course.value.courseName,
        credits: Number(course.value.credits)
      })
    })

    if (!res.ok) throw new Error()

    message.value = 'Course added!'
    resetCourse()
    await getCourses()
  } catch {
    error.value = 'Failed to add course!'
  }
}

// SELECT COURSE FOR EDITING
function editCourse(c) {
  course.value = { ...c }
  message.value = 'Editing course...'
}

// UPDATE COURSE
async function updateCourse() {
  try {
    const res = await fetch(
      `${COURSE_API}/${course.value.id}`,
      {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          courseName: course.value.courseName,
          credits: Number(course.value.credits)
        })
      }
    )

    if (!res.ok) throw new Error()

    message.value = 'Course updated!'
    resetCourse()
    await getCourses()
  } catch {
    error.value = 'Failed to update course!'
  }
}

// DELETE COURSE
async function deleteCourse(c) {
  if (!confirm('Delete ' + c.courseName + '?')) return

  try {
    const res = await fetch(
      `${COURSE_API}/${c.id}`,
      { method: 'DELETE' }
    )

    if (!res.ok) throw new Error()

    message.value = 'Course deleted!'
    error.value = ''
    await getCourses()

    if (course.value.id === c.id) resetCourse()
  } catch {
    error.value = 'Failed to delete course!'
  }
}

// RESET COURSE FORM
function resetCourse() {
  course.value = {
    id: null,
    courseName: '',
    credits: ''
  }
}


// LOAD BOTH COLLECTIONS WHEN PAGE OPENS
onMounted(() => {
  getStudents()
  getCourses()
})
</script>

<template>
  <header>
    <h1>Student and Course Manager</h1>
    <p>Full-Stack CRUD Application</p>
  </header>

  <main>
    <p>{{ message }}</p>
    <p>{{ error }}</p>

    <hr />

    <!-- STUDENT FORM -->
    <h2>
      {{ student.id === null ? 'Add Student' : 'Update Student' }}
    </h2>

    <form
      @submit.prevent="student.id === null ? addStudent() : updateStudent()"
    >
      <label>Name:</label>
      <input v-model="student.name" required />

      <label>Department:</label>
      <input v-model="student.department" required />

      <label>Year:</label>
      <input
        type="number"
        v-model="student.year"
        min="1"
        required
      />

      <button type="submit">
        {{ student.id === null ? 'Add Student' : 'Update Student' }}
      </button>

      <button type="button" @click="resetStudent">
        Clear
      </button>
    </form>

    <!-- STUDENT LIST -->
    <h2>Students ({{ students.length }})</h2>

    <p v-if="students.length === 0">No students found.</p>

    <ul>
      <li v-for="s in students" :key="s.id">
        {{ s.name }} — {{ s.department }} — Year {{ s.year }}

        <button type="button" @click="editStudent(s)">Edit</button>
        <button type="button" @click="deleteStudent(s)">Delete</button>
      </li>
    </ul>

    <hr />

    <!-- COURSE FORM -->
    <h2>
      {{ course.id === null ? 'Add Course' : 'Update Course' }}
    </h2>

    <form
      @submit.prevent="course.id === null ? addCourse() : updateCourse()"
    >
      <label>Course Name:</label>
      <input v-model="course.courseName" required />

      <label>Credits:</label>
      <input
        type="number"
        v-model="course.credits"
        min="1"
        required
      />

      <button type="submit">
        {{ course.id === null ? 'Add Course' : 'Update Course' }}
      </button>

      <button type="button" @click="resetCourse">
        Clear
      </button>
    </form>

    <!-- COURSE LIST -->
    <h2>Courses ({{ courses.length }})</h2>

    <p v-if="courses.length === 0">No courses found.</p>

    <ul>
      <li v-for="c in courses" :key="c.id">
        {{ c.courseName }} — {{ c.credits }} credits

        <button type="button" @click="editCourse(c)">Edit</button>
        <button type="button" @click="deleteCourse(c)">Delete</button>
      </li>
    </ul>
  </main>
</template>