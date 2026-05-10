package com.turkcell.libraryappv2.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BorrowRequest(
    @SerialName("student_id") val studentId: String,
    @SerialName("book_id") val bookId: String,
    @SerialName("borrow_date") val borrowDate: String,
    @SerialName("due_date") val dueDate: String,
    val status: String = "pending"
)
