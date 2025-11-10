package com.sisecam.app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
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
fun AttendanceScreen(onNavigateBack: () -> Unit) {
    var selectedOption by remember { mutableStateOf<Boolean?>(null) }
    var showConfirmation by remember { mutableStateOf(false) }

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
                        Text("Yoklama") }
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Bugün servise binecek misiniz?",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                fontSize=20.sp
            )
            Spacer(modifier = Modifier.height(48.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AttendanceButton(
                    text = "Geleceğim",
                    icon = Icons.Filled.CheckCircle,
                    color = Color(0xFF4CAF50),
                    isSelected = selectedOption == true,
                    modifier = Modifier.weight(1.1f),
                    onClick = {
                        selectedOption = true
                        showConfirmation = true
                    }
                )

                AttendanceButton(
                    text = "Gelmeyeceğim",
                    icon = Icons.Filled.Cancel,
                    color = Color(0xFFF44336),
                    isSelected = selectedOption == false,
                    modifier = Modifier.weight(1.2f),
                    onClick = {
                        selectedOption = false
                        showConfirmation = true
                    }
                )
            }

            if (showConfirmation) {
                Spacer(modifier = Modifier.height(24.dp))
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = if (selectedOption == true)
                            Color(0xFF4CAF50).copy(alpha = 0.1f)
                        else
                            Color(0xFFF44336).copy(alpha = 0.1f)
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = null,
                            tint = if (selectedOption == true) Color(0xFF4CAF50) else Color(0xFFF44336)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = if (selectedOption == true)
                                "Yoklamanız alındı. Güvenli yolculuklar!"
                            else
                                "Yoklamanız alındı. İyi günler!",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AttendanceButton(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    isSelected: Boolean,
    modifier: Modifier = Modifier,

    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(120.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) color else color.copy(alpha = 0.3f)
        ),
        shape = RoundedCornerShape(15.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}