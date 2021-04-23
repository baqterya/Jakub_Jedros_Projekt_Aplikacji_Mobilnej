package com.example.quizapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_table")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val categoryId: Int,

    val categoryName: String,
    val parentSetId: Long
)