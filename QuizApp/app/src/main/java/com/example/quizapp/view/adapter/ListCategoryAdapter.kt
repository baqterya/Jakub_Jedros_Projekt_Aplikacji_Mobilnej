package com.example.quizapp.view.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.R
import com.example.quizapp.model.Category
import com.example.quizapp.view.fragment.ListCategoryFragmentDirections

class ListCategoryAdapter(var data: LiveData<List<Category>>): RecyclerView.Adapter<ListCategoryAdapter.Holder>() {
    lateinit var context: Context

    class Holder(view: View, data: LiveData<List<Category>>): RecyclerView.ViewHolder(view) {
        init {
            //
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(
                R.layout.recyclerview_category,
                parent,
                false
        ) as View
        Toast.makeText(context, data.value?.get(0)?.categoryName.toString(), Toast.LENGTH_SHORT).show()
        return Holder(view, data)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val currentItem = data.value?.get(position)
        holder.itemView.findViewById<TextView>(R.id.categoryItemTextView).text = currentItem?.categoryName

        holder.itemView.findViewById<ImageButton>(R.id.editCategoryStartButton).setOnClickListener {
            val action = currentItem?.let { it1 -> ListCategoryFragmentDirections.actionListCategoryFragmentToEditCategoryFragment(it1) }
            if (action != null) {
                holder.itemView.findNavController().navigate(action)
            }
        }

        holder.itemView.findViewById<CardView>(R.id.categoryItemCardView).setOnClickListener {
            val action = currentItem?.let {
                it1 -> ListCategoryFragmentDirections.actionListCategoryFragmentToListQuestionAndAnswerFragment(it1.categoryId, currentItem.parentSetId)
            }
            if (action != null) {
                holder.itemView.findNavController().navigate(action)
            }
        }
    }

    override fun getItemCount(): Int {
        return data.value?.size?:0
    }
}

//class ListCategoryAdapter : RecyclerView.Adapter<ListCategoryAdapter.CategoryViewHolder>() {
//    private var categoryList = emptyList<Category>()
//
//    class CategoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
//
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
//        return CategoryViewHolder(LayoutInflater.from(parent.context).inflate(
//            R.layout.recyclerview_category,
//            parent,
//            false
//        ))
//    }
//
//    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
//        val currentItem = categoryList[position]
//        holder.itemView.findViewById<TextView>(R.id.categoryItemTextView).text = currentItem.categoryName
//
//        holder.itemView.findViewById<ImageButton>(R.id.editCategoryStartButton).setOnClickListener {
//            val action = ListCategoryFragmentDirections.actionListCategoryFragmentToEditCategoryFragment(currentItem)
//            holder.itemView.findNavController().navigate(action)
//        }
//
//        holder.itemView.findViewById<CardView>(R.id.categoryItemCardView).setOnClickListener {
//            val action = ListCategoryFragmentDirections.actionListCategoryFragmentToListQuestionAndAnswerFragment(currentItem.categoryId, currentItem.parentSetId)
//            holder.itemView.findNavController().navigate(action)
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return categoryList.size
//    }
//
//    fun setData(categories: List<Category>) {
//        this.categoryList = categories
//    }
//
//}