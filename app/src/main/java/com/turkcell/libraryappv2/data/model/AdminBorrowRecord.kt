package com.turkcell.libraryappv2.data.model

data class AdminBorrowRecord(
    val id: String,
    val bookId: String,
    val studentName: String,
    val bookTitle: String,
    val borrowDate: String,
    val dueDate: String,
    val status: String
)
