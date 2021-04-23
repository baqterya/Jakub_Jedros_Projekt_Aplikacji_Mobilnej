package com.example.quizapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.R
import com.example.quizapp.model.Category

class ListCategoryAdapter : RecyclerView.Adapter<ListCategoryAdapter.CategoryViewHolder>() {
    private var categoryList = emptyList<Category>()

    class CategoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.recyclerview_category,
            parent,
            false)
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val currentItem = categoryList[position]
        holder.itemView.findViewById<TextView>(R.id.categoryItemTextView).text = currentItem.categoryName
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    fun setData(categories: List<Category>) {
        this.categoryList = categories
    }

}