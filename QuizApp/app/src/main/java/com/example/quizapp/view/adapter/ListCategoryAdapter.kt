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
import com.example.quizapp.model.Category
import com.example.quizapp.view.fragment.ListCategoryFragmentDirections
import com.example.quizapp.viewmodel.CategoryViewModel
import com.example.quizapp.viewmodel.QuestionAndAnswerViewModel

class ListCategoryAdapter(
    private var data: LiveData<List<Category>>, private var categoryViewModel: CategoryViewModel,
    private var questionAndAnswerViewModel: QuestionAndAnswerViewModel
    ): RecyclerView.Adapter<ListCategoryAdapter.Holder>() {

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

        holder.itemView.findViewById<CardView>(R.id.categoryItemCardView).setOnLongClickListener {
            if (currentItem != null) {
                editCategoryDialog(holder, currentItem)
            }
            false
        }

        holder.itemView.findViewById<CardView>(R.id.categoryItemCardView).setOnClickListener {
            val action = currentItem?.let {
                    it1 -> ListCategoryFragmentDirections.actionListCategoryFragmentToListQuestionAndAnswerFragment(it1.categoryId, it1.parentSetId)
            }
            if (action != null) {
                holder.itemView.findNavController().navigate(action)
            }
        }
    }

    override fun getItemCount(): Int {
        return data.value?.size?:0
    }

    private fun editCategoryDialog(holder: Holder, category: Category) {
        val context = holder.itemView.context

        val dialog = MaterialDialog(context)
            .noAutoDismiss()
            .customView(R.layout.dialog_edit_category)
        dialog.findViewById<EditText>(R.id.dialogEditCategoryEditText).setText(category.categoryName)

        dialog.findViewById<Button>(R.id.dialogEditCategoryButton).setOnClickListener {
            val name = dialog.findViewById<EditText>(R.id.dialogEditCategoryEditText).text.toString()
            if (inputCheck(name)) {
                val editedCategory = Category(category.categoryId, name, category.parentSetId)
                categoryViewModel.editCategory(editedCategory)
                dialog.dismiss()
                Toast.makeText(context, "Update successful", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Please enter a name", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.findViewById<Button>(R.id.dialogEditCategoryPlayButton).setOnClickListener {
            val action = ListCategoryFragmentDirections.actionListCategoryFragmentToQuizPickerFragment(0, category.categoryId)
            dialog.dismiss()
            holder.itemView.findNavController().navigate(action)
        }

        dialog.findViewById<ImageButton>(R.id.dialogEditCategoryDeleteButton).setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setPositiveButton("Yes") {_, _ ->
                categoryViewModel.deleteCategory(category)
                questionAndAnswerViewModel.deleteQuestionsAndAnswersFromCategory(category.categoryId)
                Toast.makeText(context, "Successfully removed ${category.categoryName}", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            builder.setNegativeButton("No") {_, _ -> }
            builder.setTitle("Delete ${category.categoryName}?")
            builder.setMessage("Are you sure you want to delete?")
            builder.create().show()
        }

        dialog.show()
    }

    private fun inputCheck(name: String): Boolean {
        return !(TextUtils.isEmpty(name))
    }
}
