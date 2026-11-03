package com.example.ummauniversity.ui.screens.student

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ummauniversity.data.entities.AnnouncementEntity
import com.example.ummauniversity.data.entities.UserEntity
import com.example.ummauniversity.ui.components.SectionHeader
import com.example.ummauniversity.ui.components.StatCard
import com.example.ummauniversity.ui.components.StudentBottomNavBar
import com.example.ummauniversity.ui.components.UmmaTopBar
import com.example.ummauniversity.ui.theme.UmmaGoldAccent
import com.example.ummauniversity.ui.theme.UmmaGreenDark
import com.example.ummauniversity.ui.theme.UmmaGreenPrimary
import com.example.ummauniversity.viewmodel.StudentViewModel

@Composable
fun StudentDashboardScreen(
    currentUser: UserEntity?,
    studentViewModel: StudentViewModel,
    onNavigateToRoute: (String) -> Unit,
    onLogout: () -> Unit
) {
    val profile by studentViewModel.studentProfile.collectAsState()
    val announcements by studentViewModel.announcements.collectAsState()
    val notifications by studentViewModel.notifications.collectAsState()

    Scaffold(
        topBar = {
            UmmaTopBar(
                title = "UMMA UNIVERSITY",
                subtitle = "Student Portal Dashboard",
                actions = {
                    IconButton(onClick = { onNavigateToRoute("student_notifications") }) {
                        BadgedBox(
                            badge = {
                                val unread = notifications.count { !it.isRead }
                                if (unread > 0) {
                                    Badge { Text("$unread") }
                                }
                            }
                        ) {
                            Icon(Icons.Default.Notifications, contentDescription = "Notifications", tint = Color.White)
                        }
                    }
                    IconButton(onClick = { onNavigateToRoute("student_profile") }) {
                        Icon(Icons.Default.AccountCircle, contentDescription = "Profile", tint = Color.White)
                    }
                }
            )
        },
        bottomBar = {
            StudentBottomNavBar(
                selectedRoute = "student_home",
                onRouteSelected = onNavigateToRoute
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.surface)
                .verticalScroll(rememberScrollState())
        ) {
            // Welcome Card Banner
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = UmmaGreenDark)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Welcome back,",
                                style = MaterialTheme.typography.bodySmall,
                                color = UmmaGoldAccent
                            )
                            Text(
                                text = currentUser?.fullName ?: "Asiyah Abdullahi",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = profile?.regNumber ?: currentUser?.regOrStaffNumber ?: "UMMA/CS/001/2026",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White.copy(alpha = 0.85f)
                            )
                            Text(
                                text = "${profile?.programme ?: "BSc. Computer Science"} • ${profile?.semester ?: "Sem 1, 2026/2027"}",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White.copy(alpha = 0.7f)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(UmmaGoldAccent),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = UmmaGreenDark,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }
                }
            }

            // Quick Stats Grid
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    title = "GPA / CGPA",
                    value = "${profile?.gpa ?: 3.75} / ${profile?.cgpa ?: 3.68}",
                    icon = Icons.Default.Grade,
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    iconColor = UmmaGreenPrimary,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigateToRoute("student_results") }
                )
                StatCard(
                    title = "Fee Balance",
                    value = "KES ${String.format("%,.0f", profile?.feeBalance ?: 20000.0)}",
                    icon = Icons.Default.AccountBalanceWallet,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    iconColor = Color(0xFF7A5900),
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigateToRoute("student_finance") }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    title = "Attendance Rate",
                    value = "93.3%",
                    icon = Icons.Default.CheckCircle,
                    containerColor = Color(0xFFE8F5E9),
                    iconColor = Color(0xFF2E7D32),
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigateToRoute("student_attendance") }
                )
                StatCard(
                    title = "Registered Units",
                    value = "5 Units",
                    icon = Icons.Default.Book,
                    containerColor = Color(0xFFE3F2FD),
                    iconColor = Color(0xFF1565C0),
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigateToRoute("student_units") }
                )
            }

            // Upcoming Next Class & Next Exam Cards
            Column(modifier = Modifier.padding(16.dp)) {
                SectionHeader(title = "Schedule Overview")

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    onClick = { onNavigateToRoute("student_timetable") }
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = null,
                            tint = UmmaGreenPrimary,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "NEXT CLASS: COMP 311 - Mobile App Dev",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Monday 08:00 AM – 11:00 AM • Computer Lab 3",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0)),
                    onClick = { onNavigateToRoute("student_exams") }
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Event,
                            contentDescription = null,
                            tint = Color(0xFFE65100),
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "NEXT EXAM: COMP 311 Examination",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFE65100)
                            )
                            Text(
                                text = "2026-04-15 @ 09:00 AM • Main Exam Hall A",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFFBF360C)
                            )
                        }
                    }
                }
            }

            // Quick Access Features Grid
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                SectionHeader(title = "Modules & Services")

                val quickModules = listOf(
                    ModuleItem("Academics", Icons.Default.School, "student_academics"),
                    ModuleItem("Course Reg", Icons.Default.AppRegistration, "student_course_reg"),
                    ModuleItem("Results", Icons.Default.Assessment, "student_results"),
                    ModuleItem("Transcript", Icons.Default.Description, "student_transcript"),
                    ModuleItem("Timetable", Icons.Default.CalendarToday, "student_timetable"),
                    ModuleItem("Exams", Icons.Default.AssignmentLate, "student_exams"),
                    ModuleItem("E-Learning", Icons.Default.MenuBook, "student_elearning"),
                    ModuleItem("Finance", Icons.Default.AccountBalanceWallet, "student_finance"),
                    ModuleItem("Library", Icons.Default.LocalLibrary, "student_library"),
                    ModuleItem("Help Desk", Icons.Default.SupportAgent, "student_helpdesk"),
                    ModuleItem("Services", Icons.Default.Apps, "student_services"),
                    ModuleItem("News", Icons.Default.Campaign, "student_news")
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    for (i in 0..3) {
                        QuickModuleIcon(quickModules[i], onNavigateToRoute)
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    for (i in 4..7) {
                        QuickModuleIcon(quickModules[i], onNavigateToRoute)
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    for (i in 8..11) {
                        QuickModuleIcon(quickModules[i], onNavigateToRoute)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Recent Announcements Section
            Column(modifier = Modifier.padding(16.dp)) {
                SectionHeader(
                    title = "Recent Announcements",
                    actionLabel = "View All",
                    onActionClick = { onNavigateToRoute("student_news") }
                )

                if (announcements.isEmpty()) {
                    Text("No announcements available.", style = MaterialTheme.typography.bodySmall)
                } else {
                    announcements.take(3).forEach { ann ->
                        AnnouncementItemCard(ann)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

data class ModuleItem(
    val title: String,
    val icon: ImageVector,
    val route: String
)

@Composable
fun QuickModuleIcon(
    item: ModuleItem,
    onClick: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(76.dp)
            .clickable { onClick(item.route) }
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.size(56.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.title,
                    tint = UmmaGreenPrimary,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = item.title,
            style = MaterialTheme.typography.labelSmall,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 1
        )
    }
}

@Composable
fun AnnouncementItemCard(announcement: AnnouncementEntity) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = announcement.department,
                    style = MaterialTheme.typography.labelSmall,
                    color = UmmaGreenPrimary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = announcement.date,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = announcement.title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = announcement.content,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2
            )
        }
    }
}
