package com.example.quizapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.quizapp.database.QuestionSetDatabase
import com.example.quizapp.model.Category
import com.example.quizapp.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryViewModel(application: Application) : AndroidViewModel(application) {
    val allCategories: LiveData<List<Category>>
    private val repository: CategoryRepository

    init {
        val categoryDao = QuestionSetDatabase.getDatabase(application).categoryDao()
        repository = CategoryRepository(categoryDao)
        allCategories = repository.allCategories
    }

    fun insertCategory(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertCategory(category)
        }
    }

    fun editCategory(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.editCategory(category)
        }
    }

    fun deleteCategory(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteCategory(category)
        }
    }

    fun getAllCategoriesFromQuestionSet(questionSetId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllCategoriesFromQuestionSet(questionSetId)
        }
    }

    fun deleteAllCategoriesFromQuestionSet(questionSetId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllCategoriesFromQuestionSet(questionSetId)
        }
    }
}