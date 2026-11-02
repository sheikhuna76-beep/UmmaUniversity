# UMMA University App - Database Schema & ER Documentation

This document describes the **Room Database** design, schema entities, primary keys, foreign key relationships, and data access objects (DAOs) for the **UMMA University App**.

---

## 📊 Entity Relationship Diagram (ERD) Overview

```
                          +------------------+
                          |    users         |
                          +------------------+
                          | PK id            |
                          |    fullName      |
                          |    email         |
                          |    passwordHash  |
                          |    role          |
                          +--------+---------+
                                   |
         +-------------------------+-------------------------+
         | (1:1)                                             | (1:1)
         v                                                   v
+------------------+                               +------------------+
| student_profiles |                               | lecturer_profiles|
+------------------+                               +------------------+
| PK userId        |                               | PK userId        |
|    regNumber     |                               |    staffId       |
|    programme     |                               |    department    |
|    gpa, cgpa     |                               |    officeLocation|
|    feeBalance    |                               +------------------+
+--------+---------+
         |
         +-----------------------+-----------------------+-----------------------+
         | (1:N)                 | (1:N)                 | (1:N)                 | (1:N)
         v                       v                       v                       v
+------------------+   +------------------+    +------------------+    +--------------------+
|course_registratns|   |     results      |    |attendance_records|    | help_desk_requests |
+------------------+   +------------------+    +------------------+    +--------------------+
| PK id            |   | PK id            |    | PK id            |    | PK id              |
| FK studentId     |   | FK studentId     |    | FK studentId     |    | FK studentId       |
| FK courseId      |   | FK courseId      |    | FK courseId      |    |    requestId       |
|    courseCode    |   |    totalMarks    |    |    status        |    |    category        |
|    status        |   |    grade, gp     |    |    date          |    |    status          |
+------------------+   +------------------+    +------------------+    +--------------------+
```

---

## 🗄️ Database Tables & Schema Specifications

### 1. `users` Table
Stores authentication details and profile metadata for all system users.
- `id` (Long, Primary Key, Auto-Generate)
- `fullName` (String)
- `email` (String, Unique Index)
- `passwordHash` (String)
- `role` (String: `"STUDENT"`, `"LECTURER"`, `"ADMIN"`)
- `phone` (String)
- `regOrStaffNumber` (String)
- `department` (String)
- `programme` (String)

### 2. `student_profiles` Table
Stores academic and financial summary data for students.
- `userId` (Long, Primary Key)
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

### 3. `lecturer_profiles` Table
Stores faculty staff metadata.
- `userId` (Long, Primary Key)
- `staffId` (String)
- `department` (String)
- `officeLocation` (String)
- `title` (String)

### 4. `courses` Table
Stores university courses/units.
- `id` (Long, Primary Key, Auto-Generate)
- `code` (String)
- `title` (String)
- `creditHours` (Int)
- `semester` (String)
- `lecturerName` (String)
- `department` (String)
- `description` (String)

### 5. `course_registrations` Table
Junction table linking students to their registered semester units.
- `id` (Long, Primary Key, Auto-Generate)
- `studentId` (Long)
- `courseId` (Long)
- `courseCode` (String)
- `courseTitle` (String)
- `creditHours` (Int)
- `lecturerName` (String)
- `semester` (String)
- `status` (String: `"Registered"`, `"Dropped"`)

### 6. `results` Table
Stores student examination marks and letter grades.
- `id` (Long, Primary Key, Auto-Generate)
- `studentId` (Long)
- `courseId` (Long)
- `courseCode` (String)
- `courseTitle` (String)
- `creditHours` (Int)
- `catMarks` (Int: 0-30)
- `examMarks` (Int: 0-70)
- `totalMarks` (Int: 0-100)
- `grade` (String: `"A"`, `"B"`, `"C"`, `"D"`, `"F"`)
- `gradePoint` (Double: 4.0, 3.0, 2.0, 1.0, 0.0)
- `semester` (String)

### 7. `attendance_records` Table
Stores class attendance entries.
- `id` (Long, Primary Key, Auto-Generate)
- `courseId` (Long)
- `courseCode` (String)
- `courseTitle` (String)
- `studentId` (Long)
- `studentName` (String)
- `regNumber` (String)
- `date` (String)
- `status` (String: `"PRESENT"`, `"ABSENT"`)
- `totalClasses` (Int)
- `attendedClasses` (Int)

### 8. `timetable_entries` Table
Weekly class schedule.
- `id` (Long, Primary Key, Auto-Generate)
- `courseCode` (String)
- `courseTitle` (String)
- `dayOfWeek` (String: `"Monday"`, `"Tuesday"`, etc.)
- `startTime` (String)
- `endTime` (String)
- `room` (String)
- `lecturerName` (String)

### 9. `examination_entries` Table
Semester exam schedule.
- `id` (Long, Primary Key, Auto-Generate)
- `courseCode` (String)
- `courseTitle` (String)
- `examDate` (String)
- `startTime` (String)
- `endTime` (String)
- `venue` (String)
- `instructions` (String)

### 10. `assignments` & `assignment_submissions` Tables
E-learning task management and student submissions.
- `Assignment`: `id`, `courseId`, `courseCode`, `courseTitle`, `title`, `description`, `dueDate`, `totalMarks`, `lecturerName`
- `AssignmentSubmission`: `id`, `assignmentId`, `studentId`, `studentName`, `regNumber`, `submissionDate`, `content`, `status`, `marksObtained`, `feedback`

### 11. `fees` & `payments` Tables
Financial ledgers and digital receipts.
- `Fee`: `id`, `studentId`, `tuitionFee`, `activityFee`, `examinationFee`, `libraryFee`, `medicalFee`, `totalFees`, `amountPaid`, `balance`
- `Payment`: `id`, `studentId`, `transactionId`, `amount`, `paymentDate`, `paymentMethod`, `receiptNumber`, `description`

### 12. `books` & `borrowed_books` Tables
Library catalog and borrowing records.
- `Book`: `id`, `title`, `author`, `category`, `isbn`, `availableCopies`, `totalCopies`, `shelfLocation`
- `BorrowedBook`: `id`, `bookId`, `bookTitle`, `studentId`, `borrowDate`, `dueDate`, `status`, `fineAmount`

### 13. `announcements`, `events`, `notifications`, `help_desk_requests` Tables
Communication and ticketing systems.

---

## 🛠️ Data Access Objects (DAOs)

The application defines clean DAOs with Kotlin Coroutines & Flow support:
- `UserDao`: Handles authentication queries, user profile updates, and role filters.
- `StudentProfileDao`: Queries student GPA, fee balances, and profile stats.
- `AcademicDao`: Handles course retrieval, registrations, exam results, and attendance recording.
- `ScheduleDao`: Flow queries for timetable and examination entries.
- `ELearningDao`: Manages learning materials, assignment posting, and grading.
- `FinanceDao`: Manages fee ledgers and payment receipt recording.
- `LibraryDao`: Book search queries and book reservations.
- `CommunicationDao`: Flow updates for announcements, campus events, and notifications.
- `HelpDeskDao`: Ticket submission and administrator status updates.
- `AdminDao`: Department and programme management.
