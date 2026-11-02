package com.example.ummauniversity.ui.screens.student

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ummauniversity.ui.components.StudentBottomNavBar
import com.example.ummauniversity.ui.components.UmmaTopBar
import com.example.ummauniversity.ui.theme.UmmaGreenPrimary

@Composable
fun CampusServicesScreen(
    onNavigateToRoute: (String) -> Unit
) {
    Scaffold(
        topBar = {
            UmmaTopBar(
                title = "Campus Life & Services",
                subtitle = "Facilities, Hostels, Transport & Support",
                onBackClick = { onNavigateToRoute("student_home") }
            )
        },
        bottomBar = {
            StudentBottomNavBar(
                selectedRoute = "student_services",
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
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ServiceCard(
                title = "Interactive Campus Map & Buildings",
                subtitle = "Main Gate, Administration Complex, Library, Science & Tech Labs, Justice Hall.",
                icon = Icons.Default.Map,
                tint = UmmaGreenPrimary
            )

            ServiceCard(
                title = "Hostels & Accommodation",
                subtitle = "On-campus male and female hostels, room allocations, curfew rules, and hostel maintenance.",
                icon = Icons.Default.Hotel,
                tint = Color(0xFF1565C0)
            )

            ServiceCard(
                title = "Cafeteria Menu & Dining Hours",
                subtitle = "Breakfast: 07:00 AM - 09:00 AM | Lunch: 12:00 PM - 02:30 PM | Dinner: 06:30 PM - 08:30 PM. Halal certified meals.",
                icon = Icons.Default.Restaurant,
                tint = Color(0xFFE65100)
            )

            ServiceCard(
                title = "University Transport Schedules",
                subtitle = "Campus shuttles run hourly between Kajiado Town, Kitengela, and Main Campus.",
                icon = Icons.Default.DirectionsBus,
                tint = Color(0xFF6A1B9A)
            )

            ServiceCard(
                title = "Sports, Clubs & Societies",
                subtitle = "Football, Basketball, Volleyball pitch, Martial Arts, Tech Club, Quran & Islamic Studies Society.",
                icon = Icons.Default.SportsFootball,
                tint = Color(0xFF2E7D32)
            )

            ServiceCard(
                title = "Student Health Services & Medical Clinic",
                subtitle = "24/7 Outpatient care, emergency ambulance service, consultation, and pharmacy.",
                icon = Icons.Default.LocalHospital,
                tint = Color(0xFFC62828)
            )

            ServiceCard(
                title = "Counselling & Mental Wellness",
                subtitle = "Confidential academic, career, and personal psychological support sessions.",
                icon = Icons.Default.Psychology,
                tint = Color(0xFF00838F)
            )

            ServiceCard(
                title = "Disability & Special Needs Support",
                subtitle = "Ramp accessibility, braille resources, exam accommodations, and dedicated assistive support.",
                icon = Icons.Default.Accessible,
                tint = Color(0xFF4E342E)
            )
        }
    }
}

@Composable
fun ServiceCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    tint: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = tint.copy(alpha = 0.15f),
                modifier = Modifier.size(48.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(imageVector = icon, contentDescription = null, tint = tint, modifier = Modifier.size(26.dp))
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
