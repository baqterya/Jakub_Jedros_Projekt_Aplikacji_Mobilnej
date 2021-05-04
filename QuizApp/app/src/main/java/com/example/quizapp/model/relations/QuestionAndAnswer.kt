package com.example.quizapp.model.relations

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import com.example.quizapp.model.Answer
import com.example.quizapp.model.Question
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuestionAndAnswer(
        @Embedded
        val question: Question,

        @Relation(
                parentColumn = "questionText",
                entityColumn = "parentQuestionText"
        )
        val answer: Answer
): Parcelable