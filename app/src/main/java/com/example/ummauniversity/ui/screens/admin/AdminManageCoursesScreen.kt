package com.example.ummauniversity.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ummauniversity.ui.components.UmmaTopBar
import com.example.ummauniversity.ui.theme.UmmaGreenPrimary
import com.example.ummauniversity.viewmodel.AdminViewModel

@Composable
fun AdminManageCoursesScreen(
    adminViewModel: AdminViewModel,
    onNavigateBack: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Courses", "Departments", "Programmes")

    val courses by adminViewModel.courses.collectAsState()
    val departments by adminViewModel.departments.collectAsState()
    val programmes by adminViewModel.programmes.collectAsState()

    var showAddDialog by remember { mutableStateOf(false) }

    // Form states
    var codeInput by remember { mutableStateOf("") }
    var titleInput by remember { mutableStateOf("") }
    var creditsInput by remember { mutableStateOf("3") }
    var lecturerInput by remember { mutableStateOf("Dr. Omar Farooq") }
    var deptInput by remember { mutableStateOf("Computer Science") }

    Scaffold(
        topBar = {
            UmmaTopBar(
                title = "Manage Academics",
                subtitle = "Courses, Departments & Programmes",
                onBackClick = onNavigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = UmmaGreenPrimary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Item", tint = Color.White)
            }
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
                        text = { Text(title, fontWeight = FontWeight.Bold) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                when (selectedTab) {
                    0 -> {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            contentPadding = PaddingValues(bottom = 72.dp)
                        ) {
                            items(courses) { course ->
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .padding(16.dp)
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(text = course.code, fontWeight = FontWeight.Bold, color = UmmaGreenPrimary)
                                            Text(text = course.title, fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.titleSmall)
                                            Text(text = "Credits: ${course.creditHours} • Lecturer: ${course.lecturerName}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                        }
                                        IconButton(onClick = { adminViewModel.deleteCourse(course.id) }) {
                                            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
                                        }
                                    }
                                }
                            }
                        }
                    }
                    1 -> {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            contentPadding = PaddingValues(bottom = 72.dp)
                        ) {
                            items(departments) { dept ->
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .padding(16.dp)
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(text = "${dept.code} - ${dept.name}", fontWeight = FontWeight.Bold)
                                            Text(text = "HOD: ${dept.headOfDepartment} • Building: ${dept.building}", style = MaterialTheme.typography.bodySmall)
                                        }
                                        IconButton(onClick = { adminViewModel.deleteDepartment(dept.id) }) {
                                            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
                                        }
                                    }
                                }
                            }
                        }
                    }
                    2 -> {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            contentPadding = PaddingValues(bottom = 72.dp)
                        ) {
                            items(programmes) { prog ->
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .padding(16.dp)
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(text = "${prog.code} - ${prog.name}", fontWeight = FontWeight.Bold)
                                            Text(text = "Dept: ${prog.departmentName} • Duration: ${prog.durationYears} Years", style = MaterialTheme.typography.bodySmall)
                                        }
                                        IconButton(onClick = { adminViewModel.deleteProgramme(prog.id) }) {
                                            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        val titleText = when (selectedTab) {
            0 -> "Add New Course / Unit"
            1 -> "Add New Department"
            else -> "Add New Programme"
        }

        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text(titleText) },
            text = {
                Column {
                    OutlinedTextField(
                        value = codeInput,
                        onValueChange = { codeInput = it },
                        label = { Text("Code (e.g. COMP 316)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = titleInput,
                        onValueChange = { titleInput = it },
                        label = { Text("Title / Name") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    if (selectedTab == 0) {
                        OutlinedTextField(
                            value = creditsInput,
                            onValueChange = { creditsInput = it },
                            label = { Text("Credit Hours") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = lecturerInput,
                            onValueChange = { lecturerInput = it },
                            label = { Text("Lecturer Name") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else if (selectedTab == 1) {
                        OutlinedTextField(
                            value = lecturerInput,
                            onValueChange = { lecturerInput = it },
                            label = { Text("Head of Department (HOD)") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (codeInput.isNotBlank() && titleInput.isNotBlank()) {
                            when (selectedTab) {
                                0 -> adminViewModel.addCourse(codeInput, titleInput, creditsInput.toIntOrNull() ?: 3, lecturerInput, deptInput)
                                1 -> adminViewModel.addDepartment(codeInput, titleInput, lecturerInput, "Main Complex")
                                2 -> adminViewModel.addProgramme(codeInput, titleInput, deptInput, 4)
                            }
                            showAddDialog = false
                            codeInput = ""
                            titleInput = ""
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = UmmaGreenPrimary)
                ) {
                    Text("ADD ENTRY")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
