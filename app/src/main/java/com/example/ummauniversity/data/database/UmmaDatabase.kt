package com.example.ummauniversity.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.ummauniversity.data.dao.*
import com.example.ummauniversity.data.entities.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        UserEntity::class,
        StudentProfileEntity::class,
        LecturerProfileEntity::class,
        DepartmentEntity::class,
        ProgrammeEntity::class,
        CourseEntity::class,
        CourseRegistrationEntity::class,
        ResultEntity::class,
        AttendanceRecordEntity::class,
        TimetableEntryEntity::class,
        ExaminationEntryEntity::class,
        AssignmentEntity::class,
        AssignmentSubmissionEntity::class,
        MaterialEntity::class,
        FeeEntity::class,
        PaymentEntity::class,
        BookEntity::class,
        BorrowedBookEntity::class,
        AnnouncementEntity::class,
        EventEntity::class,
        NotificationItemEntity::class,
        HelpDeskRequestEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class UmmaDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun studentProfileDao(): StudentProfileDao
    abstract fun academicDao(): AcademicDao
    abstract fun scheduleDao(): ScheduleDao
    abstract fun eLearningDao(): ELearningDao
    abstract fun financeDao(): FinanceDao
    abstract fun libraryDao(): LibraryDao
    abstract fun communicationDao(): CommunicationDao
    abstract fun helpDeskDao(): HelpDeskDao
    abstract fun adminDao(): AdminDao

    companion object {
        @Volatile
        private var INSTANCE: UmmaDatabase? = null

        fun getInstance(context: Context): UmmaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UmmaDatabase::class.java,
                    "umma_university_db"
                )
                    .addCallback(DatabaseCallback(context.applicationContext))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback(private val context: Context) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                CoroutineScope(Dispatchers.IO).launch {
                    val database = getInstance(context)
                    populateInitialData(database)
                }
            }
        }

        private suspend fun populateInitialData(db: UmmaDatabase) {
            val userDao = db.userDao()
            val profileDao = db.studentProfileDao()
            val academicDao = db.academicDao()
            val scheduleDao = db.scheduleDao()
            val eLearningDao = db.eLearningDao()
            val financeDao = db.financeDao()
            val libraryDao = db.libraryDao()
            val commDao = db.communicationDao()
            val helpDeskDao = db.helpDeskDao()
            val adminDao = db.adminDao()

            InitialData.sampleUsers.forEach { userDao.insertUser(it) }
            profileDao.insertOrUpdateProfile(InitialData.sampleStudentProfile)
            InitialData.sampleDepartments.forEach { adminDao.insertDepartment(it) }
            InitialData.sampleProgrammes.forEach { adminDao.insertProgramme(it) }
            InitialData.sampleCourses.forEach { academicDao.insertCourse(it) }
            InitialData.sampleRegistrations.forEach { academicDao.insertRegistration(it) }
            InitialData.sampleResults.forEach { academicDao.insertResult(it) }
            InitialData.sampleAttendance.forEach { academicDao.insertAttendance(it) }
            InitialData.sampleTimetable.forEach { scheduleDao.insertTimetableEntry(it) }
            InitialData.sampleExams.forEach { scheduleDao.insertExamEntry(it) }
            InitialData.sampleAssignments.forEach { eLearningDao.insertAssignment(it) }
            InitialData.sampleSubmissions.forEach { eLearningDao.insertSubmission(it) }
            InitialData.sampleMaterials.forEach { eLearningDao.insertMaterial(it) }
            financeDao.insertOrUpdateFee(InitialData.sampleFee)
            InitialData.samplePayments.forEach { financeDao.insertPayment(it) }
            InitialData.sampleBooks.forEach { libraryDao.insertBook(it) }
            InitialData.sampleBorrowedBooks.forEach { libraryDao.insertBorrowedBook(it) }
            InitialData.sampleAnnouncements.forEach { commDao.insertAnnouncement(it) }
            InitialData.sampleEvents.forEach { commDao.insertEvent(it) }
            InitialData.sampleNotifications.forEach { commDao.insertNotification(it) }
            InitialData.sampleHelpDesk.forEach { helpDeskDao.insertRequest(it) }
        }
    }
}
