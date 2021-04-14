package com.example.quizapp.model

import androidx.room.*
import com.example.quizapp.model.relations.QuestionSetWithCategories
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionSetDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertQuestionSet(questionSet: QuestionSet)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategory(category: Category)

    @Transaction
    @Query("SELECT * FROM question_set_table")
    fun getQuestionSetsAndCategories(): List<QuestionSetWithCategories>

    @Query("SELECT * FROM question_set_table ORDER BY questionSetName ASC")
    fun getAllQuestionSets(): Flow<List<QuestionSet>>

    @Query("DELETE FROM question_set_table")
    suspend fun deleteAllQuestionSets()
}