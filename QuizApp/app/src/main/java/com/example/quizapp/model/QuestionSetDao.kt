package com.example.quizapp.model

import androidx.room.*
import com.example.quizapp.model.relations.CategoryQuestionRelation
import com.example.quizapp.model.relations.QuestionAnswerRelation

@Dao
interface QuestionSetDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertQuestionSet(questionSet: QuestionSet)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategory(category: Category)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestion(question: Question)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnswer(answer: Answer)

    @Transaction
    @Query("SELECT * FROM questionSet WHERE questionSetName = :questionSetName")
    suspend fun getQuestionSetWithCategoriesByQuestionSet(questionSetName: String): List<CategoryQuestionRelation>

    @Transaction
    @Query("SELECT * FROM category WHERE categoryName = :categoryName")
    suspend fun getCategoryWithQuestionsByCategory(categoryName: String): List<CategoryQuestionRelation>

    @Transaction
    @Query("SELECT * FROM question WHERE questionText = :questionText")
    suspend fun getQuestionAndAnswerByQuestion(questionText: String): List<QuestionAnswerRelation>

}