package com.turkcell.libraryapp.ui.screen


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.turkcell.libraryapp.data.model.Book
import com.turkcell.libraryapp.ui.navigation.Screen
import com.turkcell.libraryapp.ui.viewmodel.AuthViewModel
import com.turkcell.libraryapp.ui.viewmodel.BookViewModel
import kotlin.let

@Composable
fun HomeScreen(
    authViewModel: AuthViewModel,
    bookViewModel: BookViewModel,
    navController: NavController
) {
    val books by bookViewModel.books.collectAsState()
    val isLoading by bookViewModel.isLoading.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var editingBook by remember { mutableStateOf<Book?>(null) }
    var borrowingBook by remember { mutableStateOf<Book?>(null) }
    var selectedDays by remember { mutableIntStateOf(3) }



    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var pageCount by remember { mutableStateOf("") }

    LaunchedEffect(editingBook) {
        editingBook?.let { book ->
            title = book.title
            author = book.author
            category = book.category ?: ""
            pageCount = book.pageCount.toString()
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {

        Spacer(modifier = Modifier.height(15.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Kitaplarım",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp)
            )
            TextButton(onClick = { navController.navigate(Screen.Borrowings.route) }) {
                Text("Kiralamalarım", color = Color(0xFF6750A4))
            }
        }

        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                if (it.isEmpty()) bookViewModel.loadBooks()
                else bookViewModel.searchBooks(it)
            },
            placeholder = { Text("Kütüphanende kitap ara...") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = Color(0xFF6750A4)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = CircleShape,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color(0xFFF7F7F7),
                focusedBorderColor = Color(0xFF6750A4),
                unfocusedBorderColor = Color.LightGray
            )
        )


        Spacer(modifier = Modifier.height(8.dp))

        when {
            isLoading -> CircularProgressIndicator(
                modifier = Modifier.size(20.dp).align(Alignment.CenterHorizontally),
                strokeWidth = 2.dp
            )
            books.isEmpty() -> Text(
                "Kitap bulunamadı.",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            else -> LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                items(books, key = { it.id }) { book ->
                    BookCard(
                        book = book,
                        onDelete = { bookViewModel.deleteBook(it) },
                        onEdit = { editingBook = it },
                        onBorrow = { borrowingBook = it }
                    )
                }
            }
        }
    }

    editingBook?.let { book ->
        AlertDialog(
            onDismissRequest = { editingBook = null },
            title = { Text("Kitabı Düzenle") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Başlık") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = author,
                        onValueChange = { author = it },
                        label = { Text("Yazar") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = category,
                        onValueChange = { category = it },
                        label = { Text("Kategori") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = pageCount,
                        onValueChange = { if (it.all { char -> char.isDigit() }) pageCount = it },
                        label = { Text("Sayfa Sayısı") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    bookViewModel.updateBook(
                        book.copy(
                            title = title,
                            author = author,
                            category = category,
                            pageCount = pageCount.toIntOrNull() ?: 0
                        )
                    )
                    editingBook = null
                }) {
                    Text("Kaydet")
                }
            },
            dismissButton = {
                TextButton(onClick = { editingBook = null }) {
                    Text("İptal")
                }
            }
        )
    }


    borrowingBook?.let { book ->
        AlertDialog(
            onDismissRequest = { borrowingBook = null },
            title = { Text("Ödünç Al") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Kitap: ${book.title}", fontWeight = FontWeight.Bold)
                    Text("Kaç gün ödünç almak istiyorsunuz? (Maks. 5 gün)")
                    Slider(
                        value = selectedDays.toFloat(),
                        onValueChange = { selectedDays = it.toInt() },
                        valueRange = 1f..5f,
                        steps = 3
                    )
                    Text(
                        "Seçilen süre: $selectedDays gün",
                        color = Color(0xFF6750A4),
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    bookViewModel.borrowBook(book, selectedDays)
                    borrowingBook = null
                }) {
                    Text("Onayla")
                }
            },
            dismissButton = {
                TextButton(onClick = { borrowingBook = null }) {
                    Text("İptal")
                }
            }
        )
    }
}






