package com.turkcell.libraryappv2.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BorrowRecord(
    val id: String = "",
    @SerialName("student_id") val studentId: String = "",
    @SerialName("book_id") val bookId: String = "",
    @SerialName("borrow_date") val borrowDate: String = "",
    @SerialName("due_date") val dueDate: String = "",
    @SerialName("returned_at") val returnedAt: String? = null,
    val status: String = "pending"
)
