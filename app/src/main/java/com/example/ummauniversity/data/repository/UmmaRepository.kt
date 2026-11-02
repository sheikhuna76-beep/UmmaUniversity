package com.example.ummauniversity.data.repository

import com.example.ummauniversity.data.database.UmmaDatabase
import com.example.ummauniversity.data.entities.*
import kotlinx.coroutines.flow.Flow

class UmmaRepository(private val db: UmmaDatabase) {

    // Auth & User
    suspend fun getUserByEmail(email: String): UserEntity? = db.userDao().getUserByEmail(email)
    suspend fun getUserById(id: Long): UserEntity? = db.userDao().getUserById(id)
    suspend fun registerUser(user: UserEntity): Long = db.userDao().insertUser(user)
    suspend fun updateUser(user: UserEntity) = db.userDao().updateUser(user)
    fun getAllStudentsFlow(): Flow<List<UserEntity>> = db.userDao().getAllStudentsFlow()
    fun getAllLecturersFlow(): Flow<List<UserEntity>> = db.userDao().getAllLecturersFlow()
    suspend fun getStudentCount(): Int = db.userDao().getStudentCount()
    suspend fun getLecturerCount(): Int = db.userDao().getLecturerCount()
    suspend fun deleteUser(userId: Long) = db.userDao().deleteUser(userId)

    // Student Profile
    suspend fun getStudentProfile(userId: Long): StudentProfileEntity? = db.studentProfileDao().getProfileByUserId(userId)
    fun getStudentProfileFlow(userId: Long): Flow<StudentProfileEntity?> = db.studentProfileDao().getProfileFlowByUserId(userId)
    suspend fun saveStudentProfile(profile: StudentProfileEntity) = db.studentProfileDao().insertOrUpdateProfile(profile)

    // Academics
    fun getAllCoursesFlow(): Flow<List<CourseEntity>> = db.academicDao().getAllCoursesFlow()
    suspend fun getAllCourses(): List<CourseEntity> = db.academicDao().getAllCourses()
    suspend fun insertCourse(course: CourseEntity): Long = db.academicDao().insertCourse(course)
    suspend fun deleteCourse(courseId: Long) = db.academicDao().deleteCourse(courseId)

    fun getRegistrationsForStudentFlow(studentId: Long): Flow<List<CourseRegistrationEntity>> =
        db.academicDao().getRegistrationsForStudentFlow(studentId)
    suspend fun registerCourse(registration: CourseRegistrationEntity): Long =
        db.academicDao().insertRegistration(registration)
    suspend fun unregisterCourse(studentId: Long, courseId: Long) =
        db.academicDao().deleteRegistration(studentId, courseId)

    fun getResultsForStudentFlow(studentId: Long): Flow<List<ResultEntity>> =
        db.academicDao().getResultsForStudentFlow(studentId)
    suspend fun insertResult(result: ResultEntity): Long = db.academicDao().insertResult(result)

    fun getAttendanceForStudentFlow(studentId: Long): Flow<List<AttendanceRecordEntity>> =
        db.academicDao().getAttendanceForStudentFlow(studentId)
    suspend fun getAttendanceForCourse(courseCode: String): List<AttendanceRecordEntity> =
        db.academicDao().getAttendanceForCourse(courseCode)
    suspend fun recordAttendanceBatch(records: List<AttendanceRecordEntity>) =
        db.academicDao().insertAttendanceBatch(records)

    // Schedules
    fun getTimetableFlow(): Flow<List<TimetableEntryEntity>> = db.scheduleDao().getTimetableFlow()
    suspend fun insertTimetable(entry: TimetableEntryEntity): Long = db.scheduleDao().insertTimetableEntry(entry)

    fun getExamScheduleFlow(): Flow<List<ExaminationEntryEntity>> = db.scheduleDao().getExamScheduleFlow()
    suspend fun insertExamEntry(entry: ExaminationEntryEntity): Long = db.scheduleDao().insertExamEntry(entry)

    // E-Learning
    fun getAllAssignmentsFlow(): Flow<List<AssignmentEntity>> = db.eLearningDao().getAllAssignmentsFlow()
    suspend fun insertAssignment(assignment: AssignmentEntity): Long = db.eLearningDao().insertAssignment(assignment)

    suspend fun getSubmissionsForAssignment(assignmentId: Long): List<AssignmentSubmissionEntity> =
        db.eLearningDao().getSubmissionsForAssignment(assignmentId)
    fun getSubmissionsForStudentFlow(studentId: Long): Flow<List<AssignmentSubmissionEntity>> =
        db.eLearningDao().getSubmissionsForStudentFlow(studentId)
    suspend fun submitAssignment(submission: AssignmentSubmissionEntity): Long =
        db.eLearningDao().insertSubmission(submission)
    suspend fun gradeSubmission(submission: AssignmentSubmissionEntity) =
        db.eLearningDao().updateSubmission(submission)

