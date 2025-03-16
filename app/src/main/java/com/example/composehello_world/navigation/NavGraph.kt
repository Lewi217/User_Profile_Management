package com.example.composehello_world.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composehello_world.model.UserProfileViewModel
import com.example.composehello_world.ui.screens.HomeScreen
import com.example.composehello_world.ui.screens.ProfileDisplayScreen
import com.example.composehello_world.ui.screens.ProfileFormScreen

sealed class Screen(val route: String, val icon: ImageVector) {
    object Home : Screen("home", Icons.Filled.Home)
    object ProfileForm : Screen("profile_form", Icons.Filled.Person)
    object ProfileDisplay : Screen("profile_display", Icons.Filled.Person)
}

@Composable
fun NavGraph(navController: NavHostController = rememberNavController()) {
    val sharedViewModel: UserProfileViewModel = viewModel()
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(Screen.ProfileForm.route) {
            ProfileFormScreen(navController = navController, viewModel = sharedViewModel)
        }
        composable(Screen.ProfileDisplay.route) {
            ProfileDisplayScreen(navController = navController, viewModel = sharedViewModel)
        }
    }
}
