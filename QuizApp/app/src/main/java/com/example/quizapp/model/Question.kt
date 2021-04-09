package com.example.quizapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Question(
    @PrimaryKey(autoGenerate = true)
    val questionId: Int,

    val questionText: String,
    val categoryName: String

)
