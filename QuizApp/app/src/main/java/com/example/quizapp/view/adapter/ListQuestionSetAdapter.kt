package com.example.quizapp.view.adapter

import android.content.Context
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
import com.example.quizapp.model.QuestionSet
import com.example.quizapp.view.fragment.ListQuestionSetFragmentDirections

// spróbuj wrzucić tutal viewmodel jak livedata nie wystarczy
class ListQuestionSetAdapter(var data: LiveData<List<QuestionSet>>): RecyclerView.Adapter<ListQuestionSetAdapter.Holder>() {
    
    class Holder(view: View, data: LiveData<List<QuestionSet>>): RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(
                R.layout.recyclerview_question_set,
                parent,
                false
        ) as View

        return Holder(view, data)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val currentItem = data.value?.get(position)
        holder.itemView.findViewById<TextView>(R.id.questionSetItemTextView).text = currentItem?.questionSetName

        holder.itemView.findViewById<ImageButton>(R.id.editQuestionSetStartButton).setOnClickListener {
            val action = currentItem?.let { it1 -> ListQuestionSetFragmentDirections.actionListQuestionSetFragmentToEditQuestionSetFragment(it1) }
            if (action != null) {
                holder.itemView.findNavController().navigate(action)
            }
        }

        holder.itemView.findViewById<CardView>(R.id.questionSetItemCardView).setOnClickListener {
            val action = currentItem?.let { it1 -> ListQuestionSetFragmentDirections.actionListQuestionSetFragmentToListCategoryFragment(it1.questionSetId) }
            if (action != null) {
                holder.itemView.findNavController().navigate(action)
            }
        }
    }

    override fun getItemCount(): Int {
        return data.value?.size?:0
    }

}