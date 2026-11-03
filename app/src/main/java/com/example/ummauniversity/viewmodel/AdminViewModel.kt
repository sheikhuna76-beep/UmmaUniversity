package com.example.ummauniversity.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ummauniversity.data.entities.*
import com.example.ummauniversity.data.repository.UmmaRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AdminViewModel(private val repository: UmmaRepository) : ViewModel() {

    val students: StateFlow<List<UserEntity>> = repository.getAllStudentsFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val lecturers: StateFlow<List<UserEntity>> = repository.getAllLecturersFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val departments: StateFlow<List<DepartmentEntity>> = repository.getAllDepartmentsFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val programmes: StateFlow<List<ProgrammeEntity>> = repository.getAllProgrammesFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val courses: StateFlow<List<CourseEntity>> = repository.getAllCoursesFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val announcements: StateFlow<List<AnnouncementEntity>> = repository.getAnnouncementsFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val events: StateFlow<List<EventEntity>> = repository.getEventsFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val helpDeskRequests: StateFlow<List<HelpDeskRequestEntity>> = repository.getAllHelpDeskRequestsFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _totalCollectedFees = MutableStateFlow(0.0)
    val totalCollectedFees: StateFlow<Double> = _totalCollectedFees.asStateFlow()

    init {
        loadFeeStats()
    }

    private fun loadFeeStats() {
        viewModelScope.launch {
            _totalCollectedFees.value = repository.getTotalCollectedFees()
        }
    }

    fun addStudent(fullName: String, email: String, regNo: String, dept: String, prog: String, password: String = "student123") {
        viewModelScope.launch {
            val user = UserEntity(
                fullName = fullName,
                email = email,
                passwordHash = password,
                role = "STUDENT",
                regOrStaffNumber = regNo,
                department = dept,
                programme = prog
            )
            val id = repository.registerUser(user)
            repository.saveStudentProfile(
                StudentProfileEntity(
                    userId = id,
                    regNumber = regNo,
                    programme = prog,
                    department = dept
                )
            )
        }
    }

    fun addLecturer(fullName: String, email: String, staffId: String, dept: String, password: String = "lecturer123") {
        viewModelScope.launch {
            val user = UserEntity(
                fullName = fullName,
                email = email,
                passwordHash = password,
                role = "LECTURER",
                regOrStaffNumber = staffId,
                department = dept
            )
            repository.registerUser(user)
        }
    }

    fun deleteUser(userId: Long) {
        viewModelScope.launch {
            repository.deleteUser(userId)
        }
    }

    fun addDepartment(code: String, name: String, hod: String, building: String) {
        viewModelScope.launch {
            repository.insertDepartment(DepartmentEntity(code = code, name = name, headOfDepartment = hod, building = building))
        }
    }

    fun deleteDepartment(id: Long) {
        viewModelScope.launch {
            repository.deleteDepartment(id)
        }
    }

    fun addProgramme(code: String, name: String, deptName: String, duration: Int) {
        viewModelScope.launch {
            repository.insertProgramme(ProgrammeEntity(code = code, name = name, departmentName = deptName, durationYears = duration))
        }
    }

    fun deleteProgramme(id: Long) {
        viewModelScope.launch {
            repository.deleteProgramme(id)
        }
    }

    fun addCourse(code: String, title: String, creditHours: Int, lecturer: String, dept: String) {
        viewModelScope.launch {
            repository.insertCourse(
                CourseEntity(
                    code = code,
                    title = title,
                    creditHours = creditHours,
                    semester = "Semester 1, 2026/2027",
                    lecturerName = lecturer,
                    department = dept
                )
            )
        }
    }

    fun deleteCourse(id: Long) {
        viewModelScope.launch {
            repository.deleteCourse(id)
        }
    }

    fun addAnnouncement(title: String, content: String, dept: String) {
        viewModelScope.launch {
            val dateStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            repository.insertAnnouncement(
                AnnouncementEntity(
                    title = title,
                    content = content,
                    date = dateStr,
                    department = dept,
                    authorRole = "ADMIN",
                    targetAudience = "ALL"
                )
            )
        }
    }

    fun deleteAnnouncement(id: Long) {
        viewModelScope.launch {
            repository.deleteAnnouncement(id)
        }
    }

    fun addEvent(title: String, description: String, date: String, time: String, location: String, category: String) {
        viewModelScope.launch {
            repository.insertEvent(
                EventEntity(
                    title = title,
                    description = description,
                    date = date,
                    time = time,
                    location = location,
                    category = category
                )
            )
        }
    }

    fun updateHelpDeskRequest(id: Long, status: String, response: String) {
        viewModelScope.launch {
            repository.updateHelpDeskRequestStatus(id, status, response)
        }
    }
}
