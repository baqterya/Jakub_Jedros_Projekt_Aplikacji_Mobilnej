package com.example.quizapp.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.quizapp.model.Category
import com.example.quizapp.model.QuestionSet

data class QuestionSetCategoryRelation(
    @Embedded val questionSet: QuestionSet,

    @Relation(
        parentColumn = "questionSetName",
        entityColumn = "questionSetName"
    )
    val categories: List<Category>
)