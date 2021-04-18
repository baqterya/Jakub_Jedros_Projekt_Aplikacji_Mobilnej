package com.example.quizapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "question_set_table")
data class QuestionSet(
    @PrimaryKey(autoGenerate = true)
    val questionSetId: Int,

    val questionSetName: String
): Parcelable