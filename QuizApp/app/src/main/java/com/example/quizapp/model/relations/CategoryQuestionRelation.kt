package com.example.quizapp.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.quizapp.model.Category
import com.example.quizapp.model.Question

data class CategoryQuestionRelation(
    @Embedded val category: Category,

    @Relation(
        parentColumn = "categoryName",
        entityColumn = "categoryName"
    )
    val questions: List<Question>
)