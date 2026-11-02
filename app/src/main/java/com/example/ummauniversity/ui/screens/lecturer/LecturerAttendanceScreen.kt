package com.example.ummauniversity.ui.screens.lecturer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ummauniversity.data.entities.CourseEntity
import com.example.ummauniversity.data.entities.UserEntity
import com.example.ummauniversity.ui.components.UmmaTopBar
import com.example.ummauniversity.ui.theme.UmmaGreenPrimary
import com.example.ummauniversity.viewmodel.LecturerViewModel

@Composable
fun LecturerAttendanceScreen(
    lecturerViewModel: LecturerViewModel,
    onNavigateBack: () -> Unit
) {
    val assignedCourses by lecturerViewModel.assignedCourses.collectAsState()
    val studentsList by lecturerViewModel.studentsList.collectAsState()

    var selectedCourse by remember { mutableStateOf<CourseEntity?>(null) }
    LaunchedEffect(assignedCourses) {
        if (selectedCourse == null && assignedCourses.isNotEmpty()) {
            selectedCourse = assignedCourses.first()
        }
    }

    val attendanceState = remember { mutableStateMapOf<Long, Boolean>() }
    LaunchedEffect(studentsList) {
        studentsList.forEach { student ->
            if (!attendanceState.containsKey(student.id)) {
                attendanceState[student.id] = true
            }
        }
    }

    val snackbarHostState = remember { SnackbarHostState() }
    var snackbarMsg by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(snackbarMsg) {
        snackbarMsg?.let {
            snackbarHostState.showSnackbar(it)
            snackbarMsg = null
        }
    }

    Scaffold(
        topBar = {
            UmmaTopBar(
                title = "Record Class Attendance",
                subtitle = "Select Course & Mark Presence",
                onBackClick = onNavigateBack
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            Surface(
                tonalElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        val currentCourse = selectedCourse
                        if (currentCourse != null) {
                            lecturerViewModel.saveAttendance(
                                course = currentCourse,
                                attendanceMap = attendanceState,
                                studentList = studentsList
                            )
                            snackbarMsg = "Attendance recorded for ${currentCourse.code}!"
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = UmmaGreenPrimary)
                ) {
                    Icon(Icons.Default.Save, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("SAVE ATTENDANCE BATCH", fontWeight = FontWeight.Bold)
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        ) {
            Text("Select Assigned Course:", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))

            var dropdownExpanded by remember { mutableStateOf(false) }
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(
                    onClick = { dropdownExpanded = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(selectedCourse?.let { "${it.code} - ${it.title}" } ?: "Select Course")
                }
                DropdownMenu(
                    expanded = dropdownExpanded,
                    onDismissRequest = { dropdownExpanded = false }
                ) {
                    assignedCourses.forEach { course ->
                        DropdownMenuItem(
                            text = { Text("${course.code} - ${course.title}") },
                            onClick = {
                                selectedCourse = course
                                dropdownExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Student Enrolled List:", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(studentsList) { student ->
                    val isPresent = attendanceState[student.id] ?: true
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(14.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(text = student.fullName, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
                                Text(
                                    text = student.regOrStaffNumber.ifEmpty { "UMMA/CS/001/2026" },
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = if (isPresent) "PRESENT" else "ABSENT",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isPresent) Color(0xFF2E7D32) else Color(0xFFD32F2F)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Switch(
                                    checked = isPresent,
                                    onCheckedChange = { checked ->
                                        attendanceState[student.id] = checked
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
