package com.turkcell.libraryappv2.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.turkcell.libraryappv2.data.model.Book

@Composable
fun BookCard(book: Book, onBorrow: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text("Kitap Adı: ${book.title}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text("Yazar: ${book.author}", style = MaterialTheme.typography.bodyMedium)
            Text("Kategori: ${book.category}", style = MaterialTheme.typography.bodySmall)
            Text("Mevcut: ${book.avaiableCopies} / ${book.totalCopies}", style = MaterialTheme.typography.bodySmall)

            Spacer(modifier = Modifier.height(8.dp))

            if (book.avaiableCopies > 0) {
                Button(onClick = onBorrow, modifier = Modifier.fillMaxWidth()) {
                    Text("ÖDÜNÇ AL")
                }
            } else {
                Text(
                    text = "STOKTA YOK",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
