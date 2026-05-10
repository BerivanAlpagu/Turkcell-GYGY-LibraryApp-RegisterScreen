package com.turkcell.libraryappv2.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.turkcell.libraryappv2.data.model.AdminBorrowRecord
import com.turkcell.libraryappv2.data.model.Book
import com.turkcell.libraryappv2.data.model.BorrowRecord
import com.turkcell.libraryappv2.data.model.BorrowRequest
import com.turkcell.libraryappv2.data.model.Profile
import com.turkcell.libraryappv2.data.supabase.supabase
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.filter.FilterOperator
import java.time.Instant
import java.time.temporal.ChronoUnit

class BorrowRepository {

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun borrowBook(bookId: String, userId: String): Result<Unit> = runCatching {
        val now = Instant.now()
        val request = BorrowRequest(
            studentId = userId,
            bookId = bookId,
            borrowDate = now.toString(),
            dueDate = now.plus(5, ChronoUnit.DAYS).toString()
        )

        supabase.postgrest["borrow_records"].insert(request)

        val book = supabase.postgrest["books"]
            .select { filter { eq("id", bookId) } }
            .decodeSingle<Book>()

        supabase.postgrest["books"].update({
            set("available_copies", book.avaiableCopies - 1)
        }) {
            filter { eq("id", bookId) }
        }
    }

    suspend fun getMyBorrows(userId: String): Result<List<BorrowRecord>> = runCatching {
        supabase.postgrest["borrow_records"]
            .select { filter { eq("student_id", userId) } }
            .decodeList<BorrowRecord>()
    }

    suspend fun getPendingBorrows(): Result<List<AdminBorrowRecord>> = runCatching {
        val records = supabase.postgrest["borrow_records"]
            .select { filter { eq("status", "pending") } }
            .decodeList<BorrowRecord>()

        records.map { record ->
            val book = supabase.postgrest["books"]
                .select { filter { eq("id", record.bookId) } }
                .decodeSingleOrNull<Book>()

            val profile = supabase.postgrest["profiles"]
                .select { filter { eq("user_id", record.studentId) } }
                .decodeSingleOrNull<Profile>()

            AdminBorrowRecord(
                id = record.id,
                bookId = record.bookId,
                studentName = profile?.fullName ?: "Bilinmeyen Öğrenci",
                bookTitle = book?.title ?: "Bilinmeyen Kitap",
                borrowDate = record.borrowDate,
                dueDate = record.dueDate,
                status = record.status
            )
        }
    }

    suspend fun approveBorrow(recordId: String): Result<Unit> = runCatching {
        supabase.postgrest["borrow_records"].update({
            set("status", "approved")
        }) {
            filter { eq("id", recordId) }
        }
    }

    suspend fun rejectBorrow(recordId: String, bookId: String): Result<Unit> = runCatching {
        // Reddedildiğinde durumu güncelle
        supabase.postgrest["borrow_records"].update({
            set("status", "rejected")
        }) {
            filter { eq("id", recordId) }
        }

        // Ve stok sayısını tekrar artır (çünkü ödünç alınırken 1 eksiltilmişti)
        val book = supabase.postgrest["books"]
            .select { filter { eq("id", bookId) } }
            .decodeSingle<Book>()

        supabase.postgrest["books"].update({
            set("available_copies", book.avaiableCopies + 1)
        }) {
            filter { eq("id", bookId) }
        }
    }
}
