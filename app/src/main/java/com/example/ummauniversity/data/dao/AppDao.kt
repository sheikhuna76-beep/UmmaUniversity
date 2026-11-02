package com.example.ummauniversity.data.dao

import androidx.room.*
import com.example.ummauniversity.data.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    suspend fun getUserById(id: Long): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity): Long

    @Update
    suspend fun updateUser(user: UserEntity): Int

    @Query("SELECT * FROM users WHERE role = 'STUDENT'")
    fun getAllStudentsFlow(): Flow<List<UserEntity>>

    @Query("SELECT * FROM users WHERE role = 'LECTURER'")
    fun getAllLecturersFlow(): Flow<List<UserEntity>>

    @Query("SELECT COUNT(*) FROM users WHERE role = 'STUDENT'")
    suspend fun getStudentCount(): Int

    @Query("SELECT COUNT(*) FROM users WHERE role = 'LECTURER'")
    suspend fun getLecturerCount(): Int

    @Query("DELETE FROM users WHERE id = :userId")
    suspend fun deleteUser(userId: Long): Int
}

@Dao
interface StudentProfileDao {
    @Query("SELECT * FROM student_profiles WHERE userId = :userId LIMIT 1")
    suspend fun getProfileByUserId(userId: Long): StudentProfileEntity?

    @Query("SELECT * FROM student_profiles WHERE userId = :userId LIMIT 1")
    fun getProfileFlowByUserId(userId: Long): Flow<StudentProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateProfile(profile: StudentProfileEntity): Long
}

@Dao
interface AcademicDao {
    @Query("SELECT * FROM courses")
    fun getAllCoursesFlow(): Flow<List<CourseEntity>>

    @Query("SELECT * FROM courses")
    suspend fun getAllCourses(): List<CourseEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourse(course: CourseEntity): Long

    @Query("DELETE FROM courses WHERE id = :courseId")
    suspend fun deleteCourse(courseId: Long): Int

    @Query("SELECT * FROM course_registrations WHERE studentId = :studentId")
    fun getRegistrationsForStudentFlow(studentId: Long): Flow<List<CourseRegistrationEntity>>

    @Query("SELECT * FROM course_registrations WHERE studentId = :studentId")
    suspend fun getRegistrationsForStudent(studentId: Long): List<CourseRegistrationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRegistration(registration: CourseRegistrationEntity): Long

    @Query("DELETE FROM course_registrations WHERE studentId = :studentId AND courseId = :courseId")
    suspend fun deleteRegistration(studentId: Long, courseId: Long): Int

    @Query("SELECT * FROM results WHERE studentId = :studentId")
    fun getResultsForStudentFlow(studentId: Long): Flow<List<ResultEntity>>

    @Query("SELECT * FROM results WHERE studentId = :studentId")
    suspend fun getResultsForStudent(studentId: Long): List<ResultEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResult(result: ResultEntity): Long

    @Query("SELECT * FROM attendance_records WHERE studentId = :studentId")
    fun getAttendanceForStudentFlow(studentId: Long): Flow<List<AttendanceRecordEntity>>

    @Query("SELECT * FROM attendance_records WHERE courseCode = :courseCode")
    suspend fun getAttendanceForCourse(courseCode: String): List<AttendanceRecordEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttendance(attendance: AttendanceRecordEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttendanceBatch(records: List<AttendanceRecordEntity>): List<Long>
}

@Dao
interface ScheduleDao {
    @Query("SELECT * FROM timetable_entries ORDER BY dayOfWeek ASC, startTime ASC")
    fun getTimetableFlow(): Flow<List<TimetableEntryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTimetableEntry(entry: TimetableEntryEntity): Long

    @Query("SELECT * FROM examination_entries ORDER BY examDate ASC, startTime ASC")
    fun getExamScheduleFlow(): Flow<List<ExaminationEntryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExamEntry(entry: ExaminationEntryEntity): Long
}

@Dao
interface ELearningDao {
    @Query("SELECT * FROM assignments ORDER BY dueDate ASC")
    fun getAllAssignmentsFlow(): Flow<List<AssignmentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAssignment(assignment: AssignmentEntity): Long

