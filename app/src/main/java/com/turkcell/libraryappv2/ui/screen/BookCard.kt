package com.turkcell.libraryappv2.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.turkcell.libraryappv2.data.model.Book
//ödev3 2 may
@Composable
fun BookCard(book: Book) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text("Kitap Adı: ${book.title}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text("Yazar: ${book.author}", style = MaterialTheme.typography.bodyMedium)
            Text("Kategori: ${book.category}", style = MaterialTheme.typography.bodySmall)
            Text("Uygunluk durumu: ${book.avaiableCopies} / ${book.totalCopies} mevcut", style = MaterialTheme.typography.bodySmall)
        }
    }
}
