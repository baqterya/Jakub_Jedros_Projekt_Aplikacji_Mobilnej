package com.example.quizapp.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.quizapp.model.Category
import com.example.quizapp.model.QuestionSet

data class QuestionSetWithCategories(
    @Embedded
    val questionSet: QuestionSet,

    @Relation(
        parentColumn = "questionSetId",
        entityColumn = "parentSetId"
    )
    val categories: List<Category>
)