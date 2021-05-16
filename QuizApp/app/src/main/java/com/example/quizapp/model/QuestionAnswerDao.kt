package com.example.quizapp.model

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.quizapp.model.relations.QuestionAndAnswer

@Dao
interface QuestionAnswerDao {

    @Transaction
    suspend fun insertQuestionAndAnswer(questionAndAnswer: QuestionAndAnswer) {
        insertQuestion(questionAndAnswer.question)
        insertAnswer(questionAndAnswer.answer)
    }

    @Transaction
    suspend fun editQuestionAndAnswer(questionAndAnswer: QuestionAndAnswer) {
        editQuestion(questionAndAnswer.question)
        editAnswer(questionAndAnswer.answer)
    }

    @Transaction
    @Query("SELECT * FROM question_table WHERE parentCategoryId = :categoryId" )
    fun getQuestionAndAnswerByCategory(categoryId: Int): LiveData<List<QuestionAndAnswer>>

    @Transaction
    @Query("SELECT * FROM question_table WHERE parentQuestionSetId = :questionSetId" )
    fun getQuestionAndAnswerByQuestionSet(questionSetId: Int): LiveData<List<QuestionAndAnswer>>

    @Transaction
    suspend fun deleteQuestionAndAnswer(questionAndAnswer: QuestionAndAnswer) {
        deleteQuestion(questionAndAnswer.question)
        deleteAnswer(questionAndAnswer.answer)
    }

    @Transaction
    suspend fun deleteQuestionsAndAnswersFromCategory(categoryId: Int) {
        deleteAllQuestionsFromCategory(categoryId)
        deleteAllAnswersFromCategory(categoryId)
    }

    @Transaction
    suspend fun deleteQuestionsAndAnswersFromQuestionSet(questionSetId: Int) {
        deleteAllQuestionsFromQuestionSet(questionSetId)
        deleteAllAnswersFromQuestionSet(questionSetId)
    }

    @Transaction
    suspend fun deleteAllQuestionsAndAnswers() {
        deleteAllQuestions()
        deleteAllAnswers()
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestion(question: Question)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnswer(answer: Answer)

    @Update
    suspend fun editQuestion(question: Question)

    @Update
    suspend fun editAnswer(answer: Answer)

    @Delete
    suspend fun deleteQuestion(question: Question)

    @Delete
    suspend fun deleteAnswer(answer: Answer)

    @Query("DELETE FROM question_table WHERE parentCategoryId = :categoryId")
    suspend fun deleteAllQuestionsFromCategory(categoryId: Int)

    @Query("DELETE FROM answer_table WHERE parentCategoryId = :categoryId")
    suspend fun deleteAllAnswersFromCategory(categoryId: Int)

    @Query("DELETE FROM question_table WHERE parentQuestionSetId = :questionSetId")
    suspend fun deleteAllQuestionsFromQuestionSet(questionSetId: Int)

    @Query("DELETE FROM answer_table WHERE parentQuestionSetId = :questionSetId")
    suspend fun deleteAllAnswersFromQuestionSet(questionSetId: Int)

    @Query("DELETE FROM question_table")
    suspend fun deleteAllQuestions()

    @Query("DELETE FROM answer_table")
    suspend fun deleteAllAnswers()


}