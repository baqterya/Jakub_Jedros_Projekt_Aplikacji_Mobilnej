package com.example.quizapp.view.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentEditCategoryBinding
import com.example.quizapp.model.Category
import com.example.quizapp.viewmodel.CategoryViewModel
import com.example.quizapp.viewmodel.QuestionAndAnswerViewModel


class EditCategoryFragment : Fragment() {
    private lateinit var binding: FragmentEditCategoryBinding
    private lateinit var mCategoryViewModel: CategoryViewModel
    private lateinit var mQuestionAndAnswerViewModel: QuestionAndAnswerViewModel

    private val args by navArgs<EditCategoryFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditCategoryBinding.inflate(inflater, container, false)
        mCategoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        mQuestionAndAnswerViewModel = ViewModelProvider(this).get(QuestionAndAnswerViewModel::class.java)

        binding.editCategoryEditText.setText(args.currentCategory.categoryName)

        binding.editCategoryButton.setOnClickListener {
            editCategory()
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuDelete) {
            deleteCategory();
        }
        return super.onOptionsItemSelected(item)
    }

    private fun editCategory() {
        val name = binding.editCategoryEditText.text.toString()
        if (inputCheck(name)) {
            val editedCategory = Category(args.currentCategory.categoryId, name, args.currentCategory.parentSetId)
            mCategoryViewModel.editCategory(editedCategory)
            Toast.makeText(requireContext(), "Update successful", Toast.LENGTH_SHORT).show()
            val action = EditCategoryFragmentDirections.actionEditCategoryFragmentToListCategoryFragment(args.currentCategory.parentSetId)
            findNavController().navigate(action)
        } else {
            Toast.makeText(requireContext(), "Please enter a name", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteCategory() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") {_, _ ->
            mCategoryViewModel.deleteCategory(args.currentCategory)
            mQuestionAndAnswerViewModel.deleteQuestionsAndAnswersFromCategory(args.currentCategory.categoryId)
            Toast.makeText(requireContext(), "Successfully removed ${args.currentCategory.categoryName}", Toast.LENGTH_SHORT).show()
            val action = EditCategoryFragmentDirections.actionEditCategoryFragmentToListCategoryFragment(args.currentCategory.parentSetId)
            findNavController().navigate(action)
        }
        builder.setNegativeButton("No") {_, _ -> }
        builder.setTitle("Delete ${args.currentCategory.categoryName}?")
        builder.setMessage("Are you sure you want to delete?")
        builder.create().show()
    }

    private fun inputCheck(name: String): Boolean {
        return !(TextUtils.isEmpty(name))
    }
}