package com.example.composehello_world.model

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class UserProfileViewModel : ViewModel() {

    val userProfiles = mutableStateListOf<UserProfile>()
    fun submitProfile(profile: UserProfile) {
        userProfiles.add(profile)
    }
}