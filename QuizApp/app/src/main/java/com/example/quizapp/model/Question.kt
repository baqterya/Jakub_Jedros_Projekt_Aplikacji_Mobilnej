package com.example.quizapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "question_table")
data class Question(
        @PrimaryKey(autoGenerate = true)
        val questionId: Int,

        val questionText: String,
        val answerId: Int,

        val parentCategoryId: Int,
        val parentQuestionSetId: Int
): Parcelable