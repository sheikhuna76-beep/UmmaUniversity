package com.example.ummauniversity.ui.screens.admin

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
import com.example.ummauniversity.viewmodel.AdminViewModel

@Composable
fun AdminDashboardScreen(
    currentUser: UserEntity?,
    adminViewModel: AdminViewModel,
    onNavigateToRoute: (String) -> Unit,
    onLogout: () -> Unit
) {
    val students by adminViewModel.students.collectAsState()
    val lecturers by adminViewModel.lecturers.collectAsState()
    val courses by adminViewModel.courses.collectAsState()
    val departments by adminViewModel.departments.collectAsState()
    val totalCollectedFees by adminViewModel.totalCollectedFees.collectAsState()

    Scaffold(
        topBar = {
            UmmaTopBar(
                title = "Administrator Portal",
                subtitle = "UMMA University Management Console",
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
            // Admin Banner Card
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
                            text = "Administrator Control Center",
                            style = MaterialTheme.typography.bodySmall,
                            color = UmmaGoldAccent
                        )
                        Text(
                            text = currentUser?.fullName ?: "Admin Fatuma Hussein",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Staff ID: ${currentUser?.regOrStaffNumber ?: "ADM/001"}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.85f)
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
                            imageVector = Icons.Default.AdminPanelSettings,
                            contentDescription = null,
                            tint = UmmaGreenDark,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Summary Statistics Grid
            SectionHeader(title = "University Overview Statistics")

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    title = "Total Students",
                    value = "${students.size} Enrolled",
                    icon = Icons.Default.School,
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    iconColor = UmmaGreenPrimary,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Total Lecturers",
                    value = "${lecturers.size} Staff",
                    icon = Icons.Default.Person,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    iconColor = Color(0xFF7A5900),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    title = "Total Courses",
                    value = "${courses.size} Units",
                    icon = Icons.Default.Book,
                    containerColor = Color(0xFFE3F2FD),
                    iconColor = Color(0xFF1565C0),
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Departments",
                    value = "${departments.size} Faculties",
                    icon = Icons.Default.Apartment,
                    containerColor = Color(0xFFF3E5F5),
                    iconColor = Color(0xFF7B1FA2),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            StatCard(
                title = "Collected Tuition Revenue",
                value = "KES ${String.format("%,.0f", totalCollectedFees)}",
                icon = Icons.Default.AccountBalanceWallet,
                containerColor = Color(0xFFE8F5E9),
                iconColor = Color(0xFF2E7D32)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Management Tools Grid
            SectionHeader(title = "System Management Modules")

            val adminModules = listOf(
                AdminTileItem("Students", Icons.Default.People, UmmaGreenPrimary, "admin_manage_students"),
                AdminTileItem("Lecturers", Icons.Default.Badge, Color(0xFF1565C0), "admin_manage_lecturers"),
                AdminTileItem("Courses & Depts", Icons.Default.School, Color(0xFF7B1FA2), "admin_manage_courses"),
                AdminTileItem("Fee Management", Icons.Default.AccountBalanceWallet, Color(0xFF2E7D32), "admin_manage_fees"),
                AdminTileItem("Announcements", Icons.Default.Campaign, Color(0xFFE65100), "admin_manage_announcements"),
                AdminTileItem("Help Desk", Icons.Default.SupportAgent, Color(0xFF00838F), "admin_manage_helpdesk")
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                AdminModuleTile(item = adminModules[0], modifier = Modifier.weight(1f), onClick = onNavigateToRoute)
                AdminModuleTile(item = adminModules[1], modifier = Modifier.weight(1f), onClick = onNavigateToRoute)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                AdminModuleTile(item = adminModules[2], modifier = Modifier.weight(1f), onClick = onNavigateToRoute)
                AdminModuleTile(item = adminModules[3], modifier = Modifier.weight(1f), onClick = onNavigateToRoute)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                AdminModuleTile(item = adminModules[4], modifier = Modifier.weight(1f), onClick = onNavigateToRoute)
                AdminModuleTile(item = adminModules[5], modifier = Modifier.weight(1f), onClick = onNavigateToRoute)
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

data class AdminTileItem(
    val title: String,
    val icon: ImageVector,
    val containerColor: Color,
    val route: String
)

@Composable
fun AdminModuleTile(
    item: AdminTileItem,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit
) {
    Card(
        modifier = modifier.height(95.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = item.containerColor),
        onClick = { onClick(item.route) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Icon(imageVector = item.icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(28.dp))
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = item.title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
        }
    }
}
