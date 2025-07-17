package com.danp.werisjc.ui.screens.service

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import com.danp.werisjc.ui.theme.Celeste

@Composable
fun ServiceScreen(
    navController: NavController,
    viewModel: ServiceViewModel = hiltViewModel()
) {
    val query by viewModel.searchQuery.collectAsState()
    val services by viewModel.filteredServices.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Celeste)
                .padding(vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Weris",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        // Contenido con padding interno
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 10.dp)) {


            OutlinedTextField(
                value = query,
                onValueChange = { viewModel.search(it) },
                label = { Text("Buscar servicio...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar"
                    )
                },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(services.size) { index ->
                    val service = services[index]
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate("service_Detail/${service.id}")
                            }
                            .padding(12.dp)
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(service.img)
                                .crossfade(true)
                                .build(),
                            contentDescription = "Service Image",
                            modifier = Modifier
                                .width(100.dp)
                                .height(72.dp)
                                .padding(end = 12.dp),
                            contentScale = ContentScale.Crop
                        )
                        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                            Text(
                                text = service.name,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = service.address,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray
                            )
                            Text(
                                text = "Horario: ${service.timetable}",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }
                    }
                    Divider()
                }
            }
        }
    }
}