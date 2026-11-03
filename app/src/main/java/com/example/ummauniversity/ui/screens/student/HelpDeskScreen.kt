package com.example.ummauniversity.ui.screens.student

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ummauniversity.data.entities.HelpDeskRequestEntity
import com.example.ummauniversity.data.entities.UserEntity
import com.example.ummauniversity.ui.components.EmptyStateView
import com.example.ummauniversity.ui.components.StatusBadge
import com.example.ummauniversity.ui.components.StudentBottomNavBar
import com.example.ummauniversity.ui.components.UmmaTopBar
import com.example.ummauniversity.ui.theme.UmmaGreenPrimary
import com.example.ummauniversity.viewmodel.StudentViewModel

@Composable
fun HelpDeskScreen(
    currentUser: UserEntity?,
    studentViewModel: StudentViewModel,
    onNavigateToRoute: (String) -> Unit
) {
    val requests by studentViewModel.helpDeskRequests.collectAsState()

    var showSubmitDialog by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf("Academic Inquiry") }
    var subjectInput by remember { mutableStateOf("") }
    var descriptionInput by remember { mutableStateOf("") }

    val categories = listOf("Academic Inquiry", "Finance & Fee", "Hostel & Housing", "IT Portal Support", "General Complaint")

    Scaffold(
        topBar = {
            UmmaTopBar(
                title = "Help Desk & Support",
                subtitle = "Submit Complaints & Requests",
                onBackClick = { onNavigateToRoute("student_home") }
            )
        },
        bottomBar = {
            StudentBottomNavBar(
                selectedRoute = "student_services",
                onRouteSelected = onNavigateToRoute
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showSubmitDialog = true },
                containerColor = UmmaGreenPrimary
            ) {
                Icon(Icons.Default.Add, contentDescription = "New Ticket", tint = MaterialTheme.colorScheme.onPrimary)
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
            Text(
                text = "My Support Tickets & Requests",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (requests.isEmpty()) {
                EmptyStateView(
                    message = "You have not submitted any help desk requests yet.",
                    icon = Icons.Default.SupportAgent
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(bottom = 72.dp)
                ) {
                    items(requests) { req ->
                        HelpDeskTicketCard(request = req)
                    }
                }
            }
        }
    }

    if (showSubmitDialog) {
        AlertDialog(
            onDismissRequest = { showSubmitDialog = false },
            title = { Text("Submit Help Desk Request") },
            text = {
                Column {
                    Text("Select Category:", style = MaterialTheme.typography.labelSmall)
                    Spacer(modifier = Modifier.height(4.dp))

                    var expanded by remember { mutableStateOf(false) }
                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedButton(
                            onClick = { expanded = true },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(selectedCategory)
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            categories.forEach { cat ->
                                DropdownMenuItem(
                                    text = { Text(cat) },
                                    onClick = {
                                        selectedCategory = cat
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = subjectInput,
                        onValueChange = { subjectInput = it },
                        label = { Text("Subject") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = descriptionInput,
                        onValueChange = { descriptionInput = it },
                        label = { Text("Detailed Description") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (subjectInput.isNotBlank() && descriptionInput.isNotBlank()) {
                            studentViewModel.submitHelpDeskRequest(
                                studentName = currentUser?.fullName ?: "Asiyah Abdullahi",
                                category = selectedCategory,
                                subject = subjectInput,
                                description = descriptionInput
                            )
                            showSubmitDialog = false
                            subjectInput = ""
                            descriptionInput = ""
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = UmmaGreenPrimary)
                ) {
                    Text("SUBMIT TICKET")
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
fun HelpDeskTicketCard(request: HelpDeskRequestEntity) {
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
                Text(
                    text = "${request.requestId} • ${request.category}",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = UmmaGreenPrimary
                )
                StatusBadge(status = request.status)
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = request.subject,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = request.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            if (request.response.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = "Admin Response:",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = request.response,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Submitted on: ${request.dateSubmitted}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}
