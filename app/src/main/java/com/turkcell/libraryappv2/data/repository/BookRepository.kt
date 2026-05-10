package com.turkcell.libraryappv2.data.repository

//en az bağımlıdan başla model -> repository -> viewmodel -> screen
import com.turkcell.libraryappv2.data.model.Book
import com.turkcell.libraryappv2.data.supabase.supabase
import io.github.jan.supabase.postgrest.postgrest

class BookRepository {
    suspend fun getAllBooks(): Result<List<Book>> = runCatching {
        supabase.postgrest["books"]
            .select()
            .decodeList<Book>()
    }

    suspend fun getBookById(id:String): Result<Book> = runCatching {
        supabase.postgrest["books"]
            .select { filter { eq("id",id) } }
            .decodeSingle<Book>()
    }

    suspend fun addBook(book: Book): Result<Unit> = runCatching {
        supabase.postgrest["books"].insert(book)
    }

    // ödev 2:  Book repository güncelleme, silme, arama fonksiyonlarını tanımla

    suspend fun updateBook(book: Book): Result<Unit> = runCatching {
        supabase.postgrest["books"].update({
            set("title", book.title)
            set("author", book.author)
            set("isbn", book.isbn)
            set("category", book.category)
            set("page_count", book.pageCount)
            set("total_copies", book.totalCopies)
            set("available_copies", book.avaiableCopies)
        }) {
            filter { eq("id", book.id) }   //idye göre hangi kitap güncelleneck
        }
    }
    suspend fun deleteBook(id: String): Result<Unit> = runCatching {
        supabase.postgrest["books"].delete {
            filter { eq("id", id) } //idye göre hangi kitap delete edilecek hard delete oluyor bu
        }
    }
    suspend fun searchBooks(query: String): Result<List<Book>> = runCatching {
        supabase.postgrest["books"]
            .select {
                filter { ilike("title", "%$query%") }  // ← filter bloğunun içinde ilike olacak.
                // ARAMAK: ilike → "içinde geçiyor mu?" (büyük/küçük harf duyarsız)
            }
            .decodeList<Book>()
    }


}