package com.example.quizapp.repository

import com.example.quizapp.model.Answer
import com.example.quizapp.model.Question
import com.example.quizapp.model.QuestionAnswerDao
import com.example.quizapp.model.relations.QuestionAndAnswer

class QuestionAndAnswerRepository(private val questionAnswerDao: QuestionAnswerDao) {
    suspend fun insertQuestionAndAnswer(question: Question, answer: Answer) {
        val qna = QuestionAndAnswer(question, answer)
        questionAnswerDao.insertQuestionAndAnswer(qna)
    }

    suspend fun editQuestionAndAnswer(question: Question, answer: Answer) {
        val qna = QuestionAndAnswer(question, answer)
        questionAnswerDao.editQuestionAndAnswer(qna)
    }

    suspend fun deleteQuestionAndAnswer(question: Question, answer: Answer) {
        val qna = QuestionAndAnswer(question, answer)
        questionAnswerDao.deleteQuestionAndAnswer(qna)
    }

    fun getAllQuestionsAndAnswersFromCategory(categoryId: Int)
    = questionAnswerDao.getQuestionAndAnswerByCategory(categoryId)

    suspend fun deleteQuestionsAndAnswersFromCategory(categoryId: Int) {
        questionAnswerDao.deleteQuestionsAndAnswersFromCategory(categoryId)
    }

    suspend fun deleteQuestionsAndAnswersFromQuestionSet(questionSetId: Int) {
        questionAnswerDao.deleteQuestionsAndAnswersFromQuestionSet(questionSetId)
    }

    suspend fun deleteAllQuestionsAndAnswers() {
        questionAnswerDao.deleteAllQuestionsAndAnswers()
    }
}