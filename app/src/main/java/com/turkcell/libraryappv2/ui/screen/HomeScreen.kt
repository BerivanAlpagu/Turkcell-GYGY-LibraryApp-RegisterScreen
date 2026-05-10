package com.turkcell.libraryappv2.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.turkcell.libraryappv2.ui.viewmodel.AuthViewModel
import com.turkcell.libraryappv2.ui.viewmodel.BookViewModel
import com.turkcell.libraryappv2.ui.viewmodel.BorrowState
import com.turkcell.libraryappv2.ui.viewmodel.BorrowViewModel

@Composable
fun HomeScreen(
    authViewModel: AuthViewModel,
    bookViewModel: BookViewModel,
    borrowViewModel: BorrowViewModel,
    onNavigateToBorrowings: () -> Unit
) {
    val books by bookViewModel.books.collectAsState()
    val isLoading by bookViewModel.isLoading.collectAsState()
    val borrowState by borrowViewModel.borrowState.collectAsState()

    LaunchedEffect(borrowState) {
        if (borrowState is BorrowState.Success) {
            bookViewModel.loadBooks()
            borrowViewModel.resetState()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onNavigateToBorrowings,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Kiralamalarım →")
        }

        if (borrowState is BorrowState.Error) {
            Text(
                text = (borrowState as BorrowState.Error).message,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        when {
            isLoading -> CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                strokeWidth = 2.dp
            )
            books.isEmpty() -> Text("Kitaplar yüklenemedi.")
            else -> LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(books, key = { it.id }) { book ->
                    BookCard(
                        book = book,
                        onBorrow = { borrowViewModel.borrowBook(book.id) }
                    )
                }
            }
        }
    }
}