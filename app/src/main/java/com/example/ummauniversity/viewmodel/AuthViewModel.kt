package com.example.ummauniversity.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ummauniversity.data.entities.UserEntity
import com.example.ummauniversity.data.repository.UmmaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Authenticated(val user: UserEntity) : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel(private val repository: UmmaRepository) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private val _currentUser = MutableStateFlow<UserEntity?>(null)
    val currentUser: StateFlow<UserEntity?> = _currentUser.asStateFlow()

    fun login(emailInput: String, passwordInput: String) {
        val email = emailInput.trim()
        val password = passwordInput.trim()

        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Please enter both email and password")
            return
        }

        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val user = repository.getUserByEmail(email)
            if (user != null && user.passwordHash == password) {
                _currentUser.value = user
                _authState.value = AuthState.Authenticated(user)
            } else {
                _authState.value = AuthState.Error("Invalid email or password. Use sample credentials below.")
            }
        }
    }

    fun register(
        fullName: String,
        email: String,
        password: String,
        role: String,
        phone: String,
        regOrStaffNumber: String,
        department: String,
        programme: String
    ) {
        if (fullName.isBlank() || email.isBlank() || password.isBlank()) {
            _authState.value = AuthState.Error("Full Name, Email and Password are required.")
            return
        }
        if (password.length < 6) {
            _authState.value = AuthState.Error("Password must be at least 6 characters.")
            return
        }

        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val existing = repository.getUserByEmail(email.trim())
            if (existing != null) {
                _authState.value = AuthState.Error("An account with this email already exists.")
                return@launch
            }

            val newUser = UserEntity(
                fullName = fullName.trim(),
                email = email.trim(),
                passwordHash = password.trim(),
                role = role,
                phone = phone.trim(),
                regOrStaffNumber = regOrStaffNumber.trim(),
                department = department.trim(),
                programme = programme.trim()
            )
            val newId = repository.registerUser(newUser)
            val userWithId = newUser.copy(id = newId)
            _currentUser.value = userWithId
            _authState.value = AuthState.Authenticated(userWithId)
        }
    }

    fun logout() {
        _currentUser.value = null
        _authState.value = AuthState.Idle
    }

    fun resetState() {
        _authState.value = AuthState.Idle
    }

    fun updateProfile(fullName: String, phone: String) {
        val current = _currentUser.value ?: return
        val updated = current.copy(fullName = fullName, phone = phone)
        viewModelScope.launch {
            repository.updateUser(updated)
            _currentUser.value = updated
        }
    }
}
