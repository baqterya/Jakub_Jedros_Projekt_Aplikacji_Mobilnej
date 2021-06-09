package com.example.quizapp.view.adapter

import android.app.AlertDialog
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.example.quizapp.R
import com.example.quizapp.model.QuestionSet
import com.example.quizapp.view.fragment.ListQuestionSetFragmentDirections
import com.example.quizapp.viewmodel.CategoryViewModel
import com.example.quizapp.viewmodel.QuestionAndAnswerViewModel
import com.example.quizapp.viewmodel.QuestionSetViewModel

class ListQuestionSetAdapter(
    private var data: LiveData<List<QuestionSet>>, private var questionSetViewModel: QuestionSetViewModel,
    private var categoryViewModel: CategoryViewModel, private var questionAndAnswerViewModel: QuestionAndAnswerViewModel
    ): RecyclerView.Adapter<ListQuestionSetAdapter.Holder>() {
    
    class Holder(view: View, data: LiveData<List<QuestionSet>>): RecyclerView.ViewHolder(view)

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

        holder.itemView.findViewById<CardView>(R.id.questionSetItemCardView).setOnLongClickListener {
            if (currentItem != null) {
                editQuestionSetDialog(holder, currentItem)
            }
            false
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

    private fun editQuestionSetDialog(holder: Holder, questionSet: QuestionSet) {
        val context = holder.itemView.context

        val dialog = MaterialDialog(context)
            .noAutoDismiss()
            .customView(R.layout.dialog_edit_question_set)
        dialog.findViewById<EditText>(R.id.dialogEditQuestionSetEditText).setText(questionSet.questionSetName)

        dialog.findViewById<Button>(R.id.dialogEditQuestionSetButton).setOnClickListener {
            val name = dialog.findViewById<EditText>(R.id.dialogEditQuestionSetEditText).text.toString()
            if (inputCheck(name)) {
                val editedQuestionSet = QuestionSet(questionSet.questionSetId, name)
                questionSetViewModel.editQuestionSet(editedQuestionSet)
                dialog.dismiss()
                Toast.makeText(context, "Update successful", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Please enter a name", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.findViewById<Button>(R.id.dialogEditQuestionSetPlayButton).setOnClickListener {
            val action = ListQuestionSetFragmentDirections.actionListQuestionSetFragmentToQuizPickerFragment(questionSet.questionSetId)
            dialog.dismiss()
            holder.itemView.findNavController().navigate(action)
        }

        dialog.findViewById<ImageButton>(R.id.dialogEditQuestionSetDeleteButton).setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setPositiveButton("Yes") {_, _ ->
                questionSetViewModel.deleteQuestionSet(questionSet)
                categoryViewModel.deleteAllCategoriesFromQuestionSet(questionSet.questionSetId)
                questionAndAnswerViewModel.deleteQuestionsAndAnswersFromQuestionSet(questionSet.questionSetId)
                Toast.makeText(context, "Successfully removed ${questionSet.questionSetName}", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            builder.setNegativeButton("No") {_, _ -> }
            builder.setTitle("Delete ${questionSet.questionSetName}?")
            builder.setMessage("Are you sure you want to delete the set and all it's content?")
            builder.create().show()
        }

        dialog.show()
    }

    private fun inputCheck(name: String): Boolean {
        return !(TextUtils.isEmpty(name))
    }
}