package com.turkcell.libraryapp.ui.screen

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.turkcell.libraryapp.data.model.BorrowRecord
import com.turkcell.libraryapp.ui.viewmodel.BookViewModel


@Composable
fun BorrowingsScreen(
    bookViewModel: BookViewModel,
) {
    val borrowings by bookViewModel.borrowings.collectAsState()
    val isLoading by bookViewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        bookViewModel.loadBorrowings()
    }


    Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
        Text(
            text = "Kiralamalarım",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))


        when {
            isLoading -> CircularProgressIndicator(
                modifier = Modifier.size(20.dp).align(Alignment.CenterHorizontally),
                strokeWidth = 2.dp
            )
            borrowings.isEmpty() -> Text(
                "Henüz kiralama yok.",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            else -> LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(borrowings) { record ->
                    BorrowingCard(record = record)
                }
            }
        }
    }
}

@Composable
fun BorrowingCard(record: BorrowRecord) {
    val isActive = record.returnedAt == null
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isActive) Color(0xFFE8F5E9) else Color(0xFFF5F5F5)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = record.bookTitle.ifEmpty { record.bookId },
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleSmall
                )
                Surface(
                    color = if (isActive) Color(0xFF4CAF50) else Color.Gray,
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = if (isActive) "Aktif" else "İade Edildi",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text("Alınma: ${record.borrowedAt.take(10)}", style = MaterialTheme.typography.bodySmall)
            Text("İade tarihi: ${record.dueDate.take(10)}", style = MaterialTheme.typography.bodySmall)
            if (!isActive) {
                Text("İade edildi: ${record.returnedAt?.take(10)}", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
        }
    }
}