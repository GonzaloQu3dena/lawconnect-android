package com.qu3dena.lawconnect.android.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.qu3dena.lawconnect.android.auth.presentation.navigation.authNavGraph
import com.qu3dena.lawconnect.android.auth.presentation.ui.viewmodel.AuthViewModel
import com.qu3dena.lawconnect.android.clients.presentation.navigation.clientsNavGraph
import com.qu3dena.lawconnect.android.home.presentation.navigation.homeNavGraph
import com.qu3dena.lawconnect.android.profile.presentation.navigation.profileNavGraph

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    padding: PaddingValues,
    isLoggedIn: Boolean,
    authViewModel: AuthViewModel
) {
    NavHost(
        route = Graph.Root.route,
        navController = navController,
        startDestination = if (isLoggedIn) Graph.Home.route else Graph.Auth.route
    ) {
        authNavGraph(
            route = Graph.Auth.route,
            navController = navController,
            onLoginSuccess = {
                navController.navigate(Graph.Home.route) {
                    popUpTo(Graph.Auth.route) { inclusive = true }
                    launchSingleTop = true
                }
            }
        )

        homeNavGraph(
            route = Graph.Home.route,
            navController = navController
        )

        clientsNavGraph(
            route = Graph.Clients.route,
            navController = navController
        )

        profileNavGraph(
            route = Graph.Profile.route,
            navController = navController,
            onSignOut = {
                authViewModel.signOut()
                navController.navigate(Graph.Auth.route) {
                    popUpTo(Graph.Root.route) { inclusive = true }
                    launchSingleTop = true
                }
            }
        )
    }
}