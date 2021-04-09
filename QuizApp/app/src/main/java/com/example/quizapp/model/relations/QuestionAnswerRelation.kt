package com.example.quizapp.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.quizapp.model.Answer
import com.example.quizapp.model.Question

data class QuestionAnswerRelation(
    @Embedded val question: Question,

    @Relation(
        parentColumn = "questionText",
        entityColumn = "questionText"
    )
    val answer: Answer
)