    fun getAllMaterialsFlow(): Flow<List<MaterialEntity>> = db.eLearningDao().getAllMaterialsFlow()
    suspend fun insertMaterial(material: MaterialEntity): Long = db.eLearningDao().insertMaterial(material)

    // Finance
    fun getFeeForStudentFlow(studentId: Long): Flow<FeeEntity?> = db.financeDao().getFeeForStudentFlow(studentId)
    fun getPaymentsForStudentFlow(studentId: Long): Flow<List<PaymentEntity>> =
        db.financeDao().getPaymentsForStudentFlow(studentId)
    suspend fun recordPayment(payment: PaymentEntity): Long {
        val id = db.financeDao().insertPayment(payment)
        // Update fee balance
        val currentFee = db.financeDao().getFeeForStudent(payment.studentId)
        if (currentFee != null) {
            val newPaid = currentFee.amountPaid + payment.amount
            val newBalance = (currentFee.totalFees - newPaid).coerceAtLeast(0.0)
            db.financeDao().insertOrUpdateFee(
                currentFee.copy(amountPaid = newPaid, balance = newBalance)
            )
            // Update profile fee balance
            val currentProfile = db.studentProfileDao().getProfileByUserId(payment.studentId)
            if (currentProfile != null) {
                db.studentProfileDao().insertOrUpdateProfile(
                    currentProfile.copy(feePaid = newPaid, feeBalance = newBalance)
                )
            }
        }
        return id
    }
    suspend fun getTotalCollectedFees(): Double = db.financeDao().getTotalCollectedFees() ?: 0.0

    // Library
    fun getAllBooksFlow(): Flow<List<BookEntity>> = db.libraryDao().getAllBooksFlow()
    fun searchBooksFlow(query: String): Flow<List<BookEntity>> = db.libraryDao().searchBooksFlow(query)
    fun getBorrowedBooksForStudentFlow(studentId: Long): Flow<List<BorrowedBookEntity>> =
        db.libraryDao().getBorrowedBooksForStudentFlow(studentId)
    suspend fun reserveOrBorrowBook(borrowedBook: BorrowedBookEntity): Long =
        db.libraryDao().insertBorrowedBook(borrowedBook)

    // Communication
    fun getAnnouncementsFlow(): Flow<List<AnnouncementEntity>> = db.communicationDao().getAnnouncementsFlow()
    suspend fun insertAnnouncement(announcement: AnnouncementEntity): Long =
        db.communicationDao().insertAnnouncement(announcement)
    suspend fun deleteAnnouncement(id: Long) = db.communicationDao().deleteAnnouncement(id)

    fun getEventsFlow(): Flow<List<EventEntity>> = db.communicationDao().getEventsFlow()
    suspend fun insertEvent(event: EventEntity): Long = db.communicationDao().insertEvent(event)
    suspend fun deleteEvent(id: Long) = db.communicationDao().deleteEvent(id)

    fun getNotificationsForUserFlow(userId: Long): Flow<List<NotificationItemEntity>> =
        db.communicationDao().getNotificationsForUserFlow(userId)

    // Help Desk
    fun getHelpDeskRequestsForStudentFlow(studentId: Long): Flow<List<HelpDeskRequestEntity>> =
        db.helpDeskDao().getRequestsForStudentFlow(studentId)
    fun getAllHelpDeskRequestsFlow(): Flow<List<HelpDeskRequestEntity>> = db.helpDeskDao().getAllRequestsFlow()
    suspend fun submitHelpDeskRequest(request: HelpDeskRequestEntity): Long =
        db.helpDeskDao().insertRequest(request)
    suspend fun updateHelpDeskRequestStatus(requestId: Long, status: String, response: String) =
        db.helpDeskDao().updateRequestStatus(requestId, status, response)

    // Admin Management
    fun getAllDepartmentsFlow(): Flow<List<DepartmentEntity>> = db.adminDao().getAllDepartmentsFlow()
    suspend fun insertDepartment(department: DepartmentEntity): Long = db.adminDao().insertDepartment(department)
    suspend fun deleteDepartment(id: Long) = db.adminDao().deleteDepartment(id)

    fun getAllProgrammesFlow(): Flow<List<ProgrammeEntity>> = db.adminDao().getAllProgrammesFlow()
    suspend fun insertProgramme(programme: ProgrammeEntity): Long = db.adminDao().insertProgramme(programme)
    suspend fun deleteProgramme(id: Long) = db.adminDao().deleteProgramme(id)
}
