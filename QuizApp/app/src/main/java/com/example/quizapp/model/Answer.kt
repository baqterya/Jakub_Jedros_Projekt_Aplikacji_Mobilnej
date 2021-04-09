package com.example.quizapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Answer(
    @PrimaryKey(autoGenerate = true)
    val answerId: Int,

    val answerText: String,
    val questionText: String
)