package com.example.ummauniversity.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ummauniversity.ui.components.UmmaTopBar
import com.example.ummauniversity.ui.theme.UmmaGoldAccent
import com.example.ummauniversity.ui.theme.UmmaGreenDark
import com.example.ummauniversity.ui.theme.UmmaGreenPrimary
import com.example.ummauniversity.viewmodel.AdminViewModel

@Composable
fun AdminManageFeesScreen(
    adminViewModel: AdminViewModel,
    onNavigateBack: () -> Unit
) {
    val totalCollectedFees by adminViewModel.totalCollectedFees.collectAsState()

    Scaffold(
        topBar = {
            UmmaTopBar(
                title = "Fee & Revenue Management",
                subtitle = "Financial Statistics & Revenue Auditing",
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
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = UmmaGreenDark)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Total Collected Tuition Revenue",
                                style = MaterialTheme.typography.bodySmall,
                                color = UmmaGoldAccent
                            )
                            Text(
                                text = "KES ${String.format("%,.0f", totalCollectedFees)}",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.AccountBalanceWallet,
                            contentDescription = null,
                            tint = UmmaGoldAccent,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Standard Fee Structure (Per Semester):", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    FeeAdminLine("Tuition Fee", "KES 50,000")
                    FeeAdminLine("Activity & Sports Fee", "KES 5,000")
                    FeeAdminLine("Examination Fee", "KES 4,000")
                    FeeAdminLine("Library Fee", "KES 3,000")
                    FeeAdminLine("Medical Insurance Fee", "KES 3,000")
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    FeeAdminLine("Total Semester Fee", "KES 65,000", isBold = true)
                }
            }
        }
    }
}

@Composable
fun FeeAdminLine(label: String, value: String, isBold: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium, fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal)
        Text(text = value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = if (isBold) UmmaGreenPrimary else MaterialTheme.colorScheme.onSurface)
    }
}
