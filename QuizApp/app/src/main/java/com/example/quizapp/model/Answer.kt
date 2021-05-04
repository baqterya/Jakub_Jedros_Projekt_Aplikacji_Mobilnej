package com.example.quizapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "answer_table")
data class Answer(
        @PrimaryKey(autoGenerate = true)
        val answerId: Int,

        val answerText: String,
        val parentQuestionText: String,

        val parentCategoryId: Int,
        val parentQuestionSetId: Int
): Parcelable