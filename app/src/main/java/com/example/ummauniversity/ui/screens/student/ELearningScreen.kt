package com.example.ummauniversity.ui.screens.student

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ummauniversity.data.entities.AssignmentEntity
import com.example.ummauniversity.data.entities.AssignmentSubmissionEntity
import com.example.ummauniversity.data.entities.MaterialEntity
import com.example.ummauniversity.data.entities.UserEntity
import com.example.ummauniversity.ui.components.EmptyStateView
import com.example.ummauniversity.ui.components.StatusBadge
import com.example.ummauniversity.ui.components.StudentBottomNavBar
import com.example.ummauniversity.ui.components.UmmaTopBar
import com.example.ummauniversity.ui.theme.UmmaGreenPrimary
import com.example.ummauniversity.viewmodel.StudentViewModel

@Composable
fun ELearningScreen(
    currentUser: UserEntity?,
    studentViewModel: StudentViewModel,
    onNavigateToRoute: (String) -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Lecture Materials", "Assignments & Tasks")

    val materials by studentViewModel.materials.collectAsState()
    val assignments by studentViewModel.assignments.collectAsState()
    val submissions by studentViewModel.submissions.collectAsState()

    var showSubmitDialog by remember { mutableStateOf(false) }
    var selectedAssignmentForSubmit by remember { mutableStateOf<AssignmentEntity?>(null) }
    var submissionContent by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            UmmaTopBar(
                title = "E-Learning Portal",
                subtitle = "Notes, Resources & Assignments",
                onBackClick = { onNavigateToRoute("student_home") }
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
        ) {
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = UmmaGreenPrimary
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(text = title, fontWeight = FontWeight.Bold) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            when (selectedTab) {
                0 -> {
                    // Materials List
                    if (materials.isEmpty()) {
                        EmptyStateView(
                            message = "No learning materials uploaded yet.",
                            icon = Icons.Default.MenuBook
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            contentPadding = PaddingValues(bottom = 16.dp)
                        ) {
                            items(materials) { mat ->
                                MaterialCardItem(material = mat)
                            }
                        }
                    }
                }
                1 -> {
                    // Assignments List
                    if (assignments.isEmpty()) {
                        EmptyStateView(
                            message = "No active assignments posted.",
                            icon = Icons.Default.Assignment
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            contentPadding = PaddingValues(bottom = 16.dp)
                        ) {
                            items(assignments) { assign ->
                                val submission = submissions.firstOrNull { it.assignmentId == assign.id }
                                AssignmentCardItem(
                                    assignment = assign,
                                    submission = submission,
                                    onSubmitClick = {
                                        selectedAssignmentForSubmit = assign
                                        submissionContent = submission?.content ?: ""
                                        showSubmitDialog = true
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (showSubmitDialog && selectedAssignmentForSubmit != null) {
        val assign = selectedAssignmentForSubmit!!
        AlertDialog(
            onDismissRequest = { showSubmitDialog = false },
            title = { Text(text = "Submit Assignment") },
            text = {
                Column {
                    Text(text = assign.title, fontWeight = FontWeight.Bold)
                    Text(text = "Course: ${assign.courseCode}", style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = submissionContent,
                        onValueChange = { submissionContent = it },
                        label = { Text("Submission Notes / File Link") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        placeholder = { Text("Paste submission URL, drive link or answer notes here...") }
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (submissionContent.isNotBlank()) {
                            studentViewModel.submitAssignment(
                                assignment = assign,
                                content = submissionContent,
                                studentName = currentUser?.fullName ?: "Asiyah Abdullahi",
                                regNo = currentUser?.regOrStaffNumber ?: "UMMA/CS/001/2026"
                            )
                            showSubmitDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = UmmaGreenPrimary)
                ) {
                    Text("SUBMIT WORK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showSubmitDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun MaterialCardItem(material: MaterialEntity) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = UmmaGreenPrimary,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = material.courseCode,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = "${material.type} • ${material.fileSize}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = material.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = material.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Uploaded: ${material.uploadDate}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline
                )
                Button(
                    onClick = { /* Sample Download */ },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = UmmaGreenPrimary),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Download", fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun AssignmentCardItem(
    assignment: AssignmentEntity,
    submission: AssignmentSubmissionEntity?,
    onSubmitClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = UmmaGreenPrimary,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = assignment.courseCode,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
                StatusBadge(status = submission?.status ?: "PENDING")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = assignment.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = assignment.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Due Date: ${assignment.dueDate}",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.error
                )
                Text(
                    text = "Max Marks: ${assignment.totalMarks}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }

            if (submission != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = "Submitted: ${submission.submissionDate}",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold
                        )
                        if (submission.marksObtained != null) {
                            Text(
                                text = "Score: ${submission.marksObtained}/${assignment.totalMarks}",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold,
                                color = UmmaGreenPrimary
                            )
                            if (!submission.feedback.isNullOrBlank()) {
                                Text(
                                    text = "Feedback: ${submission.feedback}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onSubmitClick,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = UmmaGreenPrimary)
            ) {
                Icon(Icons.Default.Upload, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(if (submission == null) "SUBMIT ASSIGNMENT" else "UPDATE SUBMISSION", fontSize = 13.sp)
            }
        }
    }
}
