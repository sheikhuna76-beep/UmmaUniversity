package com.example.ummauniversity.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
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
fun AdminManageLecturersScreen(
    adminViewModel: AdminViewModel,
    onNavigateBack: () -> Unit
) {
    val lecturers by adminViewModel.lecturers.collectAsState()

    var showAddDialog by remember { mutableStateOf(false) }
    var nameInput by remember { mutableStateOf("") }
    var emailInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("lecturer123") }
    var staffIdInput by remember { mutableStateOf("LEC/CS/") }
    var deptInput by remember { mutableStateOf("Computer Science") }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            UmmaTopBar(
                title = "Manage Lecturer Staff",
                subtitle = "Faculty Academic Staff Register",
                onBackClick = onNavigateBack
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = UmmaGreenPrimary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Lecturer", tint = Color.White)
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
            Text("Faculty Lecturer Directory (${lecturers.size})", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(10.dp))

            if (lecturers.isEmpty()) {
                EmptyStateView(
                    message = "No lecturer accounts found.",
                    icon = Icons.Default.Person
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(bottom = 72.dp)
                ) {
                    items(lecturers) { lecturer ->
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
                                    Text(text = lecturer.fullName, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyLarge)
                                    Text(text = "Staff ID: ${lecturer.regOrStaffNumber}", style = MaterialTheme.typography.bodySmall, color = UmmaGreenPrimary, fontWeight = FontWeight.Bold)
                                    Text(text = "Department: ${lecturer.department}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Text(text = lecturer.email, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline)
                                }

                                IconButton(
                                    onClick = { adminViewModel.deleteUser(lecturer.id) }
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
            title = { Text("Register Lecturer Staff") },
            text = {
                Column {
                    OutlinedTextField(
                        value = nameInput,
                        onValueChange = { nameInput = it },
                        label = { Text("Full Name (e.g. Dr. Omar Farooq)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = emailInput,
                        onValueChange = { emailInput = it },
                        label = { Text("Official Email") },
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
                        value = staffIdInput,
                        onValueChange = { staffIdInput = it },
                        label = { Text("Staff ID") },
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
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (nameInput.isNotBlank() && emailInput.isNotBlank()) {
                            adminViewModel.addLecturer(
                                fullName = nameInput,
                                email = emailInput,
                                staffId = staffIdInput,
                                dept = deptInput,
                                password = passwordInput.ifBlank { "lecturer123" }
                            )
                            val addedName = nameInput
                            showAddDialog = false
                            nameInput = ""
                            emailInput = ""
                            passwordInput = "lecturer123"
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Registered lecturer staff account: $addedName")
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = UmmaGreenPrimary)
                ) {
                    Text("ADD LECTURER")
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
