package com.example.ummauniversity.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ummauniversity.ui.screens.SplashScreen
import com.example.ummauniversity.ui.screens.admin.*
import com.example.ummauniversity.ui.screens.auth.ForgotPasswordScreen
import com.example.ummauniversity.ui.screens.auth.LoginScreen
import com.example.ummauniversity.ui.screens.auth.RegisterScreen
import com.example.ummauniversity.ui.screens.lecturer.*
import com.example.ummauniversity.ui.screens.student.*
import com.example.ummauniversity.viewmodel.AdminViewModel
import com.example.ummauniversity.viewmodel.AuthViewModel
import com.example.ummauniversity.viewmodel.LecturerViewModel
import com.example.ummauniversity.viewmodel.StudentViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    studentViewModel: StudentViewModel,
    lecturerViewModel: LecturerViewModel,
    adminViewModel: AdminViewModel
) {
    val currentUser by authViewModel.currentUser.collectAsState()

    NavHost(
        navController = navController,
        startDestination = ScreenRoute.Splash.route
    ) {
        // Splash Screen
        composable(ScreenRoute.Splash.route) {
            SplashScreen(
                onSplashFinished = {
                    navController.navigate(ScreenRoute.Login.route) {
                        popUpTo(ScreenRoute.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        // Auth
        composable(ScreenRoute.Login.route) {
            LoginScreen(
                authViewModel = authViewModel,
                onLoginSuccess = { role, userId ->
                    val destination = when (role.uppercase()) {
                        "LECTURER" -> ScreenRoute.LecturerHome.route
                        "ADMIN" -> ScreenRoute.AdminHome.route
                        else -> {
                            studentViewModel.setStudentId(userId)
                            ScreenRoute.StudentHome.route
                        }
                    }
                    navController.navigate(destination) {
                        popUpTo(ScreenRoute.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = { navController.navigate(ScreenRoute.Register.route) },
                onNavigateToForgotPassword = { navController.navigate(ScreenRoute.ForgotPassword.route) }
            )
        }

        composable(ScreenRoute.Register.route) {
            RegisterScreen(
                authViewModel = authViewModel,
                onBackToLogin = { navController.popBackStack() },
                onRegisterSuccess = { role, userId ->
                    val destination = when (role.uppercase()) {
                        "LECTURER" -> ScreenRoute.LecturerHome.route
                        "ADMIN" -> ScreenRoute.AdminHome.route
                        else -> {
                            studentViewModel.setStudentId(userId)
                            ScreenRoute.StudentHome.route
                        }
                    }
                    navController.navigate(destination) {
                        popUpTo(ScreenRoute.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(ScreenRoute.ForgotPassword.route) {
            ForgotPasswordScreen(
                onBackToLogin = { navController.popBackStack() }
            )
        }

        // Student Screens
        composable(ScreenRoute.StudentHome.route) {
            StudentDashboardScreen(
                currentUser = currentUser,
                studentViewModel = studentViewModel,
                onNavigateToRoute = { route -> navController.navigate(route) },
                onLogout = {
                    authViewModel.logout()
                    navController.navigate(ScreenRoute.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(ScreenRoute.StudentAcademics.route) {
            AcademicsScreen(
                studentViewModel = studentViewModel,
                onNavigateToRoute = { route -> navController.navigate(route) }
            )
        }

        composable(ScreenRoute.StudentCourseReg.route) {
            CourseRegistrationScreen(
                studentViewModel = studentViewModel,
                onNavigateToRoute = { route -> navController.navigate(route) }
            )
        }

        composable(ScreenRoute.StudentResults.route) {
            ResultsScreen(
                studentViewModel = studentViewModel,
                onNavigateToRoute = { route -> navController.navigate(route) }
            )
        }

        composable(ScreenRoute.StudentTranscript.route) {
            TranscriptScreen(
                currentUser = currentUser,
                studentViewModel = studentViewModel,
                onNavigateToRoute = { route -> navController.navigate(route) }
            )
        }

        composable(ScreenRoute.StudentAttendance.route) {
            AttendanceScreen(
                studentViewModel = studentViewModel,
                onNavigateToRoute = { route -> navController.navigate(route) }
            )
        }

        composable(ScreenRoute.StudentTimetable.route) {
            TimetableScreen(
                studentViewModel = studentViewModel,
                onNavigateToRoute = { route -> navController.navigate(route) }
            )
        }

        composable(ScreenRoute.StudentExams.route) {
            ExamScheduleScreen(
                studentViewModel = studentViewModel,
                onNavigateToRoute = { route -> navController.navigate(route) }
            )
        }

        composable(ScreenRoute.StudentELearning.route) {
            ELearningScreen(
                currentUser = currentUser,
                studentViewModel = studentViewModel,
                onNavigateToRoute = { route -> navController.navigate(route) }
            )
        }

        composable(ScreenRoute.StudentFinance.route) {
            FinanceScreen(
                studentViewModel = studentViewModel,
                onNavigateToRoute = { route -> navController.navigate(route) }
            )
        }

        composable(ScreenRoute.StudentLibrary.route) {
            LibraryScreen(
                studentViewModel = studentViewModel,
                onNavigateToRoute = { route -> navController.navigate(route) }
            )
        }

        composable(ScreenRoute.StudentNews.route) {
            NewsEventsScreen(
                studentViewModel = studentViewModel,
                onNavigateToRoute = { route -> navController.navigate(route) }
            )
        }

        composable(ScreenRoute.StudentServices.route) {
            CampusServicesScreen(
                onNavigateToRoute = { route -> navController.navigate(route) }
            )
        }

        composable(ScreenRoute.StudentHelpDesk.route) {
            HelpDeskScreen(
                currentUser = currentUser,
                studentViewModel = studentViewModel,
                onNavigateToRoute = { route -> navController.navigate(route) }
            )
        }

        composable(ScreenRoute.StudentNotifications.route) {
            NotificationsScreen(
                studentViewModel = studentViewModel,
                onNavigateToRoute = { route -> navController.navigate(route) }
            )
        }

        composable(ScreenRoute.StudentProfile.route) {
            ProfileScreen(
                currentUser = currentUser,
                authViewModel = authViewModel,
                studentViewModel = studentViewModel,
                onNavigateToRoute = { route -> navController.navigate(route) },
                onLogout = {
                    authViewModel.logout()
                    navController.navigate(ScreenRoute.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        // Lecturer Screens
        composable(ScreenRoute.LecturerHome.route) {
            LecturerDashboardScreen(
                currentUser = currentUser,
                lecturerViewModel = lecturerViewModel,
                onNavigateToRoute = { route -> navController.navigate(route) },
                onLogout = {
                    authViewModel.logout()
                    navController.navigate(ScreenRoute.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(ScreenRoute.LecturerAttendance.route) {
            LecturerAttendanceScreen(
                lecturerViewModel = lecturerViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(ScreenRoute.LecturerAssignments.route) {
            LecturerAssignmentsScreen(
                currentUser = currentUser,
                lecturerViewModel = lecturerViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(ScreenRoute.LecturerMarks.route) {
            LecturerMarksScreen(
                lecturerViewModel = lecturerViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // Admin Screens
        composable(ScreenRoute.AdminHome.route) {
            AdminDashboardScreen(
                currentUser = currentUser,
                adminViewModel = adminViewModel,
                onNavigateToRoute = { route -> navController.navigate(route) },
                onLogout = {
                    authViewModel.logout()
                    navController.navigate(ScreenRoute.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(ScreenRoute.AdminManageStudents.route) {
            AdminManageStudentsScreen(
                adminViewModel = adminViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(ScreenRoute.AdminManageLecturers.route) {
            AdminManageLecturersScreen(
                adminViewModel = adminViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(ScreenRoute.AdminManageCourses.route) {
            AdminManageCoursesScreen(
                adminViewModel = adminViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(ScreenRoute.AdminManageFees.route) {
            AdminManageFeesScreen(
                adminViewModel = adminViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(ScreenRoute.AdminManageAnnouncements.route) {
            AdminManageAnnouncementsScreen(
                adminViewModel = adminViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(ScreenRoute.AdminManageHelpDesk.route) {
            AdminManageHelpDeskScreen(
                adminViewModel = adminViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
