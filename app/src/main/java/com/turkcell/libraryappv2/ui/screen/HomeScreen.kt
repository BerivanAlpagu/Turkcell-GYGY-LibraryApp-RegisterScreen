package com.turkcell.libraryappv2.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.turkcell.libraryappv2.ui.viewmodel.AuthViewModel
import io.ktor.websocket.Frame
import androidx.compose.runtime.collectAsState


@Composable
fun HomeScreen(authViewModel: AuthViewModel){
    val profileState = authViewModel.profile.collectAsState()

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text(profileState.value?.fullName ?: "Profil Bulunamadı")
    }
}