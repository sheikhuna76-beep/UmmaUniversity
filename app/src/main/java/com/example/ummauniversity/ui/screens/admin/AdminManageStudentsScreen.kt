package com.example.ummauniversity.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ummauniversity.data.entities.UserEntity
import com.example.ummauniversity.ui.components.EmptyStateView
import com.example.ummauniversity.ui.components.UmmaTopBar
import com.example.ummauniversity.ui.theme.UmmaGreenPrimary
import com.example.ummauniversity.viewmodel.AdminViewModel
import kotlinx.coroutines.launch

@Composable
fun AdminManageStudentsScreen(
    adminViewModel: AdminViewModel,
    onNavigateBack: () -> Unit
) {
    val students by adminViewModel.students.collectAsState()

    var showAddDialog by remember { mutableStateOf(false) }
    var nameInput by remember { mutableStateOf("") }
    var emailInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("student123") }
    var regInput by remember { mutableStateOf("UMMA/CS/") }
    var deptInput by remember { mutableStateOf("Computer Science") }
    var progInput by remember { mutableStateOf("Bachelor of Computer Science") }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            UmmaTopBar(
                title = "Manage Student Accounts",
                subtitle = "Enrolled Students Register",
                onBackClick = onNavigateBack
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = UmmaGreenPrimary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Student", tint = Color.White)
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
            Text("Registered Student Directory (${students.size})", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(10.dp))

            if (students.isEmpty()) {
                EmptyStateView(
                    message = "No student accounts found.",
                    icon = Icons.Default.School
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(bottom = 72.dp)
                ) {
                    items(students) { student ->
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
                                    Text(text = student.fullName, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyLarge)
                                    Text(text = "Reg: ${student.regOrStaffNumber}", style = MaterialTheme.typography.bodySmall, color = UmmaGreenPrimary, fontWeight = FontWeight.Bold)
                                    Text(text = "${student.programme} • ${student.department}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Text(text = student.email, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline)
                                }

                                IconButton(
                                    onClick = { adminViewModel.deleteUser(student.id) }
                                ) {
                                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Register New Student Account") },
            text = {
                Column {
                    OutlinedTextField(
                        value = nameInput,
                        onValueChange = { nameInput = it },
                        label = { Text("Full Name (e.g. Asiyah Abdullahi)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = emailInput,
                        onValueChange = { emailInput = it },
                        label = { Text("Student Email") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = passwordInput,
                        onValueChange = { passwordInput = it },
                        label = { Text("Initial Password") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = regInput,
                        onValueChange = { regInput = it },
                        label = { Text("Registration Number") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = deptInput,
                        onValueChange = { deptInput = it },
                        label = { Text("Department") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = progInput,
                        onValueChange = { progInput = it },
                        label = { Text("Programme") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (nameInput.isNotBlank() && emailInput.isNotBlank()) {
                            adminViewModel.addStudent(
                                fullName = nameInput,
                                email = emailInput,
                                regNo = regInput,
                                dept = deptInput,
                                prog = progInput,
                                password = passwordInput.ifBlank { "student123" }
                            )
                            val addedName = nameInput
                            showAddDialog = false
                            nameInput = ""
                            emailInput = ""
                            passwordInput = "student123"
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Registered student account: $addedName")
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = UmmaGreenPrimary)
                ) {
                    Text("REGISTER STUDENT")
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
