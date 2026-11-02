package com.example.ummauniversity.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ummauniversity.data.entities.HelpDeskRequestEntity
import com.example.ummauniversity.ui.components.StatusBadge
import com.example.ummauniversity.ui.components.UmmaTopBar
import com.example.ummauniversity.ui.theme.UmmaGreenPrimary
import com.example.ummauniversity.viewmodel.AdminViewModel

@Composable
fun AdminManageHelpDeskScreen(
    adminViewModel: AdminViewModel,
    onNavigateBack: () -> Unit
) {
    val requests by adminViewModel.helpDeskRequests.collectAsState()

    var showResponseDialog by remember { mutableStateOf(false) }
    var selectedRequest by remember { mutableStateOf<HelpDeskRequestEntity?>(null) }
    var selectedStatus by remember { mutableStateOf("Resolved") }
    var responseInput by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            UmmaTopBar(
                title = "Help Desk Operations",
                subtitle = "Student Support & Ticket Resolution",
                onBackClick = onNavigateBack
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        ) {
            Text("Student Tickets (${requests.size})", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(requests) { req ->
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
                                Text(text = "${req.requestId} • ${req.studentName}", fontWeight = FontWeight.Bold, color = UmmaGreenPrimary)
                                StatusBadge(status = req.status)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "Subject: ${req.subject}", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
                            Text(text = req.description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)

                            if (req.response.isNotBlank()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(text = "Admin Response: ${req.response}", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Button(
                                onClick = {
                                    selectedRequest = req
                                    responseInput = req.response
                                    selectedStatus = if (req.status == "Pending") "In Progress" else "Resolved"
                                    showResponseDialog = true
                                },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = UmmaGreenPrimary)
                            ) {
                                Text("RESPOND & UPDATE STATUS")
                            }
                        }
                    }
                }
            }
        }
    }

    if (showResponseDialog && selectedRequest != null) {
        val req = selectedRequest!!
        AlertDialog(
            onDismissRequest = { showResponseDialog = false },
            title = { Text("Update Support Ticket ${req.requestId}") },
            text = {
                Column {
                    Text("Select New Status:", style = MaterialTheme.typography.labelSmall)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        FilterChip(
                            selected = selectedStatus == "In Progress",
                            onClick = { selectedStatus = "In Progress" },
                            label = { Text("In Progress") }
                        )
                        FilterChip(
                            selected = selectedStatus == "Resolved",
                            onClick = { selectedStatus = "Resolved" },
                            label = { Text("Resolved") }
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = responseInput,
                        onValueChange = { responseInput = it },
                        label = { Text("Official Response") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        adminViewModel.updateHelpDeskRequest(
                            id = req.id,
                            status = selectedStatus,
                            response = responseInput
                        )
                        showResponseDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = UmmaGreenPrimary)
                ) {
                    Text("SAVE RESPONSE")
                }
            },
            dismissButton = {
                TextButton(onClick = { showResponseDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
