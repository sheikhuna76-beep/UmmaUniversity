# UMMA University App - University Management Mobile System

**UMMA University App** is a native Android application built for UMMA University using Kotlin, Jetpack Compose, MVVM Architecture, Room Database, Material 3 Design, and Navigation Compose.

---

## 🌟 Key Features & Role-Based Access

The application provides distinct, role-based dashboards and permissions for **Students**, **Lecturers**, and **Administrators**.

### 🎓 Student Role
- **Modern Dashboard**: Personalized welcome card, registration number, programme, semester, calculated GPA, fee balance, next class, next exam, attendance percentage, and quick access module tiles.
- **Academic Module**:
  - *My Units*: View registered semester courses, credit hours, and lecturer details.
  - *Course Registration*: Browse available department units, register units, or drop units.
  - *Results & GPA*: CAT marks (30%), Exam marks (70%), total marks, letter grades (A, B, C, D, F), grade points, and dynamic GPA calculation.
  - *Academic Transcript*: Official transcript layout with semester breakdown, CGPA, and summary statement.
  - *Attendance*: Class attendance history with percentage and status badges.
- **Schedule & Exams**:
  - *Timetable*: Weekly class schedule (Monday - Friday) with time slots, venues, and lecturers.
  - *Examination Schedule*: Exam dates, times, venues, and hall regulations notice.
- **E-Learning Portal**:
  - Lecture notes, slides, video references, and downloadable PDFs.
  - Assignment submission portal with text/link upload, status tracking, lecturer marks, and feedback.
- **Fees & Finance**:
  - Total fees billed, amount paid, and outstanding balance statement.
  - Itemized fee structure (Tuition, Activity, Exam, Library, Medical).
  - M-PESA & Bank deposit payment history with digital receipts.
  - Interactive "MAKE PAYMENT" dialog for simulated fee payments.
  - Scholarship & HELB / County Bursary information.
- **Library System**:
  - Digital catalog search by title, author, category, or ISBN.
  - Availability status, total copies, and shelf locations.
  - "RESERVE BOOK" functionality & borrowed books tracking with due dates and overdue fines.
- **News & Campus Life**:
  - Official university announcements and department notices.
  - Campus events calendar (Hackathons, Career Fairs, Symposia).
  - Notification alert feed (Timetable shifts, Exam releases, Grade alerts).
- **Campus Services**:
  - Interactive Campus Map & Building guide.
  - Hostel accommodation information & cafeteria menus/hours.
  - Transport shuttle schedules, Sports facilities, Health services, Counselling, and Disability support.
- **Help Desk & Support**:
  - Submit complaints, suggestions, or service requests (Academic, Finance, IT, Hostel).
  - Track ticket status (*Pending*, *In Progress*, *Resolved*) and view official response.
- **Profile & Account**:
  - User avatar, profile info, edit name/phone, password change, and logout.

### 👨‍🏫 Lecturer Role
- **Lecturer Dashboard**: Lecturer profile, staff ID, department, assigned courses, student counts.
- **Class Attendance**: Select course & date, toggle student presence (Present/Absent), and save attendance batch to Room DB.
- **Assignment Management**: Post new course assignments (title, description, due date) and view/grade student submissions.
- **Marks & Grading Entry**: Select course & student, enter CAT marks (out of 30) and Exam marks (out of 70), automatically compute Grade (A-F) & GP (4.0-0.0), and save to DB.

### 🛠️ Administrator Role
- **Admin Control Center**: System overview stats (Total Students, Total Lecturers, Total Courses, Total Departments, Total Fee Revenue).
- **Student Management**: View student directory, add new student, and delete student accounts.
- **Lecturer Management**: View faculty staff, add new lecturer, and delete staff accounts.
- **Course & Department Management**: Add/delete courses, departments, and academic programmes.
- **Financial Auditing**: Revenue summary and fee structure settings.
- **News & Event Publishing**: Post announcements and campus events.
- **Help Desk Operations**: Review student tickets, update status (*In Progress*, *Resolved*), and attach official responses.

---

## 🔑 Demo Login Credentials

For demonstration and examination purposes, use the following pre-configured sample accounts:

| User Role | Email | Password | Details |
| :--- | :--- | :--- | :--- |
| **Student** | `student@umma.ac.ke` | `student123` | Ahmed Hassan (UMMA/CS/001/2026, Computer Science) |
| **Lecturer** | `lecturer@umma.ac.ke` | `lecturer123` | Dr. Omar Farooq (LEC/CS/042, Computer Science) |
| **Administrator** | `admin@umma.ac.ke` | `admin123` | Admin Fatuma Hussein (ADM/001, Academic Registry) |

---

## 🏗️ Architecture & Technology Stack

The project strictly follows the **MVVM (Model-View-ViewModel)** architectural pattern:

```
[UI Layer (Jetpack Compose)] 
      ↑ ↓ (StateFlow & UI Events)
[ViewModel Layer (Auth, Student, Lecturer, Admin ViewModels)]
      ↑ ↓
[Repository Layer (UmmaRepository)]
      ↑ ↓
[DAO Layer (User, Academic, Finance, Library, Communication, HelpDesk DAOs)]
      ↑ ↓
[Local Storage (Room Database with Pre-populated Sample Data)]
```

### Technologies Used
- **Language**: Kotlin 2.0+
- **UI Framework**: Jetpack Compose with Material 3 Design
- **Local Persistence**: Room Database with Coroutines & Flow
- **Annotation Processor**: Google KSP (Kotlin Symbol Processing)
- **Navigation**: Navigation Compose
- **Architecture**: MVVM + Repository Pattern
- **Async Concurrency**: Kotlin Coroutines & StateFlow

---

## 🚀 How to Run the Project

1. Open **Android Studio** (Koala / Ladybug or newer).
2. Select **Open** and navigate to `C:/Users/Admin/AndroidStudioProjects/UmmaUniversity`.
3. Wait for Gradle Sync to complete.
4. Select an Android Emulator or connected physical device (API Level 26+).
5. Click **Run 'app'** (`Shift + F10`).
6. On the Login screen, click any of the **Quick Fill chips** (*Student*, *Lecturer*, *Admin*) and click **LOGIN** to evaluate each dashboard.

---

## 📸 Screenshots & Workflow

1. **Splash Screen**: Animated UMMA University logo and title.
2. **Login Screen**: Quick fill buttons for Student, Lecturer, and Admin demo accounts.
3. **Student Dashboard**: Welcome card, statistics grid, next class card, next exam card, and quick module navigation.
4. **Academic Portal**: Tabbed interface for My Units, Course Registration, Results, Transcript, and Attendance.
5. **Finance Portal**: Fee breakdown, payment history, and simulated M-PESA fee payment dialog.
6. **Lecturer Portal**: Attendance marking, assignment creation, and marks entry.
7. **Admin Portal**: University-wide statistics, user management, and ticket resolution.
