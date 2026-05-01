package com.turkcell.libraryapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.turkcell.libraryapp.data.model.Book
import com.turkcell.libraryapp.data.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookViewModel : ViewModel() {
    private val repository = BookRepository()

    private val  _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> = _books

    private val _isLoading = MutableStateFlow(false)
    val  isLoading : StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        loadBooks()
    }

    fun loadBooks() {
        viewModelScope.launch {
            _isLoading.value = true
            repository
                .getAllBooks()
                .onSuccess {_books.value = it }
                .onFailure {_error.value =it.message}
                _isLoading.value =false
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

}


