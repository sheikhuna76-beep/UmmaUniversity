package com.example.ummauniversity.data.database

import com.example.ummauniversity.data.entities.*

object InitialData {
    val sampleUsers = listOf(
        UserEntity(
            id = 1,
            fullName = "Asiyah Abdullahi",
            email = "student@umma.ac.ke",
            passwordHash = "student123",
            role = "STUDENT",
            phone = "+254 112 257 994",
            regOrStaffNumber = "UMMA/001/2026",
            department = "Computer Science",
            programme = "Bachelor of Computer Science"
        ),
        UserEntity(
            id = 2,
            fullName = "Dr. Suheila Hussein",
            email = "lecturer@umma.ac.ke",
            passwordHash = "lecturer123",
            role = "LECTURER",
            phone = "+254 722 987 654",
            regOrStaffNumber = "LEC/CS/042",
            department = "Computer Science",
            programme = "Faculty of Science & Tech"
        ),
        UserEntity(
            id = 3,
            fullName = "Admin Liyanah Sheikh",
            email = "admin@umma.ac.ke",
            passwordHash = "admin123",
            role = "ADMIN",
            phone = "+254 700 112 233",
            regOrStaffNumber = "ADM/001",
            department = "Academic Registry",
            programme = "Administration"
        )
    )

    val sampleStudentProfile = StudentProfileEntity(
        userId = 1,
        regNumber = "UMMA/CS/001/2026",
        programme = "Bachelor of Computer Science",
        department = "Computer Science",
        yearOfStudy = 3,
        semester = "Semester 1, 2026/2027",
        gpa = 3.75,
        cgpa = 3.68,
        totalFees = 65000.0,
        feePaid = 45000.0,
        feeBalance = 20000.0
    )

    val sampleLecturerProfile = LecturerProfileEntity(
        userId = 2,
        staffId = "LEC/CS/042",
        department = "Computer Science",
        officeLocation = "Science & Tech Complex, Room 204",
        title = "Dr."
    )

    val sampleDepartments = listOf(
        DepartmentEntity(1, "CS", "Computer Science", "Prof. Ibrahim Kamau", "Science & Tech Complex"),
        DepartmentEntity(2, "BBA", "Business Administration", "Dr. Maryam Njeri", "Business School Block A"),
        DepartmentEntity(3, "NURS", "Nursing & Health Sciences", "Dr. Zainab Ali", "Health Sciences Building"),
        DepartmentEntity(4, "LAW", "Law & Sharia Studies", "Prof. Yusuf Omondi", "Justice Hall"),
        DepartmentEntity(5, "EDU", "Department of Education", "Dr. Aisha Wanjiku", "Humanities Block C")
    )

    val sampleProgrammes = listOf(
        ProgrammeEntity(1, "BSc. CS", "Bachelor of Computer Science", "Computer Science", 4),
        ProgrammeEntity(2, "BBA", "Bachelor of Business Administration", "Business Administration", 4),
        ProgrammeEntity(3, "BSc. NURS", "Bachelor of Science in Nursing", "Nursing & Health Sciences", 4),
        ProgrammeEntity(4, "LLB", "Bachelor of Laws & Sharia", "Law & Sharia Studies", 4),
        ProgrammeEntity(5, "B.Ed", "Bachelor of Education (Arts)", "Department of Education", 4)
    )

    val sampleCourses = listOf(
        CourseEntity(1, "COMP 311", "Mobile Application Development", 3, "Semester 1, 2026/2027", "Dr. Omar Farooq", "Computer Science", "Building native Android apps using Kotlin and Jetpack Compose"),
        CourseEntity(2, "COMP 312", "Database Management Systems", 3, "Semester 1, 2026/2027", "Dr. Omar Farooq", "Computer Science", "Relational database design, SQL, normalization and Room DB"),
        CourseEntity(3, "COMP 313", "Software Engineering", 3, "Semester 1, 2026/2027", "Prof. Ibrahim Kamau", "Computer Science", "Software lifecycle, agile methodologies, and architecture patterns"),
        CourseEntity(4, "COMP 314", "Web Technologies", 3, "Semester 1, 2026/2027", "Dr. Amina Hassan", "Computer Science", "Frontend and Backend web application development with REST APIs"),
        CourseEntity(5, "COMP 315", "Computer Networks", 3, "Semester 1, 2026/2027", "Mr. David Kiprop", "Computer Science", "Network protocols, OSI model, TCP/IP, and network security")
    )

