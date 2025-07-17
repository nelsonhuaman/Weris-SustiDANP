package com.danp.werisjc.ui.screens.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import com.danp.werisjc.ui.theme.Celeste

@Composable
fun MapScreen(viewModel: MapViewModel = hiltViewModel()) {
    val services by viewModel.services.collectAsState()
    val selectedService by viewModel.selectedService.collectAsState()

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(-16.3989, -71.5350), // Arequipa
            13f
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {

        // 🔵 Barra superior
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

        // 🔵 Contenido principal con el mapa
        Box(modifier = Modifier.fillMaxSize()) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                uiSettings = MapUiSettings(
                    zoomControlsEnabled = true,
                    myLocationButtonEnabled = true
                ),
                properties = MapProperties(isMyLocationEnabled = true)
            ) {
                services.forEach { service ->
                    Marker(
                        state = MarkerState(position = LatLng(service.latitude, service.longitude)),
                        title = service.name,
                        snippet = service.address,
                        onClick = {
                            viewModel.selectService(service)
                            false
                        }
                    )
                }
            }

            selectedService?.let { service ->
                Card(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            AsyncImage(
                                model = service.img,
                                contentDescription = "Imagen del servicio",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp),
                                contentScale = ContentScale.Crop
                            )

                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Cerrar",
                                tint = Color.White,
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(8.dp)
                                    .size(24.dp)
                                    .clickable {
                                        viewModel.selectService(null)
                                    }
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = service.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "Ubicación",
                                tint = Color.Gray,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = service.address,
                                fontSize = 14.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Schedule,
                                contentDescription = "Horario",
                                tint = Color.Gray,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = service.timetable,
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}
