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
img { max-width: 100%; height: auto; display: block; margin: 15px auto; border: 1px solid #ddd; border-radius: 8px; }
</style>

# UMMA UNIVERSITY MOBILE APPLICATION
## DATABASE SCHEMA & ER DIAGRAM DOCUMENTATION

**Course**: Mobile Application Development  
**Institution**: UMMA University  
**Database Technology**: Room Database (`"umma_university_db"` SQLite Engine via Google KSP)  
**Date**: November 2026  

---

## 1. ENTITY RELATIONSHIP DIAGRAM (ERD)

The database schema utilizes an embedded SQLite database engine managed by Android Jetpack Room with 22 relational entities.

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

---

## 2. DATABASE TABLES & SCHEMA SPECIFICATIONS

### 2.1 `users` Table
Stores authentication credentials and basic profile details for Students, Lecturers, and Administrators.
- `id` (Long, Primary Key, Auto-Generate)
- `fullName` (String, Required)
- `email` (String, Unique Index)
- `passwordHash` (String, Required)
- `role` (String: `"STUDENT"`, `"LECTURER"`, `"ADMIN"`)
- `phone` (String)
- `regOrStaffNumber` (String)
- `department` (String)
- `programme` (String)

### 2.2 `student_profiles` Table
Stores academic performance indicators and financial fee balances.
- `userId` (Long, Primary Key, Foreign Key referencing `users.id`)
- `regNumber` (String)
- `programme` (String)
- `department` (String)
- `yearOfStudy` (Int)
- `semester` (String)
- `gpa` (Double)
- `cgpa` (Double)
- `totalFees` (Double)
- `feePaid` (Double)
- `feeBalance` (Double)

### 2.3 `lecturer_profiles` Table
Stores faculty member details.
- `userId` (Long, Primary Key, Foreign Key referencing `users.id`)
- `staffId` (String)
- `department` (String)
- `officeLocation` (String)
- `title` (String)

### 2.4 `courses` Table
Stores academic units offered by departments.
- `id` (Long, Primary Key, Auto-Generate)
- `code` (String)
- `title` (String)
- `creditHours` (Int)
- `semester` (String)
- `lecturerName` (String)
- `department` (String)
- `description` (String)

### 2.5 `course_registrations` Table
Junction table mapping enrolled students to registered course units.
- `id` (Long, Primary Key, Auto-Generate)
- `studentId` (Long, Foreign Key referencing `users.id`)
- `courseId` (Long, Foreign Key referencing `courses.id`)
- `courseCode` (String)
- `courseTitle` (String)
- `creditHours` (Int)
- `lecturerName` (String)
- `semester` (String)
- `status` (String: `"Registered"`, `"Dropped"`)

### 2.6 `results` Table
Stores student examination assessment marks and calculated letter grades.
- `id` (Long, Primary Key, Auto-Generate)
- `studentId` (Long, Foreign Key referencing `users.id`)
- `courseId` (Long, Foreign Key referencing `courses.id`)
- `courseCode` (String)
- `courseTitle` (String)
- `creditHours` (Int)
- `catMarks` (Int: 0-30)
- `examMarks` (Int: 0-70)
- `totalMarks` (Int: 0-100)
- `grade` (String: `"A"`, `"B"`, `"C"`, `"D"`, `"F"`)
- `gradePoint` (Double: 4.0, 3.0, 2.0, 1.0, 0.0)
- `semester` (String)

### 2.7 `attendance_records` Table
Stores student class presence or absence logs.
- `id` (Long, Primary Key, Auto-Generate)
- `courseId` (Long, Foreign Key referencing `courses.id`)
- `courseCode` (String)
- `courseTitle` (String)
- `studentId` (Long, Foreign Key referencing `users.id`)
- `studentName` (String)
- `regNumber` (String)
- `date` (String)
- `status` (String: `"PRESENT"`, `"ABSENT"`)
- `totalClasses` (Int)
- `attendedClasses` (Int)

### 2.8 `assignments` & `assignment_submissions` Tables
- **`assignments`**: `id` (PK), `courseId` (FK), `courseCode`, `courseTitle`, `title`, `description`, `dueDate`, `totalMarks`, `lecturerName`.
- **`assignment_submissions`**: `id` (PK), `assignmentId` (FK), `studentId` (FK), `studentName`, `regNumber`, `submissionDate`, `content`, `status`, `marksObtained`, `feedback`.

### 2.9 `fees` & `payments` Tables
- **`fees`**: `id` (PK), `studentId` (FK), `tuitionFee`, `activityFee`, `examinationFee`, `libraryFee`, `medicalFee`, `totalFees`, `amountPaid`, `balance`.
- **`payments`**: `id` (PK), `studentId` (FK), `transactionId`, `amount`, `paymentDate`, `paymentMethod`, `receiptNumber`, `description`.

### 2.10 `books` & `borrowed_books` Tables
- **`books`**: `id` (PK), `title`, `author`, `category`, `isbn`, `availableCopies`, `totalCopies`, `shelfLocation`.
- **`borrowed_books`**: `id` (PK), `bookId` (FK), `bookTitle`, `studentId` (FK), `borrowDate`, `dueDate`, `status`, `fineAmount`.

### 2.11 `help_desk_requests` Table
- `id` (PK), `requestId`, `studentId` (FK), `studentName`, `category`, `subject`, `description`, `dateSubmitted`, `status` (`Pending`, `In Progress`, `Resolved`), `response`.

---

## 3. DATA ACCESS OBJECTS (DAOs)

The system defines 10 DAOs handling database interactions via Kotlin Coroutines and reactive StateFlow streams:

1. **`UserDao`**: Manages user authentication, registration, password updates, and role-based user directory queries.
2. **`StudentProfileDao`**: Manages student GPA, CGPA, and fee balance updates.
3. **`AcademicDao`**: Manages course listings, course registrations, result entry, and class attendance recording.
4. **`ScheduleDao`**: Manages weekly class timetable and examination schedule entries.
5. **`ELearningDao`**: Manages learning resource materials, assignment postings, and submission evaluations.
6. **`FinanceDao`**: Manages fee billing statements and payment receipt transactions.
7. **`LibraryDao`**: Handles book catalog searches and book reservation requests.
8. **`CommunicationDao`**: Manages university announcements, campus events, and notification alerts.
9. **`HelpDeskDao`**: Handles student support ticket submissions and administrator resolutions.
10. **`AdminDao`**: Handles department and academic programme management.
