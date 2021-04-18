package com.example.quizapp.model

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.quizapp.model.relations.QuestionSetWithCategories
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionSetDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertQuestionSet(questionSet: QuestionSet)

    @Update
    suspend fun editQuestionSet(questionSet: QuestionSet)

    @Delete
    suspend fun deleteQuestionSet(questionSet: QuestionSet)

    @Query("SELECT * FROM question_set_table ORDER BY questionSetName ASC")
    fun getAllQuestionSets(): LiveData<List<QuestionSet>>

    @Query("DELETE FROM question_set_table")
    suspend fun deleteAllQuestionSets()
}