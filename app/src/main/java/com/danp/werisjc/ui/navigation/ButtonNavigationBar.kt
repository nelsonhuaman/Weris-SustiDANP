package com.danp.werisjc.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.danp.werisjc.ui.theme.Celeste

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem("Posts", NavigationRoutes.POSTS, Icons.Default.List),
        BottomNavItem("Map", NavigationRoutes.MAP, Icons.Default.Place),
        BottomNavItem("Services", NavigationRoutes.SERVICES, Icons.Default.Build),
        BottomNavItem("Profile", NavigationRoutes.PROFILE, Icons.Default.Person),
    )

    NavigationBar(
        containerColor = Celeste,  // 🔹 Color de fondo de la barra
        contentColor = Color.White // 🔹 Color de los íconos/texto
    ) {
        val navBackStackEntry = navController.currentBackStackEntryAsState().value
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(0) // 🧹 Limpia el backstack hasta el inicio
                            launchSingleTop = true
                        }
                    }
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    selectedTextColor = Color.White,
                    indicatorColor = Celeste,
                    unselectedIconColor = Color.White.copy(alpha = 0.7f),
                    unselectedTextColor = Color.White.copy(alpha = 0.7f)
                )
            )
        }
    }
}

data class BottomNavItem(
    val label: String,
    val route: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)