    val sampleRegistrations = listOf(
        CourseRegistrationEntity(1, 1, 1, "COMP 311", "Mobile Application Development", 3, "Dr. Omar Farooq", "Semester 1, 2026/2027", "Registered"),
        CourseRegistrationEntity(2, 1, 2, "COMP 312", "Database Management Systems", 3, "Dr. Omar Farooq", "Semester 1, 2026/2027", "Registered"),
        CourseRegistrationEntity(3, 1, 3, "COMP 313", "Software Engineering", 3, "Prof. Ibrahim Kamau", "Semester 1, 2026/2027", "Registered"),
        CourseRegistrationEntity(4, 1, 4, "COMP 314", "Web Technologies", 3, "Dr. Amina Hassan", "Semester 1, 2026/2027", "Registered"),
        CourseRegistrationEntity(5, 1, 5, "COMP 315", "Computer Networks", 3, "Mr. David Kiprop", "Semester 1, 2026/2027", "Registered")
    )

    val sampleResults = listOf(
        ResultEntity(1, 1, 1, "COMP 311", "Mobile Application Development", 3, 27, 61, 88, "A", 4.0, "Semester 1, 2026/2027"),
        ResultEntity(2, 1, 2, "COMP 312", "Database Management Systems", 3, 24, 52, 76, "B", 3.0, "Semester 1, 2026/2027"),
        ResultEntity(3, 1, 3, "COMP 313", "Software Engineering", 3, 26, 56, 82, "A", 4.0, "Semester 1, 2026/2027"),
        ResultEntity(4, 1, 4, "COMP 314", "Web Technologies", 3, 25, 54, 79, "B", 3.0, "Semester 1, 2026/2027"),
        ResultEntity(5, 1, 5, "COMP 315", "Computer Networks", 3, 28, 62, 90, "A", 4.0, "Semester 1, 2026/2027")
    )

    val sampleAttendance = listOf(
        AttendanceRecordEntity(1, 1, "COMP 311", "Mobile Application Development", 1, "Ahmed Hassan", "UMMA/CS/001/2026", "2026-03-01", "PRESENT", 15, 14),
        AttendanceRecordEntity(2, 2, "COMP 312", "Database Management Systems", 1, "Ahmed Hassan", "UMMA/CS/001/2026", "2026-03-02", "PRESENT", 15, 15),
        AttendanceRecordEntity(3, 3, "COMP 313", "Software Engineering", 1, "Ahmed Hassan", "UMMA/CS/001/2026", "2026-03-03", "PRESENT", 14, 13),
        AttendanceRecordEntity(4, 4, "COMP 314", "Web Technologies", 1, "Ahmed Hassan", "UMMA/CS/001/2026", "2026-03-04", "PRESENT", 15, 14),
        AttendanceRecordEntity(5, 5, "COMP 315", "Computer Networks", 1, "Ahmed Hassan", "UMMA/CS/001/2026", "2026-03-05", "PRESENT", 15, 15)
    )

    val sampleTimetable = listOf(
        TimetableEntryEntity(1, "COMP 311", "Mobile Application Development", "Monday", "08:00 AM", "11:00 AM", "Computer Lab 3", "Dr. Omar Farooq"),
        TimetableEntryEntity(2, "COMP 312", "Database Management Systems", "Tuesday", "11:30 AM", "02:30 PM", "Lecture Hall B", "Dr. Omar Farooq"),
        TimetableEntryEntity(3, "COMP 313", "Software Engineering", "Wednesday", "08:00 AM", "11:00 AM", "Science Block Rm 12", "Prof. Ibrahim Kamau"),
        TimetableEntryEntity(4, "COMP 314", "Web Technologies", "Thursday", "02:00 PM", "05:00 PM", "Computer Lab 1", "Dr. Amina Hassan"),
        TimetableEntryEntity(5, "COMP 315", "Computer Networks", "Friday", "09:00 AM", "12:00 PM", "Network Lab 2", "Mr. David Kiprop")
    )

