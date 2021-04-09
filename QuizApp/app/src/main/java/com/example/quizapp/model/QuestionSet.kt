package com.example.quizapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class QuestionSet(
    @PrimaryKey(autoGenerate = true)
    val questionSetId: Int,

    val questionSetName: String
)