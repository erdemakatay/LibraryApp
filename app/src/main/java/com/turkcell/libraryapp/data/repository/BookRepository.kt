package com.turkcell.libraryapp.data.repository

import com.turkcell.libraryapp.data.model.Book
import com.turkcell.libraryapp.data.supabase.supabase
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns

class BookRepository {
    suspend fun getAllBooks(): Result<List<Book>> = runCatching {
        supabase.postgrest["books"]
            .select()
            .decodeList<Book>()
    }

    suspend fun getBookById(id: String): Result <Book> = runCatching {
        supabase.postgrest["books"]
            .select { filter { eq("id", id) } }
            .decodeSingle<Book>()
    }

    suspend fun addBook(book: Book): Result<Unit> = runCatching {
        supabase.postgrest["books"].insert(book)
    }


    // Kitap Güncelleme kodum
    suspend fun updateBook(book: Book): Result<Unit> = runCatching {
        supabase.postgrest["books"].update(book) {
            filter {
                eq("id", book.id)
            }
        }
    }


    // Kitap silme kodum

    suspend fun deleteBook(id: String): Result<Unit> = runCatching {
        supabase.postgrest["books"]
            .delete {
                filter { eq("id",id) }
            }
    }

    // Kitap arama kodum

    suspend fun searchBooks(query: String): Result<List<Book>> = runCatching {
        supabase.postgrest["books"]
            .select {
                filter {
                    ilike("title", "%$query%") }
            }
            .decodeList<Book>()
    }

}