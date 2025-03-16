package com.example.composehello_world.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.composehello_world.model.UserProfileViewModel
import com.example.composehello_world.navigation.Screen
import com.example.composehello_world.ui.components.ProfileCard
import kotlinx.coroutines.launch

@Composable
fun ProfileDisplayScreen(
    navController: NavHostController,
    viewModel: UserProfileViewModel = viewModel()
) {
    val userProfiles = viewModel.userProfiles
    val extraTopPadding = 8.dp
    val extraBottomPadding = 8.dp

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text("Profile Display") },
                modifier = Modifier.height(56.dp)
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screen.ProfileForm.route)
            }) {
                Icon(imageVector = Icons.Filled.Star, contentDescription = "Add/Edit Profile")
            }
        }
    ) { innerPadding ->
        if (userProfiles.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(top = extraTopPadding, bottom = extraBottomPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("No profiles submitted.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(top = extraTopPadding, bottom = extraBottomPadding),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(userProfiles) { user ->
                    var isFavorite by remember { mutableStateOf(user.isFavorite) }
                    var showDialog by remember { mutableStateOf(false) }
                    ProfileCard(
                        userProfile = user,
                        isFavorite = isFavorite,
                        onFavoriteToggle = { isFavorite = !isFavorite },
                        onCardClick = { showDialog = true }
                    )
                    if (showDialog) {
                        AlertDialog(
                            onDismissRequest = { showDialog = false },
                            title = { Text("Profile Options") },
                            text = { Text("Choose an action") },
                            confirmButton = {
                                TextButton(onClick = {
                                    showDialog = false
                                    navController.navigate(Screen.ProfileForm.route)
                                }) {
                                    Text("Edit")
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = {
                                    showDialog = false
                                    coroutineScope.launch {
                                        scaffoldState.snackbarHostState
                                            .showSnackbar("Profile deleted successfully!")
                                    }
                                }) {
                                    Text("Delete")
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
