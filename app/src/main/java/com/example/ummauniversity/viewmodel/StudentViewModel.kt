package com.example.ummauniversity.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ummauniversity.data.entities.*
import com.example.ummauniversity.data.repository.UmmaRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class StudentViewModel(private val repository: UmmaRepository) : ViewModel() {

    private val _studentId = MutableStateFlow<Long>(1L)
    val studentId: StateFlow<Long> = _studentId.asStateFlow()

    fun setStudentId(id: Long) {
        _studentId.value = id
    }

    val studentProfile: StateFlow<StudentProfileEntity?> = _studentId.flatMapLatest { id ->
        repository.getStudentProfileFlow(id)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val registeredCourses: StateFlow<List<CourseRegistrationEntity>> = _studentId.flatMapLatest { id ->
        repository.getRegistrationsForStudentFlow(id)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val availableCourses: StateFlow<List<CourseEntity>> = repository.getAllCoursesFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val results: StateFlow<List<ResultEntity>> = _studentId.flatMapLatest { id ->
        repository.getResultsForStudentFlow(id)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val attendanceRecords: StateFlow<List<AttendanceRecordEntity>> = _studentId.flatMapLatest { id ->
        repository.getAttendanceForStudentFlow(id)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val timetable: StateFlow<List<TimetableEntryEntity>> = repository.getTimetableFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val examSchedule: StateFlow<List<ExaminationEntryEntity>> = repository.getExamScheduleFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val assignments: StateFlow<List<AssignmentEntity>> = repository.getAllAssignmentsFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val submissions: StateFlow<List<AssignmentSubmissionEntity>> = _studentId.flatMapLatest { id ->
        repository.getSubmissionsForStudentFlow(id)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val materials: StateFlow<List<MaterialEntity>> = repository.getAllMaterialsFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val feeDetails: StateFlow<FeeEntity?> = _studentId.flatMapLatest { id ->
        repository.getFeeForStudentFlow(id)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val paymentHistory: StateFlow<List<PaymentEntity>> = _studentId.flatMapLatest { id ->
        repository.getPaymentsForStudentFlow(id)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val books: StateFlow<List<BookEntity>> = repository.getAllBooksFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val borrowedBooks: StateFlow<List<BorrowedBookEntity>> = _studentId.flatMapLatest { id ->
        repository.getBorrowedBooksForStudentFlow(id)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val announcements: StateFlow<List<AnnouncementEntity>> = repository.getAnnouncementsFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val events: StateFlow<List<EventEntity>> = repository.getEventsFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val notifications: StateFlow<List<NotificationItemEntity>> = _studentId.flatMapLatest { id ->
        repository.getNotificationsForUserFlow(id)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val helpDeskRequests: StateFlow<List<HelpDeskRequestEntity>> = _studentId.flatMapLatest { id ->
        repository.getHelpDeskRequestsForStudentFlow(id)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Actions
    fun registerUnit(course: CourseEntity) {
        viewModelScope.launch {
            val sId = _studentId.value
            val reg = CourseRegistrationEntity(
                studentId = sId,
                courseId = course.id,
                courseCode = course.code,
                courseTitle = course.title,
                creditHours = course.creditHours,
                lecturerName = course.lecturerName,
                semester = course.semester
            )
            repository.registerCourse(reg)
        }
    }

    fun unregisterUnit(courseId: Long) {
        viewModelScope.launch {
            repository.unregisterCourse(_studentId.value, courseId)
        }
    }

    fun submitAssignment(assignment: AssignmentEntity, content: String, studentName: String, regNo: String) {
        viewModelScope.launch {
            val dateStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val submission = AssignmentSubmissionEntity(
                assignmentId = assignment.id,
                studentId = _studentId.value,
                studentName = studentName,
                regNumber = regNo,
                submissionDate = dateStr,
                content = content,
                status = "Submitted"
            )
            repository.submitAssignment(submission)
        }
    }

    fun makePayment(amount: Double, paymentMethod: String, description: String) {
        viewModelScope.launch {
            val dateStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val txId = "MPN" + (10000000..99999999).random()
            val rcpNo = "RCP-2026-" + (1000..9999).random()
            val payment = PaymentEntity(
                studentId = _studentId.value,
                transactionId = txId,
                amount = amount,
                paymentDate = dateStr,
                paymentMethod = paymentMethod,
                receiptNumber = rcpNo,
                description = description
            )
            repository.recordPayment(payment)
        }
    }

    fun reserveBook(book: BookEntity) {
        viewModelScope.launch {
            val dateStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val dueCalendar = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, 14) }
            val dueDateStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(dueCalendar.time)
            val borrowed = BorrowedBookEntity(
                bookId = book.id,
                bookTitle = book.title,
                studentId = _studentId.value,
                borrowDate = dateStr,
                dueDate = dueDateStr,
                status = "Borrowed"
            )
            repository.reserveOrBorrowBook(borrowed)
        }
    }

    fun submitHelpDeskRequest(studentName: String, category: String, subject: String, description: String) {
        viewModelScope.launch {
            val dateStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val reqId = "REQ-2026-" + String.format(Locale.US, "%03d", (100..999).random())
            val req = HelpDeskRequestEntity(
                requestId = reqId,
                studentId = _studentId.value,
                studentName = studentName,
                category = category,
                subject = subject,
                description = description,
                dateSubmitted = dateStr,
                status = "Pending"
            )
            repository.submitHelpDeskRequest(req)
        }
    }
}
