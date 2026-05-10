package com.turkcell.libraryappv2.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.turkcell.libraryappv2.ui.viewmodel.AdminViewModel
import com.turkcell.libraryappv2.ui.viewmodel.AuthViewModel

@Composable
fun AdminScreen(adminViewModel: AdminViewModel, authViewModel: AuthViewModel, onLogout: () -> Unit) {
    val pendingRequests by adminViewModel.pendingRequests.collectAsState()

    LaunchedEffect(Unit) {
        adminViewModel.loadPendingRequests()
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Admin Paneli",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Button(onClick = {
                authViewModel.signOut()
                onLogout()
            }) {
                Text("Çıkış Yap")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (pendingRequests.isEmpty()) {
            Text("Bekleyen istek yok.")
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(pendingRequests, key = { it.id }) { record ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text("Öğrenci: ${record.studentName}", fontWeight = FontWeight.Bold)
                            Text("Kitap: ${record.bookTitle}", fontWeight = FontWeight.Bold)
                            Text("Tarih: ${record.borrowDate.take(10)}")

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Button(
                                    onClick = { adminViewModel.approveRequest(record.id) },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Onayla")
                                }
                                Button(
                                    onClick = { adminViewModel.rejectRequest(record.id, record.bookId) },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Reddet")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