    val sampleExams = listOf(
        ExaminationEntryEntity(1, "COMP 311", "Mobile Application Development", "2026-04-15", "09:00 AM", "12:00 PM", "Main Exam Hall A", "Clean Desk Policy. Calculator permitted."),
        ExaminationEntryEntity(2, "COMP 312", "Database Management Systems", "2026-04-17", "02:00 PM", "05:00 PM", "Main Exam Hall B", "SQL reference sheet provided."),
        ExaminationEntryEntity(3, "COMP 313", "Software Engineering", "2026-04-20", "09:00 AM", "12:00 PM", "Science Complex Auditorium", "Closed book exam."),
        ExaminationEntryEntity(4, "COMP 314", "Web Technologies", "2026-04-22", "02:00 PM", "05:00 PM", "Computer Lab 3", "Practical on-screen examination."),
        ExaminationEntryEntity(5, "COMP 315", "Computer Networks", "2026-04-24", "09:00 AM", "12:00 PM", "Main Exam Hall A", "Packet Tracer exam setup included.")
    )

    val sampleAssignments = listOf(
        AssignmentEntity(1, 1, "COMP 311", "Mobile Application Development", "Assignment 1: Jetpack Compose UI", "Build a student dashboard interface with Compose components and state management.", "2026-03-25", 100, "Dr. Omar Farooq"),
        AssignmentEntity(2, 2, "COMP 312", "Database Management Systems", "Assignment 2: Room DB Schema Design", "Design an ER diagram and write Room entities for a library system.", "2026-03-28", 100, "Dr. Omar Farooq"),
        AssignmentEntity(3, 3, "COMP 313", "Software Engineering", "Project Proposal & SRS", "Submit software requirement specifications document for your semester project.", "2026-04-02", 100, "Prof. Ibrahim Kamau"),
        AssignmentEntity(4, 4, "COMP 314", "Web Technologies", "REST API Development", "Develop REST API endpoints in Kotlin Ktor / Spring Boot with JSON parsing.", "2026-04-05", 100, "Dr. Amina Hassan"),
        AssignmentEntity(5, 5, "COMP 315", "Computer Networks", "Subnetting & VLAN Configuration", "Configure Cisco Packet Tracer network topology for 4 departments.", "2026-04-08", 100, "Mr. David Kiprop")
    )

    val sampleSubmissions = listOf(
        AssignmentSubmissionEntity(1, 1, 1, "Ahmed Hassan", "UMMA/CS/001/2026", "2026-03-24", "Submitted Compose UI zip file with complete screens.", "Graded", 92, "Excellent UI design and clean state handling!"),
        AssignmentSubmissionEntity(2, 2, 1, "Ahmed Hassan", "UMMA/CS/001/2026", "2026-03-27", "Database schema PDF and sample SQL queries.", "Graded", 88, "Good normalization and Room entity definitions.")
    )

    val sampleMaterials = listOf(
        MaterialEntity(1, "COMP 311", "Mobile Application Development", "Lecture 1: Intro to Kotlin & Compose", "PDF Document", "2026-02-10", "4.2 MB", "Overview of Android Architecture, Jetpack Compose syntax, and Composables"),
        MaterialEntity(2, "COMP 311", "Mobile Application Development", "Lecture 2: State Management & ViewModel", "PDF Document", "2026-02-17", "3.8 MB", "Understanding MutableState, StateFlow, ViewModel lifecycle, and recomposition"),
        MaterialEntity(3, "COMP 312", "Database Management Systems", "Lecture Notes: Normalization & Indexing", "Presentation", "2026-02-12", "5.1 MB", "1NF, 2NF, 3NF principles and B-Tree indexing in SQL databases"),
        MaterialEntity(4, "COMP 313", "Software Engineering", "Architecture Patterns Slide Deck", "Presentation", "2026-02-20", "6.4 MB", "MVVM, MVI, Clean Architecture and Layered Systems in Android"),
        MaterialEntity(5, "COMP 315", "Computer Networks", "Packet Tracer Lab Guide", "Notes", "2026-02-22", "2.9 MB", "Step by step guide for configuring routers, switches and VLANs")
    )

    val sampleFee = FeeEntity(
        id = 1,
        studentId = 1,
        tuitionFee = 50000.0,
        activityFee = 5000.0,
        examinationFee = 4000.0,
        libraryFee = 3000.0,
        medicalFee = 3000.0,
        totalFees = 65000.0,
        amountPaid = 45000.0,
        balance = 20000.0,
        semester = "Semester 1, 2026/2027"
    )

