package com.example.ummauniversity.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val fullName: String,
    val email: String,
    val passwordHash: String,
    val role: String, // STUDENT, LECTURER, ADMIN
    val phone: String = "",
    val regOrStaffNumber: String = "",
    val department: String = "",
    val programme: String = ""
)

@Entity(tableName = "student_profiles")
data class StudentProfileEntity(
    @PrimaryKey val userId: Long,
    val regNumber: String,
    val programme: String,
    val department: String,
    val yearOfStudy: Int = 3,
    val semester: String = "Semester 1, 2026/2027",
    val gpa: Double = 3.75,
    val cgpa: Double = 3.68,
    val totalFees: Double = 65000.0,
    val feePaid: Double = 45000.0,
    val feeBalance: Double = 20000.0
)

@Entity(tableName = "lecturer_profiles")
data class LecturerProfileEntity(
    @PrimaryKey val userId: Long,
    val staffId: String,
    val department: String,
    val officeLocation: String = "Science & Tech Building, Room 204",
    val title: String = "Dr."
)

@Entity(tableName = "departments")
data class DepartmentEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val code: String,
    val name: String,
    val headOfDepartment: String,
    val building: String
)

@Entity(tableName = "programmes")
data class ProgrammeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val code: String,
    val name: String,
    val departmentName: String,
    val durationYears: Int
)

@Entity(tableName = "courses")
data class CourseEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val code: String,
    val title: String,
    val creditHours: Int,
    val semester: String,
    val lecturerName: String,
    val department: String,
    val description: String = ""
)

@Entity(tableName = "course_registrations")
data class CourseRegistrationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val studentId: Long,
    val courseId: Long,
    val courseCode: String,
    val courseTitle: String,
    val creditHours: Int,
    val lecturerName: String,
    val semester: String,
    val status: String = "Registered" // Registered, Dropped, Completed
)

@Entity(tableName = "results")
data class ResultEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val studentId: Long,
    val courseId: Long,
    val courseCode: String,
    val courseTitle: String,
    val creditHours: Int,
    val catMarks: Int, // out of 30
    val examMarks: Int, // out of 70
    val totalMarks: Int, // out of 100
    val grade: String, // A, B, C, D, F
    val gradePoint: Double, // 4.0, 3.0, 2.0, 1.0, 0.0
    val semester: String,
    val academicYear: String = "2026/2027"
)

@Entity(tableName = "attendance_records")
data class AttendanceRecordEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val courseId: Long,
    val courseCode: String,
    val courseTitle: String,
    val studentId: Long,
    val studentName: String,
    val regNumber: String,
    val date: String,
    val status: String, // PRESENT, ABSENT
    val totalClasses: Int = 15,
    val attendedClasses: Int = 14
)

@Entity(tableName = "timetable_entries")
data class TimetableEntryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val courseCode: String,
    val courseTitle: String,
    val dayOfWeek: String, // Monday, Tuesday, etc.
    val startTime: String,
    val endTime: String,
    val room: String,
    val lecturerName: String
)

@Entity(tableName = "examination_entries")
data class ExaminationEntryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val courseCode: String,
    val courseTitle: String,
    val examDate: String,
    val startTime: String,
    val endTime: String,
    val venue: String,
    val instructions: String = "Bring Student ID and Exam Card. No mobile phones allowed."
)

@Entity(tableName = "assignments")
data class AssignmentEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val courseId: Long,
    val courseCode: String,
    val courseTitle: String,
    val title: String,
    val description: String,
    val dueDate: String,
    val totalMarks: Int = 100,
    val lecturerName: String
)

@Entity(tableName = "assignment_submissions")
data class AssignmentSubmissionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val assignmentId: Long,
    val studentId: Long,
    val studentName: String,
    val regNumber: String,
    val submissionDate: String,
    val content: String,
    val status: String = "Submitted", // Submitted, Graded
    val marksObtained: Int? = null,
    val feedback: String? = null
)

@Entity(tableName = "learning_materials")
data class MaterialEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val courseCode: String,
    val courseTitle: String,
    val title: String,
    val type: String, // Lecture Notes, PDF, Presentation, Video
    val uploadDate: String,
    val fileSize: String,
    val description: String
)

@Entity(tableName = "fees")
data class FeeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val studentId: Long,
    val tuitionFee: Double,
    val activityFee: Double,
    val examinationFee: Double,
    val libraryFee: Double,
    val medicalFee: Double,
    val totalFees: Double,
    val amountPaid: Double,
    val balance: Double,
    val semester: String
)

@Entity(tableName = "payments")
data class PaymentEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val studentId: Long,
    val transactionId: String,
    val amount: Double,
    val paymentDate: String,
    val paymentMethod: String, // M-PESA, Bank Deposit, Card
    val receiptNumber: String,
    val description: String
)

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val author: String,
    val category: String,
    val isbn: String,
    val availableCopies: Int,
    val totalCopies: Int,
    val shelfLocation: String
)

@Entity(tableName = "borrowed_books")
data class BorrowedBookEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val bookId: Long,
    val bookTitle: String,
    val studentId: Long,
    val borrowDate: String,
    val dueDate: String,
    val status: String, // Borrowed, Returned, Overdue
    val fineAmount: Double = 0.0
)

@Entity(tableName = "announcements")
data class AnnouncementEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val content: String,
    val date: String,
    val department: String,
    val authorRole: String = "ADMIN",
    val targetAudience: String = "ALL" // ALL, STUDENT, LECTURER
)

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String,
    val date: String,
    val time: String,
    val location: String,
    val category: String
)

@Entity(tableName = "notifications")
data class NotificationItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long,
    val title: String,
    val message: String,
    val date: String,
    val type: String, // TIMETABLE, EXAM, ANNOUNCEMENT, EMERGENCY
    val isRead: Boolean = false
)

@Entity(tableName = "help_desk_requests")
data class HelpDeskRequestEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val requestId: String,
    val studentId: Long,
    val studentName: String,
    val category: String, // Academic, Finance, Hostel, IT Support, General
    val subject: String,
    val description: String,
    val dateSubmitted: String,
    val status: String = "Pending", // Pending, In Progress, Resolved
    val response: String = ""
)
