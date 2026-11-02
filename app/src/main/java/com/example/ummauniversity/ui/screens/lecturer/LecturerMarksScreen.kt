package com.example.ummauniversity.ui.screens.lecturer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun LecturerMarksScreen(
    lecturerViewModel: LecturerViewModel,
    onNavigateBack: () -> Unit
) {
    val assignedCourses by lecturerViewModel.assignedCourses.collectAsState()
    val studentsList by lecturerViewModel.studentsList.collectAsState()

    var selectedCourse by remember { mutableStateOf<CourseEntity?>(null) }
    var selectedStudent by remember { mutableStateOf<UserEntity?>(null) }

    var catInput by remember { mutableStateOf("") }
    var examInput by remember { mutableStateOf("") }

    val catVal = catInput.toIntOrNull() ?: 0
    val examVal = examInput.toIntOrNull() ?: 0
    val totalVal = (catVal + examVal).coerceAtMost(100)

    val (calculatedGrade, calculatedGp) = remember(totalVal) {
        when {
            totalVal >= 80 -> Pair("A", 4.0)
            totalVal >= 70 -> Pair("B", 3.0)
            totalVal >= 60 -> Pair("C", 2.0)
            totalVal >= 50 -> Pair("D", 1.0)
            else -> Pair("F", 0.0)
        }
    }

    LaunchedEffect(assignedCourses, studentsList) {
        if (selectedCourse == null && assignedCourses.isNotEmpty()) {
            selectedCourse = assignedCourses.first()
        }
        if (selectedStudent == null && studentsList.isNotEmpty()) {
            selectedStudent = studentsList.first()
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
                title = "Enter Examination Marks",
                subtitle = "CAT (30%) & Exam (70%) Entry",
                onBackClick = onNavigateBack
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("1. Select Course:", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))

                    var courseExpanded by remember { mutableStateOf(false) }
                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedButton(
                            onClick = { courseExpanded = true },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(selectedCourse?.let { "${it.code} - ${it.title}" } ?: "Select Course")
                        }
                        DropdownMenu(
                            expanded = courseExpanded,
                            onDismissRequest = { courseExpanded = false }
                        ) {
                            assignedCourses.forEach { c ->
                                DropdownMenuItem(
                                    text = { Text("${c.code} - ${c.title}") },
                                    onClick = {
                                        selectedCourse = c
                                        courseExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text("2. Select Student:", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))

                    var studentExpanded by remember { mutableStateOf(false) }
                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedButton(
                            onClick = { studentExpanded = true },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(selectedStudent?.let { "${it.fullName} (${it.regOrStaffNumber})" } ?: "Select Student")
                        }
                        DropdownMenu(
                            expanded = studentExpanded,
                            onDismissRequest = { studentExpanded = false }
                        ) {
                            studentsList.forEach { s ->
                                DropdownMenuItem(
                                    text = { Text("${s.fullName} (${s.regOrStaffNumber})") },
                                    onClick = {
                                        selectedStudent = s
                                        studentExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Marks Entry Input Form
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("3. Enter Assessment Marks:", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedTextField(
                            value = catInput,
                            onValueChange = { catInput = it },
                            label = { Text("CAT Marks (Max 30)") },
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = examInput,
                            onValueChange = { examInput = it },
                            label = { Text("Exam Marks (Max 70)") },
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Auto Calculation Preview
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(text = "Computed Total: $totalVal / 100", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
                                Text(text = "Grade Point: $calculatedGp", style = MaterialTheme.typography.bodySmall)
                            }
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = UmmaGreenPrimary
                            ) {
                                Text(
                                    text = "GRADE $calculatedGrade",
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.White
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            val st = selectedStudent
                            val cr = selectedCourse
                            if (st != null && cr != null) {
                                lecturerViewModel.enterMarks(
                                    student = st,
                                    course = cr,
                                    catMarks = catVal,
                                    examMarks = examVal
                                )
                                snackbarMsg = "Saved Grade $calculatedGrade ($totalVal%) for ${st.fullName}!"
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = UmmaGreenPrimary)
                    ) {
                        Text("SAVE & PUBLISH MARKS", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    }
                }
            }
        }
    }
}
