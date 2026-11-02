<style>
body, p, li, td, th, div, span, h1, h2, h3, h4, h5, h6 {
    font-family: 'Times New Roman', Times, serif !important;
    font-size: 12pt !important;
    line-height: 1.5 !important;
    text-align: justify !important;
}
h1 { font-size: 18pt !important; font-weight: bold !important; text-align: center !important; }
h2 { font-size: 15pt !important; font-weight: bold !important; margin-top: 18pt !important; }
h3 { font-size: 13pt !important; font-weight: bold !important; margin-top: 14pt !important; }
.center-text { text-align: center !important; }
.diagram-container { text-align: center !important; margin: 20px 0 !important; }
img { max-width: 100%; height: auto; display: block; margin: 15px auto; border: 1px solid #ddd; border-radius: 8px; }
</style>

# UMMA UNIVERSITY MOBILE APPLICATION
## PROJECT REPORT & USER MANUAL

**Course**: Mobile Application Development  
**Institution**: UMMA University  
**Technology Stack**: Kotlin 2.0+, Android Studio, Jetpack Compose, Room Database (SQLite), Material 3 Design, Navigation Compose  
**Architecture Pattern**: MVVM (Model-View-ViewModel) + Repository Pattern  
**Date**: November 2026  

---

## 1. EXECUTIVE SUMMARY

The UMMA University Mobile Application was commissioned to provide a centralized, secure, and intuitive digital portal for students, lecturers, administrators, and university staff. The system facilitates seamless academic information access, course registrations, result checking, class attendance logging, assignment submission and grading, library book reservations, fee payment statements, and help desk ticket resolution.

Developed natively in Kotlin using Jetpack Compose and Room Database, the application implements robust role-based access control, reactive data streams via Kotlin Coroutines and StateFlow, and compile-time code generation via Google KSP.

---

## 2. SYSTEM DESIGN & ARCHITECTURE

The application implements the standard Android MVVM (Model-View-ViewModel) architecture with a clean repository layer.

![System Architecture Diagram](screenshots/system_architecture_diagram.png)

```
+-----------------------------------------------------------------------------------+
|                            UI LAYER (Jetpack Compose)                             |
|          Material 3 Components | Custom Vector Drawables | Top App Bars           |
+-----------------------------------------+-----------------------------------------+
                                          |
                                          | StateFlow & Navigation Events
                                          v
+-----------------------------------------------------------------------------------+
|                                 VIEWMODEL LAYER                                   |
|       AuthViewModel * StudentViewModel * LecturerViewModel * AdminViewModel       |
+-----------------------------------------+-----------------------------------------+
                                          |
                                          | Coroutines Flow Data Stream
                                          v
+-----------------------------------------------------------------------------------+
|                                REPOSITORY LAYER                                   |
|                                 UmmaRepository                                    |
+-----------------------------------------+-----------------------------------------+
                                          |
                                          | Room DAO Interfaces
                                          v
+-----------------------------------------------------------------------------------+
|                            DATA LAYER (Room Database)                             |
|                 "umma_university_db" SQLite Engine via KSP                        |
+-----------------------------------------------------------------------------------+
```

### Architectural Layer Responsibilities:
1. **UI Layer (Jetpack Compose)**: Declarative Composables observing reactive `StateFlow` streams from ViewModels. Renders responsive Material 3 components and custom vector drawables.
2. **ViewModel Layer**: Manages business logic, authentication sessions, GPA computations, fee balance updates, and form validations. Retains state across configuration changes.
3. **Repository Layer (`UmmaRepository`)**: Acts as a single source of truth, mediating data operations between DAOs and ViewModels.
4. **Data Layer (Room Database)**: Manages 22 relational SQLite tables on-device with compile-time code generation via Google KSP.

---

## 3. ROLE-BASED ACCESS CONTROL & PERMISSIONS

The application enforces strict role-based authorization branching directly from authentication:

![Role-Based Access Control Diagram](screenshots/role_based_access_diagram.png)

```
                                +-----------------------+
                                |    AUTHENTICATION     |
                                | Login / Quick Fill    |
                                +-----------+-----------+
                                            |
         +----------------------------------+----------------------------------+
         |                                  |                                  |
         v                                  v                                  v
+------------------+               +------------------+               +------------------+
|   STUDENT ROLE   |               |  LECTURER ROLE   |               |  ADMINISTRATOR   |
+------------------+               +------------------+               +------------------+
| * Course Reg     |               | * Attendance Log |               | * Manage Users   |
| * View Results   |               | * Post Assignment|               | * Manage Courses |
| * Pay Fees M-Pesa|               | * Enter Marks/GP |               | * Manage Fees    |
| * Reserve Books  |               | * View Submission|               | * Resolve Tickets|
| * Submit Tickets |               +------------------+               +------------------+
+------------------+
```

### Role Permissions Matrix:

| Operational Feature | Student | Lecturer | Administrator |
| :--- | :---: | :---: | :---: |
| **View Portal Dashboards & Schedules** | Granted | Granted | Granted |
| **Course Unit Registration (Add / Drop)** | Granted | Denied | Denied |
| **View Semester Results & Transcripts** | Granted | Denied | Granted |
| **Class Attendance Recording** | Denied | Granted | Denied |
| **Post Assignments & Grade Submissions** | Denied | Granted | Denied |
| **Enter CAT & Examination Marks** | Denied | Granted | Denied |
| **Simulated M-PESA Fee Payments** | Granted | Denied | Granted |
| **Library Catalog Search & Book Reservation** | Granted | Granted | Granted |
| **Submit Help Desk Complaints & Tickets** | Granted | Denied | Denied |
| **Resolve Tickets & Provide Admin Response** | Denied | Denied | Granted |
| **User, Course, Department & System Management** | Denied | Denied | Granted |

---

## 4. DATABASE SCHEMA & ER DIAGRAM

The application utilizes **Room Database** (`"umma_university_db"`) with 22 relational entities.

![Database ERD Diagram](screenshots/database_erd_diagram.png)

```
 +----------------------------+                +----------------------------+
 |           USERS            |                |          COURSES           |
 +----------------------------+                +----------------------------+
 | int    id             (PK) |                | int    id             (PK) |
 | string fullName            |                | string code                |
 | string email               |                | string title               |
 | string passwordHash        |                | int    creditHours         |
 | string role                |                | string semester            |
 | string phone               |                | string lecturerName        |
 | string regOrStaffNum       |                | string department          |
 +----------------------------+                +----------------------------+
     |        |         |                         |        |          |
 (1:1)|   (1:N)|     (1:N)|                     (1:N)|    (1:N)|      (1:N)|
     v        v         v                         v        v          v
 +----------------------------+                +----------------------------+
 |      STUDENT_PROFILES      |                |   COURSE_REGISTRATIONS     |
 +----------------------------+                +----------------------------+
 | int    userId      (PK,FK) |                | int    id             (PK) |
 | string regNumber           |                | int    studentId      (FK) |
 | string programme           |                | int    courseId       (FK) |
 | string department          |                | string courseCode          |
 | float  gpa                 |                | string courseTitle         |
 | float  cgpa                |                | string status              |
 | float  feeBalance          |                +----------------------------+
 +----------------------------+
                                               +----------------------------+
 +----------------------------+                |        ASSIGNMENTS         |
 |          RESULTS           |                +----------------------------+
 +----------------------------+                | int    id             (PK) |
 | int    id             (PK) |                | int    courseId       (FK) |
 | int    stId           (FK) |                | string title               |
 | int    cId            (FK) |                | string description         |
 | float  catMarks            |                | date   dueDate             |
 | float  examMarks           |                | float  totalMarks          |
 | float  totalPercent        |                +----------------------------+
 | string grade               |                             |
 | float  gp                  |                             | (1:N)
 +----------------------------+                             v
                                               +----------------------------+
 +----------------------------+                |   ASSIGNMENT_SUBMISSIONS   |
 |          PAYMENTS          |                +----------------------------+
 +----------------------------+                | int    id             (PK) |
 | int    id             (PK) |                | int    assignmentId   (FK) |
 | int    stId           (FK) |                | int    studentId      (FK) |
 | string txId                |                | date   submissionDate      |
 | float  amount              |                | string content             |
 | date   date                |                | float  marksObtained       |
 | string receiptNo           |                | string status              |
 +----------------------------+                +----------------------------+

 +----------------------------+
 |     HELP_DESK_REQUESTS     |
 +----------------------------+
 | int    id             (PK) |
 | int    studentId      (FK) |
 | string requestId           |
 | string category            |
 | string subject             |
 | string status              |
 +----------------------------+
```

### Table Specifications:
1. **`users`**: Base user entity (`id`, `fullName`, `email`, `passwordHash`, `role`, `phone`, `regOrStaffNumber`).
2. **`student_profiles`**: Student profile summary (`userId`, `regNumber`, `programme`, `department`, `gpa`, `cgpa`, `feeBalance`).
3. **`courses`**: Course units offered (`id`, `code`, `title`, `creditHours`, `semester`, `lecturerName`, `department`).
4. **`course_registrations`**: Enrolled student units (`id`, `studentId`, `courseId`, `courseCode`, `courseTitle`, `status`).
5. **`results`**: Student examination marks (`id`, `studentId`, `courseId`, `catMarks`, `examMarks`, `totalPercent`, `grade`, `gp`).
6. **`assignments` & `assignment_submissions`**: E-learning tasks and student submissions.
7. **`fees` & `payments`**: Financial billing statements and M-PESA payment receipts.
8. **`help_desk_requests`**: Support tickets and resolution details.

---

## 5. SYSTEM IMPLEMENTATION DETAILS

### Core Technical Implementation Highlights:
- **`data/entities/Entities.kt`**: Contains 22 Room entity definitions mapped directly to SQLite tables.
- **`data/dao/AppDao.kt`**: Defines DAO interfaces returning `Flow<List<T>>` for reactive UI updates and `suspend` functions for asynchronous writes.
- **`data/database/UmmaDatabase.kt`**: Singleton Room Database builder utilizing KSP annotation processor and automatic `InitialData` pre-population.
- **`viewmodel/`**: Implement `AuthViewModel`, `StudentViewModel`, `LecturerViewModel`, and `AdminViewModel` managing state via `MutableStateFlow` and `StateFlow`.
- **`ui/theme/`**: Applies official UMMA University branding colors (Emerald Green `#0F5A32`, Dark Green `#08391F`, Gold `#D4AF37`).

---

## 6. TESTING & QUALITY ASSURANCE REPORT

1. **Gradle Build Verification**: Executed `assembleDebug` — completed successfully with zero compilation errors.
2. **Unit Testing**: Executed `testDebugUnitTest` — **1 passed, 0 failed**.
3. **Database Pre-population Test**: Verified `InitialData` seeds realistic records for Students, Lecturers, Administrators, Courses, Grades, Fees, Books, and Announcements on initial application launch.
4. **GPA Computation Formula**: Verified exact GPA calculation formula:
   $$\text{GPA} = \frac{\sum (\text{Grade Point} \times \text{Credit Hours})}{\sum \text{Credit Hours}}$$
5. **Fee Payment Ledger Test**: Verified that simulated M-PESA payments update fee balance in real time and generate digital receipt records.

---

## 7. SYSTEM LIMITATIONS & FUTURE SCOPE

### Current Limitations:
- **Local Storage**: Runs locally using Room SQLite without live cloud database synchronization.
- **Payment Gateway**: Fee payment operates as a simulated digital workflow rather than live M-PESA Daraja API sandbox calls.

### Future Improvements:
1. **Cloud Backend**: Sync Room Database with Google Firebase Cloud Firestore or Ktor REST API.
2. **Push Notifications**: Integrate Firebase Cloud Messaging (FCM) for real-time timetable and exam alerts.
3. **Biometric Authentication**: Add Fingerprint and Face Unlock via Android Biometric Prompt API.

---

## 8. COMPREHENSIVE USER MANUAL & SCREENSHOT WALKTHROUGH

### 8.1 Authentication Portal

#### Step 1: Splash Screen
Launch the application to view the official UMMA University splash screen featuring the official crest logo, title, and motto.

![Splash Screen](screenshots/splash_screen.png)

#### Step 2: Login Screen
Select a 1-tap **Quick Fill Chip** (*Student*, *Lecturer*, *Admin*) or enter official credentials and click **LOGIN**.

![Login Screen](screenshots/login.png)

---

### 8.2 Student Portal Walkthrough

#### Step 1: Student Dashboard
View welcome message, registration number, programme, semester, GPA (3.75), fee balance (KES 20,000), next class schedule, next exam date, and module tiles.

![Student Dashboard Screen](screenshots/student_dashboard.png)

#### Step 2: Results & Academic Transcript
Access examination marks breakdown (CAT 30% + Exam 70%), total percentage, letter grade, grade points, and official academic transcript statement.

![Examination Results Screen](screenshots/results.png)
![Academic Transcript Screen](screenshots/transcript.png)

#### Step 3: Fee Payment & Receipt History
View billed fee structure, payment history receipts, click **MAKE PAYMENT**, select M-PESA, enter amount, and click **PROCESS PAYMENT**.

![Finance & Fee Payment Screen](screenshots/finance.png)

---

### 8.3 Lecturer Portal Walkthrough

#### Step 1: Attendance Logging
Select assigned course and date, toggle student presence (*Present / Absent*), and click **SAVE ATTENDANCE BATCH**.

![Lecturer Attendance Screen](screenshots/lecturer_attendance.png)

#### Step 2: Marks & Grade Entry
Select student and unit, enter CAT marks (0-30) and Exam marks (0-70), view computed total percentage, grade, and grade point, and click **SAVE & PUBLISH MARKS**.

![Lecturer Marks Entry Screen](screenshots/lecturer_marks.png)

---

### 8.4 Administrator Portal Walkthrough

View university overview statistics (Total Students, Lecturers, Courses, Departments, Fee Revenue), manage student and lecturer user directory, add courses/departments, and resolve student help desk support tickets.

![Administrator Dashboard Screen](screenshots/admin_dashboard.png)
