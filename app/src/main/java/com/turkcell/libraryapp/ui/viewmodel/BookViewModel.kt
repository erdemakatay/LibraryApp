package com.turkcell.libraryapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turkcell.libraryapp.data.model.Book
import com.turkcell.libraryapp.data.model.BorrowRecord
import com.turkcell.libraryapp.data.repository.BookRepository
import com.turkcell.libraryapp.data.supabase.supabase
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.temporal.ChronoUnit
import java.util.UUID
import java.time.Instant

class BookViewModel : ViewModel() {
    private val repository = BookRepository()


    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> = _books

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _borrowSuccess = MutableStateFlow(false)
    val borrowSuccess: StateFlow<Boolean> = _borrowSuccess


    private val _borrowings = MutableStateFlow<List<BorrowRecord>>(emptyList())
    val borrowings: StateFlow<List<BorrowRecord>> = _borrowings

    init {
        loadBooks()
    }

    fun loadBooks() {
        viewModelScope.launch {
            _isLoading.value = true
            repository
                .getAllBooks()
                .onSuccess { _books.value = it }
                .onFailure { _error.value = it.message }
            _isLoading.value = false
        }
    }

    fun updateBook(book: Book) {
        viewModelScope.launch {
            _isLoading.value = true
            repository.updateBook(book)
                .onSuccess {
                    Log.d("BookViewModel", "Güncelleme başarılı, liste yenileniyor")
                    loadBooks()
                }
                .onFailure {
                    Log.e("BookViewModel", "Güncelleme hatası: ${it.message}")
                    _error.value = it.message
                }
            _isLoading.value = false
        }
    }

    fun deleteBook(id: String) {
        viewModelScope.launch {
            _isLoading.value = true
            repository.deleteBook(id)
                .onSuccess {
                    Log.d("BookViewModel", "Silme başarılı, liste güncelleniyor...")
                    loadBooks()
                }
                .onFailure {
                    Log.e("BookViewModel", "Silme hatası: ${it.message}")
                    _error.value = it.message
                }
            _isLoading.value = false
        }
    }

    fun searchBooks(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            repository.searchBooks(query)
                .onSuccess { _books.value = it }
                .onFailure { _error.value = it.message }
            _isLoading.value = false
        }
    }


    fun borrowBook(book: Book, days: Int) {
        viewModelScope.launch {
            _isLoading.value = true

            val currentUserId = supabase.auth.currentUserOrNull()?.id
                ?: run {
                    _error.value = "Kullanıcı bulunamadı."
                    _isLoading.value = false
                    return@launch
                }

            val now = Instant.now()
            val dueDate = now.plus(days.toLong(), ChronoUnit.DAYS)

            val record = BorrowRecord(
                id = UUID.randomUUID().toString(),
                studentId = currentUserId,
                bookId = book.id,
                bookTitle = book.title,
                borrowedAt = now.toString(),
                dueDate = dueDate.toString()
            )

            repository.borrowBook(record)
                .onSuccess {
                    Log.d("BookViewModel", "Ödünç kaydı oluşturuldu.")
                    _borrowSuccess.value = true
                    loadBooks()
                }
                .onFailure {
                    Log.e("BookViewModel", "Ödünç hatası: ${it.message}")
                    _error.value = it.message
                }
            _isLoading.value = false
        }
    }

    fun loadBorrowings() {
        viewModelScope.launch {
            _isLoading.value = true
            val userId = supabase.auth.currentUserOrNull()?.id ?: return@launch
            repository.getBorrowings(userId)
                .onSuccess { _borrowings.value = it }
                .onFailure { _error.value = it.message }
            _isLoading.value = false
        }
    }
}