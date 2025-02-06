package com.example.userloginapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun insert(user: User) = viewModelScope.launch {
        userRepository.insert(user)
    }

    fun getUserByUsername(username: String, callback: (User?) -> Unit) = viewModelScope.launch {
        val user = userRepository.getUserByUsername(username)
        callback(user)
    }
    fun getUserById(userId: Int, callback: (User?) -> Unit) = viewModelScope.launch {
        val user = userRepository.getUserById(userId)
        callback(user)
    }

    fun getAllUsers(callback: (List<User>) -> Unit) = viewModelScope.launch {
        val users = userRepository.getAllUsers()
        callback(users)
    }
}
