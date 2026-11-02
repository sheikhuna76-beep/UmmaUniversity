package com.example.ummauniversity.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ummauniversity.data.entities.*
import com.example.ummauniversity.data.repository.UmmaRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class LecturerViewModel(private val repository: UmmaRepository) : ViewModel() {

    val assignedCourses: StateFlow<List<CourseEntity>> = repository.getAllCoursesFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val studentsList: StateFlow<List<UserEntity>> = repository.getAllStudentsFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val assignments: StateFlow<List<AssignmentEntity>> = repository.getAllAssignmentsFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val announcements: StateFlow<List<AnnouncementEntity>> = repository.getAnnouncementsFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val materials: StateFlow<List<MaterialEntity>> = repository.getAllMaterialsFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun createAssignment(
        course: CourseEntity,
        title: String,
        description: String,
        dueDate: String,
        lecturerName: String
    ) {
        viewModelScope.launch {
            val assignment = AssignmentEntity(
                courseId = course.id,
                courseCode = course.code,
                courseTitle = course.title,
                title = title,
                description = description,
                dueDate = dueDate,
                totalMarks = 100,
                lecturerName = lecturerName
            )
            repository.insertAssignment(assignment)
        }
    }

    fun uploadMaterial(
        course: CourseEntity,
        title: String,
        type: String,
        description: String
    ) {
        viewModelScope.launch {
            val dateStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val material = MaterialEntity(
                courseCode = course.code,
                courseTitle = course.title,
                title = title,
                type = type,
                uploadDate = dateStr,
                fileSize = "3.5 MB",
                description = description
            )
            repository.insertMaterial(material)
        }
    }

    fun saveAttendance(
        course: CourseEntity,
        attendanceMap: Map<Long, Boolean>,
        studentList: List<UserEntity>
    ) {
        viewModelScope.launch {
            val dateStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val records = studentList.map { student ->
                val isPresent = attendanceMap[student.id] ?: true
                AttendanceRecordEntity(
                    courseId = course.id,
                    courseCode = course.code,
                    courseTitle = course.title,
                    studentId = student.id,
                    studentName = student.fullName,
                    regNumber = student.regOrStaffNumber.ifEmpty { "UMMA/CS/001/2026" },
                    date = dateStr,
                    status = if (isPresent) "PRESENT" else "ABSENT",
                    totalClasses = 15,
                    attendedClasses = if (isPresent) 15 else 14
                )
            }
            repository.recordAttendanceBatch(records)
        }
    }

    fun enterMarks(
        student: UserEntity,
        course: CourseEntity,
        catMarks: Int,
        examMarks: Int
    ) {
        viewModelScope.launch {
            val total = catMarks + examMarks
            val (grade, gp) = calculateGradeAndGp(total)
            val result = ResultEntity(
                studentId = student.id,
                courseId = course.id,
                courseCode = course.code,
                courseTitle = course.title,
                creditHours = course.creditHours,
                catMarks = catMarks,
                examMarks = examMarks,
                totalMarks = total,
                grade = grade,
                gradePoint = gp,
                semester = course.semester
            )
            repository.insertResult(result)
        }
    }

    private fun calculateGradeAndGp(total: Int): Pair<String, Double> {
        return when {
            total >= 80 -> Pair("A", 4.0)
            total >= 70 -> Pair("B", 3.0)
            total >= 60 -> Pair("C", 2.0)
            total >= 50 -> Pair("D", 1.0)
            else -> Pair("F", 0.0)
        }
    }
}
