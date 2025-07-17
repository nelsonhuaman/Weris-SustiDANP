package com.danp.werisjc.ui.navigation

import android.net.Uri
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.danp.werisjc.ui.screens.post.*
import com.danp.werisjc.ui.screens.login.*
import com.danp.werisjc.ui.screens.service.*
import com.danp.werisjc.ui.screens.map.*
import com.danp.werisjc.ui.screens.profile.*

@Composable
fun AppNavigation(navController: NavHostController) {
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination?.route

    // Mostrar BottomBar en todas las pantallas excepto LOGIN
    val showBottomBar = currentDestination != NavigationRoutes.LOGIN

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavigationRoutes.LOGIN,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(NavigationRoutes.LOGIN) {
                LoginScreen(onLogin = {
                    navController.navigate(NavigationRoutes.POSTS) {
                        popUpTo(NavigationRoutes.LOGIN) { inclusive = true }
                    }
                })
            }

            composable(NavigationRoutes.POSTS) {
                PostScreen(navController = navController)
            }

            composable("${NavigationRoutes.POST_DETAIL}/{postId}") { backStackEntry ->
                val postId = backStackEntry.arguments?.getString("postId")
                postId?.let {
                    PostDetailScreen(postId = it)
                }
            }

            composable(NavigationRoutes.MAP) {
                MapScreen()
            }

            composable(NavigationRoutes.SERVICES) {
                ServiceScreen(navController = navController)
            }

            composable("${NavigationRoutes.SERVICE_DETAIL}/{serviceId}") { backStackEntry ->
                val serviceId = backStackEntry.arguments?.getString("serviceId")
                serviceId?.let {
                    ServiceDetailScreen(serviceId = it, navController = navController) // 👈 Ahora lo recibe
                }
            }

            composable(NavigationRoutes.PROFILE) {
                ProfileScreen()
            }

            composable(
                route = "${NavigationRoutes.SERVICE_WEB}/{serviceName}/{serviceUrl}",
                arguments = listOf(
                    navArgument("serviceName") { type = NavType.StringType },
                    navArgument("serviceUrl") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val serviceName = backStackEntry.arguments?.getString("serviceName") ?: ""
                val serviceUrl = backStackEntry.arguments?.getString("serviceUrl") ?: ""

                ServiceWebScreen(
                    serviceName = serviceName,
                    serviceUrl = Uri.decode(serviceUrl) // 🔥 Decodifica si fue encoded
                )
            }
        }
    }
}
