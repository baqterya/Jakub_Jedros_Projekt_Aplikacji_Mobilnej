package com.example.quizapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "category_table")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val categoryId: Int,

    val categoryName: String,
    val parentSetId: Int
): Parcelable