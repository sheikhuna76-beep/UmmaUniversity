package com.example.ummauniversity.ui.screens.lecturer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.example.ummauniversity.data.entities.UserEntity
import com.example.ummauniversity.ui.components.SectionHeader
import com.example.ummauniversity.ui.components.StatCard
import com.example.ummauniversity.ui.components.UmmaTopBar
import com.example.ummauniversity.ui.theme.UmmaGoldAccent
import com.example.ummauniversity.ui.theme.UmmaGreenDark
import com.example.ummauniversity.ui.theme.UmmaGreenPrimary
import com.example.ummauniversity.viewmodel.LecturerViewModel

@Composable
fun LecturerDashboardScreen(
    currentUser: UserEntity?,
    lecturerViewModel: LecturerViewModel,
    onNavigateToRoute: (String) -> Unit,
    onLogout: () -> Unit
) {
    val assignedCourses by lecturerViewModel.assignedCourses.collectAsState()
    val studentsList by lecturerViewModel.studentsList.collectAsState()
    val assignments by lecturerViewModel.assignments.collectAsState()

    Scaffold(
        topBar = {
            UmmaTopBar(
                title = "Lecturer Portal",
                subtitle = "UMMA University Academic Management",
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Logout", tint = Color.White)
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Lecturer Banner Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = UmmaGreenDark)
            ) {
                Row(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Faculty Dashboard",
                            style = MaterialTheme.typography.bodySmall,
                            color = UmmaGoldAccent
                        )
                        Text(
                            text = currentUser?.fullName ?: "Dr. Omar Farooq",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Staff ID: ${currentUser?.regOrStaffNumber ?: "LEC/CS/042"}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.85f)
                        )
                        Text(
                            text = "Department: ${currentUser?.department ?: "Computer Science"}",
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
                            imageVector = Icons.Default.School,
                            contentDescription = null,
                            tint = UmmaGreenDark,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Quick Stats Grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    title = "Assigned Courses",
                    value = "${assignedCourses.size} Units",
                    icon = Icons.Default.Class,
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    iconColor = UmmaGreenPrimary,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Enrolled Students",
                    value = "${studentsList.size} Students",
                    icon = Icons.Default.People,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    iconColor = Color(0xFF7A5900),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Lecturer Core Actions
            SectionHeader(title = "Faculty Actions")

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                LecturerActionTile(
                    title = "Record Attendance",
                    icon = Icons.Default.FactCheck,
                    containerColor = UmmaGreenPrimary,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigateToRoute("lecturer_attendance") }
                )
                LecturerActionTile(
                    title = "Manage Assignments",
                    icon = Icons.Default.Assignment,
                    containerColor = Color(0xFF1565C0),
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigateToRoute("lecturer_assignments") }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                LecturerActionTile(
                    title = "Enter Marks & Grades",
                    icon = Icons.Default.Grade,
                    containerColor = Color(0xFFE65100),
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigateToRoute("lecturer_marks") }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Assigned Courses List
            SectionHeader(title = "My Teaching Courses")

            assignedCourses.forEach { course ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = course.code,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = UmmaGreenPrimary
                            )
                            Text(
                                text = "${course.creditHours} Credits",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = course.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = course.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LecturerActionTile(
    title: String,
    icon: ImageVector,
    containerColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.height(100.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(28.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
    }
}
