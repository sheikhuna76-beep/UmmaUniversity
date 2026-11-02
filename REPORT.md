# UMMA UNIVERSITY MOBILE APPLICATION
## 📄 PROJECT REPORT & USER MANUAL

**Course**: Mobile Application Development  
**Institution**: UMMA University  
**Technology**: Kotlin, Android Studio, Jetpack Compose, Room Database, Material 3, Navigation Compose  
**Architecture**: MVVM (Model-View-ViewModel) + Repository Pattern  
**Date**: November 2026  

---

## TABLE OF CONTENTS
1. [Executive Summary](#1-executive-summary)
2. [System Design & Architecture](#2-system-design--architecture)
3. [Role-Based Access Control & Permissions](#3-role-based-access-control--permissions)
4. [Database Schema & ER Diagram](#4-database-schema--er-diagram)
5. [System Implementation Details](#5-system-implementation-details)
6. [Testing & Quality Assurance Report](#6-testing--quality-assurance-report)
7. [System Limitations & Future Scope](#7-system-limitations--future-scope)
8. [Comprehensive User Manual & Screenshot Walkthrough](#8-comprehensive-user-manual--screenshot-walkthrough)

---

## 1. EXECUTIVE SUMMARY

UMMA University required a centralized, secure, and modern mobile platform for students, lecturers, administrators, and university staff to access academic information, manage university activities, and communicate efficiently. 

This project delivers the **UMMA University Mobile Application**, a native Android app built using **Kotlin** and **Jetpack Compose**. The system features role-based authentication, real-time local persistence via **Room Database**, automated GPA and fee balance calculations, class attendance logging, assignment submission and grading, library book reservations, and help desk request tracking.

---

## 2. SYSTEM DESIGN & ARCHITECTURE

The application implements the **MVVM (Model-View-ViewModel)** architectural pattern recommended by Google for modern Android development.

```
+-----------------------------------------------------------------------+
|                    UI LAYER (Jetpack Compose)                         |
|     (Material 3 Components, Custom Vector Drawables, Top App Bars)    |
+-----------------------------------+-----------------------------------+
                                    | (StateFlow & Navigation Events)
                                    v
+-----------------------------------------------------------------------+
|                        VIEWMODEL LAYER                                |
| (AuthViewModel, StudentViewModel, LecturerViewModel, AdminViewModel)   |
+-----------------------------------+-----------------------------------+
                                    | (Coroutines Flow Data Stream)
                                    v
+-----------------------------------------------------------------------+
|                       REPOSITORY LAYER                                |
|                        (UmmaRepository)                               |
+-----------------------------------+-----------------------------------+
                                    | (Room DAO Interfaces)
                                    v
+-----------------------------------------------------------------------+
|                   DATA LAYER (Room Database)                          |
|         ("umma_university_db" SQLite Engine via KSP)                  |
+-----------------------------------------------------------------------+
```

### Architectural Principles:
1. **Separation of Concerns**: UI components are purely declarative composables observing `StateFlow` from ViewModels.
2. **Single Source of Truth**: `UmmaRepository` mediates data access between Room DAOs and ViewModels.
3. **Reactivity & Concurrency**: Asynchronous operations run on background threads using **Kotlin Coroutines** (`Dispatchers.IO`).
4. **Compile-Time Safety**: **Google KSP (Kotlin Symbol Processing)** generates Kotlin-native DAO implementations, eliminating runtime reflection.

---

## 3. ROLE-BASED ACCESS CONTROL & PERMISSIONS

The application enforces strict role-based access control across three user roles:

```
                  +-----------------------------------+
                  |          AUTHENTICATION           |
                  |     (Login / Quick Fill Chips)    |
                  +-----------------+-----------------+
                                    |
          +-------------------------+-------------------------+
          |                         |                         |
          v                         v                         v
+-------------------+     +-------------------+     +-------------------+
|   STUDENT ROLE    |     |   LECTURER ROLE   |     |   ADMINISTRATOR   |
+-------------------+     +-------------------+     +-------------------+
| - Course Reg      |     | - Attendance Log  |     | - Manage Users    |
| - View Results    |     | - Post Assignment |     | - Manage Courses  |
| - Pay Fees (M-Pesa)|    | - Enter Marks/GP  |     | - Manage Fees     |
| - Reserve Books   |     | - View Submissions|     | - Resolve Tickets |
| - Submit Tickets  |     +-------------------+     +-------------------+
+-------------------+
```

---

## 4. DATABASE SCHEMA & ER DIAGRAM

The database utilizes **Room Database** on top of an embedded **SQLite** engine, containing 22 relational tables.

### 4.1 Visual Entity Relationship Diagram (ERD)

```
+-------------------------------------------------------------------------------------------------------+
|                              UMMA UNIVERSITY APP - DATABASE ER DIAGRAM                                |
+-------------------------------------------------------------------------------------------------------+

  +-------------------------------+                       +-------------------------------+
  |             USERS             |                       |       STUDENT_PROFILES        |
  +-------------------------------+                       +-------------------------------+
  | [PK] id                       |<--------- (1:1) ----->| [PK,FK] userId                |
  |      fullName                 |                       |         regNumber             |
  |      email                    |                       |         programme             |
  |      passwordHash             |                       |         department            |
  |      role (STUDENT/LEC/ADMIN) |                       |         gpa                   |
  |      phone                    |                       |         cgpa                  |
  |      regOrStaffNumber         |                       |         feeBalance            |
  +-------------------------------+                       +-------------------------------+
     |           |            |
     | (1:N)     | (1:N)      | (1:N)
     v           v            v
  +----------+ +----------+ +-------------------+
  | RESULTS  | | PAYMENTS | | HELP_DESK_REQUESTS|
  +----------+ +----------+ +-------------------+
  | [PK] id  | | [PK] id  | | [PK] id           |
  | [FK] stId| | [FK] stId| | [FK] studentId    |
  | [FK] coId| |      txId| |      requestId    |
  |   catMarks| |    amount| |      category     |
  |  examMarks| |      date| |      subject      |
  |  total%   | | receiptNo| |      status       |
  |  grade, gp| +----------+ +-------------------+
  +----------+
       ^
       |
       +------------------------------------+
                                            |
                                            | (1:N)
  +-------------------------------+         |             +-------------------------------+
  |            COURSES            |---------+             |          ASSIGNMENTS          |
  +-------------------------------+                       +-------------------------------+
  | [PK] id                       |                       | [PK] id                       |
  |      code                     |                       | [FK] courseId                 |
  |      title                    |---------------------->|      title                    |
  |      creditHours              |        (1:N)          |      description              |
  |      semester                 |                       |      dueDate                  |
  |      lecturerName             |                       |      totalMarks               |
  |      department               |                       +-------------------------------+
  +-------------------------------+                                       |
                 |                                                        | (1:N)
                 | (1:N)                                                  v
                 v                                        +-------------------------------+
  +-------------------------------+                       |    ASSIGNMENT_SUBMISSIONS     |
  |     COURSE_REGISTRATIONS      |                       +-------------------------------+
  +-------------------------------+                       | [PK] id                       |
  | [PK] id                       |                       | [FK] assignmentId             |
  | [FK] studentId                |                       | [FK] studentId                |
  | [FK] courseId                 |                       |      submissionDate           |
  |      courseCode               |                       |      content                  |
  |      courseTitle              |                       |      marksObtained            |
  |      status                   |                       |      status                   |
  +-------------------------------+                       +-------------------------------+
```

---

## 5. SYSTEM IMPLEMENTATION DETAILS

### Key Modules Implemented:
- **`data/entities/Entities.kt`**: Defines Room `@Entity` classes for all 22 database tables.
- **`data/dao/AppDao.kt`**: Defines `@Dao` interfaces with SQL `@Query`, `@Insert`, `@Update`, and `@Delete` operations returning `StateFlow` and `suspend` types.
- **`data/database/UmmaDatabase.kt`**: Room database singleton with pre-population callback initializing realistic sample university records.
- **`viewmodel/`**: `AuthViewModel`, `StudentViewModel`, `LecturerViewModel`, and `AdminViewModel` managing business logic, GPA calculations, fee balance updates, and role-based permissions.
- **`ui/screens/`**: Jetpack Compose screens for all student, lecturer, and admin screens.

---

## 6. TESTING & QUALITY ASSURANCE REPORT

1. **Gradle Build Verification**: Executed `assembleDebug` — completed successfully with zero compilation errors.
2. **Unit Test Verification**: Executed `testDebugUnitTest` — **1 passed, 0 failed**.
3. **Database Pre-population Test**: Verified that `InitialData` seeds sample student records, lecturer records, admin records, courses, grades, fees, books, and announcements on initial startup.
4. **GPA Calculation Test**: Verified formula:
   $$\text{GPA} = \frac{\sum (\text{Grade Point} \times \text{Credit Hours})}{\sum \text{Credit Hours}}$$
5. **Fee Payment & Balance Test**: Verified that simulated M-PESA payments update tuition balance in real time and generate payment receipts.

---

## 7. SYSTEM LIMITATIONS & FUTURE SCOPE

### Current Limitations:
- **Local Persistence**: Runs locally on Room SQLite without active cloud server sync.
- **Payment Gateway**: Fee payment operates as a simulated digital workflow rather than live M-PESA Daraja API sandbox integration.

### Future Improvements:
1. **Cloud Synchronization**: Sync Room Database with Google Firebase Cloud Firestore / Ktor REST Backend.
2. **Push Notifications**: Integrate Firebase Cloud Messaging (FCM) for real-time exam and fee alerts.
3. **Biometric Auth**: Add Fingerprint / Face Unlock via Android Biometric Prompt API.

---

## 8. COMPREHENSIVE USER MANUAL & SCREENSHOT WALKTHROUGH

### 8.1 Login & Authentication
1. Launch the application to view the animated **Splash Screen**.
2. On the **Login Screen**, click any of the 1-tap **Quick Fill chips** (*Student*, *Lecturer*, *Admin*) or enter valid credentials:
   - **Student**: `student@umma.ac.ke` / `student123`
   - **Lecturer**: `lecturer@umma.ac.ke` / `lecturer123`
   - **Admin**: `admin@umma.ac.ke` / `admin123`
3. Click **LOGIN** to access the corresponding role dashboard.

![Login Screen](screenshots/login.png)

---

### 8.2 Student Portal Walkthrough
1. **Dashboard**: View welcome banner, GPA (3.75), Fee Balance (KES 20,000), Next Class, Next Exam, and bottom navigation bar.
2. **Academics**: Navigate to **Academics** to access *My Units*, *Course Registration*, *Results*, *Transcript*, and *Attendance*.
3. **Fee Payment**: Navigate to **Finance**, click **MAKE PAYMENT**, select payment channel (*M-PESA / Bank Deposit*), enter amount, and click **PROCESS PAYMENT**.
4. **Library**: Navigate to **Services** $\to$ **Library**, search books, view location shelf, and click **RESERVE BOOK**.
5. **Help Desk**: Navigate to **Help Desk**, click the **+ FAB**, enter category, subject, and description, and track status (*Pending*, *In Progress*, *Resolved*).

![Student Dashboard](screenshots/student_dashboard.png)
![Student Results & GPA](screenshots/results.png)
![Official Transcript](screenshots/transcript.png)
![Finance & Fees](screenshots/finance.png)

---

### 8.3 Lecturer Portal Walkthrough
1. **Dashboard**: View assigned teaching units, student enrollment counts, and quick action cards.
2. **Class Attendance**: Click **Record Attendance**, select course and date, toggle student presence (*Present/Absent*), and click **SAVE ATTENDANCE BATCH**.
3. **Assignments**: Click **Manage Assignments**, click the **+ FAB**, set title, guidelines, and due date, and click **POST ASSIGNMENT**.
4. **Marks Entry**: Click **Enter Marks & Grades**, select student and unit, enter CAT marks (0-30) and Exam marks (0-70), view auto-computed grade (A-F) & Grade Point, and click **SAVE & PUBLISH MARKS**.

![Lecturer Dashboard](screenshots/lecturer_dashboard.png)
![Marks Entry](screenshots/lecturer_marks.png)

---

### 8.4 Administrator Portal Walkthrough
1. **Dashboard**: View university-wide statistics (Total Students, Lecturers, Courses, Departments, Fee Revenue).
2. **Manage Students & Lecturers**: Click **Students** or **Lecturers**, click **+ FAB** to register new accounts or click the **Delete** icon to remove users.
3. **Manage Courses & Departments**: Click **Courses & Depts** to manage units, departments, and academic programmes.
4. **Help Desk Operations**: Click **Help Desk**, select a student ticket, update status (*In Progress / Resolved*), type official admin response, and click **SAVE RESPONSE**.

![Admin Control Center](screenshots/admin_dashboard.png)
