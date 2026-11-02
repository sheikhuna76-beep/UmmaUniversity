package com.example.ummauniversity.ui.screens.student

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ummauniversity.ui.components.StudentBottomNavBar
import com.example.ummauniversity.ui.components.UmmaTopBar
import com.example.ummauniversity.ui.theme.UmmaGoldAccent
import com.example.ummauniversity.ui.theme.UmmaGreenDark
import com.example.ummauniversity.ui.theme.UmmaGreenPrimary
import com.example.ummauniversity.viewmodel.StudentViewModel

@Composable
fun TranscriptScreen(
    studentViewModel: StudentViewModel,
    onNavigateToRoute: (String) -> Unit
) {
    val profile by studentViewModel.studentProfile.collectAsState()
    val results by studentViewModel.results.collectAsState()

    Scaffold(
        topBar = {
            UmmaTopBar(
                title = "Academic Transcript",
                subtitle = "Official Record of Performance",
                onBackClick = { onNavigateToRoute("student_academics") }
            )
        },
        bottomBar = {
            StudentBottomNavBar(
                selectedRoute = "student_academics",
                onRouteSelected = onNavigateToRoute
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
            // Header Certificate Style Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = UmmaGreenDark)
            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "UMMA UNIVERSITY",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "OFFICIAL ACADEMIC TRANSCRIPT",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = UmmaGoldAccent
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(text = "Name: Ahmed Hassan", color = Color.White, fontSize = 12.sp)
                            Text(text = "Reg No: ${profile?.regNumber ?: "UMMA/CS/001/2026"}", color = Color.White, fontSize = 12.sp)
                            Text(text = "Programme: ${profile?.programme ?: "BSc. Computer Science"}", color = Color.White, fontSize = 12.sp)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(text = "Year of Study: Year 3", color = Color.White, fontSize = 12.sp)
                            Text(text = "CGPA: ${profile?.cgpa ?: 3.68}", color = UmmaGoldAccent, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Text(text = "Status: PASS", color = Color(0xFF81C784), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "ACADEMIC YEAR: 2026/2027 • SEMESTER 1",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = UmmaGreenPrimary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Transcript Table Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(UmmaGreenPrimary, RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Course", color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.weight(2f), fontSize = 12.sp)
                Text(text = "Title", color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.weight(3f), fontSize = 12.sp)
                Text(text = "Marks", color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f), fontSize = 12.sp)
                Text(text = "Grade", color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f), fontSize = 12.sp)
                Text(text = "GP", color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f), fontSize = 12.sp)
            }

            // Transcript Rows
            results.forEach { res ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(0.5.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = res.courseCode, fontWeight = FontWeight.Bold, modifier = Modifier.weight(2f), fontSize = 12.sp)
                    Text(text = res.courseTitle, modifier = Modifier.weight(3f), fontSize = 11.sp, maxLines = 1)
                    Text(text = "${res.totalMarks}%", modifier = Modifier.weight(1f), fontSize = 12.sp)
                    Text(text = res.grade, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f), fontSize = 12.sp, color = UmmaGreenPrimary)
                    Text(text = "${res.gradePoint}", modifier = Modifier.weight(1f), fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Summary Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Transcript Summary Statement",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Total Credit Hours Attempted: 15", style = MaterialTheme.typography.bodySmall)
                        Text(text = "Total Units Passed: 5", style = MaterialTheme.typography.bodySmall)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Semester GPA: ${profile?.gpa ?: 3.75}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                        Text(text = "Cumulative GPA (CGPA): ${profile?.cgpa ?: 3.68}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = UmmaGreenPrimary)
                    }
                }
            }
        }
    }
}
