package com.danp.werisjc.ui.screens.post

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.danp.werisjc.ui.theme.Celeste
import kotlinx.coroutines.flow.collectLatest
import java.text.SimpleDateFormat
import java.util.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun PostScreen(
    navController: NavController,
    viewModel: PostViewModel = hiltViewModel()
) {
    val posts by viewModel.visiblePosts.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val listState = rememberLazyListState()
    var query by remember { mutableStateOf("") }

    // Control de la barra de estado
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = true // Usa íconos oscuros si el fondo es claro
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Celeste,
            darkIcons = useDarkIcons
        )
    }

    // Buscar según el query
    LaunchedEffect(query) {
        viewModel.search(query)
    }

    // Cargar más al llegar al final
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collectLatest { index ->
                if (index == posts.lastIndex && !isLoading) {
                    viewModel.loadNextPage()
                }
            }
    }

    Column(modifier = Modifier.fillMaxSize()) {

        // Título
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = {
                    query = it
                    viewModel.search(it)
                },
                label = { Text("Buscar post...") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Buscar")
                },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(posts) { _, post ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate("post_Detail/${post.id}")
                            }
                            .padding(12.dp)
                    ) {
                        AsyncImage(
                            model = post.img,
                            contentDescription = post.label,
                            modifier = Modifier
                                .width(100.dp)
                                .height(72.dp)
                                .padding(end = 12.dp),
                            contentScale = ContentScale.Crop
                        )

                        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                            Text(
                                text = post.label,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = post.message,
                                style = MaterialTheme.typography.bodyMedium,
                                maxLines = 2
                            )
                            Text(
                                text = formatDateTime(post.datetime),
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }
                    }
                    Divider()
                }

                if (isLoading) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}

fun formatDateTime(datetime: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")
        val date: Date = inputFormat.parse(datetime)!!
        val outputFormat = SimpleDateFormat("MM/dd - HH:mm", Locale.getDefault())
        outputFormat.format(date)
    } catch (e: Exception) {
        datetime
    }
}
