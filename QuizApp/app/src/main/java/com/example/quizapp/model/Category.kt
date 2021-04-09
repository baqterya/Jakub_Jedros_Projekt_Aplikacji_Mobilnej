package com.example.quizapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Category(
    @PrimaryKey(autoGenerate = true)
    val categoryId: Int,

    val categoryName: String,
    val questionSetName: String
)