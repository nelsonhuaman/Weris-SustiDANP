package com.danp.werisjc.ui.screens.service

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.danp.werisjc.domain.model.Service
import com.danp.werisjc.domain.model.Post
import com.danp.werisjc.ui.theme.Celeste
import com.google.maps.android.compose.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.danp.werisjc.ui.navigation.NavigationRoutes
import com.danp.werisjc.ui.screens.post.PostViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.CameraPosition

@Composable
fun ServiceDetailScreen(
    serviceId: String,
    navController: NavController, // 👈 Agregado
    serviceViewModel: ServiceViewModel = hiltViewModel(),
    postViewModel: PostViewModel = hiltViewModel()
){
    var service by remember { mutableStateOf<Service?>(null) }
    var posts by remember { mutableStateOf<List<Post>>(emptyList()) }

    LaunchedEffect(serviceId) {
        service = serviceViewModel.getServiceById(serviceId)
        posts = postViewModel.getPostsByServiceId(serviceId)
        Log.d("ServiceDetailScreen", "Cargados ${posts.size} posts para el servicio $serviceId")
    }

    Column(modifier = Modifier.fillMaxSize()) {

        // Encabezado fijo
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

        service?.let { s ->

            // Imagen destacada arriba
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(s.img)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Imagen del servicio",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter) // Centrado horizontal, abajo
                        .padding(bottom = 16.dp) // Separación desde abajo
                        .background(
                            color = Celeste, // Azul con transparencia
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = s.name,
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // LazyColumn con resto del contenido
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 10.dp), // padding lateral + vertical solo abajo
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text(text = s.description)
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccessTime,
                            contentDescription = "Horario",
                            tint = Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = s.timetable)
                    }

                    Text(text = "Dirección: ${s.address}")

                    Button(
                        onClick = {
                            s.url.let { url ->
                                val encodedName = Uri.encode(s.name)
                                val encodedUrl = Uri.encode(url)
                                navController.navigate("${NavigationRoutes.SERVICE_WEB}/$encodedName/$encodedUrl")
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        Text("Visitar página web")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    if (s.latitude != null && s.longitude != null) {
                        val latLng = LatLng(s.latitude, s.longitude)
                        val cameraPositionState = rememberCameraPositionState {
                            position = CameraPosition.fromLatLngZoom(latLng, 15f)
                        }
                        val markerState = rememberMarkerState(position = latLng)

                        GoogleMap(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            cameraPositionState = cameraPositionState
                        ) {
                            Marker(
                                state = markerState,
                                title = s.name
                            )
                        }
                    } else {
                        Text("Ubicación no disponible", color = Color.Gray)
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Publicaciones relacionadas", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }

                if (posts.isNotEmpty()) {
                    items(posts) { post ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFE6F0F3))
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(text = post.label, fontWeight = FontWeight.Bold)
                                Text(text = post.message)
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current).data(post.img).crossfade(true).build(),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(180.dp),
                                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                                )
                                Text(
                                    text = "Fecha: ${post.datetime.take(10).replace("-", "/")} - ${post.datetime.takeLast(8)}",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                } else {
                    item {
                        Text("No hay publicaciones relacionadas.", color = Color.Gray)
                    }
                }
            }
        } ?: run {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Cargando detalles del servicio...")
            }
        }
    }
}
