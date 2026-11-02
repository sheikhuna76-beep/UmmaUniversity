package com.example.ummauniversity.ui.navigation

sealed class ScreenRoute(val route: String) {
    object Splash : ScreenRoute("splash")
    object Login : ScreenRoute("login")
    object Register : ScreenRoute("register")
    object ForgotPassword : ScreenRoute("forgot_password")

    // Student
    object StudentHome : ScreenRoute("student_home")
    object StudentAcademics : ScreenRoute("student_academics")
    object StudentCourseReg : ScreenRoute("student_course_reg")
    object StudentResults : ScreenRoute("student_results")
    object StudentTranscript : ScreenRoute("student_transcript")
    object StudentAttendance : ScreenRoute("student_attendance")
    object StudentTimetable : ScreenRoute("student_timetable")
    object StudentExams : ScreenRoute("student_exams")
    object StudentELearning : ScreenRoute("student_elearning")
    object StudentFinance : ScreenRoute("student_finance")
    object StudentLibrary : ScreenRoute("student_library")
    object StudentNews : ScreenRoute("student_news")
    object StudentServices : ScreenRoute("student_services")
    object StudentHelpDesk : ScreenRoute("student_helpdesk")
    object StudentNotifications : ScreenRoute("student_notifications")
    object StudentProfile : ScreenRoute("student_profile")

    // Lecturer
    object LecturerHome : ScreenRoute("lecturer_home")
    object LecturerAttendance : ScreenRoute("lecturer_attendance")
    object LecturerAssignments : ScreenRoute("lecturer_assignments")
    object LecturerMarks : ScreenRoute("lecturer_marks")

    // Admin
    object AdminHome : ScreenRoute("admin_home")
    object AdminManageStudents : ScreenRoute("admin_manage_students")
    object AdminManageLecturers : ScreenRoute("admin_manage_lecturers")
    object AdminManageCourses : ScreenRoute("admin_manage_courses")
    object AdminManageFees : ScreenRoute("admin_manage_fees")
    object AdminManageAnnouncements : ScreenRoute("admin_manage_announcements")
    object AdminManageHelpDesk : ScreenRoute("admin_manage_helpdesk")
}
