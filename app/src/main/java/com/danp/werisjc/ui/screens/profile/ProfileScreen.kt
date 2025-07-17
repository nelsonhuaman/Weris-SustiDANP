package com.danp.werisjc.ui.screens.profile

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.danp.werisjc.ui.theme.Celeste
import java.io.File

enum class ProfileScreenState {
    MAIN, CAMERA, GALLERY
}

@Composable
fun ProfileScreen() {
    var currentScreen by remember { mutableStateOf(ProfileScreenState.MAIN) }
    var savedPhotos by remember { mutableStateOf<List<File>>(emptyList()) }
    val context = LocalContext.current
    
    LaunchedEffect(Unit) {
        savedPhotos = loadSavedPhotos(context)
    }
    
    when (currentScreen) {
        ProfileScreenState.MAIN -> {
            MainProfileScreen(
                onCameraClick = { currentScreen = ProfileScreenState.CAMERA },
                onGalleryClick = { currentScreen = ProfileScreenState.GALLERY },
                photoCount = savedPhotos.size
            )
        }
        ProfileScreenState.CAMERA -> {
            CameraScreen(
                onBackClick = { currentScreen = ProfileScreenState.MAIN },
                onPhotoTaken = { photoFile ->
                    savedPhotos = savedPhotos + photoFile
                    currentScreen = ProfileScreenState.MAIN
                }
            )
        }
        ProfileScreenState.GALLERY -> {
            GalleryScreen(
                photos = savedPhotos,
                onBackClick = { currentScreen = ProfileScreenState.MAIN },
                onPhotoDeleted = { deletedPhoto ->
                    deletedPhoto.delete()
                    savedPhotos = savedPhotos.filter { it != deletedPhoto }
                }
            )
        }
    }
}

@Composable
fun MainProfileScreen(
    onCameraClick: () -> Unit,
    onGalleryClick: () -> Unit,
    photoCount: Int
) {
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
                text = "Perfil de Usuario",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Información del usuario
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Información del Usuario",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Nombre: Usuario Demo")
                    Text("Email: usuario@demo.com")
                    Text("Fotos guardadas: $photoCount")
                }
            }
            
            // Botones de acción
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Botón Cámara
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onCameraClick() },
                    colors = CardDefaults.cardColors(containerColor = Celeste)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = "Cámara",
                            tint = Color.White,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Tomar Foto",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                
                // Botón Galería
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onGalleryClick() },
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF4CAF50))
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.PhotoLibrary,
                            contentDescription = "Galería",
                            tint = Color.White,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Ver Galería",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "($photoCount fotos)",
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}

private fun getOutputDirectory(context: Context): File {
    val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
        File(it, "Weris").apply { mkdirs() }
    }
    return if (mediaDir != null && mediaDir.exists()) mediaDir else context.filesDir
}

private fun loadSavedPhotos(context: Context): List<File> {
    val outputDirectory = getOutputDirectory(context)
    return outputDirectory.listFiles()?.filter { 
        it.isFile && it.extension.lowercase() == "jpg" 
    }?.sortedByDescending { it.lastModified() } ?: emptyList()
}