    @Query("SELECT * FROM assignment_submissions WHERE assignmentId = :assignmentId")
    suspend fun getSubmissionsForAssignment(assignmentId: Long): List<AssignmentSubmissionEntity>

    @Query("SELECT * FROM assignment_submissions WHERE studentId = :studentId")
    fun getSubmissionsForStudentFlow(studentId: Long): Flow<List<AssignmentSubmissionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubmission(submission: AssignmentSubmissionEntity): Long

    @Update
    suspend fun updateSubmission(submission: AssignmentSubmissionEntity): Int

    @Query("SELECT * FROM learning_materials")
    fun getAllMaterialsFlow(): Flow<List<MaterialEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMaterial(material: MaterialEntity): Long
}

@Dao
interface FinanceDao {
    @Query("SELECT * FROM fees WHERE studentId = :studentId LIMIT 1")
    fun getFeeForStudentFlow(studentId: Long): Flow<FeeEntity?>

    @Query("SELECT * FROM fees WHERE studentId = :studentId LIMIT 1")
    suspend fun getFeeForStudent(studentId: Long): FeeEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateFee(fee: FeeEntity): Long

    @Query("SELECT * FROM payments WHERE studentId = :studentId ORDER BY paymentDate DESC")
    fun getPaymentsForStudentFlow(studentId: Long): Flow<List<PaymentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPayment(payment: PaymentEntity): Long

    @Query("SELECT SUM(amountPaid) FROM fees")
    suspend fun getTotalCollectedFees(): Double?
}

@Dao
interface LibraryDao {
    @Query("SELECT * FROM books")
    fun getAllBooksFlow(): Flow<List<BookEntity>>

    @Query("SELECT * FROM books WHERE title LIKE '%' || :query || '%' OR author LIKE '%' || :query || '%' OR category LIKE '%' || :query || '%'")
    fun searchBooksFlow(query: String): Flow<List<BookEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: BookEntity): Long

    @Query("SELECT * FROM borrowed_books WHERE studentId = :studentId")
    fun getBorrowedBooksForStudentFlow(studentId: Long): Flow<List<BorrowedBookEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBorrowedBook(borrowedBook: BorrowedBookEntity): Long
}

@Dao
interface CommunicationDao {
    @Query("SELECT * FROM announcements ORDER BY id DESC")
    fun getAnnouncementsFlow(): Flow<List<AnnouncementEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnnouncement(announcement: AnnouncementEntity): Long

    @Query("DELETE FROM announcements WHERE id = :id")
    suspend fun deleteAnnouncement(id: Long): Int

    @Query("SELECT * FROM events ORDER BY date ASC")
    fun getEventsFlow(): Flow<List<EventEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: EventEntity): Long

    @Query("DELETE FROM events WHERE id = :id")
    suspend fun deleteEvent(id: Long): Int

    @Query("SELECT * FROM notifications WHERE userId = :userId ORDER BY id DESC")
    fun getNotificationsForUserFlow(userId: Long): Flow<List<NotificationItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: NotificationItemEntity): Long
}

@Dao
interface HelpDeskDao {
    @Query("SELECT * FROM help_desk_requests WHERE studentId = :studentId ORDER BY id DESC")
    fun getRequestsForStudentFlow(studentId: Long): Flow<List<HelpDeskRequestEntity>>

    @Query("SELECT * FROM help_desk_requests ORDER BY id DESC")
    fun getAllRequestsFlow(): Flow<List<HelpDeskRequestEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRequest(request: HelpDeskRequestEntity): Long

    @Query("UPDATE help_desk_requests SET status = :status, response = :response WHERE id = :requestId")
    suspend fun updateRequestStatus(requestId: Long, status: String, response: String): Int
}

@Dao
interface AdminDao {
    @Query("SELECT * FROM departments")
    fun getAllDepartmentsFlow(): Flow<List<DepartmentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDepartment(department: DepartmentEntity): Long

    @Query("DELETE FROM departments WHERE id = :id")
    suspend fun deleteDepartment(id: Long): Int

    @Query("SELECT * FROM programmes")
    fun getAllProgrammesFlow(): Flow<List<ProgrammeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgramme(programme: ProgrammeEntity): Long

    @Query("DELETE FROM programmes WHERE id = :id")
    suspend fun deleteProgramme(id: Long): Int
}
