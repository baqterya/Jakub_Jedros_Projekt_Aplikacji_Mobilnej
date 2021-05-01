package com.example.quizapp.model

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.quizapp.model.relations.QuestionSetWithCategories

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategory(category: Category)

    @Update
    suspend fun editCategory(category: Category)

    @Delete
    suspend fun deleteCategory(category: Category)

    @Query("SELECT * FROM category_table WHERE parentSetId = :questionSetId ORDER BY categoryName ASC")
    fun getAllCategoriesFromQuestionSet(questionSetId: Int): LiveData<List<Category>>

    @Query("SELECT * FROM category_table ORDER BY categoryName ASC")
    fun getAllCategories(): LiveData<List<Category>>

    @Query("DELETE FROM category_table WHERE parentSetId = :questionSetId")
    suspend fun deleteAllCategoriesFromQuestionSet(questionSetId: Int)
}