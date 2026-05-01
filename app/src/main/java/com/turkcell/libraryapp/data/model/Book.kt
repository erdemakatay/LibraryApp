package com.turkcell.libraryapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Book(
    val id: String  = "",
    val title: String,
    val category: String,
    val author: String,
    val isbn: String= "",
    @SerialName("page_count") val pageCount: Int = 0,
    @SerialName("total_copies") val totalCopies: Int = 1,
    @SerialName("available_copies") val availableCopies: Int = 1,

    )


