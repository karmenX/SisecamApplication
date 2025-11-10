package com.sisecam.app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
fun ScheduleScreen(onNavigateBack: () -> Unit) {
    var scheduleItems by remember {
        mutableStateOf(
            listOf(
                ScheduleItem("Monday", "08:00", "17:30", "Home", AddressColor.BLUE, true),
                ScheduleItem("Tuesday", "08:00", "17:30", "Home", AddressColor.BLUE, true),
                ScheduleItem("Wednesday", "09:00", "18:00", "Office", AddressColor.GREEN, true),
                ScheduleItem("Thursday", "08:00", "17:30", "Home", AddressColor.BLUE, true),
                ScheduleItem("Friday", "08:00", "16:00", "Home", AddressColor.BLUE, true),
                ScheduleItem("Saturday", "-", "-", "", AddressColor.GRAY, false),
                ScheduleItem("Sunday", "-", "-", "", AddressColor.GRAY, false)
            )
        )
    }

    var showEditDialog by remember { mutableStateOf(false) }
    var selectedDayIndex by remember { mutableStateOf(-1) }
    val addresses = listOf("Home", "Office", "Secondary Office")
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.company_logo),
                            contentDescription = "Logo",
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Weekly Schedule")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
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
                .background(Color(0xFFF5F5F5))
                .padding(paddingValues)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Address Legend",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        LegendItem(color = AddressColor.BLUE.color, label = "Home")
                        LegendItem(color = AddressColor.GREEN.color, label = "Office")
                        LegendItem(color = AddressColor.ORANGE.color, label = "Other")
                    }
                }
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(scheduleItems.size) { index ->
                    val item = scheduleItems[index]
                    ScheduleCard(
                        item = item,
                        onClick = {
                            selectedDayIndex = index
                            showEditDialog = true
                        }
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
    if (showEditDialog && selectedDayIndex >= 0) {
        val currentItem = scheduleItems[selectedDayIndex]

        var departureTime by remember { mutableStateOf(currentItem.departureTime) }
        var returnTime by remember { mutableStateOf(currentItem.returnTime) }
        var selectedAddress by remember { mutableStateOf(currentItem.address) }
        var isActive by remember { mutableStateOf(currentItem.isActive) }

        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            title = { Text("Edit ${currentItem.day}") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Active")
                        Switch(
                            checked = isActive,
                            onCheckedChange = { isActive = it }
                        )
                    }
                    if (isActive) {
                        Text("Pickup Location", fontWeight = FontWeight.Bold)
                        addresses.forEach { address ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { selectedAddress = address }
                                    .padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = selectedAddress == address,
                                    onClick = { selectedAddress = address }
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(address)
                            }
                        }
                        OutlinedTextField(
                            value = departureTime,
                            onValueChange = { departureTime = it },
                            label = { Text("Departure Time") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = returnTime,
                            onValueChange = { returnTime = it },
                            label = { Text("Return Time") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    val addressColor = when (selectedAddress) {
                        "Home" -> AddressColor.BLUE
                        "Office" -> AddressColor.GREEN
                        else -> AddressColor.ORANGE
                    }

                    scheduleItems = scheduleItems.toMutableList().apply {
                        this[selectedDayIndex] = ScheduleItem(
                            day = currentItem.day,
                            departureTime = if (isActive) departureTime else "-",
                            returnTime = if (isActive) returnTime else "-",
                            address = if (isActive) selectedAddress else "",
                            addressColor = if (isActive) addressColor else AddressColor.GRAY,
                            isActive = isActive
                        )
                    }
                    showEditDialog = false
                }) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
@Composable
fun ScheduleCard(
    item: ScheduleItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(60.dp)
                    .background(
                        color = item.addressColor.color,
                        shape = RoundedCornerShape(2.dp)
                    )
            )

            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.day,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                if (item.isActive) {
                    Spacer(modifier = Modifier.height(4.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Icon(
                            imageVector = Icons.Filled.LocationOn,
                            contentDescription = null,
                            tint = item.addressColor.color,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = item.address,
                            style = MaterialTheme.typography.bodySmall,
                            color = item.addressColor.color,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Departure: ${item.departureTime} | Return: ${item.returnTime}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        fontSize = 13.sp
                    )
                } else {
                    Text(
                        text = "No service",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }
            Icon(
                imageVector = if (item.isActive) Icons.Filled.CheckCircle else Icons.Filled.Cancel,
                contentDescription = null,
                tint = if (item.isActive) Color(0xFF4CAF50) else Color.Gray,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}
@Composable
fun LegendItem(color: Color, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(color, RoundedCornerShape(4.dp))
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            fontSize = 12.sp
        )
    }
}
data class ScheduleItem(
    val day: String,
    val departureTime: String,
    val returnTime: String,
    val address: String,
    val addressColor: AddressColor,
    val isActive: Boolean
)
enum class AddressColor(val color: Color) {
    BLUE(Color(0xFF2196F3)),
    GREEN(Color(0xFF4CAF50)),
    ORANGE(Color(0xFFFF9800)),
    GRAY(Color(0xFF9E9E9E))
}