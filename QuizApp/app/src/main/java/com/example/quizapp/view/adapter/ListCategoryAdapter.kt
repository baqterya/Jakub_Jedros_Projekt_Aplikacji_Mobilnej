package com.example.quizapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.R
import com.example.quizapp.model.Category
import com.example.quizapp.view.fragment.ListCategoryFragmentDirections
import com.example.quizapp.view.fragment.ListQuestionSetFragmentDirections

class ListCategoryAdapter(private var data: LiveData<List<Category>>): RecyclerView.Adapter<ListCategoryAdapter.Holder>() {

    class Holder(view: View, data: LiveData<List<Category>>): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(
                R.layout.recyclerview_category,
                parent,
                false
        ) as View

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

        holder.itemView.findViewById<ImageButton>(R.id.playCategoryButton).setOnClickListener {
            val action = currentItem?.let { it1 -> ListCategoryFragmentDirections.actionListCategoryFragmentToQuizPickerFragment(0, it1.categoryId) }
            if (action != null) {
                holder.itemView.findNavController().navigate(action)
            }
        }
    }

    override fun getItemCount(): Int {
        return data.value?.size?:0
    }
}
