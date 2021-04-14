package com.example.quizapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "question_set_table")
data class QuestionSet(
    @PrimaryKey(autoGenerate = true)
    val questionSetId: Long,

    val questionSetName: String
)