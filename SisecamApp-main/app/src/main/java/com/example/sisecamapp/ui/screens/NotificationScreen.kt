package com.sisecam.app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sisecam.app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(onNavigateBack: () -> Unit) {
    val notifications = remember {
        listOf(
            NotificationItem(
                title = "Servis Hatırlatıcı",
                message = "Servisiniz yarın sabah 08:00'de yola çıkacak.",
                time = "2 saat önce",
                isRead = false,
                type = NotificationType.SHUTTLE
            ),
            NotificationItem(
                title = "Rota Güncellendi",
                message = "Alınacağınız yer güncellendi.",
                time = "5 saat önce",
                isRead = false,
                type = NotificationType.ROUTE
            ),
            NotificationItem(
                title = "Yoklama Onaylandı",
                message = "Yarın için yoklamanız onaylandı.",
                time = "1 gün önce",
                isRead = true,
                type = NotificationType.ATTENDANCE
            ),
            NotificationItem(
                title = "Rota Güncellendi",
                message = "Haftalık plan güncellendi. Lütfen takviminizi kontrol edin.",
                time = "2 days ago",
                isRead = true,
                type = NotificationType.SCHEDULE
            ),
            NotificationItem(
                title = "Yeni Adres Eklendi",
                message = "Adresiniz başarılı bir şekilde kayıtlı adreslere eklendi.",
                time = "3 gün önce",
                isRead = true,
                type = NotificationType.LOCATION
            )
        )
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.company_logo),
                            contentDescription = "Logo",
                            modifier = Modifier.size(75.dp).padding(top = 15.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Bildirimler")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Geri")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0099D0),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(notifications) { notification ->
                NotificationCard(notification)
            }
        }
    }
}
@Composable
fun NotificationCard(notification: NotificationItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (notification.isRead) Color.White else Color(0xFFE3F2FD)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = notification.type.color.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(24.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = notification.type.icon,
                    contentDescription = null,
                    tint = notification.type.color,
                    modifier = Modifier.size(24.dp)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = notification.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = if (notification.isRead) FontWeight.Normal else FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = notification.message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = notification.time,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }

            if (!notification.isRead) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(
                            color = Color(0xFF0099D0),
                            shape = RoundedCornerShape(4.dp)
                        )
                )
            }
        }
    }
}

data class NotificationItem(
    val title: String,
    val message: String,
    val time: String,
    val isRead: Boolean,
    val type: NotificationType
)

enum class NotificationType(
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val color: Color
) {
    SHUTTLE(Icons.Filled.DirectionsBus, Color(0xFF4CAF50)),
    ROUTE(Icons.Filled.Map, Color(0xFF9C27B0)),
    ATTENDANCE(Icons.Filled.CheckCircle, Color(0xFF2196F3)),
    SCHEDULE(Icons.Filled.CalendarToday, Color(0xFFFF9800)),
    LOCATION(Icons.Filled.LocationOn, Color(0xFFF44336))
}