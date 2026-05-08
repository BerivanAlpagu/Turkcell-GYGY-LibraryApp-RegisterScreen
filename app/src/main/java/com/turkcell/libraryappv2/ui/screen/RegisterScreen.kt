package com.turkcell.libraryappv2.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.turkcell.libraryappv2.ui.viewmodel.AuthState
import com.turkcell.libraryappv2.ui.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(onNavigateToLogin: () -> Unit) {
    // ViewModel'imi çağırdım ve state'ini dinlemeye başladım
    val authViewModel: AuthViewModel = viewModel()
    val authState by authViewModel.authState.collectAsState()

    // Kullanıcının gireceği ad, e-posta ve şifre değerlerini tutmak için değişkenlerimi oluşturdum
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var studentNo by remember { mutableStateOf("") }

    // Kayıt başarılı olduğunda Login'e gitmesi için state'i dinliyorum
    LaunchedEffect(authState) {
        if (authState is AuthState.Success) {
            onNavigateToLogin()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Kütüphane Sistemi")
        Spacer(modifier =  Modifier.height(8.dp))
        Text("Kayıt Ol")

        // Ad Soyad için kutucuğumu yaptım
        OutlinedTextField(
            enabled = authState !is AuthState.Loading,
            modifier = Modifier.fillMaxWidth(),
            value = fullName,
            label = { Text("Ad Soyad") },
            onValueChange = { value -> fullName = value },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(10.dp))

        // E-posta için kutucuğumu yaptım ve klavye tipini email olarak ayarladım
        OutlinedTextField(
            enabled = authState !is AuthState.Loading,
            modifier = Modifier.fillMaxWidth(),
            value = email,
            label = { Text("E-posta") },
            onValueChange = { value -> email = value },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = studentNo,
            onValueChange = { studentNo = it },
            label = { Text("Öğrenci No (opsiyonel)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Şifre için kutucuğumu yaptım ve gizli görünmesi için PasswordVisualTransformation ekledim
        OutlinedTextField(
            enabled = authState !is AuthState.Loading,
            modifier = Modifier.fillMaxWidth(),
            value = password,
            label = { Text("Şifre") },
            onValueChange = { value -> password = value },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(10.dp))

        // İşlem yapılıyorsa (Loading) dönen bir ikon gösteriyorum, yoksa butonumu gösteriyorum
        if(authState is AuthState.Loading) {
            Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        } else {
            Button(
                onClick = {
                    authViewModel.signUp(fullName, email, password, studentNo)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Kayıt Ol")
            }
        }

        // Eğer hata varsa, hatayı ekrana kırmızı renkli yazıyla basıyorum
        if (authState is AuthState.Error) {
            Text(
                text = (authState as AuthState.Error).message,
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Kullanıcının zaten hesabı varsa Login ekranına dönebilmesi için buton
        TextButton(onClick = { onNavigateToLogin() }) {
            Text("Zaten hesabınız var mı? Giriş Yap")
        }
    }
}

