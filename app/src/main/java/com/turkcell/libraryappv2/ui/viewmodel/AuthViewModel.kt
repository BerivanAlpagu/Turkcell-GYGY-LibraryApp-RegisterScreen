package com.turkcell.libraryappv2.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turkcell.libraryappv2.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AuthState{
    // o anki sayfanın durumu başarılı,başarısız,loading(hala yükleniyor)
    // register, login durumunun (stateleri) tanımlarım, tutarım.
    object Idle: AuthState()
    object Loading: AuthState()
    data class Success(val token: String): AuthState()
    data class Error(val message: String): AuthState()
}
class AuthViewModel: ViewModel() {
    private val repository = AuthRepository()
    //iş mantığını stateti tutarıx viewmodelda,, bşarılı, yükleniyor, idle vs gibi stateleri(durumları tanımlarız
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState;

    fun signIn(email: String, password: String)
    {
        viewModelScope.launch {
            _authState.value = AuthState.Loading

            repository
                .signIn(email, password)
                .onSuccess { result -> _authState.value = AuthState.Success("student") }
                .onFailure { ex -> _authState.value = AuthState.Error(ex.message ?: "Giriş başarısız") }
        }
    }

    fun signUp(name: String, email: String, password: String)
    {
        viewModelScope.launch {
            _authState.value = AuthState.Loading

            repository
                .signUp(name, email, password)
                .onSuccess { result -> _authState.value = AuthState.Success("student") }
                .onFailure { ex -> _authState.value = AuthState.Error(ex.message ?: "Kayıt başarısız") }
        }
    }
}