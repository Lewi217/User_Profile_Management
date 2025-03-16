package com.example.composehello_world.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.composehello_world.model.UserProfile
import com.example.composehello_world.model.UserProfileViewModel
import com.example.composehello_world.navigation.Screen

@Composable
fun ProfileFormScreen(
    navController: NavHostController,
    viewModel: UserProfileViewModel = viewModel()
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("Male") }
    var notificationsEnabled by remember { mutableStateOf(false) }
    var reading by remember { mutableStateOf(false) }
    var traveling by remember { mutableStateOf(false) }
    var coding by remember { mutableStateOf(false) }

    val extraTopPadding = 8.dp
    val extraBottomPadding = 8.dp

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile Form") },
                modifier = Modifier.height(56.dp)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(top = extraTopPadding, bottom = extraBottomPadding, start = 16.dp, end = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Phone") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = age,
                onValueChange = { age = it },
                label = { Text("Age") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Text("Gender")
            Row {
                listOf("Male", "Female", "Other").forEach { option ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        RadioButton(
                            selected = (gender == option),
                            onClick = { gender = option }
                        )
                        Text(option)
                    }
                }
            }
            Text("Hobbies")
            Row {
                Checkbox(
                    checked = reading,
                    onCheckedChange = { reading = it }
                )
                Text("Reading", modifier = Modifier.padding(end = 8.dp))
                Checkbox(
                    checked = traveling,
                    onCheckedChange = { traveling = it }
                )
                Text("Traveling", modifier = Modifier.padding(end = 8.dp))
                Checkbox(
                    checked = coding,
                    onCheckedChange = { coding = it }
                )
                Text("Coding")
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Enable Notifications")
                Switch(checked = notificationsEnabled, onCheckedChange = { notificationsEnabled = it })
            }
            Button(
                onClick = {
                    val profile = UserProfile(
                        name = name,
                        email = email,
                        phone = phone,
                        age = age.toIntOrNull() ?: 0,
                        gender = gender,
                        hobbies = listOfNotNull(
                            if (reading) "Reading" else null,
                            if (traveling) "Traveling" else null,
                            if (coding) "Coding" else null
                        ),
                        notificationsEnabled = notificationsEnabled,
                        isFavorite = false
                    )
                    viewModel.submitProfile(profile)
                    name = ""
                    email = ""
                    phone = ""
                    age = ""
                    gender = "Male"
                    notificationsEnabled = false
                    reading = false
                    traveling = false
                    coding = false

                    navController.navigate(Screen.ProfileDisplay.route)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Submit")
            }
        }
    }
}