    val samplePayments = listOf(
        PaymentEntity(1, 1, "MPN8932711", 30000.0, "2026-01-10", "M-PESA", "RCP-2026-0041", "Tuition Fee Initial Payment"),
        PaymentEntity(2, 1, "MPN9041284", 15000.0, "2026-02-15", "M-PESA", "RCP-2026-0182", "Tuition Fee Second Installment")
    )

    val sampleBooks = listOf(
        BookEntity(1, "Android Programming: The Big Nerd Ranch Guide", "Bill Phillips & Chris Stewart", "Computer Science", "978-0134706054", 4, 5, "Shelf CS-04"),
        BookEntity(2, "Kotlin in Action", "Dmitry Jemerov & Svetlana Isakova", "Programming", "978-1617293290", 3, 3, "Shelf CS-02"),
        BookEntity(3, "Database System Concepts (7th Edition)", "Abraham Silberschatz", "Database Systems", "978-0078022159", 2, 4, "Shelf DB-01"),
        BookEntity(4, "Clean Architecture: A Craftsman's Guide", "Robert C. Martin", "Software Engineering", "978-0134494166", 5, 5, "Shelf SE-03"),
        BookEntity(5, "Computer Networking: A Top-Down Approach", "James Kurose & Keith Ross", "Networking", "978-0133594140", 3, 4, "Shelf NET-02")
    )

    val sampleBorrowedBooks = listOf(
        BorrowedBookEntity(1, 1, "Android Programming: The Big Nerd Ranch Guide", 1, "2026-03-01", "2026-03-21", "Borrowed", 0.0),
        BorrowedBookEntity(2, 2, "Kotlin in Action", 1, "2026-02-10", "2026-03-02", "Returned", 0.0)
    )

    val sampleAnnouncements = listOf(
        AnnouncementEntity(1, "Mid-Semester Examination Timetable Timelines", "The draft timetable for Semester 1 mid-semester examinations has been published. Please review and notify the exam office of any overlaps.", "2026-03-10", "Academic Registry", "ADMIN", "ALL"),
        AnnouncementEntity(2, "Annual University Tech & Innovation Hackathon", "UMMA University invites all Computer Science & IT students to register for the 2026 Innovation Hackathon scheduled for next month.", "2026-03-12", "Computer Science Dept", "LECTURER", "STUDENT"),
        AnnouncementEntity(3, "Fee Payment Deadline Notice", "All students are reminded to clear at least 75% of semester tuition fees prior to mid-semester exams to avoid exam card blocking.", "2026-03-14", "Finance Department", "ADMIN", "STUDENT")
    )

    val sampleEvents = listOf(
        EventEntity(1, "UMMA Tech Hackathon 2026", "24-Hour Mobile & Web Software Hackathon", "2026-04-05", "08:00 AM", "Innovation Center Auditorium", "Academic / Tech"),
        EventEntity(2, "Career & Job Placement Fair", "Meet top ICT, Banking and Healthcare Industry Employers", "2026-04-12", "09:00 AM", "University Pavilion", "Career"),
        EventEntity(3, "Islamic Ethics & Modern Science Symposium", "Keynote address by distinguished scholars", "2026-04-18", "10:00 AM", "Main Mosque Hall", "Cultural")
    )

    val sampleNotifications = listOf(
        NotificationItemEntity(1, 1, "Timetable Updated", "COMP 311 class room shifted to Computer Lab 3.", "2026-03-14", "TIMETABLE", false),
        NotificationItemEntity(2, 1, "Exam Schedule Published", "Semester 1 Exam Schedule is now available under Examinations.", "2026-03-12", "EXAM", false),
        NotificationItemEntity(3, 1, "Assignment Graded", "Your submission for Assignment 1 (COMP 311) was graded: 92/100.", "2026-03-10", "ANNOUNCEMENT", true)
    )

    val sampleHelpDesk = listOf(
        HelpDeskRequestEntity(1, "REQ-2026-001", 1, "Asiyah Abdullahi", "Academic", "Course Registration Adjustment", "Requesting permission to add COMP 315 after registration deadline.", "2026-03-05", "Resolved", "Approved by Head of Department."),
        HelpDeskRequestEntity(2, "REQ-2026-002", 1, "Asiyah Abdullahi", "IT Support", "WiFi Portal Login Issue", "Unable to log into the library WiFi portal using student portal credentials.", "2026-03-11", "In Progress", "IT support is updating RADIUS server credentials.")
    )
}
