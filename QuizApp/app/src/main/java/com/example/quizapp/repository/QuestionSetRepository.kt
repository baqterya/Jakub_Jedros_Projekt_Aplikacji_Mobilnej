package com.example.quizapp.repository

import androidx.lifecycle.LiveData
import com.example.quizapp.model.QuestionSet
import com.example.quizapp.model.QuestionSetDao

class QuestionSetRepository(private val questionSetDao: QuestionSetDao) {

    val allQuestionSets: LiveData<List<QuestionSet>> = questionSetDao.getAllQuestionSets()

    suspend fun insertQuestionSet(questionSet: QuestionSet) {
        questionSetDao.insertQuestionSet(questionSet)
    }

    suspend fun editQuestionSet(questionSet: QuestionSet) {
        questionSetDao.editQuestionSet(questionSet)
    }

    suspend fun deleteQuestionSet(questionSet: QuestionSet) {
        questionSetDao.deleteQuestionSet(questionSet)
    }

    suspend fun deleteAllQuestionSets() {
        questionSetDao.deleteAllQuestionSets()
    }
}