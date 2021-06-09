package com.example.quizapp.repository

import androidx.lifecycle.LiveData
import com.example.quizapp.model.Category
import com.example.quizapp.model.CategoryDao

class CategoryRepository(private val categoryDao: CategoryDao) {

    suspend fun insertCategory(category: Category) {
        categoryDao.insertCategory(category)
    }

    suspend fun editCategory(category: Category) {
        categoryDao.editCategory(category)
    }

    suspend fun deleteCategory(category: Category) {
        categoryDao.deleteCategory(category)
    }

    fun getAllCategoriesFromQuestionSet(questionSetId: Int)
    = categoryDao.getAllCategoriesFromQuestionSet(questionSetId)

    fun allCategories() : LiveData<List<Category>>
    = categoryDao.getAllCategories()

    suspend fun deleteAllCategoriesFromQuestionSet(questionSetId: Int) {
        categoryDao.deleteAllCategoriesFromQuestionSet(questionSetId)
    }

    suspend fun deleteAllCategories() {
        categoryDao.deleteAllCategories()
    }
}