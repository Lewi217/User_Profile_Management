package com.example.composehello_world.model

data class UserProfile(
    val name: String,
    val email: String,
    val phone: String,
    val age: Int,
    val gender: String,
    val hobbies: List<String>,
    val notificationsEnabled: Boolean,
    val isFavorite: Boolean
)
