package com.danp.werisjc.ui.screens.post

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.danp.werisjc.domain.model.Post
import com.danp.werisjc.domain.model.Service
import com.danp.werisjc.ui.theme.Celeste
import com.danp.werisjc.ui.screens.service.ServiceViewModel
import com.google.maps.android.compose.*

import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng

@Composable
fun PostDetailScreen(
    postId: String,
    viewModel: PostViewModel = hiltViewModel(),
    serviceViewModel: ServiceViewModel = hiltViewModel()
) {
    var post by remember { mutableStateOf<Post?>(null) }
    var service by remember { mutableStateOf<Service?>(null) }

    LaunchedEffect(postId) {
        post = viewModel.getPostById(postId)
        service = post?.serviceId?.let { serviceViewModel.getServiceById(it) }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Header
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

        if (post != null) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Imagen con label sobrepuesto
                Box(modifier = Modifier.fillMaxWidth()) {
                    AsyncImage(
                        model = post!!.img,
                        contentDescription = "Post Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                    Text(
                        text = post!!.label,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .background(Color.Black.copy(alpha = 0.5f))
                            .padding(8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(post!!.message, fontSize = 16.sp)

                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.AccessTime, contentDescription = "Time", tint = Color.Gray)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = formatDateTime(post!!.datetime),
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Divider()
                Spacer(modifier = Modifier.height(16.dp))

                service?.let { s ->

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(s.name, fontWeight = FontWeight.Medium)

                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Dirección: ${s.address}")

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
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Cargando post...")
            }
        }
    }
}
