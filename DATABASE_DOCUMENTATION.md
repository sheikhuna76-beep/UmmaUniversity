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

<svg viewBox="0 0 900 660" width="100%" xmlns="http://www.w3.org/2000/svg" style="max-width:900px; display:block; margin:20px auto; background:#ffffff;">
  <rect x="20" y="20" width="250" height="180" rx="6" fill="#F1F8E9" stroke="#2E7D32" stroke-width="2"/>
  <rect x="20" y="20" width="250" height="30" fill="#2E7D32"/>
  <text x="145" y="40" font-family="'Times New Roman', Times, serif" font-size="13" font-weight="bold" fill="#FFFFFF" text-anchor="middle">USERS</text>
  <text x="30" y="68" font-family="monospace" font-size="11" fill="#C62828">int     id (PK)</text>
  <text x="30" y="88" font-family="monospace" font-size="11" fill="#333">string  fullName</text>
  <text x="30" y="108" font-family="monospace" font-size="11" fill="#333">string  email</text>
  <text x="30" y="128" font-family="monospace" font-size="11" fill="#333">string  passwordHash</text>
  <text x="30" y="148" font-family="monospace" font-size="11" fill="#333">string  role</text>
  <text x="30" y="168" font-family="monospace" font-size="11" fill="#333">string  phone, regOrStaffNum</text>

  <rect x="630" y="20" width="250" height="180" rx="6" fill="#F1F8E9" stroke="#2E7D32" stroke-width="2"/>
  <rect x="630" y="20" width="250" height="30" fill="#2E7D32"/>
  <text x="755" y="40" font-family="'Times New Roman', Times, serif" font-size="13" font-weight="bold" fill="#FFFFFF" text-anchor="middle">COURSES</text>
  <text x="640" y="68" font-family="monospace" font-size="11" fill="#C62828">int     id (PK)</text>
  <text x="640" y="88" font-family="monospace" font-size="11" fill="#333">string  code</text>
  <text x="640" y="108" font-family="monospace" font-size="11" fill="#333">string  title</text>
  <text x="640" y="128" font-family="monospace" font-size="11" fill="#333">int     creditHours</text>
  <text x="640" y="148" font-family="monospace" font-size="11" fill="#333">string  semester</text>
  <text x="640" y="168" font-family="monospace" font-size="11" fill="#333">string  lecturerName, dept</text>

  <rect x="20" y="230" width="200" height="170" rx="6" fill="#F1F8E9" stroke="#2E7D32" stroke-width="2"/>
  <rect x="20" y="230" width="200" height="30" fill="#2E7D32"/>
  <text x="120" y="250" font-family="'Times New Roman', Times, serif" font-size="12" font-weight="bold" fill="#FFFFFF" text-anchor="middle">STUDENT_PROFILES</text>
  <text x="30" y="278" font-family="monospace" font-size="11" fill="#1565C0">int    userId (PK,FK)</text>
  <text x="30" y="298" font-family="monospace" font-size="11" fill="#333">string regNumber</text>
  <text x="30" y="318" font-family="monospace" font-size="11" fill="#333">string programme</text>
  <text x="30" y="338" font-family="monospace" font-size="11" fill="#333">float  gpa, cgpa</text>
  <text x="30" y="358" font-family="monospace" font-size="11" fill="#333">float  feeBalance</text>

  <rect x="240" y="230" width="200" height="190" rx="6" fill="#F1F8E9" stroke="#2E7D32" stroke-width="2"/>
  <rect x="240" y="230" width="200" height="30" fill="#2E7D32"/>
  <text x="340" y="250" font-family="'Times New Roman', Times, serif" font-size="12" font-weight="bold" fill="#FFFFFF" text-anchor="middle">RESULTS</text>
  <text x="250" y="278" font-family="monospace" font-size="11" fill="#C62828">int   id (PK)</text>
  <text x="250" y="298" font-family="monospace" font-size="11" fill="#1565C0">int   stId, cId (FK)</text>
  <text x="250" y="318" font-family="monospace" font-size="11" fill="#333">float catMarks</text>
  <text x="250" y="338" font-family="monospace" font-size="11" fill="#333">float examMarks</text>
  <text x="250" y="358" font-family="monospace" font-size="11" fill="#333">float totalPercent</text>
  <text x="250" y="378" font-family="monospace" font-size="11" fill="#333">string grade, float gp</text>

  <rect x="460" y="230" width="200" height="170" rx="6" fill="#F1F8E9" stroke="#2E7D32" stroke-width="2"/>
  <rect x="460" y="230" width="200" height="30" fill="#2E7D32"/>
  <text x="560" y="250" font-family="'Times New Roman', Times, serif" font-size="12" font-weight="bold" fill="#FFFFFF" text-anchor="middle">PAYMENTS</text>
  <text x="470" y="278" font-family="monospace" font-size="11" fill="#C62828">int    id (PK)</text>
  <text x="470" y="298" font-family="monospace" font-size="11" fill="#1565C0">int    stId (FK)</text>
  <text x="470" y="318" font-family="monospace" font-size="11" fill="#333">string txId</text>
  <text x="470" y="338" font-family="monospace" font-size="11" fill="#333">float  amount, date</text>
  <text x="470" y="358" font-family="monospace" font-size="11" fill="#333">string receiptNo</text>

  <rect x="680" y="230" width="200" height="170" rx="6" fill="#F1F8E9" stroke="#2E7D32" stroke-width="2"/>
  <rect x="680" y="230" width="200" height="30" fill="#2E7D32"/>
  <text x="780" y="250" font-family="'Times New Roman', Times, serif" font-size="11" font-weight="bold" fill="#FFFFFF" text-anchor="middle">HELP_DESK_REQUESTS</text>
  <text x="690" y="278" font-family="monospace" font-size="11" fill="#C62828">int    id (PK)</text>
  <text x="690" y="298" font-family="monospace" font-size="11" fill="#1565C0">int    studentId (FK)</text>
  <text x="690" y="318" font-family="monospace" font-size="11" fill="#333">string requestId</text>
  <text x="690" y="338" font-family="monospace" font-size="11" fill="#333">string category, subject</text>
  <text x="690" y="358" font-family="monospace" font-size="11" fill="#333">string status, response</text>

  <rect x="240" y="450" width="240" height="170" rx="6" fill="#F1F8E9" stroke="#2E7D32" stroke-width="2"/>
  <rect x="240" y="450" width="240" height="30" fill="#2E7D32"/>
  <text x="360" y="470" font-family="'Times New Roman', Times, serif" font-size="12" font-weight="bold" fill="#FFFFFF" text-anchor="middle">COURSE_REGISTRATIONS</text>
  <text x="250" y="498" font-family="monospace" font-size="11" fill="#C62828">int    id (PK)</text>
  <text x="250" y="518" font-family="monospace" font-size="11" fill="#1565C0">int    studentId, courseId (FK)</text>
  <text x="250" y="538" font-family="monospace" font-size="11" fill="#333">string courseCode, courseTitle</text>
  <text x="250" y="558" font-family="monospace" font-size="11" fill="#333">string status</text>

  <rect x="520" y="450" width="220" height="170" rx="6" fill="#F1F8E9" stroke="#2E7D32" stroke-width="2"/>
  <rect x="520" y="450" width="220" height="30" fill="#2E7D32"/>
  <text x="630" y="470" font-family="'Times New Roman', Times, serif" font-size="12" font-weight="bold" fill="#FFFFFF" text-anchor="middle">ASSIGNMENTS</text>
  <text x="530" y="498" font-family="monospace" font-size="11" fill="#C62828">int    id (PK)</text>
  <text x="530" y="518" font-family="monospace" font-size="11" fill="#1565C0">int    courseId (FK)</text>
  <text x="530" y="538" font-family="monospace" font-size="11" fill="#333">string title, description</text>
  <text x="530" y="558" font-family="monospace" font-size="11" fill="#333">date   dueDate, float totalMarks</text>

  <path d="M 145 200 L 120 230 M 145 200 L 340 230 M 145 200 L 560 230 M 145 200 L 780 230" stroke="#888888" stroke-width="1.5" stroke-dasharray="4,4"/>
  <path d="M 755 200 L 340 230 M 755 200 L 360 450 M 755 200 L 630 450" stroke="#888888" stroke-width="1.5" stroke-dasharray="4,4"/>
</svg>

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
